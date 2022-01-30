package com.reflection.QuoteWizard;

import java.util.ArrayList;
import java.util.List;

public class Quote{
    private int quoteId;
    public int getId(){ return quoteId; }

    private String quoteName;
    public String getName() { return quoteName; }
    public void setName(String newName){ quoteName = newName; }

    private String quoteContact;
    public String getContact(){ return quoteContact; }
    public void setContact(String newContact){ quoteContact = newContact; }

    private List<QuoteItem> basket;
    public List<QuoteItem> getBasket(){ return basket; }

    private double basketTotal;
    public double getBasketTotal() { return basketTotal; }

    private double basketVatTotal;
    public double getBasketVatTotal() { return basketVatTotal; }

    private int amountInBasket;
    public int getAmountInBasket(){ return amountInBasket; }


    public void UpdateProductTotals(){
        double newTotal = 0;
        double newVatTotal = 0;
        int newAmount = 0;

        for(QuoteItem item : basket){
            newTotal += item.getTotal();
            newVatTotal += item.getVatTotal();
            newAmount += item.getProductAmount();
        }

        basketTotal = newTotal;
        basketVatTotal = newVatTotal;
        amountInBasket = newAmount;
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

    public Quote(int id){
        quoteId = id;
        quoteName = "Quote " + quoteId;
        basket = new ArrayList<>();
    }
}
