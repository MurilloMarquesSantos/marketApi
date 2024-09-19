package market.api.util;

import market.api.domain.Roles;

public class RoleCreator {

    public static Roles createValidRole() {
        return Roles.builder()
                .id(1L)
                .name("ROLE_ADMIN")
                .build();
    }
    public static Roles createValidRoleUser() {
        return Roles.builder()
                .name("ROLE_USER")
                .build();
    }

    public static Roles createInvalidRole() {
        return Roles.builder()
                .name("ROLE_INVALID")
                .build();
    }

}
