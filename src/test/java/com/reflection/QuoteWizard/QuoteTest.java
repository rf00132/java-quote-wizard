package com.reflection.QuoteWizard;
import org.junit.*;
import static org.junit.Assert.*;

public class QuoteTest {

    Product testProduct1;
    Product testProduct2;
    QuoteItem testItem1;
    QuoteItem testItem2;
    Quote testQuote;

    @Before
    public void setUp() {
        testQuote = new Quote(0);
        testProduct1 = new Product(0);
        testProduct1.setPrice(10);
        testItem1 = new QuoteItem(0, testProduct1, testQuote);

        testProduct2 = new Product(1);
        testProduct2.setPrice(5);
        testItem2 = new QuoteItem(1, testProduct2, testQuote);


        testQuote.AddToBasket(testItem1);
    }

    @Test
    public void addItemTest(){
        testQuote.AddToBasket(testItem2);
        assertEquals(5, testQuote.getBasket().get(1).getProduct().getPrice(), 0.0);
    }

    @Test
    public void removeItemTest(){
        testQuote.AddToBasket(testItem2);
        testQuote.DeleteFromBasket(testItem1);
        assertEquals(5, testQuote.getBasket().get(0).getProduct().getPrice(), 0.0);
    }

    @Test
    public void checkPriceTotalOnAddToBasket(){
        testQuote.AddToBasket(testItem2);
        assertEquals(15, testQuote.getBasketTotal(), 0.0);
    }

    @Test
    public void checkPriceVatTotalOnAddToBasket(){
        testQuote.AddToBasket(testItem2);
        assertEquals(testQuote.getBasketVatTotal(), 15 * (1 + testQuote.getBasket().get(0).getProduct().getVatRate()), 0.0);
    }

}
