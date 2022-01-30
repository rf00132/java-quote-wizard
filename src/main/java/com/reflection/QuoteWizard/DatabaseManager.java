package com.reflection.QuoteWizard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class DatabaseManager {
    /*Connection con;

    final List<String> StatementTypes = List.of("SELECT", "UPDATE", "DELETE", "INSERT");
    final List<String> TableNames = List.of("Quote", "Product", "QuoteProduct", "TestDB");

    public DatabaseManager(){
        InitDataBases();
    }

    //todo: look into prepared statements

    public void InitDataBases(){
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

    public List GetWholeTable(int tableToQueryIndex){
        try{
            String query = StatementTypes.get(0) + "SELECT * FROM " + TableNames.get(tableToQueryIndex);
            var smt = con.createStatement();
            var rs = smt.executeQuery(query);
            return ResultsToList(tableToQueryIndex);
        } catch (SQLException ex){
            //TODO: handle errors
            return null;
        }
    }

    public List SearchDatabase(int tableToQueryIndex, String columnToQuery, String queryTerm){
        try{
            String query = StatementTypes.get(0) + "SELECT * FROM " + TableNames.get(tableToQueryIndex)
                    + " WHERE " + columnToQuery + " LIKE '%" + queryTerm+"%'";
            var smt = con.prepareStatement(query);

            return ResultsToList(tableToQueryIndex);
        } catch (SQLException ex){
            //TODO: handle errors
            return null;
        }
    }

    public void UpdateDatabase(int tableToQueryIndex, String columnToQuery, int productId, String updatedTerm ){

        try{
            String query = "UPDATE " + TableNames.get(tableToQueryIndex) + " set " + columnToQuery
                    + " = " + updatedTerm + " WHERE idProduct = " + productId;
            smt.executeQuery(query);
        } catch (SQLException ex){
            //TODO: handle errors
        }
    }

    public void DeleteFromDatabase(int tableToQueryIndex, String columnToQuery, String queryTerm) {
        try{
            String query = StatementTypes.get(2) + " FROM " + TableNames.get(tableToQueryIndex)
                    + " WHERE " + columnToQuery + "=" + queryTerm;
            smt.executeQuery(query);
        } catch (SQLException ex){
            //TODO: handle errors
        }
    }

    public void InsertIntoQuoteDatabase(Quote newEntry) {
        try{
            String query = StatementTypes.get(3) + " INTO " + TableNames.get(0)
                    + " VALUES (" + newEntry.getName() + ", " + newEntry.getContact() + ")";
            smt.executeQuery(query);
        } catch (SQLException ex){
            //TODO: handle errors
        }
    }

    public void InsertIntoProductDatabase(Product newEntry) {
        try{
            String query = StatementTypes.get(3) + " INTO " + TableNames.get(1)
                    + " VALUES (" + newEntry.getName() + ", " + newEntry.getPrice() + ", " + newEntry.getVatRate() +  ")";
            smt.executeQuery(query);
        } catch (SQLException ex){
            //TODO: handle errors
        }
    }

    public void InsertIntoQuoteProductDatabase(QuoteItem newEntry) {
        try{
            String query = StatementTypes.get(3) + " INTO " + TableNames.get(2)
                    + " VALUES (" + newEntry.getQuote().getId() + ", " + newEntry.getProduct().getId() + ")";
            smt.executeQuery(query);
        } catch (SQLException ex){
            //TODO: handle errors
        }
    }

    public void InsertIntoTestDatabase(String testString, int testInt) {
        try{
            String query = StatementTypes.get(3) + " INTO " + TableNames.get(3)
                    + " VALUES (" + testString + ", " + testInt + ")";
            smt.executeQuery(query);
        } catch (SQLException ex){
            //TODO: handle errors
        }
    }



    private List ResultsToList(int tableIndex) throws SQLException {
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
                    product.setPrice(rs.getBigDecimal("productPrice"));
                    product.setVatRate(rs.getBigDecimal("productVatRate"));
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
    */

}
