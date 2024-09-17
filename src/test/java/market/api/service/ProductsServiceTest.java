package market.api.service;

import market.api.domain.Products;
import market.api.exception.BadRequestException;
import market.api.repository.ProductsRepository;
import market.api.requests.ProductsPostRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static market.api.util.ProductCreator.createValidProduct;
import static market.api.util.ProductPostRequestCreator.createInvalidPostProduct;
import static market.api.util.ProductPostRequestCreator.createValidPostProduct;
import static market.api.util.ProductPutRequestCreator.createValidPutProduct;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class ProductsServiceTest {
    @InjectMocks
    private ProductsService productsService;
    @Mock
    private ProductsRepository productsRepositoryMock;

    @BeforeEach
    void setUp() {
        PageImpl<Products> productPage = new PageImpl<>(List.of(createValidProduct()));
        BDDMockito.when(productsRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(productPage);

        BDDMockito.when(productsRepositoryMock.findAll())
                .thenReturn(List.of(createValidProduct()));

        BDDMockito.when(productsRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(createValidProduct()));

        BDDMockito.when(productsRepositoryMock.save(ArgumentMatchers.any(Products.class)))
                .thenReturn(createValidProduct());

        BDDMockito.when(productsRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(createValidProduct()));

        BDDMockito.doNothing().when(productsRepositoryMock).delete(ArgumentMatchers.any(Products.class));


    }

    @Test
    @DisplayName("listAll returns page of products when successful")
    void listAll_ReturnsPageOfProducts_WhenSuccessful() {
        String expectedName = createValidProduct().getName();
        Page<Products> productsPage = productsService.listAll(PageRequest.of(1, 1));

        assertThat(productsPage).isNotNull();

        assertThat(productsPage.toList()).isNotEmpty().hasSize(1);

        assertThat(productsPage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAllNonPageable returns list of products when successful")
    void listAllNonPageable_ReturnsListOfProducts_WhenSuccessful() {
        String expectedName = createValidProduct().getName();

        List<Products> productsList = productsService.listAllNonPageable();

        assertThat(productsList).isNotNull().isNotEmpty().hasSize(1);

        assertThat(productsList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns list of products containing specific name when successful")
    void findByName_ReturnsListOfProductsContainingSpecificName_WhenSuccessful() {
        String expectedName = createValidProduct().getName();

        List<Products> foundName = productsService.findByName("");

        assertThat(foundName).isNotNull().isNotEmpty().hasSize(1);

        assertThat(foundName.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("save returns saved product when successful")
    void save_ReturnsSavedProduct_WhenSuccessful() {

        Products savedProduct = productsService.save(createValidPostProduct());

        assertThat(savedProduct).isNotNull().isEqualTo(createValidProduct());
    }

    @Test
    @DisplayName("save throws BadRequestException when product name is empty")
    void save_ThrowsBadRequestException_WhenProductNameIsEmpty() {
        ProductsPostRequest invalidPostProduct = createInvalidPostProduct();
        BDDMockito.when(productsRepositoryMock.save(ArgumentMatchers.any(Products.class)))
                .thenThrow(new BadRequestException("The product name cannot be empty"));

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> productsService.save(invalidPostProduct))
                .withMessageContaining("The product name cannot be empty");
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns Product when successful")
    void findByIdOrThrowBadRequestException_ReturnsProducts_WhenSuccessful() {

        Long expectedId = createValidProduct().getId();

        Products foundProduct = productsService.findByIdOrThrowBadRequestException(1);

        assertThat(foundProduct).isNotNull();

        assertThat(foundProduct.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when product is not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenProductIsNotFound() {
        BDDMockito.when(productsRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> productsService.findByIdOrThrowBadRequestException(1));
    }

    @Test
    @DisplayName("delete removes product when successful")
    void delete_RemovesProduct_WhenSuccessful() {

        assertThatCode(() -> productsService.delete(1))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("replace updates product when successful")
    void replace_UpdatesExistingProduct_WhenSuccessful() {

        assertThatCode(() -> productsService.replace(createValidPutProduct()))
                .doesNotThrowAnyException();
    }

}