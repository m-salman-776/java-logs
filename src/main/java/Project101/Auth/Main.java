package Project101.Auth;

import Project101.Auth.model.*;
import Project101.Auth.policy.OwnershipPolicy;
import Project101.Auth.policy.Policy;
import Project101.Auth.repository.MockAuthorizationRepository;
import Project101.Auth.resource.File;
import Project101.Auth.resource.Folder;
import Project101.Auth.repository.MockResourceRepository;
import Project101.Auth.service.AuthorizationService;
public class Main {
    public static void check(){
        MockAuthorizationRepository authRepo = new MockAuthorizationRepository();
        MockResourceRepository resourceRepo = new MockResourceRepository();



        User alice = new User("user-01","Alice");
        User bob = new User("user-02","Bob");

        Folder projectX = new Folder("folder-proj-x","FOLDER","user-01",null);
        File designDoc = new File("design-doc","DOCUMENT","user-02","folder-proj-x");

        resourceRepo.save(projectX);
        resourceRepo.save(designDoc);

        Role viewRole = new Role("view-role-01","View");
        Role editRole = new Role("edit-role-01","Edit");

        Permission documentViewPermission = new Permission("DOCUMENT:VIEW");
        Permission documentEditPermission = new Permission("DOCUMENT:EDIT");


        authRepo.addGrant(viewRole,documentViewPermission,Scope.ofType("DOCUMENT"));
        authRepo.addGrant(editRole,documentEditPermission,Scope.ofType("DOCUMENT"));
        authRepo.addGrant(editRole,documentViewPermission,Scope.ofInstance("folder-proj-x"));

        authRepo.addUserRole(alice,editRole);
        authRepo.addUserRole(bob,viewRole);

        authRepo.addPermissionPolicy(documentEditPermission,new OwnershipPolicy());

        AuthorizationService authorizationService = new AuthorizationService(authRepo,resourceRepo);
        Boolean allowed = authorizationService.isAuthorized(bob,designDoc,"DOCUMENT:VIEW");
        System.out.println(allowed);

    }
    public static void main(String[] args) {
        check();

        // --- Setup Phase ---
        MockAuthorizationRepository authRepo = new MockAuthorizationRepository();
        MockResourceRepository resourceRepo = new MockResourceRepository();

        // 1. Define Users, Roles, Permissions
        User alice = new User("user-1", "Alice");
        User bob = new User("user-2", "Bob");

        Role editorRole = new Role("role-editor", "Editor");
        Role viewerRole = new Role("role-viewer", "Viewer");

        Permission viewPerm = new Permission("document:view");
        Permission editPerm = new Permission("document:edit");

        // 2. Assign Roles to Users
        // 2. Define Policies
        Policy ownershipPolicy = new OwnershipPolicy();
        // 3. Assign Roles to Users

        authRepo.addUserRole(alice, editorRole);
        authRepo.addUserRole(bob, editorRole); // Let's make Bob an Editor too for a better test

        // 3. Define Permission Grants (The Scoped RBAC rules)
        // 4. Define Permission Grants (The Scoped RBAC rules)
        // The "Editor" role can VIEW documents in "folder-proj-x".
        authRepo.addGrant(editorRole, editPerm, Scope.ofInstance("folder-proj-x"));
        authRepo.addGrant(viewerRole, viewPerm, Scope.ofInstance("folder-proj-x"));
        // The "Editor" role can EDIT ALL documents globally.
        // The "Editor" role can EDIT ALL documents.
        authRepo.addGrant(editorRole, editPerm, Scope.ofType("DOCUMENT"));
        // BLOG_POST STRATEGY_DOC / DOCUMENT

        // 4. Create Resources with Hierarchy
        Folder projectFolder = new Folder("folder-proj-x", "FOLDER", "user-1", null);
        File designDoc = new File("doc-101", "DOCUMENT", "user-2", "folder-proj-x");
        File unrelatedDoc = new File("doc-102", "DOCUMENT", "user-2", "folder-other");
        // 5. *** ASSOCIATE POLICY WITH PERMISSION (The ABAC "Deadbolt") ***
        // Any time someone tries to use 'document:edit', they must also pass the OwnershipPolicy.
        authRepo.addPermissionPolicy(editPerm, ownershipPolicy);

        resourceRepo.save(projectFolder);
        resourceRepo.save(designDoc);
        resourceRepo.save(unrelatedDoc);
        // 6. Create Resources
        File alicesDoc = new File("doc-101", "DOCUMENT", "user-1", null); // Alice owns this
        File bobsDoc = new File("doc-102", "DOCUMENT", "user-2", null);   // Bob owns this

        // 5. Instantiate the service
        resourceRepo.save(alicesDoc);
        resourceRepo.save(bobsDoc);
        // 7. Instantiate the service
        AuthorizationService authService = new AuthorizationService(authRepo, resourceRepo);

        // --- Scenarios ---

        // Scenario 1: Alice tries to VIEW the design document.
        // Expected: GRANTED. She has no direct permission on the doc, but inherits the 'document:view'
        // permission from its parent folder, 'folder-proj-x'.
        boolean x= authService.isAuthorized(alice, designDoc, "document:view");

        // Scenario 2: Alice tries to EDIT the design document.
        // Expected: GRANTED. She has no specific grant on the doc or its parent, but she has a
        // grant for the RESOURCE_TYPE "DOCUMENT".
        authService.isAuthorized(alice, designDoc, "document:edit");
        // Scenario 1: Alice tries to EDIT her OWN document.
        // Expected: GRANTED.
        // - RBAC Check: Alice is an Editor, and Editors can edit documents (Scope: RESOURCE_TYPE). PASSES.
        // - ABAC Check: The OwnershipPolicy is triggered. Alice is the owner. PASSES.
        authService.isAuthorized(alice, alicesDoc, "document:edit");

        // Scenario 3: Alice tries to VIEW the unrelated document.
        // Expected: DENIED. She doesn't have a grant on the instance, its type, or its parent.
        // The grant she has is only for "folder-proj-x".
        authService.isAuthorized(alice, unrelatedDoc, "document:view");
        // Scenario 2: Alice (an Editor) tries to EDIT BOB's document.
        // Expected: DENIED.
        // - RBAC Check: Alice is an Editor, and Editors can edit documents. PASSES.
        // - ABAC Check: The OwnershipPolicy is triggered. Alice is NOT the owner of Bob's doc. FAILS.
        authService.isAuthorized(alice, bobsDoc, "document:edit");
    }
}
