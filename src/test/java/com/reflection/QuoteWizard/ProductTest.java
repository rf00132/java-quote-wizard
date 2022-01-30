package com.reflection.QuoteWizard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Product")
class ProductTest {

    Product testProduct;

    @BeforeEach
    void beforeEachTest() {
        testProduct = new Product(0);
    }

    @Test
    @DisplayName("can set name")
    void setNameTest(){
        testProduct.setName("Cloth");
        assertEquals("Cloth", testProduct.getName());
    }

    @Test
    void setPriceTest(){
        testProduct.setPrice(new BigDecimal(12.32));
        assertEquals(new BigDecimal(12.32), testProduct.getPrice());
    }





}
