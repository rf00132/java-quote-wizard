package com.reflection.QuoteWizard;

import java.util.List;

public class ProductManager{
    private static List<Product> products;
    public static List<Product> getList(){ return products;}
    public static void InitialiseList(List currentData){
        products = currentData;
    }

    private Product selectedProduct;
    public Product getSelectedProduct(){ return selectedProduct; }
    public void setSelectedProduct(Product newProduct){ selectedProduct = newProduct; }

    public void AddItem(Product itemToAdd) {
        products.add(itemToAdd);
        DatabaseManager.InsertIntoProductDatabase(itemToAdd);
    }

    public void DeleteItem(Product itemToDelete) {
        products.remove(itemToDelete);
        DatabaseManager.DeleteFromDatabase(1, "idProduct", "" + itemToDelete.getId());
    }

    public void UpdateItem(Product updatedItem) {
        if(updatedItem.getName() != products.get(updatedItem.getId()).getName()){
            DatabaseManager.UpdateDatabase(1, "productName", updatedItem.getId(), updatedItem.getName());
        }
        if(updatedItem.getPrice() != products.get(updatedItem.getId()).getPrice()){
            DatabaseManager.UpdateDatabase(1, "productPrice", updatedItem.getId(), updatedItem.getPrice() + "");
        }
        if(updatedItem.getVatRate() != products.get(updatedItem.getId()).getVatRate()){
            DatabaseManager.UpdateDatabase(1, "productVatRate", updatedItem.getId(), updatedItem.getVatRate() + "");
        }

        products.get(updatedItem.getId()).equals(updatedItem);
    }

    public Product GetItem(int searchId){
        return products.get(searchId);
    }
}
