package com.example;


public class StringCalculator 
{
    public StringCalculator() {
    }

    public int add(String numbers) {
        if (numbers.equals("")) return 0;
        return Integer.parseInt(numbers);
    }
}
