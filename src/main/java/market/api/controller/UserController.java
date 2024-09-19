package market.api.controller;

import lombok.RequiredArgsConstructor;
import market.api.domain.Users;
import market.api.service.UserService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("market/admin/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<Users>> listAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(userService.listAll(pageable));

    }

    @GetMapping("/list")
    public ResponseEntity<List<Users>> list() {
        return ResponseEntity.ok(userService.listAllNonPageable());
    }

    
}
