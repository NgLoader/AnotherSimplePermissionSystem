package de.ngloader.asps.core.database.model;

import java.io.Serializable;

public record UserPermissionId(int userId, String permission) implements Serializable {
}