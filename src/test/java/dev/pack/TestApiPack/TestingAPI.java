package dev.pack.TestApiPack;

import dev.pack.entity.Product;
import dev.pack.repository.ProductRepository;
import dev.pack.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TestingAPI {
    
    @Mock
    ProductRepository repository;

    @InjectMocks
    ProductService service;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    void testGetUserByEmail() {
//        Product product = new Product();
//        product.setEmail("MockingEmail@gmail.com");
//
//        Optional<Product> email = repository.findByEmail(product.getEmail());
//
//        assertNotNull(email);
//    }

//    @Test
//    void testAddUserValidation() {
//        Product product = new Product(
//                1,
//                "Name Product 1",
//                "Description Product 1",
//                100.0
//        );
//        assertEquals(product,service.addProduct(product));
//    }
}
