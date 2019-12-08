package com.mac.tdd.isbntools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidateISBNTest {
    @Test
    public void checkAValid10DigitsISBN() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("0140449116");
        assertTrue(result, "first value");
        result = validator.checkISBN("0140177396");
        assertTrue(result, "second value");

    }

    @Test
    public void checkAValid13DigitsISBN() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("9780984782802");
        assertTrue(result, "first value");
        result = validator.checkISBN("9780262033848");
        assertTrue(result, "second value");
    }

    @Test
    public void TenDigitsISBNNumberEndingInAnXAreValid() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("098478280X");
        assertTrue(result);
    }

    @Test
    public void checkAnInvalid10DigitsISBN() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("0140449117");
        assertFalse(result);
    }

    @Test
    public void checkAnInvalid13DigitsISBN() {
        ValidateISBN validator = new ValidateISBN();
        boolean result = validator.checkISBN("9780984782804");
        assertFalse(result);
    }

    //junit 4 @Test(expected = StringIndexOutOfBoundsException.class)
    @Test
    public void nineDigitISBNsAreNotAllowed() {
        ValidateISBN validator = new ValidateISBN();
        assertThrows(NumberFormatException.class,
                () -> {
                    validator.checkISBN("123456789");
                });
    }

    @Test
    public void nonNumericISBNAreNotAllowed() {
        ValidateISBN validator = new ValidateISBN();
        assertThrows(NumberFormatException.class,
                () -> {
                    validator.checkISBN("helloworld");
                });
    }
}