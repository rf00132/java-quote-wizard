package com.reflection.QuoteWizard;
import spark.ModelAndView;

import spark.template.handlebars.HandlebarsTemplateEngine;


import java.math.BigDecimal;
import java.sql.SQLException;

import static com.reflection.QuoteWizard.HandlebarsModelHandler.*;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static spark.Spark.*;

public class Main {
    static final HandlebarsModelHandler HBMH = new HandlebarsModelHandler();

    public static void main(String[] args) {
        staticFileLocation("/public/");

       //Home page
       get("/", (req, res) -> {
           return new ModelAndView(null, "index.hbs");
       }, new HandlebarsTemplateEngine());

       quotePageGets();
       productPageGets();

       quotePagePosts();
       productPagePosts();
    }

    private static void quotePageGets(){

        get("/quotes", (req, res) -> {
            return new ModelAndView(HBMH, "quotes.hbs");
        }, new HandlebarsTemplateEngine());

        get("/quotes-add", (req, res) -> {
            return new ModelAndView(HBMH, "quotes-add.hbs");
        }, new HandlebarsTemplateEngine());

        get("/quotes-delete", (req, res) -> {
            return new ModelAndView(HBMH, "quotes-delete.hbs");
        }, new HandlebarsTemplateEngine());

        //TODO: quotes send page
        get("/quotes-send", (req, res) -> {
            return new ModelAndView(HBMH, "quotes-send.hbs");
        }, new HandlebarsTemplateEngine());

        get("/quotes-update", (req, res) -> {
            return new ModelAndView(HBMH, "quotes-update.hbs");
        }, new HandlebarsTemplateEngine());

        get("/quotes-view", (req, res) -> {
            return new ModelAndView(HBMH, "quotes-view.hbs");
        }, new HandlebarsTemplateEngine());
    }

    private static void quotePagePosts(){
        post("/quotes-add", (req, res) -> {
            try{
                Quote newQuote = new Quote(HBMH.getNextQuoteId());
                if(req.queryParams("quoteNameInput") != null){
                    newQuote.setName(req.queryParams("quoteNameInput"));
                }
                if(req.queryParams("quoteContactInput") != null){
                    newQuote.setContact(req.queryParams("quoteContactInput"));
                }
                QUOTES.addItem(newQuote);
                HBMH.setSelectedQuote(QUOTES.searchForQuote(newQuote.getId()));
                res.redirect("/products-quote");
            } catch (Exception e) {
                System.out.println("Error adding Quote: " + e);
            }
            return null;
        });

        post("/quotes-add-product", (req, res) -> {
            try{
                Product productToAdd = HBMH.getProducts().get(parseInt(req.queryParams("addToQuote")));

                boolean alreadyInBasket = false;

                for(QuoteItem item : HBMH.getSelectedQuote().getBasket()){
                    if(item.getProduct().getId() == productToAdd.getId()){
                        alreadyInBasket = true;
                        break;
                    }
                }
                if(!alreadyInBasket){
                    QuoteItem newItem = new QuoteItem(
                            HBMH.getNextBasketId(),
                            productToAdd.getId() ,
                            HBMH.getSelectedQuote().getId()
                    );
                    HBMH.getSelectedQuote().addToBasket(newItem);
                    HBMH.BASKETS.addItem(newItem);
                    res.redirect("/products-quote");
                }
                else{
                    res.redirect("/quotes-view");

                }



            } catch (Exception e) {
                System.out.println("Error adding Product to Quote: " + e);
            }
            return null;
        });

        post("/quotes-delete", (req, res) -> {
            try{
                int quoteId = parseInt(req.queryParams("deleteBtn"));
                HBMH.setSelectedQuote(HBMH.getQuotes().get(quoteId));
                res.redirect("/quotes-delete");
            } catch (Exception e) {
                System.out.println("Error selecting Quote to delete: " + e);
            }
            return null;
        });

        post("/quotes-delete-confirm", (req, res) -> {
            try{
                QUOTES.deleteItem(HBMH.getSelectedQuote());
                res.redirect("/quotes");
            } catch (Exception e) {
                System.out.println("Error deleting Quote: " + e);
            }
            return null;
        });

        post("/quotes-delete-deny", (req, res) -> {
            try{
                res.redirect("/quotes");
            } catch (Exception e) {
                System.out.println("Error redirecting to quotes page: " + e);
            }
            return null;
        });

        post("/quotes-edit", (req, res) -> {
            try{
                int productId = parseInt(req.queryParams("editBtn"));
                HBMH.setSelectedQuote(HBMH.getQuotes().get(productId));
                res.redirect("/quotes-update");
            } catch (Exception e) {
                System.out.println("Error selecting Quote to edit: " + e);
            }
            return null;
        });

        post("/quotes-update", (req, res) -> {
            try{
                Quote quoteToEdit = QUOTES.searchForQuote(HBMH.getSelectedQuote().getId());
                String newName = req.queryParams("quoteNameInput");
                String newContact = req.queryParams("quoteContactInput");

                if(!quoteToEdit.getQuoteName().equals(newName) && newName != null){
                    QUOTES.searchForQuote(quoteToEdit.getId()).setName(newName);
                }

                System.out.println(HBMH.getSelectedQuote().getQuoteName());
                if(!quoteToEdit.getContact().equals(newContact) && newContact != null){
                    QUOTES.searchForQuote(quoteToEdit.getId()).setContact(newContact);
                }

                res.redirect("/quotes-view");
            } catch (Exception e) {
                System.out.println("Error updating Quote: " + e);
                throw new RuntimeException(e);
            }
            return null;
        });

        post("/quotes-add-one", (req, res) -> {
            try{
                Quote quoteToUpdate = HBMH.getSelectedQuote();
                QuoteItem itemToUpdate = quoteToUpdate.getItemFromBasket(parseInt(req.queryParams("updateAmount")));
                itemToUpdate.incrementProductAmount(1);

                QUOTES.updateItem(quoteToUpdate);

                res.redirect("/quotes-view");
            } catch (Exception e) {
                System.out.println("Error adding to basket item: " + e);
                throw new RuntimeException(e);
            }
            return null;
        });

        post("/quotes-subtract-one", (req, res) -> {
            try{
                Quote quoteToUpdate = HBMH.getSelectedQuote();
                QuoteItem itemToUpdate = quoteToUpdate.getItemFromBasket(parseInt(req.queryParams("updateAmount")));
                itemToUpdate.incrementProductAmount(-1);

                QUOTES.updateItem(quoteToUpdate);

                res.redirect("/quotes-view");
            } catch (Exception e) {
                System.out.println("Error subtracting from basket item: " + e);
                throw new RuntimeException(e);
            }
            return null;
        });

        post("/quotes-view", (req, res) -> {
           try{
               HBMH.setSelectedQuote(
                   HBMH.getQuotes()
                   .get(parseInt(req.queryParams("viewBtn")))
               );
               res.redirect("/quotes-view");
           } catch (Exception e) {
               System.out.println("Error viewing quote: " + e);
               throw new RuntimeException(e);
           }
            return null;
        });
    }

