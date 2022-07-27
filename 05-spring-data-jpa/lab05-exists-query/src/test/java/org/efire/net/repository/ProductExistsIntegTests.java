package org.efire.net.repository;

import org.efire.net.ProductApp;
import org.efire.net.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ProductApp.class)
@Testcontainers
@ContextConfiguration(initializers = {BasePostgresContainerTest.PropertiesInitializer.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductExistsIntegTests extends BasePostgresContainerTest {

    @Autowired
    private ProductRepository productRepository;

    private String laptop_slug = "laptop_slug-1";
    @BeforeEach
    void setUp() {
        var product1 = new Product().setId(1L)
                .setName("Laptop")
                .setDescription("Laptop 1")
                .setProductSlug(laptop_slug);

        productRepository.save(product1);

        //Use saveAll method
        List<Product> products = new ArrayList<>();
        for (long i = 2; i < 1001; i++) {
            products.add(new Product().setId(i)
                    .setName("Laptop")
                    .setDescription(String.format("Laptop %d", i))
                    .setProductSlug(String.format("laptop_slug-%d",i)));

        }
        productRepository.saveAll(products);
    }

    @Test
    void test() {
        assertTrue(productRepository.findById(1L).isPresent());

        assertThat(productRepository.count()).isEqualTo(1000);

        // Bad idea
        assertTrue(productRepository.findByProductSlug(laptop_slug).isPresent());

        // Bad idea
        assertTrue(productRepository.existsById(1L));

        // Good idea
        assertTrue(productRepository.existsBySlugWithCount(laptop_slug));

        // Better (using native query)
        assertTrue(productRepository.existsBySlugWithCase(laptop_slug));
    }
}
