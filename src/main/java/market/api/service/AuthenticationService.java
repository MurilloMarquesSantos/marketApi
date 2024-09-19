package market.api.service;

import lombok.RequiredArgsConstructor;
import market.api.domain.Users;
import market.api.mapper.UsersMapper;
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
        if (roleName.isEmpty()) {
            roleName += "ROLE_USER";
        }
        if (!roleName.startsWith("ROLE_")) {
            roleName = "ROLE_" + roleName;
        }
        Users user = UsersMapper.INSTANCE.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singleton(roleService.getRoleByName(roleName)));
        Users savedUser = userRepository.save(user);
        return Users.builder()
                .id(savedUser.getId())
                .name(request.getName())
                .username(request.getUsername())
                .password(request.getPassword())
                .roles(savedUser.getRoles())
                .build();

    }

    @Transactional(rollbackFor = Exception.class)
    public Users createAccount(NewUserAccountRequest request) {
        String defaultRole = "ROLE_USER";

        Users user = UsersMapper.INSTANCE.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singleton(roleService.getRoleByName(defaultRole)));
        Users savedUser = userRepository.save(user);
        return Users.builder()
                .id(savedUser.getId())
                .name(request.getName())
                .username(request.getUsername())
                .password(request.getPassword())
                .roles(savedUser.getRoles())
                .build();
    }

}
