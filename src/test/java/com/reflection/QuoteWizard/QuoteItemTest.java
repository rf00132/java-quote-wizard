package com.reflection.QuoteWizard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.reflection.QuoteWizard.HandlebarsModelHandler.*;
import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_UP;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuoteItemTest {

    Product testProduct;
    QuoteItem testItem;
    Quote testQuote;
    @BeforeEach
    public void setUp() {
        DBMANAGER.setTestDbUrl();
        DBMANAGER.clearDatabaseRecords();
        DBMANAGER.createTables();
        DBMANAGER.initDataBases();

        testQuote = new Quote(1);
        QUOTES.addItem(testQuote);

        testProduct = new Product(1);
        testProduct.setPrice(new BigDecimal(10));
        PRODUCTS.addItem(testProduct);

        testItem = new QuoteItem(1, testProduct.getId(), testQuote.getId());
        BASKETS.addItem(testItem);
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
        assertEquals(testItem.getVatTotal(), testItem.getProduct().getVatRate().add(ONE).multiply(new BigDecimal(40)).setScale(2, HALF_UP));
    }

}
