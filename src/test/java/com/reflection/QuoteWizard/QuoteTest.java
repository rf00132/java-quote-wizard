package com.reflection.QuoteWizard;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.reflection.QuoteWizard.HandlebarsModelHandler.*;
import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_UP;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuoteTest {

    Product testProduct1;
    Product testProduct2;
    QuoteItem testItem1;
    QuoteItem testItem2;
    Quote testQuote;

    @BeforeEach
    public void setUp() {
        DBMANAGER.setTestDbUrl();
        DBMANAGER.clearDatabaseRecords();
        DBMANAGER.createTables();
        DBMANAGER.initDataBases();

        testQuote = new Quote(0);
        QUOTES.addItem(testQuote);

        testProduct1 = new Product(0);
        testProduct1.setPrice(new BigDecimal(10));
        PRODUCTS.addItem(testProduct1);

        testProduct2 = new Product(1);
        testProduct2.setPrice(new BigDecimal(5));
        PRODUCTS.addItem(testProduct2);

        testItem1 = new QuoteItem(0, testProduct1.getId(), testQuote.getId());
        BASKETS.addItem(testItem1);

        testItem2 = new QuoteItem(1, testProduct2.getId(), testQuote.getId());
        BASKETS.addItem(testItem2);

        testQuote.addToBasket(testItem1);
    }

    @Test
    public void addItemTest(){
        testQuote.addToBasket(testItem2);
        assertEquals(5, testQuote.getBasket().get(1).getProduct().getProductPrice().doubleValue());
    }

    @Test
    public void removeItemTest(){
        testQuote.addToBasket(testItem2);
        testQuote.DeleteFromBasket(testItem1);
        assertEquals(5, testQuote.getBasket().get(0).getProduct().getProductPrice().doubleValue());
    }

    @Test
    public void checkPriceTotalOnAddToBasket(){
        testQuote.addToBasket(testItem2);
        assertEquals(15, testQuote.getBasketTotal().doubleValue());
    }

    @Test
    public void checkPriceVatTotalOnAddToBasket(){
        testQuote.addToBasket(testItem2);
        assertEquals(testQuote.getBasketVatTotal(), testQuote.getBasket().get(0).getProduct().getVatRate().add(ONE).multiply(new BigDecimal(15)).setScale(2, HALF_UP));
    }

}
