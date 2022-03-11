package com.reflection.QuoteWizard;

import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.reflection.QuoteWizard.HandlebarsModelHandler.*;

public class DatabaseTest {
    Product testProduct1;
    Product testProduct2;
    QuoteItem testItem1;
    QuoteItem testItem2;
    Quote testQuote;

    @BeforeEach
    public void setUp(){
        DBMANAGER.setTestDbUrl();
        DBMANAGER.clearDatabaseRecords();
        DBMANAGER.createTables();
        DBMANAGER.initDataBases();

        testProduct1 = new Product(1);
        testProduct1.setName("Big Brick");
        testProduct1.setPrice(new BigDecimal(10));

        testProduct2 = new Product(2);
        testProduct2.setName("Small Brick");
        testProduct2.setPrice(new BigDecimal(5));

        PRODUCTS.addItem(testProduct1);
        PRODUCTS.addItem(testProduct2);

        testQuote = new Quote(1);
        testQuote.setContact("Brick Land");
        QUOTES.addItem(testQuote);

        testItem1 = new QuoteItem(1, testProduct1.getId(), testQuote.getId());
        testItem2 = new QuoteItem(2, testProduct2.getId(), testQuote.getId());
        testItem1.setProductAmount(4);
        testItem2.setProductAmount(3);
        testQuote.addToBasket(testItem1);
        testQuote.addToBasket(testItem2);

        BASKETS.addItem(testItem1);
        BASKETS.addItem(testItem2);

        DBMANAGER.refreshAllSearchResults();
    }

    //Insert Tests

    @Test
    public void insertIntoProductTableTest(){
        List<Product> products = DBMANAGER.getProductResults();
        assertEquals("Big Brick", products.get(0).getName());
        assertEquals("10.00", products.get(0).getProductPrice().toString());
        assertEquals("Small Brick", products.get(1).getName());
        assertEquals("5.00", products.get(1).getProductPrice().toString());
    }

    @Test
    public void insertIntoQuoteTableTest(){
        Quote quote = QUOTES.getList().get(DBMANAGER.getQuoteResults().get(0).getId() - 1);
        assertEquals("Quote 1", quote.getQuoteName());
        assertEquals("7", quote.getAmountInBasket() + "");
        assertEquals("Brick Land", quote.getContact());
        assertEquals("66.00", quote.getBasketVatTotal().toString());
    }

    @Test
    public void insertIntoBasketTableTest(){
        List<QuoteItem> basketItems = BASKETS.getList();
        assertEquals("4", basketItems.get(0).getProductAmount() + "");
        assertEquals("3", basketItems.get(1).getProductAmount() + "");
        assertEquals("1", basketItems.get(0).getQuote().getId() + "");
        assertEquals("1", basketItems.get(1).getQuote().getId() + "");
        assertEquals("1", basketItems.get(0).getProduct().getId() + "");
        assertEquals("2", basketItems.get(1).getProduct().getId() + "");
    }

    //Update Tests

    @Test
    public void updateQuoteEntriesTest(){
        Quote originalQuote = null;
        for(Quote quote : DBMANAGER.getQuoteResults()){
            if(quote.getId() == 1){
                originalQuote = quote;
                break;
            }
        }

        Quote quoteToUpdate = QUOTES.searchForQuote(1);
        System.out.println(originalQuote.getQuoteName() + ", " + quoteToUpdate.getQuoteName());
        Quote updatedQuote = QUOTES.searchForQuote(1);
        updatedQuote.setName("new Name");
        updatedQuote.setContact("Brick Lord Land");

        for(Quote quote : DBMANAGER.getQuoteResults()){
            if(quote.getId() == 1){
                originalQuote = quote;
                break;
            }
        }

        quoteToUpdate = QUOTES.searchForQuote(1);
        System.out.println(originalQuote.getQuoteName() + ", " + quoteToUpdate.getQuoteName());
        QUOTES.updateItem(updatedQuote);

        Quote newQuote = DBMANAGER.getQuoteResults().get(0);
        assertEquals("new Name", newQuote.getQuoteName());
        assertEquals("Brick Lord Land", newQuote.getContact());
    }

    @Test
    public void updateProductEntriesTest(){

        Product updatedProduct = PRODUCTS.searchForProduct(2);
        updatedProduct.setName("Massive Brick");
        updatedProduct.setPrice(new BigDecimal(12));
        updatedProduct.setVatRate(new BigDecimal(0.5));
        PRODUCTS.updateItem(updatedProduct);

        Product newProduct = DBMANAGER.getProductResults().get(1);
        assertEquals("Massive Brick", newProduct.getName());
        assertEquals("12.00", newProduct.getProductPrice().toString());
        assertEquals("0.5000", newProduct.getVatRate().toString());
    }

    @Test
    public void updateBasketEntriesTest(){
        QuoteItem updatedBasketItem = BASKETS.searchForItem(2);
        updatedBasketItem.incrementProductAmount(2);
        BASKETS.updateItem(updatedBasketItem);

        QuoteItem newBasketItem = DBMANAGER.getBasketResults().get(1);
        assertEquals("5", newBasketItem.getProductAmount() + "");
    }

    //Delete Tests

    @Test
    public void deleteFromQuoteTableTest(){
        Quote quoteToDelete = QUOTES.searchForQuote(1);
        QUOTES.deleteItem(quoteToDelete);

        DBMANAGER.refreshAllSearchResults();
        assertEquals("0", DBMANAGER.getQuoteResults().size() + "");
        assertEquals("0", DBMANAGER.getBasketResults().size() + "");
    }

    @Test
    public void deleteFromProductTableTest(){
        Product productToDelete = PRODUCTS.searchForProduct(1);
        PRODUCTS.deleteItem(productToDelete);

        DBMANAGER.refreshAllSearchResults();
        assertEquals("1", DBMANAGER.getProductResults().size() + "");
        assertEquals("1", DBMANAGER.getBasketResults().size() + "");
    }

    @Test
    public void deleteFromBasketTableTest(){
        QuoteItem itemToDelete = BASKETS.searchForItem(1);
        BASKETS.deleteItem(itemToDelete);

        DBMANAGER.refreshAllSearchResults();
        assertEquals("1", DBMANAGER.getBasketResults().size() + "");
        assertEquals("1", itemToDelete.getQuote().getBasket().size() + "");
    }
}
