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

    @Test void returnZeroEmptyString() throws  Exception{
        assertEquals(0,stringCalculator.add(""));
    }
    @Test void returnNumberSingleElementString() throws Exception {
        assertEquals(1,stringCalculator.add("1"));
        assertEquals(4,stringCalculator.add("4"));
    }
    
    @Test void returnSumTwoNumberString() throws Exception {
        assertEquals(3,stringCalculator.add("1,2"));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2,3,1:6",
            "1,2,3,4,5,6,7,8,9,10:55"},delimiter = ':')
    void returnSumManyNumberString(String numbers,int output) throws Exception {
        assertEquals(output,stringCalculator.add(numbers));
    }

    @ParameterizedTest
    @CsvSource(value = {
            "'1\n2,4':7",
            "'1\n2\n3,4':10",
            "'10,20\n100,50\n30,40':250"},delimiter = ':')
    void returnSumNumbersSeparatedByNewLine(String numbers,int output) throws Exception {
        assertEquals(output,stringCalculator.add(numbers));

    }

    @Test
    void returnSumNumbersSeparatedByCustomDelimiter() throws Exception {
        assertEquals(3,stringCalculator.add("//;\n1;2"));
        assertEquals(3,stringCalculator.add("//:\n1:2"));
        assertEquals(3,stringCalculator.add("//;\n3"));

    }
    @Test
    void throwNegativeNumberException() throws Exception {
        String exceptedMessage = "Negative Number Not Allowed -2";
        assertThrows(IllegalArgumentException.class , () -> stringCalculator.add("//;\n1;-2"));

    }

    @ParameterizedTest
    @CsvSource(value = {"'-2,-3,-1':Negative Number Not Allowed -2-3-1",
            "'//;\n1;-2':Negative Number Not Allowed -2",
            "'-2':Negative Number Not Allowed -2",
            "'//;\n1;-2;-3':Negative Number Not Allowed -2-3",
    },delimiter = ':')
    void throwMultipleNegativeNumberException(String numbers,String output) throws Exception {
        Exception  exception = assertThrows(IllegalArgumentException.class , () -> stringCalculator.add(numbers));
        assertTrue(exception.getMessage().equals(output));
    }

    @Test
    void skipNumberGt1000(){
        assertEquals(4,stringCalculator.add("//;\n1;3;1001"));
        assertEquals(1000,stringCalculator.add("//;\n1001;1000"));
        assertEquals(0,stringCalculator.add("//;\n1001"));
    }

    @Test
    void returnSumPassingDelimiterAnyLength(){
        assertEquals(22,stringCalculator.add("//[;;]\n10;;12"));
        assertEquals(22,stringCalculator.add("//[:*:]\n10:*:12"));
    }

    @Test
    void returnSumPassingMultipleDelimiters(){
        assertEquals(33,stringCalculator.add("//[;;][::]\n10;;12::11"));
        assertEquals(36,stringCalculator.add("//[****][::]\n10****12::11\n3"));


    }

}
