package Project101.Auth;

import Project101.Auth.model.*;
import Project101.Auth.policy.OwnershipPolicy;
import Project101.Auth.policy.Policy;
import Project101.Auth.repository.MockAuthorizationRepository;
import Project101.Auth.repository.MockResourceRepository;
import Project101.Auth.resource.File;
import Project101.Auth.resource.Folder;
import Project101.Auth.service.AuthorizationService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthorizationServiceTest {

    private MockAuthorizationRepository authRepo;
    private MockResourceRepository resourceRepo;
    private AuthorizationService authService;

    private int aliceId = 1;
    private int bobId = 2;
    private Role editorRole;
    private Role viewerRole;
    private Permission viewPerm;
    private Permission editPerm;

    @Before
    public void setUp() {
        authRepo = new MockAuthorizationRepository();
        resourceRepo = new MockResourceRepository();
        authService = new AuthorizationService(authRepo, resourceRepo);

        editorRole = new Role("role-editor", "Editor");
        viewerRole = new Role("role-viewer", "Viewer");

        viewPerm = new Permission("document:view");
        editPerm = new Permission("document:edit");

        // 2. Assign Roles to Users
        authService.assignRole(aliceId, editorRole);
        authService.assignRole(bobId, viewerRole);
    }

    @Test
    public void testDirectInstancePermission() {
        // Grant viewerRole VIEW permission on specific file
        File doc = new File("doc-1", "DOCUMENT", "1", null);

        File doc2 = new File("doc-02","DOCUMENT","1",null);
        resourceRepo.save(doc);
        resourceRepo.save(doc2);

        
        authService.grantAccess(viewerRole, viewPerm, Scope.ofInstance("doc-1"));
        authService.grantAccess(viewerRole, viewPerm, Scope.global());

        // Bob (Viewer) should be able to view
//        assertTrue(authService.isAuthorized(bobId, doc, "document:view"));
        assertTrue(authService.isAuthorized(bobId, doc2, "document:view"));
        
        // Bob should NOT be able to edit
        assertFalse(authService.isAuthorized(bobId, doc, "document:edit"));
    }

    @Test
    public void testTypePermission() {
        // Grant editorRole EDIT permission on all DOCUMENT types
        File doc = new File("doc-2", "DOCUMENT", "1", null);
        resourceRepo.save(doc);

        authService.grantAccess(editorRole, editPerm, Scope.ofType("DOCUMENT"));

        // Alice (Editor) should be able to edit
        assertTrue(authService.isAuthorized(aliceId, doc, "document:edit"));
    }

    @Test
    public void testGlobalPermission() {
        // Grant editorRole global VIEW permission
        File doc = new File("doc-3", "DOCUMENT", "1", null);
        resourceRepo.save(doc);

        authService.grantAccess(editorRole, viewPerm, Scope.global());

        // Alice (Editor) should be able to view
        assertTrue(authService.isAuthorized(aliceId, doc, "document:view"));
    }

    @Test
    public void testInheritedPermission() {
        // Folder -> File structure
        Folder folder = new Folder("folder-1", "FOLDER", "1", null);
        File doc = new File("doc-4", "DOCUMENT", "1", "folder-1");
        
        resourceRepo.save(folder);
        resourceRepo.save(doc);

        // Grant viewerRole VIEW permission on the FOLDER
        authService.grantAccess(viewerRole, viewPerm, Scope.ofInstance("folder-1"));

        // Bob (Viewer) should be able to view the doc inside the folder
        assertTrue(authService.isAuthorized(bobId, doc, "document:view"));
    }

    @Test
    public void testABACPolicy_Ownership() {
        // Setup Ownership Policy for EDIT permission
        Policy ownershipPolicy = new OwnershipPolicy();
        authService.addPolicy(editPerm, ownershipPolicy);

        // Grant editorRole EDIT permission on DOCUMENT type
        authService.grantAccess(editorRole, editPerm, Scope.ofType("DOCUMENT"));

        // Alice owns doc-5
        File alicesDoc = new File("doc-5", "DOCUMENT", "1", null);
        resourceRepo.save(alicesDoc);

        // Bob owns doc-6
        File bobsDoc = new File("doc-6", "DOCUMENT", "2", null);
        resourceRepo.save(bobsDoc);

        // Alice (Editor) tries to edit her own doc -> Should PASS (RBAC + ABAC)
        assertTrue("Alice should be able to edit her own doc", 
                authService.isAuthorized(aliceId, alicesDoc, "document:edit"));

        // Alice (Editor) tries to edit Bob's doc -> Should FAIL (RBAC passes, ABAC fails)
        assertFalse("Alice should NOT be able to edit Bob's doc due to ownership policy", 
                authService.isAuthorized(aliceId, bobsDoc, "document:edit"));
    }

    @Test
    public void testNoRoles() {
        int noRoleUserId = 3;
        File doc = new File("doc-7", "DOCUMENT", "1", null);
        resourceRepo.save(doc);

        assertFalse(authService.isAuthorized(noRoleUserId, doc, "document:view"));
    }
    
    @Test
    public void testNoMatchingGrant() {
        File doc = new File("doc-8", "DOCUMENT", "1", null);
        resourceRepo.save(doc);
        
        // Alice has Editor role, but no grants set up for this test case yet
        assertFalse(authService.isAuthorized(aliceId, doc, "document:view"));
    }

    @Test
    public void testRevokeAccess() {
        File doc = new File("doc-9", "DOCUMENT", "1", null);
        resourceRepo.save(doc);

        authService.grantAccess(editorRole, viewPerm, Scope.global());
        assertTrue(authService.isAuthorized(aliceId, doc, "document:view"));

        authService.revokeAccess(editorRole, viewPerm, Scope.global());
        assertFalse(authService.isAuthorized(aliceId, doc, "document:view"));
    }
}
