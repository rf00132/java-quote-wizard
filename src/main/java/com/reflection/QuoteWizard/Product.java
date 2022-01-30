package com.reflection.QuoteWizard;

public class Product{

    private int productId;
    public int getId(){ return productId; }

    private String productName;
    public String getName(){ return productName; }
    public void setName(String newName){ productName = newName; }

    private double productPrice;
    public double getPrice(){ return productPrice; }
    public void setPrice(double newPrice){
        productPrice = newPrice;
        setVatCost();
    }

    private double vatRate = 0.2;
    public double getVatRate() { return vatRate; }
    public void setVatRate(double newRate){
        vatRate = newRate;
        setVatCost();
    }

    private double vatCost;
    private double getVatCost(){ return vatCost; }
    public void setVatCost() {
        vatCost = vatRate * productPrice;
        setGrandTotal();
    }

    private double grandTotal;
    public double getGrandTotal() { return grandTotal; }
    public void setGrandTotal(){ grandTotal = vatCost + productPrice; }

    private double[] productDimensions;
    public double[] getDimensions(){ return productDimensions; }
    public void setDimensions(double[] newDimensions){ productDimensions = newDimensions; }

    //TODO: add product image field


    public Product(int id){
        productId = id;
        productName = "";
        productPrice = 0;
        productDimensions = new double[]{0, 0, 0};

        //todo: possibly add default image for product constructor
    }
}
