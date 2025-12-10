package Project101.Auth.model;

import lombok.Getter;

import java.util.Objects;

/**
 * Represents the scope of a permission grant.
 * A scope can be GLOBAL, a specific RESOURCE_TYPE, or a specific RESOURCE_INSTANCE.
 */
@Getter
public class Scope {
    public enum Type {
        GLOBAL,
        RESOURCE_TYPE,
        RESOURCE_INSTANCE
    }

    private final Type type;
    private final String value; // e.g., "DOCUMENT" for RESOURCE_TYPE, or "doc-123" for RESOURCE_INSTANCE

    public Scope(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public static Scope global() {
        return new Scope(Type.GLOBAL, "*");
    }

    public static Scope ofType(String resourceType) {
        return new Scope(Type.RESOURCE_TYPE, resourceType);
    }

    public static Scope ofInstance(String resourceId) {
        return new Scope(Type.RESOURCE_INSTANCE, resourceId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scope scope = (Scope) o;
        return type == scope.type && Objects.equals(value, scope.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }
}
