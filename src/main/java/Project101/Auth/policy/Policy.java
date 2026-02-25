package Project101.Auth.policy;

import Project101.Auth.resource.AuthorizableResource;

public interface Policy {
    boolean evaluate(int userId, AuthorizableResource resource);
}
