package com.reflection.QuoteWizard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class DatabaseManager {
    static Connection con;
    static Statement smt;
    static ResultSet rs;

    static List<String> StatementTypes = List.of("SELECT", "UPDATE", "DELETE", "INSERT");
    static List<String> TableNames = List.of("Quote", "Product", "QuoteProduct", "TestDB");

    public static void InitDataBases(){
        try {
            //TODO: set up connection properly to database
            con = DriverManager.getConnection("jdbc:mysql://localhost/6543?" +
                                                  "user=db&password=user");
            QuoteManager.InitialiseList(GetWholeTable(0));
            ProductManager.InitialiseList(GetWholeTable(1));
            BasketManager.InitialiseList(GetWholeTable(2));
        } catch (SQLException ex) {
            //TODO: handle errors
        }
    }

    public static List GetWholeTable(int tableToQueryIndex){
        try{
            String query = StatementTypes.get(0) + "SELECT * FROM " + TableNames.get(tableToQueryIndex);
            smt = con.createStatement();
            rs = smt.executeQuery(query);
            return ResultsToList(tableToQueryIndex);
        } catch (SQLException ex){
            //TODO: handle errors
            return null;
        }
    }

    public static List SearchDatabase(int tableToQueryIndex, String columnToQuery, String queryTerm){
        try{
            String query = StatementTypes.get(0) + "SELECT * FROM " + TableNames.get(tableToQueryIndex)
                    + " WHERE " + columnToQuery + " LIKE '%" + queryTerm+"%'";
            smt = con.createStatement();
            rs = smt.executeQuery(query);
            return ResultsToList(tableToQueryIndex);
        } catch (SQLException ex){
            //TODO: handle errors
            return null;
        }
    }

    public static void UpdateDatabase(int tableToQueryIndex, String columnToQuery, int productId, String updatedTerm ){

        try{
            String query = "UPDATE " + TableNames.get(tableToQueryIndex) + " set " + columnToQuery
                    + " = " + updatedTerm + " WHERE idProduct = " + productId;
            smt.executeQuery(query);
        } catch (SQLException ex){
            //TODO: handle errors
        }
    }

    public static void DeleteFromDatabase(int tableToQueryIndex, String columnToQuery, String queryTerm) {
        try{
            String query = StatementTypes.get(2) + " FROM " + TableNames.get(tableToQueryIndex)
                    + " WHERE " + columnToQuery + "=" + queryTerm;
            smt.executeQuery(query);
        } catch (SQLException ex){
            //TODO: handle errors
        }
    }

    public static void InsertIntoQuoteDatabase(Quote newEntry) {
        try{
            String query = StatementTypes.get(3) + " INTO " + TableNames.get(0)
                    + " VALUES (" + newEntry.getName() + ", " + newEntry.getContact() + ")";
            smt.executeQuery(query);
        } catch (SQLException ex){
            //TODO: handle errors
        }
    }

    public static void InsertIntoProductDatabase(Product newEntry) {
        try{
            String query = StatementTypes.get(3) + " INTO " + TableNames.get(1)
                    + " VALUES (" + newEntry.getName() + ", " + newEntry.getPrice() + ", " + newEntry.getVatRate() +  ")";
            smt.executeQuery(query);
        } catch (SQLException ex){
            //TODO: handle errors
        }
    }

    public static void InsertIntoQuoteProductDatabase(QuoteItem newEntry) {
        try{
            String query = StatementTypes.get(3) + " INTO " + TableNames.get(2)
                    + " VALUES (" + newEntry.getQuote().getId() + ", " + newEntry.getProduct().getId() + ")";
            smt.executeQuery(query);
        } catch (SQLException ex){
            //TODO: handle errors
        }
    }

    public static void InsertIntoTestDatabase(String testString, int testInt) {
        try{
            String query = StatementTypes.get(3) + " INTO " + TableNames.get(3)
                    + " VALUES (" + testString + ", " + testInt + ")";
            smt.executeQuery(query);
        } catch (SQLException ex){
            //TODO: handle errors
        }
    }



    private static List ResultsToList(int tableIndex) throws SQLException {
        List listOfResults = new ArrayList<>();

        while(rs.next()){
            switch(tableIndex){
                case 0:
                    Quote quote = new Quote(rs.getInt("idQuote"));
                    quote.setName(rs.getString("quoteName"));
                    quote.setContact(rs.getString("quoteContact"));
                    //TODO: add code to get basket for each quote before adding to the list
                    listOfResults.add(quote);

                    break;
                case 1:
                    Product product = new Product(rs.getInt("idProduct"));
                    product.setName(rs.getString("productName"));
                    product.setPrice(rs.getDouble("productPrice"));
                    product.setVatRate(rs.getDouble("productVatRate"));
                    listOfResults.add(product);
                    break;
                case 2:
                    Product basketProduct = ProductManager.getList().get(rs.getInt("idProduct"));
                    Quote basketQuote = QuoteManager.getList().get(rs.getInt("idQuote"));
                    QuoteItem quoteItem = new QuoteItem(rs.getInt("idQuoteProduct"), basketProduct, basketQuote);
                    quoteItem.incrementProductAmount(rs.getInt("amountInBasket"));
                    listOfResults.add(quoteItem);
                    break;
                case 3:
                    //TODO: add test db code if i add it in the end
                    break;
                default:
                    break;
            }
        }
        return listOfResults;
    }
}
