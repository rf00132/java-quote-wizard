package com.reflection.QuoteWizard;

public class IdManager {
    private static int nextFreeQuoteId;

    public static int GetNextQuoteId(){
        nextFreeQuoteId++;
        return nextFreeQuoteId - 1;
    }

    private static int nextFreeProductId;

    public static int GetNextProductId(){
        nextFreeProductId++;
        return nextFreeProductId - 1;
    }

    private static int nextFreeQuoteItemId;

    public static int GetNextQuoteItemId(){
        nextFreeQuoteItemId++;
        return nextFreeQuoteItemId - 1;
    }

    public static void InitializeIdValues(){
        if( ProductManager.getList() != null )
            nextFreeProductId = ProductManager.getList().size();

        if( QuoteManager.getList() != null )
            nextFreeQuoteId = QuoteManager.getList().size();

        if( QuoteManager.getList() != null )
            nextFreeQuoteItemId = BasketManager.getList().size();
    }

}
