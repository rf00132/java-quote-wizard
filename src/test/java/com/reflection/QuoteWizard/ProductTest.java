package test.java.com.reflection.QuoteWizard;
import main.java.com.reflection.QuoteWizard.Product;

import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.assertTrue;

public class ProductTest {

    Product testProduct;

    @Before
    public void setUp() {
        testProduct = new Product();
    }

    @Test
    public void setNameTest(){
        testProduct.setName("Cloth");
        assertTrue(testProduct.getName().equals("Cloth"));
    }

    @Test
    public void setPriceTest(){
        testProduct.setPrice(12.32);
        assertTrue(testProduct.getPrice() == 12.32);
    }

    @Test
    public void setDimensions(){
        double[] dimsToReplace = new double[]{1,2,3};
        testProduct.setDimensions(dimsToReplace);
        double[] dimsRecBack = testProduct.getDimensions();
        assertTrue(dimsRecBack[0] == 1 && dimsRecBack[1] == 2 && dimsRecBack[2] == 3);
    }



}
