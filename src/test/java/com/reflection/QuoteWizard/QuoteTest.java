package test.java.com.reflection.QuoteWizard;

import main.java.com.reflection.QuoteWizard.Product;
import main.java.com.reflection.QuoteWizard.Quote;
import main.java.com.reflection.QuoteWizard.QuoteItem;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class QuoteTest {

    Product testProduct1;
    Product testProduct2;
    QuoteItem testItem1;
    QuoteItem testItem2;
    Quote testQuote;

    @Before
    public void setUp() {
        testProduct1 = new Product();
        testProduct1.setPrice(10);
        testItem1 = new QuoteItem(testProduct1);

        testProduct2 = new Product();
        testProduct2.setPrice(5);
        testItem2 = new QuoteItem(testProduct2);

        testQuote = new Quote();
        testQuote.AddToBasket(testItem1);
    }

    @Test
    public void addItemTest(){
        testQuote.AddToBasket(testItem2);
        assertTrue(testQuote.getBasket().get(1).getProduct().getPrice() == 5);
    }

    @Test
    public void removeItemTest(){
        testQuote.AddToBasket(testItem2);
        testQuote.DeleteFromBasket(testItem1);
        assertTrue(testQuote.getBasket().get(0).getProduct().getPrice() == 5);
    }

    @Test
    public void checkPriceTotalOnAddToBasket(){
        testQuote.AddToBasket(testItem2);
        assertTrue(testQuote.getBasketTotal() == 15);
    }

    @Test
    public void checkPriceVatTotalOnAddToBasket(){
        testQuote.AddToBasket(testItem2);
        assertTrue(testQuote.getBasketVatTotal() == 15 * ( 1 + testQuote.getBasket().get(0).getVatRate() ));
    }

}
