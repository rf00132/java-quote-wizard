package com.reflection.QuoteWizard;

import java.util.List;

public class BasketManager {
    private static List<QuoteItem> quoteItems;
    public static List<QuoteItem> getList(){ return quoteItems; }
    public static void InitialiseList(List currentData){
        quoteItems = currentData;
    }

    public void AddItem(QuoteItem itemToAdd) {
        quoteItems.add(itemToAdd);
        DatabaseManager.InsertIntoQuoteProductDatabase(itemToAdd);
    }

    public void DeleteItem(QuoteItem itemToDelete) {
        quoteItems.remove(itemToDelete);
        DatabaseManager.DeleteFromDatabase(2, "idQuoteProduct", "" + itemToDelete.getId());
    }

    public void UpdateItem(QuoteItem updatedItem) {
        if(updatedItem.getProductAmount() != quoteItems.get(updatedItem.getId()).getProductAmount()){
            DatabaseManager.UpdateDatabase(2, "amountInBasket", updatedItem.getId(), updatedItem.getProductAmount() + "");
        }
        quoteItems.get(updatedItem.getId()).equals(updatedItem);
    }

    public QuoteItem SearchForItem(int searchId) {
        return quoteItems.get(searchId);
    }
}
