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
    
}
