package test.java.com.reflection.QuoteWizard;

import main.java.com.reflection.QuoteWizard.Product;
import main.java.com.reflection.QuoteWizard.QuoteItem;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class QuoteItemTest {

    Product testProduct;
    QuoteItem testItem;

    @Before
    public void setUp() {
        testProduct = new Product();
        testProduct.setPrice(10);
        testItem = new QuoteItem(testProduct);
    }

    @Test
    public void addProductAmountTest(){
        testItem.incrementProductAmount(10);
        assertTrue(testItem.getProductAmount() == 11);
    }

    @Test
    public void productAmountMinIsZero(){
        testItem.incrementProductAmount(-10);
        assertTrue(testItem.getProductAmount() == 0);
    }

    @Test
    public void priceAutoUpdateTest(){
        testItem.incrementProductAmount(3);
        assertTrue(testItem.getTotal() == 40);
    }

    @Test
    public void priceVatAutoUpdateTest(){
        testItem.incrementProductAmount(3);
        assertTrue(testItem.getVatTotal() == 40 * (1 + testItem.getVatRate()));
    }

}
