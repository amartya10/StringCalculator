package com.example;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

    @ParameterizedTest
    @CsvSource(value = {
            "2,3,1:6",
            "1,2,3,4,5,6,7,8,9,10:55"},delimiter = ':')
    void returnSumManyNumberString(String numbers,int output){
        assertEquals(output,stringCalculator.add(numbers));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "'1\n2,4':7",
            "'1\n2\n3,4':10",
            "'10,20\n100,50\n30,40':250"},delimiter = ':')
    void returnSumNumbersSeparatedByNewLine(String numbers,int output){
        assertEquals(output,stringCalculator.add(numbers));

    }

    @Test
    void returnSumNumbersSeparatedByCustomDelimiter(){
        assertEquals(3,stringCalculator.add("//;\n1;2"));
        assertEquals(3,stringCalculator.add("//:\n1:2"));
        assertEquals(3,stringCalculator.add("//;\n3"));

    }

}
