package market.api.util;

import market.api.requests.NewUserAccountRequestAdmin;

public class NewUserAdminRequestCreator {

    public static NewUserAccountRequestAdmin createValidUserAdmin() {
        return NewUserAccountRequestAdmin.builder()
                .name("Murillo")
                .username("Murillo")
                .password("marques")
                .roleName("ROLE_ADMIN")
                .build();
    }

    public static NewUserAccountRequestAdmin createValidUserAdminWithoutPrefix() {
        return NewUserAccountRequestAdmin.builder()
                .name("Murillo")
                .username("Murillo")
                .password("marques")
                .roleName("ADMIN")
                .build();
    }

    public static NewUserAccountRequestAdmin createValidUserWithEmptyRole() {
        return NewUserAccountRequestAdmin.builder()
                .name("Murillo")
                .username("Murillo")
                .password("marques")
                .roleName("")
                .build();
    }

}
