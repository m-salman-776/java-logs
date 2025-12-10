package Project101.Auth.repository;

import Project101.Auth.resource.AuthorizableResource;

public interface ResourceRepository {
    AuthorizableResource findById(String resourceId);
}
