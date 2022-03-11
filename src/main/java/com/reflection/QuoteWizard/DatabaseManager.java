package com.reflection.QuoteWizard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.reflection.QuoteWizard.HandlebarsModelHandler.*;

public class DatabaseManager {

    final List<String> StatementTypes = List.of("SELECT", "UPDATE", "DELETE", "INSERT");
    final List<String> TableNames = List.of("Quotes", "Products", "Baskets", "TestDB");
    final List<List<String>> ColumnNames = List.of(
            List.of("quoteId", "quoteName", "quoteContact"),
            List.of("productId", "productName", "productPrice", "productVatRate"),
            List.of("basketId", "productId", "quoteId", "numOfItems")
    );

    final String dbRealUrl = "jdbc:sqlite:quoteManager.db";
    final String dbTestUrl = "jdbc:sqlite:quoteManagerTest.db";
    private String dbUrl = dbRealUrl;

    private List<Quote> quoteSearchResults;
    private List<Product> productSearchResults;
    private List<QuoteItem> basketSearchResults;

    public List<Quote> getQuoteResults(){
        return quoteSearchResults;
    }

    public List<Product> getProductResults(){
        return productSearchResults;
    }

    public List<QuoteItem> getBasketResults(){
        return basketSearchResults;
    }

    public void setTestDbUrl(){
        dbUrl = dbTestUrl;
    }

    public void setRealDbUrl(){
        dbUrl = dbRealUrl;
    }

    public void initDataBases() {
        quoteSearchResults = new ArrayList<>();
        productSearchResults = new ArrayList<>();
        basketSearchResults = new ArrayList<>();
        getWholeTable(0);
        QUOTES.initialiseList(quoteSearchResults);

        getWholeTable(1);
        PRODUCTS.initialiseList(productSearchResults);

        getWholeTable(2);
        BASKETS.initialiseList(basketSearchResults);

        for(Quote quote : QUOTES.getList()){
            quote.refreshBasket();
        }
    }

    public void getWholeTable(int tableToQueryIndex) {
        try (Connection con = DriverManager.getConnection(dbUrl)) {
            String query = "SELECT * FROM " + TableNames.get(tableToQueryIndex);

            PreparedStatement smt = con.prepareStatement(query);

            try(ResultSet rs = smt.executeQuery()) {

                resultsToList(tableToQueryIndex, rs);
                smt.close();
            } catch(SQLException ex){
                System.out.println(ex);
            }

        } catch (SQLException ex) {
           System.out.println(ex);
        }
    }

    public void insertIntoQuoteDatabase(Quote newEntry) {
        try (Connection con = DriverManager.getConnection(dbUrl)) {
            String query = StatementTypes.get(3)
                            + " INTO "
                            + TableNames.get(0)
                            + " VALUES ( null, ?, ? )";

            PreparedStatement smt = con.prepareStatement(query);
            smt.setString(1, newEntry.getQuoteName());
            smt.setString(2, newEntry.getContact());
            smt.executeUpdate();
            smt.close();
        } catch (SQLException ex){
            System.out.println(ex);
        }
    }

    public void insertIntoProductDatabase(Product newEntry) {
        try (Connection con = DriverManager.getConnection(dbUrl)) {
            String query = StatementTypes.get(3)
                            + " INTO "
                            + TableNames.get(1)
                            + " VALUES ( null, ? , ? , ? )";
            PreparedStatement smt = con.prepareStatement(query);
            smt.setString(1, newEntry.getName());
            smt.setBigDecimal(2, newEntry.getProductPrice());
            smt.setBigDecimal(3, newEntry.getVatRate());
            smt.executeUpdate();
            smt.close();

        } catch (SQLException ex){
            System.out.println(ex);
        }
    }