    private static void productPageGets(){

        get("/products", (req, res) -> {
            return new ModelAndView(HBMH, "products.hbs");
        }, new HandlebarsTemplateEngine());

        get("/products-add", (req, res) -> {
            return new ModelAndView(HBMH, "products-add.hbs");
        }, new HandlebarsTemplateEngine());

        get("/products-delete", (req, res) -> {
            return new ModelAndView(HBMH, "products-delete.hbs");
        }, new HandlebarsTemplateEngine());

        get("/products-quote", (req, res) -> {
            return new ModelAndView(HBMH, "products-quote.hbs");
        }, new HandlebarsTemplateEngine());

        get("/products-update", (req, res) -> {
            return new ModelAndView(HBMH, "products-update.hbs");
        }, new HandlebarsTemplateEngine());

        get("/products-view", (req, res) -> {
            return new ModelAndView(HBMH, "products-view.hbs");
        }, new HandlebarsTemplateEngine());
    }

    private static void productPagePosts(){
        post("/products-add", (req, res) -> {
            try{
                Product newProduct = new Product(HBMH.getNextProductId());

                if(req.queryParams("productNameInput") != null){
                    newProduct.setName(req.queryParams("productNameInput"));
                }
                if(req.queryParams("productCostInput") != null){
                    newProduct.setPrice(new BigDecimal(parseDouble(req.queryParams("productCostInput"))));
                }
                if(req.queryParams("productVatInput") != null){
                    newProduct.setVatRate(new BigDecimal(parseDouble(req.queryParams("productVatInput"))));
                }
                PRODUCTS.addItem(newProduct);
                HBMH.setSelectedProduct(PRODUCTS.searchForProduct(newProduct.getId()));
                res.redirect("/products-view");
            } catch (Exception e) {
                System.out.println("Error adding product: " + e);
            }
            return null;
        });


        post("/products-delete", (req, res) -> {
            try{
                int productId = parseInt(req.queryParams("deleteBtn"));

                HBMH.setSelectedProduct(PRODUCTS.getList().get(productId));
                res.redirect("/products-delete");
            } catch (Exception e) {
                System.out.println("Error selecting product for deletion: " + e);
            }
            return null;
        });

        post("/products-delete-confirm", (req, res) -> {
            try{
                PRODUCTS.deleteItem(HBMH.getSelectedProduct());
                res.redirect("/products");

            } catch (Exception e) {
                System.out.println("Error deleting product: " + e);
            }
            return null;
        });

        post("/products-delete-deny", (req, res) -> {
            try{
                res.redirect("/products");
            } catch (Exception e) {
                System.out.println("Error redirecting to products page: " + e);
            }
            return null;
        });

        post("/products-edit", (req, res) -> {
            try{
                int productId = parseInt(req.queryParams("editBtn"));
                System.out.println(productId);
                HBMH.setSelectedProduct(PRODUCTS.searchForProduct(productId));
                res.redirect("/products-update");
            } catch (Exception e) {
                System.out.println("Error selecting product to edit: " + e);
            }
            return null;
        });

        post("/products-update", (req, res) -> {
            try{
                Product productToEdit = PRODUCTS.searchForProduct(HBMH.getSelectedProduct().getId());
                String newName = req.queryParams("productNameInput");

                if(newName != null && !productToEdit.getName().equals(newName)){
                    PRODUCTS.searchForProduct(productToEdit.getId()).setName(newName);
                }

                if(req.queryParams("productPriceInput") != null){
                    PRODUCTS.searchForProduct(productToEdit.getId()).setPrice(new BigDecimal(parseDouble(req.queryParams("productPriceInput"))));
                }

                if(req.queryParams("productVatInput") != null){
                    PRODUCTS.searchForProduct(productToEdit.getId()).setVatRate(new BigDecimal(parseDouble(req.queryParams("productVatInput"))));
                }

                res.redirect("/products-view");
            } catch (Exception e) {
                System.out.println("Error updating product: " + e);
                throw new RuntimeException(e);
            }
            return null;
        });

        post("/products-view", (req, res) -> {
            try{
                HBMH.setSelectedProduct(
                        HBMH.getProducts()
                                .get(parseInt(req.queryParams("viewBtn")))
                );
                res.redirect("/products-view");
            } catch (Exception e) {
                System.out.println("Error viewing product: " + e);
                throw new RuntimeException(e);
            }
            return null;
        });
    }

}
