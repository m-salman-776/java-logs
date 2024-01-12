package RBAC.Classes;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
class Role {
    private String name;
    private Set<Permission> permissions;
    public Role(String name) {
        this.name = name;
        this.permissions = new HashSet<>();
    }
    public void addPermission(Permission permission) {
        permissions.add(permission);
    }
    public void removePermission(Permission permission) {
        permissions.remove(permission);
    }
}
