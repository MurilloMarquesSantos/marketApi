package market.api.service;

import lombok.RequiredArgsConstructor;
import market.api.domain.Products;
import market.api.domain.Users;
import market.api.exception.BadRequestException;
import market.api.repository.UserRepositoryImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepositoryImpl userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Page<Users> listAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }


    public List<Users> listAllNonPageable() {
        return userRepository.findAll();
    }

    public Users findByIdOrThrowBadRequestException(long id) {
        return userRepository.findById(id).orElseThrow(() -> new BadRequestException("Product not found"));
    }

    public void delete(long id) {
        userRepository.delete(findByIdOrThrowBadRequestException(id));
    }
}
