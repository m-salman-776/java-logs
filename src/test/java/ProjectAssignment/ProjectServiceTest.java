package ProjectAssignment;

import Project101.ProjectAssignment.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ProjectServiceTest {

    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService();
    }

    @Test
    void testCodeflow() {
        // 1. Onboard Users
        projectService.addUser("Lead lead-01", UserRole.LEAD); // ID 0
        projectService.addUser("Dev Dev-01", UserRole.DEVELOPER); // ID 1

        // 2. Post Project
        boolean posted = projectService.postProject(0, Category.BACKEND);
        assertTrue(posted, "Lead should be able to post project");

        List<Project> projects = projectService.viewAllProject();
        assertEquals(1, projects.size());
        int projectId = projects.get(0).id;

        // 3. Request Project
        projectService.requestProject(1, projectId);
        assertEquals(ProjectStatus.REQUESTED, projectService.getProjectStatus(projectId));

        // 4. Approve Request
        boolean approved = projectService.approveRequest(0, projectId, 1);
        assertTrue(approved, "Lead should be able to approve request");
        assertEquals(ProjectStatus.ASSIGNED, projectService.getProjectStatus(projectId));

        // 5. Complete Project
        boolean updated = projectService.updateProjectStatus(1, projectId, ProjectStatus.COMPLETED);
        assertTrue(updated);
        assertEquals(ProjectStatus.COMPLETED, projectService.getProjectStatus(projectId));

        // 6. Verify Developer is Free (by assigning another project)
        projectService.postProject(0, Category.FRONTEND); // ID 1
        projectService.requestProject(1, 1);
        boolean approvedAgain = projectService.approveRequest(0, 1, 1);
        assertTrue(approvedAgain, "Developer should be free after project completion");
    }

    @Test
    void testConcurrencyOneDevTwoProjects() throws InterruptedException {

        projectService.addUser("Lead1", UserRole.LEAD); // 0
        projectService.addUser("Lead2", UserRole.LEAD); // 1
        projectService.addUser("Dev1", UserRole.DEVELOPER); // 2

        projectService.postProject(0, Category.BACKEND); // Proj 0
        projectService.postProject(1, Category.FRONTEND); // Proj 1

        projectService.requestProject(2, 0);
        projectService.requestProject(2, 1);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);
        boolean[] results = new boolean[2];

        executor.submit(() -> {
            try {
                results[0] = projectService.approveRequest(0, 0, 2);
            } finally {
                latch.countDown();
            }
        });

        executor.submit(() -> {
            try {
                results[1] = projectService.approveRequest(1, 1, 2);
            } finally {
                latch.countDown();
            }
        });

        latch.await(5, TimeUnit.SECONDS);
        executor.shutdown();

        // XOR: Exactly one should be true
        assertTrue(results[0] ^ results[1], "Only one project should be assigned to the developer due to concurrency");
    }

    @Test
    void testCancelAssignedProjectFails() {
        projectService.addUser("Lead1", UserRole.LEAD); // 0
        projectService.addUser("Dev1", UserRole.DEVELOPER); // 1

        projectService.postProject(0, Category.DEVOPS); // 0
        projectService.requestProject(1, 0);
        projectService.approveRequest(0, 0, 1);

        assertEquals(ProjectStatus.ASSIGNED, projectService.getProjectStatus(0));

        // Cancel
        boolean cancelled = projectService.cancelProject(0, 0);
        assertFalse(cancelled, "Should not be able to cancel assigned project");
        assertEquals(ProjectStatus.ASSIGNED, projectService.getProjectStatus(0));
    }

    @Test
    void testAutoCancellation() throws InterruptedException {
        projectService.addUser("Lead1", UserRole.LEAD);
        projectService.postProject(0, Category.FRONTEND); // 0

        assertEquals(ProjectStatus.OPEN, projectService.getProjectStatus(0));

        // Wait for auto-cancellation (10s configured in code)
        // Adding buffer to ensure scheduler has run
        Thread.sleep(10500);

        assertEquals(ProjectStatus.CANCELED, projectService.getProjectStatus(0), "Project should be auto-cancelled after timeout");
    }
}