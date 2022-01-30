package com.reflection.QuoteWizard;
import org.junit.*;
import static org.junit.Assert.*;

public class QuoteItemTest {

    Product testProduct;
    QuoteItem testItem;

    @Before
    public void setUp() {
        testProduct = new Product(0);
        testProduct.setPrice(10);
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
        assertEquals(40, testItem.getTotal(), 0.0);
    }

    @Test
    public void priceVatAutoUpdateTest(){
        testItem.incrementProductAmount(3);
        assertEquals(testItem.getVatTotal(), 40 * (1 + testItem.getProduct().getVatRate()), 0.0);
    }

}
