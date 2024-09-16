package market.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import market.api.domain.Products;
import market.api.requests.ProductPostRequest;
import market.api.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("market")
@RequiredArgsConstructor
@Log4j2
public class ProductController {
    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<Products> save(@RequestBody @Valid ProductPostRequest productPostRequest) {
        return new ResponseEntity<>(productService.save(productPostRequest), HttpStatus.CREATED);
    }

}
