package RBAC.Classes;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
class User {
    private String username;
    private Set<Role> roles;
    public User(String username) {
        this.username = username;
        this.roles = new HashSet<>();
    }
    public void addRole(Role role) {
        roles.add(role);
    }
    public void removeRole(Role role) {
        roles.remove(role);
    }
}
