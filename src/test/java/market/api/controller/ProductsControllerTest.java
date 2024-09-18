package market.api.controller;

import market.api.domain.Products;
import market.api.requests.ProductsPostRequest;
import market.api.requests.ProductsPutRequest;
import market.api.service.ProductsService;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static market.api.util.ProductCreator.createValidProduct;
import static market.api.util.ProductPostRequestCreator.createValidPostProduct;
import static market.api.util.ProductPutRequestCreator.createValidPutProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@ExtendWith(SpringExtension.class)
class ProductsControllerTest {

    @InjectMocks
    private ProductsController productsController;
    @Mock
    private ProductsService productsServiceMock;

    @BeforeEach
    void setUp() {
        PageImpl<Products> productPage = new PageImpl<>(List.of(createValidProduct()));
        BDDMockito.when(productsServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(productPage);

        BDDMockito.when(productsServiceMock.listAllNonPageable())
                .thenReturn(List.of(createValidProduct()));

        BDDMockito.when(productsServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(createValidProduct()));

        BDDMockito.when(productsServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(createValidProduct());

        BDDMockito.when(productsServiceMock.save(ArgumentMatchers.any(ProductsPostRequest.class)))
                .thenReturn(createValidProduct());

        BDDMockito.doNothing().when(productsServiceMock).replace(ArgumentMatchers.any(ProductsPutRequest.class));

        BDDMockito.doNothing().when(productsServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("list returns page of products when successful")
    void list_ReturnsPageOfProducts_WhenSuccessful() {
        String expectedName = createValidProduct().getName();
        Page<Products> productsPage = productsController.list(null).getBody();

        assertThat(productsPage).isNotNull();

        assertThat(productsPage.toList()).isNotEmpty().hasSize(1);

        assertThat(productsPage.toList().get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("listAllNonPageable returns list of products when successful")
    void listAllNonPageable_ReturnsListOfProducts_WhenSuccessful() {
        String expectedName = createValidProduct().getName();

        List<Products> productsList = productsController.listAllNonPageable().getBody();

        assertThat(productsList).isNotNull().isNotEmpty().hasSize(1);

        assertThat(productsList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns list of products containing specific name when successful")
    void findByName_ReturnsListOfProductsContainingSpecificName_WhenSuccessful() {
        String expectedName = createValidProduct().getName();

        List<Products> foundName = productsController.findByName("").getBody();

        assertThat(foundName).isNotNull().isNotEmpty().hasSize(1);

        assertThat(foundName.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns Product when successful")
    void findById_ReturnsProducts_WhenSuccessful() {

        Long expectedId = createValidProduct().getId();

        Products foundProduct = productsController.findById(1).getBody();

        assertThat(foundProduct).isNotNull();

        assertThat(foundProduct.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("save returns saved product when successful")
    void save_ReturnsSavedProduct_WhenSuccessful() {

        Products savedProduct = productsController.save(createValidPostProduct()).getBody();

        assertThat(savedProduct).isNotNull().isEqualTo(createValidProduct());
    }
    @Test
    @DisplayName("delete removes product when successful")
    void delete_RemovesProduct_WhenSuccessful() {

        assertThatCode(() -> productsController.delete(1))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("replace updates product when successful")
    void replace_UpdatesExistingProduct_WhenSuccessful() {

        assertThatCode(() -> productsController.replace(createValidPutProduct()))
                .doesNotThrowAnyException();
    }
}