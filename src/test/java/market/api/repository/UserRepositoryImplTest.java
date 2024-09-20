package market.api.repository;

import market.api.domain.Users;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static market.api.util.UserCreator.createValidUserToBeSaved;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryImplTest {

    @Autowired
    private UsersRepository userRepository;

    @Test
    @DisplayName("findByUsername returns Optional of User when successful")
    void findByUsername_ReturnsOptionalOfUser_WhenSuccessful() {

        Users validUserToBeSaved = createValidUserToBeSaved();

        Users savedUser = userRepository.save(validUserToBeSaved);

        Optional<Users> userFound = userRepository.findByUsername(savedUser.getUsername());

        Users user = userFound.get();

        Assertions.assertThat(user).isNotNull().isEqualTo(validUserToBeSaved);

    }

    @Test
    @DisplayName("findAll returns list of users when successful")
    void findAll_ReturnsListOfUsers_WhenSuccessful() {
        Users validUserToBeSaved = createValidUserToBeSaved();

        Users savedUser = userRepository.save(validUserToBeSaved);

        List<Users> users = userRepository.findAll();

        assertThat(users).isNotEmpty().contains(savedUser);


    }

    @Test
    @DisplayName("findById returns optional of user with specific Id when successful")
    void findById_ReturnsOptionalOfUserWithSpecificId_WhenSuccessful() {

        Users validUserToBeSaved = createValidUserToBeSaved();

        Users savedUser = userRepository.save(validUserToBeSaved);

        Optional<Users> userById = userRepository.findById(savedUser.getId());

        Users user = userById.get();

        assertThat(userById).isNotEmpty();

        assertThat(user).isEqualTo(savedUser);

    }

    @Test
    @DisplayName("delete removes user when successful")
    void delete_RemovesUser_WhenSuccessful() {

        Users validUserToBeSaved = createValidUserToBeSaved();

        Users savedUser = userRepository.save(validUserToBeSaved);

        userRepository.delete(savedUser);

        Optional<Users> userOpt = userRepository.findById(savedUser.getId());

        assertThat(userOpt).isEmpty();
    }


}