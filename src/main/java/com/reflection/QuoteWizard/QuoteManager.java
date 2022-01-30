package com.reflection.QuoteWizard;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class QuoteManager{
    private DatabaseManager dm;
    private List<Quote> quotes = new ArrayList<>();
    private Quote selectedQuote;

    public QuoteManager(){
        dm = new DatabaseManager();
    }

    public List<Quote> getList(){
        return quotes;
    }

    public Quote getSelectedQuote(){
        return selectedQuote;
    }

    public void InitialiseList(List currentData){
        quotes = currentData;
    }

    public void setSelectedQuote( Quote newSelection) {
        selectedQuote = newSelection;
    }

    public void addItem(Quote itemToAdd) {
        quotes.add(itemToAdd);
        //dm.InsertIntoQuoteDatabase(itemToAdd);
    }

    public void DeleteItem(Quote itemToDelete) {
        quotes.remove(itemToDelete);
        //dm.DeleteFromDatabase(0, "idQuote", "" + itemToDelete.getId());
    }

    public void UpdateItem(Quote updatedItem) {
        if(updatedItem.getName() != quotes.get(updatedItem.getId()).getName()){
            //dm.UpdateDatabase(1, "quoteName", updatedItem.getId(), updatedItem.getName());
            quotes.get(updatedItem.getId()).setName(updatedItem.getName());
        }
        if(updatedItem.getContact() != quotes.get(updatedItem.getId()).getContact()){
           // dm.UpdateDatabase(1, "quoteContact", updatedItem.getId(), updatedItem.getContact());
            quotes.get(updatedItem.getId()).setContact(updatedItem.getContact());
        }
        if(updatedItem.getBasket() != quotes.get(updatedItem.getId()).getBasket()){
            quotes.get(updatedItem.getId()).setBasket(updatedItem.getBasket());
        }
    }

    public Quote SearchForItem(int searchId) {
        return quotes.get(searchId);
    }
}
