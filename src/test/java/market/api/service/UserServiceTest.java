package market.api.service;

import market.api.domain.Users;
import market.api.repository.UsersRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static market.api.util.UserCreator.createValidUser;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @InjectMocks
    private UsersService userService;

    @Mock
    private UsersRepository userRepositoryMock;


    @BeforeEach
    void setUp() {
        BDDMockito.when(userRepositoryMock.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(createValidUser()));

        PageImpl<Users> usersPage = new PageImpl<>(List.of(createValidUser()));
        BDDMockito.when(userRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(usersPage);

        BDDMockito.when(userRepositoryMock.findAll())
                .thenReturn(List.of(createValidUser()));

        BDDMockito.when(userRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(createValidUser()));

        BDDMockito.doNothing().when(userRepositoryMock).delete(ArgumentMatchers.any(Users.class));
    }

    @Test
    @DisplayName("loadByUsername returns UserDetails when successful")
    void loadByUsername_ReturnsUserDetails_WhenSuccessful() {
        Users expectedUser = createValidUser();

        UserDetails userReturned = userService.loadUserByUsername("");

        assertThat(userReturned).isNotNull();

        assertThat(userReturned.getUsername()).isEqualTo(expectedUser.getUsername());
    }

    @Test
    @DisplayName("loadByUsername throws UsernameNotFoundException when username is not found ")
    void loadByUsername_ThrowsUsernameNotFoundException_WhenUsernameIsNotFound() {
        String invalidUsername = " ";
        BDDMockito.when(userRepositoryMock.findByUsername(ArgumentMatchers.anyString()))
                .thenThrow(new UsernameNotFoundException("User not found"));

        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> userService.loadUserByUsername(invalidUsername))
                .withMessageContaining("User not found");
    }

    @Test
    @DisplayName("listAll returns page of users when successful")
    void listAll_ReturnsPageOfUsers_WhenSuccessful() {
        String expectedName = createValidUser().getName();

        Page<Users> users = userService.listAll(PageRequest.of(1, 5));

        assertThat(users).isNotEmpty().isNotNull();

        List<Users> userList = users.toList();

        assertThat(userList.get(0).getName()).isNotNull().isNotEmpty().isEqualTo(expectedName);

        assertThat(userList.get(0)).isEqualTo(createValidUser());

    }

    @Test
    @DisplayName("listAll returns page of users when successful")
    void listAllNonPageable_ReturnsListOfUsers_WhenSuccessful() {
        String expectedName = createValidUser().getName();

        List<Users> userList = userService.listAllNonPageable();


        assertThat(userList.get(0).getName()).isNotNull().isNotEmpty().isEqualTo(expectedName);

        assertThat(userList.get(0)).isEqualTo(createValidUser());

    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns user found by id when successful")
    void findByIdOrThrowBadRequestException_ReturnsUserFoundById_WhenSuccessful() {
        Users validUser = createValidUser();

        Users userById = userService.findByIdOrThrowBadRequestException(1);

        assertThat(userById).isNotNull().isEqualTo(validUser);
    }

    @Test
    @DisplayName("delete removes user when successful")
    void delete_RemovesUserWhenSuccessful() {
        assertThatCode(() -> userService.delete(1))
                .doesNotThrowAnyException();
    }

}