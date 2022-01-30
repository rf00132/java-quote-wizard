package com.reflection.QuoteWizard;

import java.util.ArrayList;
import java.util.List;

public class BasketManager {
    private DatabaseManager dm;
    private static List<QuoteItem> quoteItems = new ArrayList<>();

    public BasketManager(){
        dm = new DatabaseManager();
    }

    public static List<QuoteItem> getList(){
        return quoteItems;
    }

    public static void InitialiseList(List currentData){
        quoteItems = currentData;
    }

    public void AddItem(QuoteItem itemToAdd) {
        quoteItems.add(itemToAdd);
        //dm.InsertIntoQuoteProductDatabase(itemToAdd);
    }

    public void DeleteItem(QuoteItem itemToDelete) {
        quoteItems.remove(itemToDelete);
        //dm.DeleteFromDatabase(2, "idQuoteProduct", "" + itemToDelete.getId());
    }

    public void UpdateItem(QuoteItem updatedItem) {
        if(updatedItem.getProductAmount() != quoteItems.get(updatedItem.getId()).getProductAmount()){
            //dm.UpdateDatabase(2, "amountInBasket", updatedItem.getId(), updatedItem.getProductAmount() + "");
            int currentAmount = quoteItems.get(updatedItem.getId()).getProductAmount();
            quoteItems.get(updatedItem.getId()).incrementProductAmount(updatedItem.getProductAmount() - currentAmount);
        }

    }

    public QuoteItem SearchForItem(int searchId) {
        return quoteItems.get(searchId);
    }


}
