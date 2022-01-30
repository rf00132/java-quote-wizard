package com.reflection.QuoteWizard;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public class Product{

    private int productId;
    private String productName;
    private BigDecimal productPrice;
    private BigDecimal vatRate = new BigDecimal(0.2);
    private BigDecimal vatCost;
    private BigDecimal grandTotal;

    public Product(int id){
        productId = id;
        productName = "";
        productPrice = ZERO;
    }

    //public methods
    public int getId(){
        return productId;
    }

    public String getName() {
        return productName;
    }

    public BigDecimal getPrice(){
        return productPrice;
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public BigDecimal getVatCost() {
        return vatCost;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public void setName(String newName) {
        productName = newName;
    }

    public void setPrice(BigDecimal newPrice) {
        productPrice = newPrice;
        updateVatCost();
    }

    public void setVatRate(BigDecimal newRate) {
        vatRate = newRate;
        updateVatCost();
    }

    //private methods
    private void updateVatCost() {
        vatCost = vatRate.multiply(productPrice);
        updateGrandTotal();
    }

    private void updateGrandTotal(){
        grandTotal = productPrice.add(vatCost);
    }

}
