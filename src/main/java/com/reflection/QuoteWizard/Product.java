package com.reflection.QuoteWizard;

import java.math.BigDecimal;
import java.util.HashMap;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

public class Product{

    private final int productId;
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

    public BigDecimal getProductPrice(){
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
        productPrice = newPrice.setScale(2, HALF_UP);
        updateVatCost();
    }

    public void setVatRate(BigDecimal newRate) {
        vatRate = newRate.setScale(4, HALF_UP);
        updateVatCost();
    }

    //private methods
    private void updateVatCost() {
        vatCost = vatRate.multiply(productPrice).setScale(2, HALF_UP);
        updateGrandTotal();
    }

    private void updateGrandTotal(){
        grandTotal = productPrice.add(vatCost).setScale(2, HALF_UP);
    }



}
