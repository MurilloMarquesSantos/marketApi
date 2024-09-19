package market.api.util;

import market.api.domain.Roles;
import market.api.domain.Users;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserCreator {

    private static Set<Roles> roles = new HashSet<>(List.of(new Roles(1L, "ROLE_ADMIN")));

    public static Users createValidUser() {
        return Users.builder()
                .id(1L)
                .name("Murillo")
                .username("Murillo")
                .password("marques")
                .roles(roles)
                .build();
    }

    public static Users createValidUserDefault() {
        return Users.builder()
                .id(1L)
                .name("William")
                .username("William")
                .password("Suane")
                .build();
    }

}
