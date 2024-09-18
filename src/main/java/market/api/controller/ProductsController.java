package market.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import market.api.domain.Products;
import market.api.requests.ProductsPostRequest;
import market.api.requests.ProductsPutRequest;
import market.api.service.ProductsService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("market")
@RequiredArgsConstructor
@Log4j2
public class ProductsController {

    private final ProductsService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Products>> list(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(productService.listAll(pageable));
    }

    @GetMapping("/products/list")
    public ResponseEntity<List<Products>> listAllNonPageable() {
        return ResponseEntity.ok(productService.listAllNonPageable());
    }

    @GetMapping("/products/find")
    public ResponseEntity<List<Products>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(productService.findByName(name));
    }

    @GetMapping("/products/find/{id}")
    public ResponseEntity<Products> findById(@PathVariable long id) {
        return ResponseEntity.ok(productService.findByIdOrThrowBadRequestException(id));
    }

    @PostMapping("/products/add")
    public ResponseEntity<Products> save(@RequestBody @Valid ProductsPostRequest productPostRequest) {
        return new ResponseEntity<>(productService.save(productPostRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/products/update")
    public ResponseEntity<Void> replace(@RequestBody @Valid ProductsPutRequest productPutRequest) {
        productService.replace(productPutRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
