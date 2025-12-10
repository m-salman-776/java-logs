package Project101.Auth.policy;

import Project101.Auth.model.Permission;
import Project101.Auth.model.User;
import Project101.Auth.resource.AuthorizableResource;

public class OwnershipPolicy implements Policy {
    @Override
    public boolean evaluate(User user, AuthorizableResource resource, Permission permission) {
        System.out.println("  - Evaluating OwnershipPolicy...");
        boolean isOwner = user.getId().equals(resource.getOwnerId());
        if (!isOwner) {
            System.out.println("    - Ownership check FAILED. User '" + user.getName() + "' is not the owner of resource '" + resource.getResourceId() + "'.");
        } else {
            System.out.println("    - Ownership check PASSED.");
        }
        return isOwner;
    }
}
