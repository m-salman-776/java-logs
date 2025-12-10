package Project101.Auth.policy;


import Project101.Auth.model.Permission;
import Project101.Auth.model.User;
import Project101.Auth.resource.AuthorizableResource;

public class UserStatusPolicy implements Policy {
    @Override
    public boolean evaluate(User user, AuthorizableResource resource, Permission permission) {
        return false;
    }
}
