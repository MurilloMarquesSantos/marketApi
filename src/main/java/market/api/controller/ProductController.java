package market.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import market.api.domain.Products;
import market.api.requests.ProductPostRequest;
import market.api.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("market")
@RequiredArgsConstructor
@Log4j2
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Products>> listAll() {
        return ResponseEntity.ok(productService.listAll());
    }

    @PostMapping("/products/add")
    public ResponseEntity<Products> save(@RequestBody @Valid ProductPostRequest productPostRequest) {
        return new ResponseEntity<>(productService.save(productPostRequest), HttpStatus.CREATED);
    }

}
