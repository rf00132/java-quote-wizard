package com.reflection.QuoteWizard;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DatabaseTest {

    @Before
    public void setUp(){
        DatabaseManager.InitDataBases();
        DatabaseManager.InsertIntoTestDatabase(
                "asdf",1);
    }

    @Test
    public void searchTest(){
        List list = DatabaseManager.SearchDatabase(3, "testString", "asdf");

    }
}
