package market.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import market.api.domain.Products;
import market.api.repository.ProductsRepository;
import market.api.requests.ProductPostRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductService {
    private final ProductsRepository productsRepository;

    public Products save(ProductPostRequest productPostRequest){
        Products products = Products.builder()
                .name(productPostRequest.getName())
                .quantity(productPostRequest.getQuantity())
                .build();
        return productsRepository.save(products);
    }


}
