package RBAC.Classes;

import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
class Folder extends Resource {
    private final Set<Resource> contents;
    public Folder(String name) {
        super(name);
        this.contents = new HashSet<>();
    }
    public void addResource(Resource resource) {
        contents.add(resource);
    }
    public void removeResource(Resource resource) {
        contents.remove(resource);
    }
}