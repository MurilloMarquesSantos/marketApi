package market.api.service;

import lombok.RequiredArgsConstructor;
import market.api.domain.Users;
import market.api.repository.UserRepositoryImpl;
import market.api.requests.NewUserAccountRequest;
import market.api.requests.NewUserAccountRequestAdmin;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepositoryImpl userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public Users createAccountAdmin(NewUserAccountRequestAdmin request) {
        String roleName = request.getRoleName();
        if (!request.getRoleName().startsWith("ROLE_")) {
            roleName = "ROLE_" + roleName;
        }
        return userRepository.save(Users.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singleton(
                        roleService.getRoleByName(roleName))).build());
    }

    @Transactional(rollbackFor = Exception.class)
    public Users createAccount(NewUserAccountRequest request) {
        String defaultRole = "ROLE_USER";
        return userRepository.save(Users.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Collections.singleton(
                        roleService.getRoleByName(defaultRole))).build());
    }

}
