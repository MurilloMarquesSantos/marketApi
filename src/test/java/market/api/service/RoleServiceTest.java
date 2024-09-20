package market.api.service;

import market.api.domain.Roles;
import market.api.exception.BadRequestException;
import market.api.repository.RolesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static market.api.util.RoleCreator.createInvalidRole;
import static market.api.util.RoleCreator.createValidRole;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@ExtendWith(SpringExtension.class)
class RoleServiceTest {

    @InjectMocks
    private RolesService roleService;

    @Mock
    private RolesRepository rolesRepositoryMock;

    @BeforeEach
    void setUp() {
        BDDMockito.when(rolesRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Optional.ofNullable(createValidRole()));
    }

    @Test
    @DisplayName("getRoleByName returns role when successful")
    void getRoleByName_ReturnsRole_WhenSuccessful() {
        Roles expectedRole = createValidRole();

        Roles role = roleService.getRoleByName("");

        assertThat(role).isNotNull();

        assertThat(role.getAuthority()).isEqualTo(expectedRole.getAuthority());

        assertThat(role.getName()).isEqualTo(expectedRole.getName());
    }

    @Test
    @DisplayName("getRoleByName throws BadRequestException when role is null")
    void getRoleByName_ThrowsBadRequestException_WhenRoleIsNull() {
        String invalidRoleName = createInvalidRole().getName();
        BDDMockito.when(rolesRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> roleService.getRoleByName(invalidRoleName))
                .withMessageContaining("This role does not exists");
    }
}