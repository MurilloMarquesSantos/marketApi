package market.api.util;

import market.api.requests.NewUserAccountRequest;

public class NewUserRequestCreator {

    public static NewUserAccountRequest createValidUserDefaultRole() {
        return NewUserAccountRequest.builder()
                .name("William")
                .username("William")
                .password("Suane")
                .build();

    }

    public static NewUserAccountRequest createValidUserRoleUser() {
        return NewUserAccountRequest.builder()
                .name("William")
                .username("William")
                .password("Suane")
                .roleName("ROLE_USER")
                .build();

    }

}
