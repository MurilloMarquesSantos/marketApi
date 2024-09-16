package market.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import market.api.domain.Products;
import market.api.exception.BadRequestException;
import market.api.mapper.ProductMapper;
import market.api.repository.ProductsRepository;
import market.api.requests.ProductPostRequest;
import market.api.requests.ProductPutRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductService {
    private final ProductsRepository productsRepository;

    public List<Products> listAll() {
        return productsRepository.findAll();
    }

    public List<Products> findByName(String name) {
        return productsRepository.findByName(name);
    }

    @Transactional(rollbackFor = Exception.class)
    public Products save(ProductPostRequest productPostRequest) {
        Products products = ProductMapper.INSTANCE.toProduct(productPostRequest);
        return productsRepository.save(products);
    }

    public Products findByIdOrThrowBadRequestException(long id) {
        return productsRepository.findById(id).orElseThrow(() -> new BadRequestException("Product now found"));
    }

    public void delete(long id) {
        productsRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(ProductPutRequest productPutRequest) {
        Products savedProduct = findByIdOrThrowBadRequestException(productPutRequest.getId());
        Products products = ProductMapper.INSTANCE.toProduct(productPutRequest);
        products.setId(savedProduct.getId());
        productsRepository.save(products);
    }
}
