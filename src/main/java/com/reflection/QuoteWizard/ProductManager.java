package com.reflection.QuoteWizard;

import java.util.List;

public class ProductManager{
    private DatabaseManager dm;
    private static List<Product> products;
    private Product selectedProduct;

    public ProductManager(){
        dm = new DatabaseManager();
    }

    public static List<Product> getList(){
        return products;
    }

    public Product getSelectedProduct(){
        return selectedProduct;
    }

    public static void InitialiseList(List currentData){
        products = currentData;
    }

    public void setSelectedProduct(Product newProduct){
        selectedProduct = newProduct;
    }

    public void addItem(Product itemToAdd) {
        products.add(itemToAdd);
        dm.InsertIntoProductDatabase(itemToAdd);
    }

    public void deleteItem(Product itemToDelete) {
        products.remove(itemToDelete);
        dm.DeleteFromDatabase(1, "idProduct", "" + itemToDelete.getId());
    }

    public void updateItem(Product updatedItem) {
        if(updatedItem.getName() != products.get(updatedItem.getId()).getName()){
            dm.UpdateDatabase(1, "productName", updatedItem.getId(), updatedItem.getName());
        }
        if(updatedItem.getPrice() != products.get(updatedItem.getId()).getPrice()){
            dm.UpdateDatabase(1, "productPrice", updatedItem.getId(), updatedItem.getPrice() + "");
        }
        if(updatedItem.getVatRate() != products.get(updatedItem.getId()).getVatRate()){
            dm.UpdateDatabase(1, "productVatRate", updatedItem.getId(), updatedItem.getVatRate() + "");
        }

        products.get(updatedItem.getId()).equals(updatedItem);
    }

    public Product getItem(int searchId){
        return products.get(searchId);
    }
}
