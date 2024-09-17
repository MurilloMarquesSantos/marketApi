package market.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import market.api.domain.Products;
import market.api.exception.BadRequestException;
import market.api.mapper.ProductsMapper;
import market.api.repository.ProductsRepository;
import market.api.requests.ProductsPostRequest;
import market.api.requests.ProductsPutRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductsService {
    private final ProductsRepository productsRepository;

    public Page<Products> listAll(Pageable pageable) {
        return productsRepository.findAll(pageable);
    }

    public List<Products> listAllNonPageable() {
        return productsRepository.findAll();
    }

    public List<Products> findByName(String name) {
        return productsRepository.findByName(name);
    }

    @Transactional(rollbackFor = Exception.class)
    public Products save(ProductsPostRequest productPostRequest) {
        Products products = ProductsMapper.INSTANCE.toProduct(productPostRequest);
        return productsRepository.save(products);
    }

    public Products findByIdOrThrowBadRequestException(long id) {
        return productsRepository.findById(id).orElseThrow(() -> new BadRequestException("Product not found"));
    }

    public void delete(long id) {
        productsRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    public void replace(ProductsPutRequest productPutRequest) {
        Products savedProduct = findByIdOrThrowBadRequestException(productPutRequest.getId());
        Products products = ProductsMapper.INSTANCE.toProduct(productPutRequest);
        products.setId(savedProduct.getId());
        productsRepository.save(products);
    }
}
