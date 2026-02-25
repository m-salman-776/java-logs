package Project101.Auth.policy;

import Project101.Auth.resource.AuthorizableResource;

public class OwnershipPolicy implements Policy {
    @Override
    public boolean evaluate(int userId, AuthorizableResource resource) {
        System.out.println("  - Evaluating OwnershipPolicy...");
        boolean isOwner = String.valueOf(userId).equals(resource.getOwnerId());
        if (!isOwner) {
            System.out.println("    - Ownership check FAILED. User '" + userId + "' is not the owner of resource '" + resource.getResourceId() + "'.");
        } else {
            System.out.println("    - Ownership check PASSED.");
        }
        return isOwner;
    }
}
