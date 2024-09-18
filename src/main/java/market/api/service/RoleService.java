package market.api.service;

import lombok.RequiredArgsConstructor;
import market.api.domain.Roles;
import market.api.exception.BadRequestException;
import market.api.repository.RolesRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RolesRepositoryImpl rolesRepository;

    public Roles getRoleByName(String name) {
        Roles role = rolesRepository.findByName(name).orElse(null);
        if (Objects.isNull(role)) {
            throw new BadRequestException("This role does not exists");
        }
        return role;
    }


}
