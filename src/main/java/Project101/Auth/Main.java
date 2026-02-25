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

        int aliceId = 1;
        int bobId = 2;

        Folder projectX = new Folder("folder-proj-x","FOLDER","1",null);
        File designDoc = new File("design-doc","DOCUMENT","2","folder-proj-x");

        resourceRepo.save(projectX);
        resourceRepo.save(designDoc);

        Role viewRole = new Role("view-role-01","View");
        Role editRole = new Role("edit-role-01","Edit");

        Permission documentViewPermission = new Permission("DOCUMENT:VIEW");
        Permission documentEditPermission = new Permission("DOCUMENT:EDIT");

        AuthorizationService authorizationService = new AuthorizationService(authRepo,resourceRepo);

        authorizationService.grantAccess(viewRole,documentViewPermission,Scope.ofType("DOCUMENT"));
        authorizationService.grantAccess(editRole,documentEditPermission,Scope.ofType("DOCUMENT"));
        authorizationService.grantAccess(editRole,documentViewPermission,Scope.ofInstance("folder-proj-x"));

        authorizationService.assignRole(aliceId,editRole);
        authorizationService.assignRole(bobId,viewRole);

        authorizationService.addPolicy(documentEditPermission,new OwnershipPolicy());

        Boolean allowed = authorizationService.isAuthorized(bobId,designDoc,"DOCUMENT:VIEW");
        System.out.println(allowed);

    }
    public static void main(String[] args) {
        check();
    }
}
