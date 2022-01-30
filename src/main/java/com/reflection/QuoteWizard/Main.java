package com.reflection.QuoteWizard;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {
    static QuoteManager quotes;
    static ProductManager products;
    static BasketManager baskets;
    public static void main(String[] args) {

        staticFileLocation("/public/");
        quotes = new QuoteManager();
        products = new ProductManager();
        baskets = new BasketManager();

       //Home page
       get("/", (req, res) -> {
           return new ModelAndView(null, "index.hbs");
       }, new HandlebarsTemplateEngine());

       QuotePages();
       ProductPages();
    }

    private static void QuotePages(){

        get("/quotes", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("quotes", quotes.getList());
            return new ModelAndView(model, "quotes.hbs");
        }, new HandlebarsTemplateEngine());

        get("/quotes-add", (req, res) -> {
            return new ModelAndView(null, "quotes-add.hbs");
        }, new HandlebarsTemplateEngine());

        get("/quotes-update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("selected-quote", quotes.getSelectedQuote());
            model.put("selected-basket", quotes.getSelectedQuote().getBasket());
            return new ModelAndView(model, "quotes-update.hbs");
        }, new HandlebarsTemplateEngine());
        //TODO: quotes send page
        get("/quotes-send", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("selected-quote", quotes.getSelectedQuote());
            return new ModelAndView(model, "quotes-send.hbs");
        }, new HandlebarsTemplateEngine());
    }

    private static void ProductPages(){

        get("/products", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("products", products.getList());
            return new ModelAndView(null, "products.hbs");
        }, new HandlebarsTemplateEngine());

        get("/products-add", (req, res) -> {
            return new ModelAndView(null, "products-add.hbs");
        }, new HandlebarsTemplateEngine());

        get("/products-update", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("selected-product", products.getSelectedProduct());
            return new ModelAndView(null, "products-update.hbs");
        }, new HandlebarsTemplateEngine());
    }
}
