package com.reflection.QuoteWizard;

public class QuoteItem {

    private int quoteItemId;
    public int getId(){ return quoteItemId; }

    private Product product;
    public Product getProduct(){ return product; }

    private Quote quote;
    public Quote getQuote(){ return quote; }

    private double productTotal;
    public double getTotal(){ return productTotal; }
    private void setTotal(){ productTotal = product.getPrice() * productAmount; }

    private double productTotalVat;
    public double getVatTotal() { return productTotalVat; }
    private void setVatTotal(){ productTotalVat = productTotal*( 1 + product.getVatRate() ); }

    private int productAmount;
    public int getProductAmount(){ return productAmount; }
    public void incrementProductAmount(int amountToAdd){
        productAmount += amountToAdd;
        if(productAmount < 0) productAmount = 0;
        setTotal();
        setVatTotal();
    }

    public QuoteItem(int id, Product product, Quote quote){
        quoteItemId = id;
        this.product = product;
        this.quote = quote;
        productAmount = 1;
        productTotal = this.product.getPrice();
        quoteItemId = IdManager.GetNextQuoteItemId();
    }


}
