package market.api.controller;

import market.api.domain.Users;
import market.api.requests.NewUserAccountRequest;
import market.api.requests.NewUserAccountRequestAdmin;
import market.api.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static market.api.util.NewUserAdminRequestCreator.createValidUserAdmin;
import static market.api.util.NewUserRequestCreator.createValidUserDefaultRole;
import static market.api.util.UserCreator.createValidUser;
import static market.api.util.UserCreator.createValidUserDefault;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthenticationService authServiceMock;

    @BeforeEach
    void setUp() {
        BDDMockito.when(authServiceMock.createAccount(ArgumentMatchers.any(NewUserAccountRequest.class)))
                .thenReturn(createValidUserDefault());
        BDDMockito.when(authServiceMock.createAccountAdmin(ArgumentMatchers.any(NewUserAccountRequestAdmin.class)))
                .thenReturn(createValidUser());
    }

    @Test
    void createAccount_ReturnsUserWithDefaultRole_WhenSuccessful() {

        Users createdUser = authController.createAccount(createValidUserDefaultRole()).getBody();

        assertThat(createdUser).isNotNull().isEqualTo(createValidUserDefault());
    }

    @Test
    void createAccountAdmin_ReturnsUserWithSpecificRole_WhenSuccessful() {

        Users createdUser = authController.createAccountAdmin(createValidUserAdmin()).getBody();

        assertThat(createdUser).isNotNull().isEqualTo(createValidUser());
    }
}
