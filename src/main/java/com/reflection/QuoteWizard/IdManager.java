package main.java.com.reflection.QuoteWizard;

public class IdManager {
    private static int nextFreeContactId;

    public static int GetNextContactId(){
        nextFreeContactId++;
        return nextFreeContactId - 1;
    }

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
}
