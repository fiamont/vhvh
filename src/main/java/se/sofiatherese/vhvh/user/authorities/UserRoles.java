package se.sofiatherese.vhvh.user.authorities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static se.sofiatherese.vhvh.user.authorities.UserPermissions.*;

@AllArgsConstructor
@Getter
public enum UserRoles {

    ADMIN(Set.of(ADMIN_CAN_POST, ADMIN_CAN_READ, ADMIN_CAN_WRITE, ADMIN_CAN_DELETE)),

    USER(Set.of(USER_CAN_DELETE, USER_CAN_POST, USER_CAN_WRITE, USER_CAN_READ));

    private final Set<UserPermissions> userPermissionsList;

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() {

        Set<SimpleGrantedAuthority> permissionsSet = getUserPermissionsList().stream().map(
                index -> new SimpleGrantedAuthority(index.getUserPermission())
        ).collect(Collectors.toSet());

        permissionsSet.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permissionsSet;
    }
}
