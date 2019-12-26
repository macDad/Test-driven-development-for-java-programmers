package com.mac.tdd.isbntools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StockManagementTests {

    /////////////////////////////////////////////////////////////////////////////////
    ////--------------------------------- Stubs ---------------------------------////
    /////////////////////////////////////////////////////////////////////////////////
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

    /////////////////////////////////////////////////////////////////////////////////
    ////---------------------------- Mocks and Mockito --------------------------////
    /////////////////////////////////////////////////////////////////////////////////
    @Test
    public void databaseIsUsedIfDataIsPresent() {
        ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

        when(databaseService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abs"));
        StockManager stockManager = new StockManager();
        stockManager.setDatabaseService(databaseService);
        stockManager.setWebService(webService);

        String isbn = "0140177396";
        String locationCode = stockManager.getLocationCode(isbn);

        verify(databaseService, times(1)).lookup("0140177396");
        verify(webService, times(0)).lookup(anyString());
    }

    @Test
    public void webserviceIsUsedIfDataIsNotPresentInDatabase() {
        ExternalISBNDataService databaseService = mock(ExternalISBNDataService.class);
        ExternalISBNDataService webService = mock(ExternalISBNDataService.class);

        when(databaseService.lookup("0140177396")).thenReturn(null);
        when(webService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abs"));
        StockManager stockManager = new StockManager();
        stockManager.setDatabaseService(databaseService);
        stockManager.setWebService(webService);

        String isbn = "0140177396";
        String locationCode = stockManager.getLocationCode(isbn);

        verify(databaseService, times(1)).lookup("0140177396");
        verify(webService, times(1)).lookup("0140177396");
    }
}
