package com.reflection.QuoteWizard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.ZERO;

public class Quote{
    private int quoteId;
    private String quoteName;
    private String quoteContact;
    private List<QuoteItem> basket;
    private BigDecimal basketTotal;
    private BigDecimal basketVatTotal;
    private int amountInBasket;

    public Quote(int id){
        quoteId = id;
        quoteName = "Quote " + quoteId;
        basket = new ArrayList<>();
        basketTotal = ZERO;
        basketVatTotal = ZERO;
    }

    public int getId(){ return quoteId; }
    public String getName() { return quoteName; }
    public String getContact(){ return quoteContact; }
    public List<QuoteItem> getBasket(){ return basket; }
    public BigDecimal getBasketTotal() { return basketTotal; }
    public BigDecimal getBasketVatTotal() { return basketVatTotal; }
    public int getAmountInBasket(){ return amountInBasket; }

    public void setName(String newName){ quoteName = newName; }
    public void setContact(String newContact){ quoteContact = newContact; }

    private void UpdateProductTotals(){
        BigDecimal newTotal = ZERO;
        BigDecimal newVatTotal = ZERO;
        int newAmount = 0;

        for(QuoteItem item : basket){
            newTotal.add(item.getTotal());
            newVatTotal.add(item.getVatTotal());
            newAmount += item.getProductAmount();
        }

        basketTotal = newTotal;
        basketVatTotal = newVatTotal;
        amountInBasket = newAmount;
    }

    public void AddToBasket(QuoteItem itemToAdd){
        basket.add(itemToAdd);
        basketTotal = basketTotal.add(itemToAdd.getTotal());
        basketVatTotal = basketVatTotal.add(itemToAdd.getVatTotal());
    }

    public void DeleteFromBasket(QuoteItem itemToDelete){
        basket.remove(itemToDelete);
        if(basketTotal.doubleValue() - itemToDelete.getTotal().doubleValue() > 0) {
            basketTotal = basketTotal.subtract(itemToDelete.getTotal());
        }
        else {
            basketTotal = ZERO;
        }

        if(basketVatTotal.doubleValue() - itemToDelete.getTotal().doubleValue() > 0) {
            basketVatTotal = basketVatTotal.subtract(itemToDelete.getVatTotal());
        }
        else{
            basketVatTotal = ZERO;
        }
    }


}
