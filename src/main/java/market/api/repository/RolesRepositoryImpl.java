package market.api.repository;

import market.api.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepositoryImpl extends JpaRepository<Roles, Long> {
}