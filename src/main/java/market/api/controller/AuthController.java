package market.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import market.api.domain.Users;
import market.api.requests.NewUserAccountRequest;
import market.api.requests.NewUserAccountRequestAdmin;
import market.api.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("market")
public class AuthController {

    private final AuthenticationService authService;

    @PostMapping("/account/register")
    @Operation(summary = "Create a new user with role \"ROLE_USER\"",
            description = "Do not require authentication to use")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "When user infos are not insert properly")
    })
    public ResponseEntity<Users> createAccount(@RequestBody NewUserAccountRequest request) {
        return new ResponseEntity<>(authService.createAccount(request), HttpStatus.CREATED);
    }

    @PostMapping("/admin/account/register")
    @Operation(summary = "Create a new user with  specific role," +
            " if role is not specified then the default role is applied:  \"ROLE_USER\"",
            description = "Must be Admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "When user infos are not insert properly")
    })
    public ResponseEntity<Users> createAccountAdmin(@RequestBody NewUserAccountRequestAdmin request) {
        return new ResponseEntity<>(authService.createAccountAdmin(request), HttpStatus.CREATED);

    }

}
