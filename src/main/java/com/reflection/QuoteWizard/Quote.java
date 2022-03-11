package com.reflection.QuoteWizard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.reflection.QuoteWizard.HandlebarsModelHandler.BASKETS;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;

public class Quote{
    private final int quoteId;
    private String quoteName;
    private String quoteContact;
    private List<QuoteItem> basket;
    private BigDecimal basketTotal;
    private BigDecimal basketVatTotal;
    private int amountInBasket;

    public Quote(int id){
        quoteId = id;
        quoteName = "Quote " + (quoteId);
        quoteContact = "";
        basket = new ArrayList<>();
        basketTotal = ZERO;
        basketVatTotal = ZERO;
    }

    public int getId(){
        return quoteId;
    }

    public String getQuoteName() {
        return quoteName;
    }

    public String getContact(){
        return quoteContact;
    }

    public List<QuoteItem> getBasket(){
        return basket;
    }

    public BigDecimal getBasketTotal() {
        return basketTotal;
    }

    public BigDecimal getBasketVatTotal() {
        return basketVatTotal;
    }

    public int getAmountInBasket(){
        return amountInBasket;
    }

    public void setName(String newName){
        quoteName = newName;
    }

    public void setContact(String newContact){
        quoteContact = newContact;
    }

    public void setBasket(List<QuoteItem> newBasket){
        basket = newBasket;
    }

    public void updateProductTotals() {
        BigDecimal newTotal = ZERO;
        BigDecimal newVatTotal = ZERO;
        int newAmount = 0;

        for(int i = 0; i < basket.size(); i++) {
            QuoteItem item = basket.get(i);
            newTotal = newTotal.add(item.getTotal()).setScale(2, HALF_UP);
            newVatTotal = newVatTotal.add(item.getVatTotal()).setScale(2, HALF_UP);
            newAmount += item.getProductAmount();
        }

        basketTotal = newTotal;
        basketVatTotal = newVatTotal;
        amountInBasket = newAmount;
    }

    public void addToBasket(QuoteItem itemToAdd) {
        basket.add(itemToAdd);
        updateProductTotals();
    }

    public void DeleteFromBasket(QuoteItem itemToDelete) {
        basket.remove(itemToDelete);
        if (basketTotal.compareTo(itemToDelete.getTotal()) > 0) {
            basketTotal = basketTotal.subtract(itemToDelete.getTotal()).setScale(2, HALF_UP);
        } else {
            basketTotal = ZERO;
        }

        if (basketVatTotal.compareTo(itemToDelete.getTotal()) > 0) {
            basketVatTotal = basketVatTotal.subtract(itemToDelete.getVatTotal()).setScale(2, HALF_UP);
        } else {
            basketVatTotal = ZERO;
        }
    }

    //returns true if product is in the basket
    public boolean isProductInBasket(Product searchProduct){
        for(QuoteItem item : basket){
            if(item.getProduct() == searchProduct){
                return true;
            }
        }
        return false;
    }

    public void refreshBasket(){
        basket.clear();
        for (QuoteItem item : BASKETS.getList()){
            if(item.getQuote().getId() == quoteId){
                addToBasket(item);
            }
        }
        updateProductTotals();
    }

    public QuoteItem getItemFromBasket(int id){
        for(QuoteItem item : basket){
            if(item.getId() == id){
                return item;
            }
        }

        System.out.println("no item found with that id");
        return null;
    }
}
