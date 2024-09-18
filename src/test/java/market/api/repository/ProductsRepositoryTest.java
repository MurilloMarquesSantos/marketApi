package market.api.repository;

import jakarta.validation.ConstraintViolationException;
import market.api.domain.Products;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static market.api.util.ProductCreator.createValidProductToBeSaved;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
@DisplayName("Tests for Products Repository")
class ProductsRepositoryTest {

    @Autowired
    private ProductsRepository productsRepository;

    @Test
    @DisplayName("findAll returns list of products when successful")
    void findAll_ReturnsListOfProducts_WhenSuccessful() {

        Products productToBeSaved = createValidProductToBeSaved();

        Products savedProduct = productsRepository.save(productToBeSaved);

        List<Products> products = productsRepository.findAll();

        assertThat(products).isNotEmpty().contains(savedProduct);


    }


    @Test
    @DisplayName("findAll returns list of products with specific name when successful")
    void findByName_ReturnsListOfProductsWithSpecificName_WhenSuccessful() {

        Products productToBeSaved = createValidProductToBeSaved();

        Products savedProduct = productsRepository.save(productToBeSaved);

        String name = savedProduct.getName();

        Long quantity = savedProduct.getQuantity();

        List<Products> products = productsRepository.findByName(name);

        assertThat(products).isNotEmpty().contains(savedProduct);

        assertThat(products.get(0).getName()).isEqualTo(name);

        assertThat(products.get(0).getQuantity()).isEqualTo(quantity);

    }

    @Test
    @DisplayName("findAll returns empty list of products when product is not found")
    void findByName_ReturnsEmptyListOfProducts_WhenProductIsNotFound() {

        List<Products> productsList = productsRepository.findByName("");

        assertThat(productsList).isEmpty();
    }

    @Test
    @DisplayName("save persists product when successful")
    void save_PersistsProduct_WhenSuccessful() {

        Products productToBeSaved = createValidProductToBeSaved();

        Products savedProduct = productsRepository.save(productToBeSaved);

        assertThat(savedProduct.getName()).isNotNull().isEqualTo(productToBeSaved.getName());

        assertThat(savedProduct.getId()).isNotNull();

        assertThat(savedProduct.getQuantity()).isEqualTo(productToBeSaved.getQuantity());

    }

    @Test
    @DisplayName("save throw ConstraintViolationException when name is empty or null")
    void save_ThrowConstraintViolationException_WhenNameIsEmptyOrNull() {
        Products products = new Products();
        assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> productsRepository.save(products))
                .withMessageContaining("The product name cannot be empty");
    }

    @Test
    @DisplayName("save updates product when successful")
    void save_UpdatesProduct_WhenSuccessful() {

        Products productToBeSaved = createValidProductToBeSaved();

        Products savedProduct = productsRepository.save(productToBeSaved);

        savedProduct.setName("test");

        Products updatedProduct = productsRepository.save(savedProduct);

        assertThat(updatedProduct.getName()).isNotNull().isEqualTo(savedProduct.getName());

        assertThat(updatedProduct.getQuantity()).isNotNull().isEqualTo(savedProduct.getQuantity());

    }

    @Test
    @DisplayName("delete removes product when successful")
    void delete_RemovesProduct_WhenSuccessful() {

        Products productToBeSaved = createValidProductToBeSaved();

        Products savedProduct = productsRepository.save(productToBeSaved);

        productsRepository.delete(savedProduct);

        Optional<Products> productOpt = productsRepository.findById(productToBeSaved.getId());

        assertThat(productOpt).isEmpty();
    }

    @Test
    @DisplayName("findById returns optional of products with specific Id when successful")
    void findById_ReturnsOptionalOfProductsWithSpecificId_WhenSuccessful() {

        Products productToBeSaved = createValidProductToBeSaved();

        Products savedProduct = productsRepository.save(productToBeSaved);

        String name = savedProduct.getName();

        Long quantity = savedProduct.getQuantity();

        Optional<Products> productId = productsRepository.findById(savedProduct.getId());

        Products products = productId.get();

        assertThat(productId).isNotEmpty();

        assertThat(products.getQuantity()).isEqualTo(quantity);

        assertThat(products.getName()).isEqualTo(name);

        assertThat(products.getId()).isEqualTo(savedProduct.getId());

    }
}