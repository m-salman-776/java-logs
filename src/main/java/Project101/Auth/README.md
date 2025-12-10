## AuthV2: Database Schema & Sample Data

This section provides the database schema and sample data to illustrate how the authorization model is physically stored.

### `roles`
Defines the roles available in the system.

| role_id | name | description |
| :--- | :--- | :--- |
| `role-editor` | Editor | Can view and edit resources based on grants. |
| `role-viewer` | Viewer | Can only view resources based on grants. |

### `permissions`
Defines the atomic actions that can be performed.

| permission_id | name | description |
| :--- | :--- | :--- |
| `perm-doc-view` | `document:view` | Allows viewing a document. |
| `perm-doc-edit` | `document:edit` | Allows editing a document. |

### `users`
Stores the users of the system.

| user_id | name | email |
| :--- | :--- | :--- |
| `user-1` | Alice | `alice@example.com` |
| `user-2` | Bob | `bob@example.com` |

### `user_roles`
Links users to their assigned roles.

| user_id | role_id |
| :--- | :--- |
| `user-1` | `role-editor` |
| `user-2` | `role-viewer` |

### `permission_grants`
This is the core table of the system, defining **WHO** can do **WHAT**, and **WHERE**.

| grant_id | role_id | permission_id | scope_type | scope_value |
| :--- | :--- | :--- | :--- | :--- |
| 1 | `role-editor` | `perm-doc-view` | `RESOURCE_INSTANCE` | `folder-proj-x` |
| 2 | `role-editor` | `perm-doc-edit` | `RESOURCE_TYPE` | `DOCUMENT` |
| 3 | `role-viewer` | `perm-doc-view` | `RESOURCE_TYPE` | `DOCUMENT` |

**Explanation of Grants:**
*   **Grant 1:** Gives "Editors" the permission to "view" only on the specific resource instance "folder-proj-x". This is how inherited permissions are modeled.
*   **Grant 2:** Gives "Editors" the permission to "edit" on any resource of the type "DOCUMENT" (subject to policies).
*   **Grant 3:** Gives "Viewers" the permission to "view" on any resource of the type "DOCUMENT" (subject to policies).

### `permission_policies`
Links a permission to a fine-grained ABAC policy class that must be evaluated.

| policy_id | permission_id | policy_class_name |
| :--- | :--- | :--- |
| 1 | `perm-doc-edit` | `Project101.Auth.policy.OwnershipPolicy` |

**Explanation of Policy:**
*   This rule dictates that even if a user has the `document:edit` permission through a grant, they *also* must pass the logic contained within the `OwnershipPolicy` Java class before access is granted.

