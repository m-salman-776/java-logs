package Project101.Auth.policy;
import Project101.Auth.model.Permission;
import Project101.Auth.model.User;
import Project101.Auth.resource.AuthorizableResource;

public class ViewPolicy implements Policy {
    @Override
    public boolean evaluate(User user, AuthorizableResource resource, Permission permission) {
        System.out.println("Evaluating ViewPolicy...");

        String status = resource.getAttribute("status");

        // If the document is published, anyone with the 'document:view' permission can view it.
        if ("PUBLISHED".equals(status)) {
            System.out.println("  - Resource status is PUBLISHED. Access granted by this policy.");
            return true;
        }

        // If the document is a DRAFT (or any other status), only the owner can view it.
        System.out.println("  - Resource status is DRAFT. Checking ownership...");
        boolean isOwner = user.getId().equals(resource.getOwnerId());
        System.out.println("  - Is user the owner? " + isOwner);
        return isOwner;
    }
}
