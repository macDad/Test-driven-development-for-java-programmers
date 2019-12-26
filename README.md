## Test driven development for Java program

####  Stubs
> * To use stubs in junit you don't need any frameworks.
* If you want to stub some interface just implement it:
* Then create new stub object and inject it to tested object.
* If you want to stub a concrete class, create subclass and override stubbed methods:

```
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
```

####  Mocks and Mockito

```
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
```
#### Mockito syntax options

```
  verify(myClass, times(?)).myMethod(params);
```
>  ##### times(?)
* times(1) is default. so no need to add
* times(0) is same as never().

>  ##### params
* "abc"
* anyString()
* any(Book.class)