    public void insertIntoQuoteProductDatabase(QuoteItem newEntry) {
        try (Connection con = DriverManager.getConnection(dbUrl)) {
            String query = StatementTypes.get(3)
                            + " INTO "
                            + TableNames.get(2)
                            + " VALUES ( null, ?, ?, ?)";
            PreparedStatement smt = con.prepareStatement(query);
            smt.setInt(1, newEntry.getProduct().getId());
            smt.setInt(2, newEntry.getQuote().getId());
            smt.setInt(3, newEntry.getProductAmount());
            smt.executeUpdate();
            smt.close();

        } catch (SQLException ex){
            System.out.println(ex);
        }
    }

/*
    public void SearchDatabase(int tableToQueryIndex, String columnToQuery, String queryTerm){
        try (Connection con = DriverManager.getConnection(dbUrl)) {
            String query = StatementTypes.get(0)
                            + "SELECT * FROM "
                            + TableNames.get(tableToQueryIndex)
                            + " WHERE ? LIKE '%?%'";
            PreparedStatement smt = con.prepareStatement(query);
            smt.setString(1, columnToQuery);
            smt.setString(2, queryTerm);
            ResultSet rs = smt.executeQuery(query);
            ResultsToList(tableToQueryIndex, rs);
            // do something with results
        } catch (SQLException ex){
            // handle errors
        }
    }
*/
    public void updateDatabase(int tableToQueryIndex, int columnToQueryIndex, int id, String updatedTerm ){

        try (Connection con = DriverManager.getConnection(dbUrl)) {
            String query = "UPDATE "
                            + TableNames.get(tableToQueryIndex)
                            + " set "+ ColumnNames.get(tableToQueryIndex).get(columnToQueryIndex) +" = '"
                            + updatedTerm
                            + "' WHERE " + ColumnNames.get(tableToQueryIndex).get(0) + " = ?";

            PreparedStatement smt = con.prepareStatement(query);

            smt.setInt(1, id);
            smt.executeUpdate();
            smt.close();


        } catch (SQLException ex){
            System.out.println("www " + ex);
        }
    }

    public void deleteFromDatabase(int tableToQueryIndex, int columnIndex, int itemToDeleteId) {
        try (Connection con = DriverManager.getConnection(dbUrl)) {
            String query = StatementTypes.get(2)
                            + " FROM "
                            + TableNames.get(tableToQueryIndex)
                            + " WHERE "
                            + ColumnNames.get(tableToQueryIndex).get(columnIndex)
                            + " = ? ";
            PreparedStatement smt = con.prepareStatement(query);
            smt.setInt(1, itemToDeleteId);
            smt.executeUpdate();
            smt.close();

        } catch (SQLException ex){
            System.out.println(ex);
        }
    }



    public void insertIntoTestDatabase(String testString, int testInt) {
        try (Connection con = DriverManager.getConnection(dbUrl)) {
            String query = StatementTypes.get(3) + " INTO " + TableNames.get(3)
                    + " VALUES (" + testString + ", " + testInt + ")";
            var smt = con.createStatement();
            var rs = smt.executeQuery(query);
        } catch (SQLException ex){
            System.out.println(ex);
        }
    }

    private void resultsToList(int tableIndex, ResultSet rs) throws SQLException {
        List<Quote> listOfQuotes = new ArrayList<>();
        List<Product> listOfProducts = new ArrayList<>();
        List<QuoteItem> listOfBaskets = new ArrayList<>();

        while (rs.next()) {

            switch (tableIndex) {

                case 0:
                    Quote quote = new Quote(rs.getInt("quoteId"));
                    quote.setName(rs.getString("quoteName"));
                    quote.setContact(rs.getString("quoteContact"));
                    listOfQuotes.add(quote);

                    break;
                case 1:
                    Product product = new Product(rs.getInt("productId"));
                    product.setName(rs.getString("productName"));
                    product.setPrice(rs.getBigDecimal("productPrice"));
                    product.setVatRate(rs.getBigDecimal("productVatRate"));
                    listOfProducts.add(product);
                    break;
                case 2:
                    Product basketProduct = PRODUCTS.searchForProduct(rs.getInt("productId"));
                    Quote basketQuote = QUOTES.searchForQuote(rs.getInt("quoteId"));
                    QuoteItem quoteItem = new QuoteItem(rs.getInt("basketId"), basketProduct.getId(), basketQuote.getId());
                    quoteItem.setProductAmount(rs.getInt("numOfItems"));
                    listOfBaskets.add(quoteItem);
                    break;
                default:
                    break;
            }
        }
        switch (tableIndex) {
            case 0:
                quoteSearchResults = listOfQuotes;
            case 1:
                productSearchResults = listOfProducts;
            case 2:
                basketSearchResults = listOfBaskets;
            default:
                break;
        }
    }

