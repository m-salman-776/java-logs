package Project101.Auth.model;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Permission {
    private final String name; // e.g., "document:edit"

    public Permission(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
