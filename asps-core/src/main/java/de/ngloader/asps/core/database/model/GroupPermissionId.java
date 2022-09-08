package de.ngloader.asps.core.database.model;

import java.io.Serializable;

public record GroupPermissionId(int groupId, String permission) implements Serializable {
}