package se.sofiatherese.vhvh.user.authorities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserPermissions {
    ADMIN_CAN_READ("admin:read"),
    ADMIN_CAN_DELETE("admin:delete"),
    ADMIN_CAN_WRITE("admin:write"),
    ADMIN_CAN_POST("admin:post"),
    USER_CAN_READ("user:read"),
    USER_CAN_DELETE("user:delete"),
    USER_CAN_WRITE("user:write"),
    USER_CAN_POST("user:post");

    private final String userPermission;

}
