package com.mac.tdd.isbntools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class StockManagementTests {

    @Test
    public void testCanGetACorrectLocationCode() {
        ExternalISBNDataService testDatabaseService = new ExternalISBNDataService() {
            @Override
            public Book lookup(String isbn) {
                return null;
            }
        };
        ExternalISBNDataService testWebService = new ExternalISBNDataService() {
            @Override
            public Book lookup(String isbn) {
                return new Book(isbn, "Of Mice And Men", "J. Steinbeck");
            }
        };

        StockManager stockManager = new StockManager();
        stockManager.setDatabaseService(testDatabaseService);
        stockManager.setWebService(testWebService);

        String isbn = "0140177396";
        String locationCode = stockManager.getLocationCode(isbn);
        assertEquals("7396J4", locationCode);
    }

    @Test
    public void databaseIsUsedIfDataIsPresent() {
        ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

        StockManager stockManager = new StockManager();
        stockManager.setDatabaseService(databaseService);
        stockManager.setWebService(webService);

        String isbn = "0140177396";
        String locationCode = stockManager.getLocationCode(isbn);
        assertEquals("7396J4", locationCode);
    }

    @Test
    public void webserviceIsUsedIfDataIsNotPresentInDatabase() {
        fail();
    }
}
