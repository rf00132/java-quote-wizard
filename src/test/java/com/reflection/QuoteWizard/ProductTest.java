package com.reflection.QuoteWizard;
import org.junit.*;
import static org.junit.Assert.*;

public class ProductTest {

    Product testProduct;

    @Before
    public void setUp() {
        testProduct = new Product(0);
    }

    @Test
    public void setNameTest(){
        testProduct.setName("Cloth");
        assertEquals("Cloth", testProduct.getName());
    }

    @Test
    public void setPriceTest(){
        testProduct.setPrice(12.32);
        assertEquals(12.32, testProduct.getPrice(), 0.0);
    }

    @Test
    public void setDimensions(){
        double[] dimsToReplace = new double[]{1,2,3};
        testProduct.setDimensions(dimsToReplace);
        double[] dimensionsReturned = testProduct.getDimensions();
        assertTrue(dimensionsReturned[0] == 1 && dimensionsReturned[1] == 2 && dimensionsReturned[2] == 3);
    }



}