    public void createTables(){
        String createDatabaseQuery = "CREATE DATABASE QuoteWizard";

        String createQuoteTableQuery = "CREATE TABLE IF NOT EXISTS Quotes "
                + "('quoteId' INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " 'quoteName' VARCHAR(255), "
                + " 'quoteContact' VARCHAR(255))";

        String createProductTableQuery = "CREATE TABLE IF NOT EXISTS Products "
                + "('productId' INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " 'productName' VARCHAR(255), "
                + " 'productPrice' BIGINTEGER, "
                + " 'productVatRate' BIGINTEGER)";

        String createBasketTableQuery = "CREATE TABLE IF NOT EXISTS Baskets "
                + "('basketId' INTEGER PRIMARY KEY AUTOINCREMENT, "
                + " 'productId' INTEGER not Null, "
                + " 'quoteId' INTEGER not Null, "
                + " 'numOfItems' INTEGER not Null, "
                + " FOREIGN KEY ( 'quoteId' ) REFERENCES Quotes( 'quoteId' )"
                + " FOREIGN KEY ( 'productId' ) REFERENCES Products( 'productId' ))";


        try (Connection con = DriverManager.getConnection(dbUrl)) {
            PreparedStatement smt1 = con.prepareStatement(createQuoteTableQuery);
            smt1.executeUpdate();
            smt1.close();
        } catch (SQLException ex){
            System.out.println("Error in setup: " + ex.getSQLState() + ", " + ex.getMessage() + ", " + ex.getErrorCode());
        }
        try (Connection con = DriverManager.getConnection(dbUrl)) {
            PreparedStatement smt2 = con.prepareStatement(createProductTableQuery);
            smt2.executeUpdate();
            smt2.close();
        } catch (SQLException ex){
            System.out.println("Error in setup: " + ex.getSQLState() + ", " + ex.getMessage() + ", " + ex.getErrorCode());
        }
        try (Connection con = DriverManager.getConnection(dbUrl)) {
            PreparedStatement smt3 = con.prepareStatement(createBasketTableQuery);
            smt3.executeUpdate();
            smt3.close();
        } catch (SQLException ex){
            System.out.println("Error in setup: " + ex.getSQLState() + ", " + ex.getMessage() + ", " + ex.getErrorCode());
        }

    }

    private List<QuoteItem> getAllItemsInBasket(Quote quote){
        List<QuoteItem> basket = new ArrayList<>();
        for(QuoteItem item : basketSearchResults){
            if(item.getQuote().getId() == quote.getId()){
                basket.add(item);
            }
        }
        return basket;
    }

    public void clearDatabaseRecords(){
        try (Connection con = DriverManager.getConnection(dbUrl)) {
            for (int i = 0; i < 3; i++){
                String query ="DROP TABLE "
                        + TableNames.get(i);
                PreparedStatement smt = con.prepareStatement(query);
                smt.executeUpdate();
                smt.close();
            }

        } catch (SQLException ex){
            System.out.println(ex);
        }

    }

    public void refreshAllSearchResults(){
        getWholeTable(0);
        getWholeTable(1);
        getWholeTable(2);
    }

    public void setSearchResults(){
        QUOTES.initialiseList(quoteSearchResults);
        PRODUCTS.initialiseList(productSearchResults);
        BASKETS.initialiseList(basketSearchResults);

        for(Quote quote : QUOTES.getList()){
            quote.setBasket(getAllItemsInBasket(quote));
            quote.updateProductTotals();
        }
    }

}
