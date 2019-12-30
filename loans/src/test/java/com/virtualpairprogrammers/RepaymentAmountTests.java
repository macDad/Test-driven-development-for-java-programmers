package com.virtualpairprogrammers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RepaymentAmountTests {
    @Spy
    LoanApplication loansApplication;
    LoanCalculatorController controller;

    @BeforeEach
    public void setup() {
        loansApplication = spy(new LoanApplication());
        controller = new LoanCalculatorController();

        LoanRepository loanRepository = mock(LoanRepository.class);
        JavaMailSender mailSender = mock(JavaMailSender.class);
        RestTemplate restTemplate = mock(RestTemplate.class);


        controller.setData(loanRepository);
        controller.setMailSender(mailSender);
        controller.setRestTemplate(restTemplate);
    }

    @Test
    public void test1YearLoanWholePound() {

        loansApplication.setPrincipal(1200);
        loansApplication.setTermInMonths(12);
        doReturn(new BigDecimal(10)).when(loansApplication).getInterestRate();


        controller.processNewLoanApplication(loansApplication);

        assertEquals(new BigDecimal(110), loansApplication.getRepayment());
    }

    @Test
    public void test2YearLoanWholePound() {

        loansApplication.setPrincipal(1200);
        loansApplication.setTermInMonths(24);
        doReturn(new BigDecimal(10)).when(loansApplication).getInterestRate();


        controller.processNewLoanApplication(loansApplication);

        assertEquals(new BigDecimal(60), loansApplication.getRepayment());
    }

    @Test
    public void test5YearLoanWithRounding() {

        loansApplication.setPrincipal(5000);
        loansApplication.setTermInMonths(60);
        doReturn(new BigDecimal(6.5)).when(loansApplication).getInterestRate();


        controller.processNewLoanApplication(loansApplication);

        assertEquals(new BigDecimal(111), loansApplication.getRepayment());
        //fail();
    }
}
