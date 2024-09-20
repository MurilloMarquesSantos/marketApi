package market.api.controller;

import market.api.domain.Users;
import market.api.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static market.api.util.UserCreator.createValidUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @InjectMocks
    private UsersController userController;

    @Mock
    private UsersService userServiceMock;

    @BeforeEach
    void setUp() {
        PageImpl<Users> userPage = new PageImpl<>(List.of(createValidUser()));
        BDDMockito.when(userServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(userPage);

        BDDMockito.when(userServiceMock.listAllNonPageable())
                .thenReturn(List.of(createValidUser()));

        BDDMockito.doNothing().when(userServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    void listAll_ReturnsPageOfUsers_WhenSuccessful() {
        Users validUser = createValidUser();

        Page<Users> userPage = userController.listAll(null).getBody();

        assertThat(userPage).isNotNull().isNotEmpty();

        assertThat(userPage.toList()).isNotEmpty().contains(validUser).hasSize(1);
    }

    @Test
    void list_ReturnsListOfUserWhenSuccessful() {
        Users validUser = createValidUser();

        List<Users> userList = userController.list().getBody();

        assertThat(userList).isNotNull().isNotEmpty().hasSize(1).contains(validUser);
    }

    @Test
    @DisplayName("delete removes user when successful")
    void delete_RemovesUser_WhenSuccessful() {

        assertThatCode(() -> userController.delete(1))
                .doesNotThrowAnyException();
    }

}