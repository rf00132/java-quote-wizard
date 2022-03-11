package com.reflection.QuoteWizard;


import java.util.ArrayList;
import java.util.List;

import static com.reflection.QuoteWizard.HandlebarsModelHandler.DBMANAGER;
import static com.reflection.QuoteWizard.HandlebarsModelHandler.QUOTES;
import static com.reflection.QuoteWizard.Main.HBMH;

public class BasketManager {
    private static List<QuoteItem> quoteItems = new ArrayList<>();
    public List<QuoteItem> getList(){
        return quoteItems;
    }


    public void initialiseList(List<QuoteItem> currentData){
        quoteItems = currentData;
    }

    public void addItem(QuoteItem itemToAdd) {
        quoteItems.add(itemToAdd);
        DBMANAGER.insertIntoQuoteProductDatabase(itemToAdd);
    }

    public void deleteItem(QuoteItem itemToDelete) {

        DBMANAGER.deleteFromDatabase(2, 0, itemToDelete.getId());
        itemToDelete.getQuote().getBasket().remove(itemToDelete);
        quoteItems.remove(itemToDelete);
        HBMH.setSelectedQuote(itemToDelete.getQuote());
    }

    public void updateItem(QuoteItem updatedItem) {
        QuoteItem originalBasketItem = null;

        for(QuoteItem item : DBMANAGER.getBasketResults()){
            if(item.getId() == updatedItem.getId()){
                originalBasketItem = item;
                break;
            }
        }
        if(originalBasketItem == null) {
            return;
        }

        DBMANAGER.updateDatabase(2, 3, updatedItem.getId(), updatedItem.getProductAmount() + "");

        DBMANAGER.refreshAllSearchResults();
        DBMANAGER.setSearchResults();
    }

    public QuoteItem searchForItem(int id) {
        QuoteItem itemToReturn;
        for(int i = 0; i < quoteItems.size(); i++){
            if (quoteItems.get(i).getId() == id){
                return quoteItems.get(i);
            }
        }

        System.out.println("no item with that id found");
        return null;
    }





}
