package market.api.integration;

import market.api.domain.Products;
import market.api.domain.Roles;
import market.api.domain.Users;
import market.api.repository.ProductsRepository;
import market.api.repository.RolesRepositoryImpl;
import market.api.repository.UserRepositoryImpl;
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
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static market.api.util.ProductCreator.createValidProduct;
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

    private static Set<Roles> roles = new HashSet<>(List.of(new Roles(1L, "ROLE_ADMIN")));
    private static Set<Roles> roleUser = new HashSet<>(List.of(new Roles(2L, "ROLE_USER")));

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
}
