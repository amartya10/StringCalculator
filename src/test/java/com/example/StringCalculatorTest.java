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

}
