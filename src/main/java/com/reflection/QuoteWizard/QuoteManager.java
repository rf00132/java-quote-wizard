package com.reflection.QuoteWizard;

import java.util.ArrayList;
import java.util.List;

import static com.reflection.QuoteWizard.HandlebarsModelHandler.DBMANAGER;
import static com.reflection.QuoteWizard.HandlebarsModelHandler.QUOTES;

public class QuoteManager{
    private List<Quote> quotes = new ArrayList<>();
    private Quote selectedQuote;


    public List<Quote> getList(){
        return quotes;
    }

    public Quote getSelectedQuote(){
        return selectedQuote;
    }

    public void initialiseList(List<Quote> currentData){
        quotes = currentData;

    }

    public void setSelectedQuote( Quote newSelection) {
        selectedQuote = newSelection;
    }

    public void addItem(Quote itemToAdd) {
        quotes.add(itemToAdd);
        DBMANAGER.insertIntoQuoteDatabase(itemToAdd);
    }

    public void deleteItem(Quote itemToDelete) {
        quotes.remove(itemToDelete);
        for(QuoteItem item : itemToDelete.getBasket()){
            DBMANAGER.deleteFromDatabase(2, 0, item.getId());
        }
        DBMANAGER.deleteFromDatabase(0, 0, itemToDelete.getId());

    }

    public void updateItem(Quote updatedItem) {
        Quote originalQuote = null;
        for(Quote quote : DBMANAGER.getQuoteResults()){
            if(quote.getId() == updatedItem.getId()){
                originalQuote = quote;
                break;
            }
        }
        Quote quoteToUpdate = QUOTES.searchForQuote(updatedItem.getId());
        if(!updatedItem.getQuoteName().equals(originalQuote.getQuoteName())){
            DBMANAGER.updateDatabase(0, 1, updatedItem.getId(), updatedItem.getQuoteName());
            quoteToUpdate.setName(updatedItem.getQuoteName());
        }
        if(!updatedItem.getContact().equals(originalQuote.getContact())){
           DBMANAGER.updateDatabase(0, 2, updatedItem.getId(), updatedItem.getContact());
           quoteToUpdate.setContact(updatedItem.getContact());
        }

        DBMANAGER.refreshAllSearchResults();
        DBMANAGER.setSearchResults();
    }

    public Quote searchForQuote(int id) {
        for(int i = 0; i < quotes.size(); i++){
            if (quotes.get(i).getId() == id){
                return quotes.get(i);
            }
        }

        System.out.println("no item with that id found");
        return null;
    }

}
