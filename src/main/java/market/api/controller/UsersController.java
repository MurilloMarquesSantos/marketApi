package market.api.controller;

import lombok.RequiredArgsConstructor;
import market.api.domain.Users;
import market.api.service.UsersService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("market/admin/user")
public class UsersController {

    private final UsersService userService;

    @GetMapping
    public ResponseEntity<Page<Users>> listAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(userService.listAll(pageable));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Users>> list() {
        return ResponseEntity.ok(userService.listAllNonPageable());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
