package Project101.Auth.repository;

import Project101.Auth.resource.AuthorizableResource;

import java.util.HashMap;
import java.util.Map;

public class MockResourceRepository implements ResourceRepository {
    private final Map<String, AuthorizableResource> resources = new HashMap<>();
    public void save(AuthorizableResource resource) { resources.put(resource.getResourceId(), resource); }
    @Override
    public AuthorizableResource findById(String id) { return resources.get(id); }
}