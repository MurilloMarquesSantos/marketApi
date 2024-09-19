package market.api.controller;

import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<String> createAccount(@RequestBody NewUserAccountRequest request) {
        authService.createAccount(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping("/admin/account/register")
    public ResponseEntity<String> createAccountAdmin(@RequestBody NewUserAccountRequestAdmin request) {
        authService.createAccountAdmin(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
