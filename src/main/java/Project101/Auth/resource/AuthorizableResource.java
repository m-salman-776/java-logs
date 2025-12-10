package Project101.Auth.resource;

public interface AuthorizableResource {
    String getResourceId();
    String getResourceType(); // e.g., "DOCUMENT", "FOLDER"
    String getOwnerId();
    String getAttribute(String key);
    String getParentId(); // Can be null if it's a top-level resource
}
