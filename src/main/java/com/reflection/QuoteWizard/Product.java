package main.java.com.reflection.QuoteWizard;

import java.util.Vector;

public class Product {
    private int productId;
    public int getId(){ return productId; }

    private String productName;
    public String getName(){ return productName; }
    public void setName(String newName){ productName = newName; }

    private double productPrice;
    public double getPrice(){ return productPrice; }
    public void setPrice(double newPrice){ productPrice = newPrice; }

    private double[] productDimensions;
    public double[] getDimensions(){ return productDimensions; }
    public void setDimensions(double[] newDimensions){ productDimensions = newDimensions; }

    //TODO: add product image field


    public Product(){
        productId = IdManager.GetNextProductId();
        productName = "";
        productPrice = 0;
        productDimensions = new double[]{0, 0, 0};

        //todo: possibly add default image for product constructor
    }
}
