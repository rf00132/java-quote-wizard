package com.reflection.QuoteWizard;

import java.util.ArrayList;
import java.util.List;

import static com.reflection.QuoteWizard.HandlebarsModelHandler.DBMANAGER;
import static com.reflection.QuoteWizard.HandlebarsModelHandler.QUOTES;

public class ProductManager{
    private List<Product> products = new ArrayList<>();
    private Product selectedProduct;

    public List<Product> getList(){
        return products;
    }

    public Product getSelectedProduct(){
        return selectedProduct;
    }

    public void initialiseList(List<Product> currentData){
        products = currentData;
    }

    public void setSelectedProduct(Product newProduct){
        selectedProduct = newProduct;
    }

    public void addItem(Product itemToAdd) {
        products.add(itemToAdd);
        DBMANAGER.insertIntoProductDatabase(itemToAdd);
    }

    public void deleteItem(Product itemToDelete) {
        for(Quote quote : QUOTES.getList()){
            for(QuoteItem item : quote.getBasket()){
                if(item.getProduct().getId() == itemToDelete.getId()){
                    System.out.println("deleting " + item.getName() + " from db");
                    DBMANAGER.deleteFromDatabase(2, 0, item.getId());
                }
            }
        }

        products.remove(itemToDelete);
        DBMANAGER.deleteFromDatabase(1, 0, itemToDelete.getId());
        DBMANAGER.refreshAllSearchResults();
        DBMANAGER.setSearchResults();
    }

    public void updateItem(Product updatedItem) {
        Product originalProduct = null;
        for(Product product : DBMANAGER.getProductResults()){
            if(product.getId() == updatedItem.getId()){
                originalProduct = product;
                break;
            }
        }

        Product productToUpdate = searchForProduct(updatedItem.getId());

        if(!updatedItem.getName().equals(originalProduct.getName())){
            DBMANAGER.updateDatabase(1, 1, updatedItem.getId(), updatedItem.getName());
            productToUpdate.setName(updatedItem.getName());        }
        if(!updatedItem.getProductPrice().equals(originalProduct.getProductPrice())){
            DBMANAGER.updateDatabase(1, 2, updatedItem.getId(), updatedItem.getProductPrice() + "");
            productToUpdate.setPrice(updatedItem.getProductPrice());
        }
        if(!updatedItem.getVatRate().equals(originalProduct.getVatRate())){
            DBMANAGER.updateDatabase(1, 3, updatedItem.getId(), updatedItem.getVatRate() + "");
            productToUpdate.setVatRate(updatedItem.getVatRate());
        }
        DBMANAGER.refreshAllSearchResults();
        DBMANAGER.setSearchResults();
    }

    public Product searchForProduct(int id){
        for(int i = 0; i < products.size(); i++){
            if (products.get(i).getId() == id){
                return products.get(i);
            }
        }

        System.out.println("no product with that id found");
        return null;
    }






}
