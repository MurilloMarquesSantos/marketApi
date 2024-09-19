package market.api.repository;

import market.api.domain.Roles;
import market.api.util.RoleCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RolesRepositoryImplTest {

    @Autowired
    RolesRepositoryImpl rolesRepository;

    @Test
    @DisplayName("findByName returns Optional of Roles when successful")
    void findByName_ReturnsOptionalOfRoles_WhenSuccessful() {
        Roles validRole = RoleCreator.createValidRole();

        Roles savedRole = rolesRepository.save(validRole);

        Optional<Roles> roleOpt = rolesRepository.findByName(savedRole.getName());

        Roles roles = roleOpt.get();

        assertThat(roleOpt).isNotEmpty().isNotNull();

        assertThat(roles).isEqualTo(validRole);


    }

}