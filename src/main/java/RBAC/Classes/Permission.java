package RBAC.Classes;

import lombok.Getter;

@Getter
class Permission {
    private String name;
    private Resource resource;
    public Permission(String name, Resource resource) {
        this.name = name;
        this.resource = resource;
    }
    public boolean isApplicableTo(Resource otherResource) {

        // Check if the permission is applicable to the given resource or its parent hierarchy
        if (resource.equals(otherResource)) {
            return true;
        }
        if (otherResource instanceof Folder) {
            Folder folder = (Folder) otherResource;
            for (Resource content : folder.getContents()) {
                if (isApplicableTo(content)) {
                    return true;
                }
            }
        }
        return false;
    }
}
