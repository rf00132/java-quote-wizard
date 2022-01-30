package com.reflection.QuoteWizard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuoteItemTest {

    Product testProduct;
    QuoteItem testItem;

    @BeforeEach
    public void setUp() {
        testProduct = new Product(0);
        testProduct.setPrice(new BigDecimal(10));
        testItem = new QuoteItem(0, testProduct, null);
    }

    @Test
    public void addProductAmountTest(){
        testItem.incrementProductAmount(10);
        assertEquals(11, testItem.getProductAmount());
    }

    @Test
    public void productAmountMinIsZero(){
        testItem.incrementProductAmount(-10);
        assertEquals(0, testItem.getProductAmount());
    }

    @Test
    public void priceAutoUpdateTest(){
        testItem.incrementProductAmount(3);
        assertEquals(40, testItem.getTotal().doubleValue());
    }

    @Test
    public void priceVatAutoUpdateTest(){
        testItem.incrementProductAmount(3);
        assertEquals(testItem.getVatTotal(), testItem.getProduct().getVatRate().add(ONE).multiply(new BigDecimal(40)));
    }

}
