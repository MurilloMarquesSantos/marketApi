package market.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "List all users paginated", description = "Must be Admin")
    public ResponseEntity<Page<Users>> listAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(userService.listAll(pageable));
    }

    @GetMapping("/list")
    @Operation(summary = "List all users as list", description = "Must be Admin")
    public ResponseEntity<List<Users>> list() {
        return ResponseEntity.ok(userService.listAllNonPageable());
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete an existing product", description = "Must be Admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "When user not exists in database")
    })
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
