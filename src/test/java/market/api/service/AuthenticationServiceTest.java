package market.api.service;

import market.api.domain.Roles;
import market.api.domain.Users;
import market.api.repository.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static market.api.util.NewUserAdminRequestCreator.createValidUserAdmin;
import static market.api.util.NewUserAdminRequestCreator.createValidUserAdminWithoutPrefix;
import static market.api.util.NewUserRequestCreator.createValidUserDefaultRole;
import static market.api.util.UserCreator.createValidUser;
import static market.api.util.UserCreator.createValidUserDefault;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authService;

    @Mock
    private UserRepositoryImpl userRepositoryMock;

    @Mock
    private RoleService roleServiceMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    @BeforeEach
    void setUp() {
        BDDMockito.when(roleServiceMock.getRoleByName(ArgumentMatchers.anyString()))
                .thenReturn(new Roles(1L, "ROLE_ADMIN"));

        BDDMockito.when(passwordEncoderMock.encode(ArgumentMatchers.anyString()))
                .thenReturn("marques");

        when(userRepositoryMock.save(ArgumentMatchers.any(Users.class)))
                .thenReturn(createValidUser());

    }

    @Test
    @DisplayName("createAccountAdmin saves user with specific role when successful")
    void createAccountAdmin_SavesUser_WhenSuccessful() {

        Users savedUser = authService.createAccountAdmin(createValidUserAdmin());

        assertThat(savedUser).isNotNull().isEqualTo(createValidUser());

    }

    @Test
    @DisplayName("createAccount save user with default \"ROLE_USER\" when successful")
    void createAccount_SavesUserWithDefaultRole_WhenSuccessful() {
        BDDMockito.when(roleServiceMock.getRoleByName(ArgumentMatchers.anyString()))
                .thenReturn(new Roles(2L, "ROLE_USER"));

        BDDMockito.when(passwordEncoderMock.encode(ArgumentMatchers.anyString()))
                .thenReturn("Suane");

        when(userRepositoryMock.save(ArgumentMatchers.any(Users.class)))
                .thenReturn(createValidUserDefault());


        Users savedUser = authService.createAccount(createValidUserDefaultRole());

        assertThat(savedUser).isNotNull().isEqualTo(createValidUserDefault());

    }

    @Test
    @DisplayName("createAccountAdmin add prefix \"ROLE_\" when prefix is not insert")
    void createAccountAdmin_AddPrefixToRole_WhenPrefixIsNotInsert() {
        Users validUser = createValidUser();

        Users savedUser = authService.createAccountAdmin(createValidUserAdminWithoutPrefix());

        assertThat(savedUser).isNotNull().isEqualTo(validUser);

    }
}