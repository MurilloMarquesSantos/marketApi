package market.api.integration;

import market.api.domain.Products;
import market.api.domain.Roles;
import market.api.domain.Users;
import market.api.repository.ProductsRepository;
import market.api.repository.RolesRepositoryImpl;
import market.api.repository.UserRepositoryImpl;
import market.api.requests.ProductsPostRequest;
import market.api.wrapper.PageableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static market.api.util.ProductCreator.createValidProduct;
import static market.api.util.ProductPostRequestCreator.createValidPostProduct;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductsControllerIT {

    @Autowired
    @Qualifier(value = "TestRestTemplateRoleUser")
    private TestRestTemplate testRestTemplateRoleUser;

    @Autowired
    @Qualifier(value = "TestRestTemplateRoleAdmin")
    private TestRestTemplate testRestTemplateRoleAdmin;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private RolesRepositoryImpl rolesRepository;

    private static final Set<Roles> roles = new HashSet<>(List.of(new Roles(1L, "ROLE_ADMIN")));
    private static final Set<Roles> roleUser = new HashSet<>(List.of(new Roles(2L, "ROLE_USER")));

    private static final Users USER = Users.builder()
            .name("Fabiano")
            .username("Fabiano")
            .password("$2a$10$XCVGeTfEJkPNqtTutwjb0.Li6/UPpQSsScnu/0lBbcP3Qc/HjKo/O")
            .roles(roleUser)
            .build();

    private static final Users ADMIN = Users.builder()
            .name("Murillo")
            .username("Murillo")
            .password("$2a$10$770lgSC4pE.TLdepODR9Ue8BwBjydkkq65NhjgEPHHgjrI2A9WCcy")
            .roles(roles)
            .build();

    @TestConfiguration
    @Lazy
    static class Config {
        @Bean(name = "TestRestTemplateRoleUser")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("Fabiano", "Santos");
            return new TestRestTemplate(restTemplateBuilder);
        }

        @Bean(name = "TestRestTemplateRoleAdmin")
        public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:" + port)
                    .basicAuthentication("Murillo", "marques");
            return new TestRestTemplate(restTemplateBuilder);
        }
    }


    @Test
    @DisplayName("list returns page of product when successful")
    void list_ReturnsPageOfProducts_WhenSuccessful() {
        rolesRepository.save(new Roles(1L, "ROLE_ADMIN"));
        rolesRepository.save(new Roles(2L, "ROLE_USER"));

        Products savedProduct = productsRepository.save(createValidProduct());
        userRepository.save(USER);
        String expectedName = savedProduct.getName();

        PageableResponse<Products> productsPage = testRestTemplateRoleUser.exchange("/market/products", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Products>>() {
                }).getBody();

        assertThat(productsPage).isNotNull().isNotEmpty();

        assertThat(productsPage.toList()).hasSize(1);

        assertThat(productsPage.toList().get(0).getName()).isEqualTo(expectedName);


    }

    @Test
    @DisplayName("listAllNonPageable returns list of products when successful")
    void listAllNonPageable_ReturnsListOfProducts_WhenSuccessful() {
        rolesRepository.save(new Roles(1L, "ROLE_ADMIN"));
        rolesRepository.save(new Roles(2L, "ROLE_USER"));

        Products savedProduct = productsRepository.save(createValidProduct());
        userRepository.save(USER);
        String expectedName = savedProduct.getName();

        List<Products> productsList = testRestTemplateRoleUser.exchange("/market/products/list", HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Products>>() {
                }).getBody();

        assertThat(productsList).isNotNull()
                .isNotEmpty().hasSize(1);

        assertThat(productsList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns list of products with specific name when successful")
    void findByName_ReturnsListOfProductsWithSpecificName_WhenSuccessful() {
        rolesRepository.save(new Roles(1L, "ROLE_ADMIN"));
        rolesRepository.save(new Roles(2L, "ROLE_USER"));

        Products savedProduct = productsRepository.save(createValidProduct());
        userRepository.save(USER);
        String expectedName = savedProduct.getName();

        String url = String.format("/market/products/find?name=%s", expectedName);

        List<Products> productsList = testRestTemplateRoleUser.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Products>>() {
                }).getBody();

        assertThat(productsList).isNotNull().isNotEmpty().hasSize(1);

        assertThat(productsList.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findByName returns empty list when product is not found")
    void findByName_ReturnsEmptyList_WhenProductIsNotFound() {
        rolesRepository.save(new Roles(1L, "ROLE_ADMIN"));
        rolesRepository.save(new Roles(2L, "ROLE_USER"));
        userRepository.save(USER);

        List<Products> productsList = testRestTemplateRoleUser.exchange("/market/products/find?name=x",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Products>>() {
                }).getBody();

        assertThat(productsList).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("findById returns product when successful")
    void findById_ReturnsProduct_WhenSuccessful() {
        rolesRepository.save(new Roles(1L, "ROLE_ADMIN"));
        rolesRepository.save(new Roles(2L, "ROLE_USER"));

        Products savedProduct = productsRepository.save(createValidProduct());
        userRepository.save(USER);
        Long expectedId = savedProduct.getId();

        Products productFound = testRestTemplateRoleUser.getForObject("/market/products/find/{id}",
                Products.class, expectedId);

        assertThat(productFound).isNotNull();

        assertThat(productFound.getId()).isEqualTo(expectedId);
    }

    @Test
    @DisplayName("save returns product when successful")
    void save_ReturnsProduct_WhenSuccessful() {
        rolesRepository.save(new Roles(1L, "ROLE_ADMIN"));
        rolesRepository.save(new Roles(2L, "ROLE_USER"));

        ProductsPostRequest validPostProduct = createValidPostProduct();
        userRepository.save(ADMIN);

        ResponseEntity<Products> productsResponseEntity =
                testRestTemplateRoleAdmin.postForEntity("/market/products/add", validPostProduct, Products.class);

        assertThat(productsResponseEntity).isNotNull();

        assertThat(productsResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        assertThat(productsResponseEntity.getBody()).isNotNull();

        assertThat(productsResponseEntity.getBody().getId()).isNotNull();

        assertThat(productsResponseEntity.getBody().getQuantity()).isNotNull();

        assertThat(productsResponseEntity.getBody().getName()).isNotNull();


    }

    @Test
    @DisplayName("save returns 403 when user is not admin")
    void save_Returns403_WhenUserIsNotAdmin() {
        rolesRepository.save(new Roles(1L, "ROLE_ADMIN"));
        rolesRepository.save(new Roles(2L, "ROLE_USER"));

        ProductsPostRequest validPostProduct = createValidPostProduct();
        userRepository.save(USER);

        ResponseEntity<Products> productsResponseEntity =
                testRestTemplateRoleUser.postForEntity("/market/products/add", validPostProduct, Products.class);

        assertThat(productsResponseEntity).isNotNull();

        assertThat(productsResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);


    }

    @Test
    @DisplayName("delete removes product when successful")
    void delete_RemovesProduct_WhenSuccessful() {
        rolesRepository.save(new Roles(1L, "ROLE_ADMIN"));
        rolesRepository.save(new Roles(2L, "ROLE_USER"));

        Products savedProduct = productsRepository.save(createValidProduct());
        userRepository.save(ADMIN);

        ResponseEntity<Void> deletedProduct = testRestTemplateRoleAdmin.exchange(
                "/market/products/delete/{id}", HttpMethod.DELETE,
                null, Void.class, savedProduct.getId());

        assertThat(deletedProduct).isNotNull();

        assertThat(deletedProduct.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName("delete returns 403 when user is not admin")
    void delete_Returns403_WhenUserIsNotAdmin() {
        rolesRepository.save(new Roles(1L, "ROLE_ADMIN"));
        rolesRepository.save(new Roles(2L, "ROLE_USER"));

        Products savedProduct = productsRepository.save(createValidProduct());
        userRepository.save(USER);

        ResponseEntity<Void> deletedProduct = testRestTemplateRoleUser.exchange(
                "/market/products/delete/{id}", HttpMethod.DELETE,
                null, Void.class, savedProduct.getId());

        assertThat(deletedProduct).isNotNull();

        assertThat(deletedProduct.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

    }

    @Test
    @DisplayName("replace updates product when successful")
    void replace_UpdatesProductWhenSuccessful() {
        rolesRepository.save(new Roles(1L, "ROLE_ADMIN"));
        rolesRepository.save(new Roles(2L, "ROLE_USER"));

        Products savedProduct = productsRepository.save(createValidProduct());
        userRepository.save(ADMIN);

        savedProduct.setName("new name");
        ResponseEntity<Void> updatedProduct = testRestTemplateRoleAdmin.exchange(
                "/market/products/update", HttpMethod.PUT, new HttpEntity<>(savedProduct),
                Void.class);

        assertThat(updatedProduct).isNotNull();

        assertThat(updatedProduct.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);


    }

    @Test
    @DisplayName("replace returns 403 user is not admin")
    void replace_Returns403_WhenUserIsNotAdmin() {
        rolesRepository.save(new Roles(1L, "ROLE_ADMIN"));
        rolesRepository.save(new Roles(2L, "ROLE_USER"));

        Products savedProduct = productsRepository.save(createValidProduct());
        userRepository.save(USER);

        savedProduct.setName("new name");
        ResponseEntity<Void> updatedProduct = testRestTemplateRoleUser.exchange(
                "/market/products/update", HttpMethod.PUT, new HttpEntity<>(savedProduct),
                Void.class);

        assertThat(updatedProduct).isNotNull();

        assertThat(updatedProduct.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);


    }

}
