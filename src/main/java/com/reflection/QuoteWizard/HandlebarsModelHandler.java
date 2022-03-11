package com.reflection.QuoteWizard;

import java.math.BigDecimal;
import java.util.List;

public class HandlebarsModelHandler {
    public static final DatabaseManager DBMANAGER = new DatabaseManager();
    public static final QuoteManager QUOTES = new QuoteManager();
    public static final ProductManager PRODUCTS = new ProductManager();
    public static final BasketManager BASKETS = new BasketManager();

    public HandlebarsModelHandler(){
        //DBMANAGER.setTestDbUrl();
        //DBMANAGER.clearDatabaseRecords();
        //DBMANAGER.createTables();
        //generateTestData();
        DBMANAGER.initDataBases();
    }

    public List<Quote> getQuotes() {
        return QUOTES.getList();
    }

    public List<Product> getProducts() {
        return PRODUCTS.getList();
    }

    public List<QuoteItem> getBaskets() {
        return BASKETS.getList();
    }

    public Quote getSelectedQuote() {
        return QUOTES.getSelectedQuote();
    }

    public Product getSelectedProduct() {
        return PRODUCTS.getSelectedProduct();
    }

    public void setSelectedQuote( Quote newQuote) {
        QUOTES.setSelectedQuote(newQuote);
    }

    public void setSelectedProduct(Product newProduct) {
        PRODUCTS.setSelectedProduct(newProduct);
    }

    public int getNextProductId(){
        int indexOfLastProduct = getProducts().size() - 1;
        int returnId = 0;
        if(indexOfLastProduct >= 0){
            returnId = getProducts().get(indexOfLastProduct).getId() + 1;
        }
        return returnId;
    }

    public int getNextQuoteId(){
        int indexOfLastQuote = getQuotes().size() - 1;
        int returnId = 0;
        if(indexOfLastQuote >= 0){
            returnId = getQuotes().get(indexOfLastQuote).getId() + 1;
        }

        return returnId;
    }
    public int getNextBasketId(){
        int indexOfLastBasket = getBaskets().size() - 1;
        int returnId = 0;
        if(indexOfLastBasket >= 0) {
            returnId = getBaskets().get(indexOfLastBasket).getId() + 1;
        }
        return returnId;
    }

    private void generateTestData(){

        Product product1 = new Product(1);
        product1.setPrice(new BigDecimal(5.99));
        product1.setName("Brick");
        product1.setVatRate(new BigDecimal(0.2333));

        Product product2 = new Product(2);
        product2.setPrice(new BigDecimal(18.95));
        product2.setName("4 Bricks");

        Product product3 = new Product(3);
        product3.setPrice(new BigDecimal(200.99));
        product3.setName("Lots of Bricks");

        Product product4 = new Product(4);
        product4.setPrice(new BigDecimal(76.55));
        product4.setName("Not quite as many Bricks");
        product4.setVatRate(new BigDecimal(0.1588));

        PRODUCTS.addItem(product1);
        PRODUCTS.addItem(product2);
        PRODUCTS.addItem(product3);
        PRODUCTS.addItem(product4);

        Quote quote1 = new Quote(1);
        quote1.setContact("Joe Bloggs");
        Quote quote2 = new Quote(2);
        quote2.setContact("Jane Bloggs");
        Quote quote3 = new Quote(3);
        quote3.setContact("Jim Bloggs");

        QUOTES.addItem(quote1);
        QUOTES.addItem(quote2);
        QUOTES.addItem(quote3);

        QuoteItem quoteItem1 = new QuoteItem(1, product1.getId(), quote1.getId());
        quoteItem1.setProductAmount(4);
        quote1.addToBasket(quoteItem1);

        QuoteItem quoteItem2 = new QuoteItem(2, product3.getId(), quote1.getId());
        quoteItem2.setProductAmount(3);
        quote1.addToBasket(quoteItem2);

        QuoteItem quoteItem3 = new QuoteItem(3, product2.getId(), quote2.getId());
        quoteItem3.setProductAmount(1);
        quote2.addToBasket(quoteItem3);

        QuoteItem quoteItem4 = new QuoteItem(4, product4.getId(), quote2.getId());
        quoteItem4.setProductAmount(4);
        quote2.addToBasket(quoteItem4);

        QuoteItem quoteItem5 = new QuoteItem(5, product1.getId(), quote3.getId());
        quoteItem5.setProductAmount(3);
        quote3.addToBasket(quoteItem5);

        QuoteItem quoteItem6 = new QuoteItem(6, product3.getId(), quote3.getId());
        quoteItem6.setProductAmount(9);
        quote3.addToBasket(quoteItem6);

        QuoteItem quoteItem7 = new QuoteItem(7, product4.getId(), quote3.getId());
        quoteItem7.setProductAmount(2);
        quote3.addToBasket(quoteItem7);



        BASKETS.addItem(quoteItem1);
        BASKETS.addItem(quoteItem2);
        BASKETS.addItem(quoteItem3);
        BASKETS.addItem(quoteItem4);
        BASKETS.addItem(quoteItem5);
        BASKETS.addItem(quoteItem6);
        BASKETS.addItem(quoteItem7);


        QUOTES.setSelectedQuote(getQuotes().get(0));
        PRODUCTS.setSelectedProduct(getProducts().get(0));

    }
}
