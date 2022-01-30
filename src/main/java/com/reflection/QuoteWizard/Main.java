package com.reflection.QuoteWizard;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


import static spark.Spark.*;

public class Main {
    static final QuoteManager QUOTES = new QuoteManager();
    static final ProductManager PRODUCTS = new ProductManager();
    static final BasketManager BASKETS = new BasketManager();
    public static void main(String[] args) {
        //TODO: look into post methods.


        generateTestData();
        staticFileLocation("/public/");


       //Home page
       get("/", (req, res) -> {
           return new ModelAndView(null, "index.hbs");
       }, new HandlebarsTemplateEngine());

       quotePages();
       productPages();
    }

    private static void quotePages(){

        get("/quotes", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("quotes", QUOTES.getList());
            return new ModelAndView(model, "quotes.hbs");
        }, new HandlebarsTemplateEngine());


        get("/quotes-add", (req, res) -> {
            return new ModelAndView(null, "quotes-add.hbs");
        }, new HandlebarsTemplateEngine());

        get("/quotes-update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("selected-quote", QUOTES.getSelectedQuote());
            model.put("selected-basket", QUOTES.getSelectedQuote().getBasket());
            return new ModelAndView(model, "quotes-update.hbs");
        }, new HandlebarsTemplateEngine());

        //TODO: quotes send page
        get("/quotes-send", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("selected-quote", QUOTES.getSelectedQuote());
            return new ModelAndView(model, "quotes-send.hbs");
        }, new HandlebarsTemplateEngine());
    }

    private static void productPages(){

        get("/products", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("products", PRODUCTS.getList());
            return new ModelAndView(null, "products.hbs");
        }, new HandlebarsTemplateEngine());

        get("/products-add", (req, res) -> {
            return new ModelAndView(null, "products-add.hbs");
        }, new HandlebarsTemplateEngine());

        get("/products-update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("selected-product", PRODUCTS.getSelectedProduct());
            return new ModelAndView(null, "products-update.hbs");
        }, new HandlebarsTemplateEngine());
    }

    private static void generateTestData(){
        Product product1 = new Product(0);
        product1.setPrice(new BigDecimal(5.99));
        product1.setName("Brick");
        product1.setVatRate(new BigDecimal(0.2333));

        Product product2 = new Product(1);
        product2.setPrice(new BigDecimal(18.95));
        product2.setName("4 Bricks");

        Product product3 = new Product(2);
        product3.setPrice(new BigDecimal(200.99));
        product3.setName("Lots of Bricks");

        Product product4 = new Product(3);
        product4.setPrice(new BigDecimal(76.55));
        product4.setName("Not quite as many Bricks");
        product4.setVatRate(new BigDecimal(0.1588));

        Quote quote1 = new Quote(0);
        Quote quote2 = new Quote(1);
        Quote quote3 = new Quote(2);

        QuoteItem quoteItem1 = new QuoteItem(0, product1, quote1);
        quoteItem1.incrementProductAmount(3);
        quote1.AddToBasket(quoteItem1);

        QuoteItem quoteItem2 = new QuoteItem(1, product3, quote1);
        quoteItem2.incrementProductAmount(2);
        quote1.AddToBasket(quoteItem2);

        QuoteItem quoteItem3 = new QuoteItem(2, product2, quote2);
        quoteItem3.incrementProductAmount(1);
        quote1.AddToBasket(quoteItem3);

        QuoteItem quoteItem4 = new QuoteItem(3, product4, quote2);
        quoteItem4.incrementProductAmount(4);
        quote1.AddToBasket(quoteItem4);

        QuoteItem quoteItem5 = new QuoteItem(4, product1, quote3);
        quoteItem5.incrementProductAmount(3);
        quote1.AddToBasket(quoteItem5);

        QuoteItem quoteItem6 = new QuoteItem(5, product3, quote3);
        quoteItem6.incrementProductAmount(9);
        quote1.AddToBasket(quoteItem6);

        QuoteItem quoteItem7 = new QuoteItem(6, product4, quote3);
        quoteItem7.incrementProductAmount(2);
        quote1.AddToBasket(quoteItem7);

        QUOTES.addItem(quote1);
        QUOTES.addItem(quote2);
        QUOTES.addItem(quote3);

        PRODUCTS.addItem(product1);
        PRODUCTS.addItem(product2);
        PRODUCTS.addItem(product3);
        PRODUCTS.addItem(product4);

        BASKETS.AddItem(quoteItem1);
        BASKETS.AddItem(quoteItem2);
        BASKETS.AddItem(quoteItem3);
        BASKETS.AddItem(quoteItem4);
        BASKETS.AddItem(quoteItem5);
        BASKETS.AddItem(quoteItem6);
        BASKETS.AddItem(quoteItem7);


        QUOTES.setSelectedQuote(quote1);
        PRODUCTS.setSelectedProduct(product1);
    }
}
