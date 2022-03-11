package com.reflection.QuoteWizard;

import java.math.BigDecimal;

import static com.reflection.QuoteWizard.HandlebarsModelHandler.*;
import static com.reflection.QuoteWizard.Main.HBMH;
import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_UP;

public class QuoteItem {

    private final int quoteItemId;
    private int productId;
    private int quoteId;
    private int productAmount;
    private BigDecimal productTotal;
    private BigDecimal productTotalVat;

    public QuoteItem(int id, int linkedProductId, int linkedQuoteId){
        quoteItemId = id;
        productId = linkedProductId;
        quoteId = linkedQuoteId;
        productAmount = 1;
        productTotal = PRODUCTS.searchForProduct(productId).getProductPrice();
        productTotalVat =  PRODUCTS.searchForProduct(productId).getGrandTotal();
    }

    //public methods
    public int getId(){
        return quoteItemId ;
    }

    public String getName(){
        return PRODUCTS.searchForProduct(productId).getName();
    }

    public Product getProduct(){
        return PRODUCTS.searchForProduct(productId);
    }

    public Quote getQuote(){
        return QUOTES.searchForQuote(quoteId);
    }

    public BigDecimal getTotal(){
        return productTotal;
    }

    public BigDecimal getVatTotal() {
        return productTotalVat;
    }

    public int getProductAmount(){
        return productAmount;
    }

    public void setProductAmount(int amount) {
        productAmount = amount;

        setTotal();
        setVatTotal();
        QUOTES.searchForQuote(quoteId).updateProductTotals();
    }

    //private methods
    private void setTotal(){
        productTotal = PRODUCTS.searchForProduct(productId).getProductPrice().multiply(new BigDecimal(productAmount)).setScale(2, HALF_UP);
    }

    private void setVatTotal(){
        //sets total with vat equal to the total multiply by 1 + vatrate
        productTotalVat = productTotal.multiply(PRODUCTS.searchForProduct(productId).getVatRate().add(ONE)).setScale(2, HALF_UP);
    }

    public void incrementProductAmount(int amountToAdd){
        productAmount += amountToAdd;
        if(productAmount <= 0) {
            productAmount = 0;
            QUOTES.searchForQuote(quoteId).DeleteFromBasket(this);
            BASKETS.updateItem(this);
            BASKETS.deleteItem(this);
        }
        else{
            BASKETS.updateItem(this);
        }

        setTotal();
        setVatTotal();

        QUOTES.searchForQuote(quoteId).updateProductTotals();
        QUOTES.updateItem(QUOTES.searchForQuote(quoteId));
        HBMH.setSelectedQuote(getQuote());

    }




}
