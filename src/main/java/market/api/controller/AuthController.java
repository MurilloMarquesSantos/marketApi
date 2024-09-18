package market.api.controller;

import lombok.RequiredArgsConstructor;
import market.api.requests.NewUserAccountRequest;
import market.api.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/market/auth")
public class AuthController {

    private final AuthenticationService service;

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody NewUserAccountRequest request) {
        service.createAccount(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
