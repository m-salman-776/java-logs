package Project101.Auth.resource;

public class Folder implements AuthorizableResource {
    private final String id;
    private final String type;
    private final String ownerId;
    private final String parentId;

    public Folder(String id, String type, String ownerId, String parentId) {
        this.id = id;
        this.type = type;
        this.ownerId = ownerId;
        this.parentId = parentId;
    }

    @Override public String getResourceId() { return id; }
    @Override public String getResourceType() { return type; }
    @Override public String getOwnerId() { return ownerId; }
    @Override public String getAttribute(String key) { return null; }
    @Override public String getParentId() { return parentId; }
}