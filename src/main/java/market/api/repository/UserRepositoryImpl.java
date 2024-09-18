package market.api.repository;

import market.api.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositoryImpl extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);
}