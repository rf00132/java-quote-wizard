package com.reflection.QuoteWizard;

import java.math.BigDecimal;

public class QuoteItem {

    private int quoteItemId;
    private Product product;
    private Quote quote;
    private BigDecimal productTotal;
    private BigDecimal productTotalVat;
    private int productAmount;

    public QuoteItem(int id, Product product, Quote quote){
        quoteItemId = id;
        this.product = product;
        this.quote = quote;
        productAmount = 1;
        productTotal = this.product.getPrice();
        productTotalVat = this.product.getGrandTotal();
    }

    //public methods
    public int getId(){
        return quoteItemId;
    }

    public Product getProduct(){
        return product;
    }

    public Quote getQuote(){
        return quote;
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

    //private methods
    private void setTotal(){
        productTotal = product.getPrice().multiply(new BigDecimal(productAmount));
    }

    private void setVatTotal(){
        //sets total with vat equal to the total multiply by 1 + vatrate
        productTotalVat = productTotal.multiply(product.getVatRate().add(BigDecimal.ONE));
    }

    public void incrementProductAmount(int amountToAdd){
        productAmount += amountToAdd;
        if(productAmount < 0) productAmount = 0;
        setTotal();
        setVatTotal();
    }




}
