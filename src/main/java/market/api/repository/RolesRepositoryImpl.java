package market.api.repository;

import market.api.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepositoryImpl extends JpaRepository<Roles, Long> {

    Optional<Roles> findByName(String name);
}
