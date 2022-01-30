package com.reflection.QuoteWizard;

import javax.xml.crypto.Data;
import java.util.List;

public class QuoteManager{
    private DatabaseManager dm;
    private static List<Quote> quotes;
    private static Quote selectedQuote;

    public QuoteManager(){
        dm = new DatabaseManager();
    }

    public static List<Quote> getList(){
        return quotes;
    }

    public static Quote getSelectedQuote(){
        return selectedQuote;
    }

    public static void InitialiseList(List currentData){
        quotes = currentData;
    }

    public static void setSelectedQuote( Quote newSelection) {
        selectedQuote = newSelection;
    }

    public void AddItem(Quote itemToAdd) {
        quotes.add(itemToAdd);
        dm.InsertIntoQuoteDatabase(itemToAdd);
    }

    public void DeleteItem(Quote itemToDelete) {
        quotes.remove(itemToDelete);
        dm.DeleteFromDatabase(0, "idQuote", "" + itemToDelete.getId());
    }

    public void UpdateItem(Quote updatedItem) {
        if(updatedItem.getName() != quotes.get(updatedItem.getId()).getName()){
            dm.UpdateDatabase(1, "quoteName", updatedItem.getId(), updatedItem.getName());
        }
        if(updatedItem.getContact() != quotes.get(updatedItem.getId()).getContact()){
            dm.UpdateDatabase(1, "quoteContact", updatedItem.getId(), updatedItem.getContact());
        }

        quotes.get(updatedItem.getId()).equals(updatedItem);
    }

    public Quote SearchForItem(int searchId) {
        return quotes.get(searchId);
    }
}
