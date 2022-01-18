package main.java.com.reflection.QuoteWizard;

public class QuoteItem {
    private double vatRate = 0.2;
    public double getVatRate() { return vatRate; }

    private Product product;
    public Product getProduct(){ return product; }

    private double productTotal;
    public double getTotal(){ return productTotal; }
    private void setTotal(){ productTotal = product.getPrice() * productAmount; }

    private double productTotalVat;
    public double getVatTotal() { return productTotalVat; }
    private void setVatTotal(){ productTotalVat = productTotal*( 1 + vatRate ); }

    private int productAmount;
    public int getProductAmount(){ return productAmount; }
    public void incrementProductAmount(int amountToAdd){
        productAmount += amountToAdd;
        if(productAmount < 0) productAmount = 0;
        setTotal();
        setVatTotal();
    }

    public QuoteItem(Product product){
        this.product = product;
        productAmount = 1;
        productTotal = this.product.getPrice();
    }


}
