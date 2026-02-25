package Project101.Auth.policy;

import Project101.Auth.resource.AuthorizableResource;

public class UserStatusPolicy implements Policy {
    @Override
    public boolean evaluate(int userId, AuthorizableResource resource) {
        return false;
    }
}
