package com.reflection.QuoteWizard;

import java.util.ArrayList;
import java.util.List;

public class ProductManager{
    private final DatabaseManager dm;
    private List<Product> products = new ArrayList<>();
    private Product selectedProduct;

    public ProductManager(){
        dm = new DatabaseManager();
    }

    public List<Product> getList(){
        return products;
    }

    public Product getSelectedProduct(){
        return selectedProduct;
    }

    public void InitialiseList(List<Product> currentData){
        products = currentData;
    }

    public void setSelectedProduct(Product newProduct){
        selectedProduct = newProduct;
    }

    public void addItem(Product itemToAdd) {
        products.add(itemToAdd);
        //dm.InsertIntoProductDatabase(itemToAdd);
    }

    public void deleteItem(Product itemToDelete) {
        products.remove(itemToDelete);
        //dm.DeleteFromDatabase(1, "idProduct", "" + itemToDelete.getId());
    }

    public void updateItem(Product updatedItem) {
        if(!updatedItem.getName().equals(products.get(updatedItem.getId()).getName())){
            //dm.UpdateDatabase(1, "productName", updatedItem.getId(), updatedItem.getName());
            products.get(updatedItem.getId()).setName(updatedItem.getName());
        }
        if(!updatedItem.getPrice().equals(products.get(updatedItem.getId()).getPrice())){
            //dm.UpdateDatabase(1, "productPrice", updatedItem.getId(), updatedItem.getPrice() + "");
            products.get(updatedItem.getId()).setPrice(updatedItem.getPrice());
        }
        if(!updatedItem.getVatRate().equals(products.get(updatedItem.getId()).getVatRate())){
            //dm.UpdateDatabase(1, "productVatRate", updatedItem.getId(), updatedItem.getVatRate() + "");
            products.get(updatedItem.getId()).setVatRate(updatedItem.getVatRate());
        }


    }

    public Product getItem(int searchId){
        return products.get(searchId);
    }
}
