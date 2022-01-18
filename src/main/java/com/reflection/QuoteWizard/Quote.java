package main.java.com.reflection.QuoteWizard;

import java.util.ArrayList;
import java.util.List;

public class Quote {
    private int quoteId;
    public int getId(){ return quoteId; }

    private String quoteName;
    public String getName() { return quoteName; }
    public void setName(String newName){ quoteName = newName; }

    private Contact quoteContact;
    public Contact getContact(){ return quoteContact; }
    public void setContact(Contact newContact){ quoteContact = newContact; }

    private List<QuoteItem> basket;
    public List<QuoteItem> getBasket(){ return basket; }

    private double basketTotal;
    public double getBasketTotal() { return basketTotal; }

    private double basketVatTotal;
    public double getBasketVatTotal() { return basketVatTotal; }

    public void UpdateProductTotals(){
        double newTotal = 0;
        double newVatTotal = 0;

        for(QuoteItem item : basket){
            newTotal += item.getTotal();
            newVatTotal += item.getVatTotal();
        }

        basketTotal = newTotal;
        basketVatTotal = newVatTotal;
    }

    public void AddToBasket(QuoteItem itemToAdd){
        basket.add(itemToAdd);
        basketTotal += itemToAdd.getTotal();
        basketVatTotal += itemToAdd.getVatTotal();
    }

    public void DeleteFromBasket(QuoteItem itemToDelete){
        basket.remove(itemToDelete);
        if(basketTotal - itemToDelete.getTotal() > 0) basketTotal -= itemToDelete.getTotal();
        else basketTotal = 0;
        if(basketTotal - itemToDelete.getTotal() > 0) basketVatTotal -= itemToDelete.getVatTotal();
        else basketVatTotal = 0;
    }

    public Quote(){
        quoteId = IdManager.GetNextQuoteId();
        quoteName = "Quote " + quoteId;
        basket = new ArrayList<>();
    }

    //TODO: add total price things e.g. total price and total price no vat
}
