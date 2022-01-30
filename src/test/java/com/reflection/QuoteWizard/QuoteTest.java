package com.reflection.QuoteWizard;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QuoteTest {

    Product testProduct1;
    Product testProduct2;
    QuoteItem testItem1;
    QuoteItem testItem2;
    Quote testQuote;

    @BeforeEach
    public void setUp() {
        testQuote = new Quote(0);
        testProduct1 = new Product(0);
        testProduct1.setPrice(new BigDecimal(10));
        testItem1 = new QuoteItem(0, testProduct1, testQuote);

        testProduct2 = new Product(1);
        testProduct2.setPrice(new BigDecimal(5));
        testItem2 = new QuoteItem(1, testProduct2, testQuote);


        testQuote.AddToBasket(testItem1);
    }

    @Test
    public void addItemTest(){
        testQuote.AddToBasket(testItem2);
        assertEquals(5, testQuote.getBasket().get(1).getProduct().getPrice().doubleValue());
    }

    @Test
    public void removeItemTest(){
        testQuote.AddToBasket(testItem2);
        testQuote.DeleteFromBasket(testItem1);
        assertEquals(5, testQuote.getBasket().get(0).getProduct().getPrice().doubleValue());
    }

    @Test
    public void checkPriceTotalOnAddToBasket(){
        testQuote.AddToBasket(testItem2);
        assertEquals(15, testQuote.getBasketTotal().doubleValue());
    }

    @Test
    public void checkPriceVatTotalOnAddToBasket(){
        testQuote.AddToBasket(testItem2);
        assertEquals(testQuote.getBasketVatTotal(), testQuote.getBasket().get(0).getProduct().getVatRate().add(ONE).multiply(new BigDecimal(15)));
    }

}
