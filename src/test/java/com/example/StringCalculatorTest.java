package com.example;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringCalculatorTest
{
    private StringCalculator stringCalculator;

    @BeforeEach
    void initEach() {
        stringCalculator = new StringCalculator();
    }

    @Test void returnZeroEmptyString(){
        assertEquals(0,stringCalculator.add(""));
    }
    @Test void returnNumberSingleElementString(){
        assertEquals(1,stringCalculator.add("1"));
        assertEquals(4,stringCalculator.add("4"));
    }
    
    @Test void returnSumTwoNumberString(){
        assertEquals(3,stringCalculator.add("1,2"));
    }

    @Test void returnSumManyNumberString(){
        assertEquals(6,stringCalculator.add("2,3,1"));
        assertEquals(55,stringCalculator.add("1,2,3,4,5,6,7,8,9,10"));
    }
}
