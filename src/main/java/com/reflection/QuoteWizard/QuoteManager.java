package com.reflection.QuoteWizard;

import java.util.List;

public class QuoteManager{
    private static List<Quote> quotes;
    public static List<Quote> getList(){ return quotes; }
    public static void InitialiseList(List currentData){
        quotes = currentData;
    }

    private static Quote selectedQuote;
    public static Quote getSelectedQuote(){
        return selectedQuote;
    }
    public static void setSelectedQuote( Quote newSelection) {
        selectedQuote = newSelection;
    }

    public void AddItem(Quote itemToAdd) {
        quotes.add(itemToAdd);
        DatabaseManager.InsertIntoQuoteDatabase(itemToAdd);
    }

    public void DeleteItem(Quote itemToDelete) {
        quotes.remove(itemToDelete);
        DatabaseManager.DeleteFromDatabase(0, "idQuote", "" + itemToDelete.getId());
    }

    public void UpdateItem(Quote updatedItem) {
        if(updatedItem.getName() != quotes.get(updatedItem.getId()).getName()){
            DatabaseManager.UpdateDatabase(1, "quoteName", updatedItem.getId(), updatedItem.getName());
        }
        if(updatedItem.getContact() != quotes.get(updatedItem.getId()).getContact()){
            DatabaseManager.UpdateDatabase(1, "quoteContact", updatedItem.getId(), updatedItem.getContact());
        }

        quotes.get(updatedItem.getId()).equals(updatedItem);
    }

    public Quote SearchForItem(int searchId) {
        return quotes.get(searchId);
    }


}
