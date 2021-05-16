package com.example;

import java.util.Arrays;

public class StringCalculator 
{
    public StringCalculator() {
    }

    private static final String START_DELIMITER_WITH = "//";
    private static final String END_DELIMITER_WITH = "\n";

    public int add(final String numbers) {
        if (numbers.equals("")) return 0;
        StringBuilder regex =  new StringBuilder("\n,");
        String numbersSeparatedByDelimiters = numbers;
        if (numbers.startsWith(START_DELIMITER_WITH)){
            int startIndexDelimiter = numbers.indexOf(START_DELIMITER_WITH) + START_DELIMITER_WITH.length();
            int endIndexDelimiter = numbers.indexOf(END_DELIMITER_WITH);
            String delimiter = numbers.substring(startIndexDelimiter,endIndexDelimiter);
            numbersSeparatedByDelimiters = numbers.substring(endIndexDelimiter + 1);
            regex.append(delimiter);
        }
        return Arrays.stream(numbersSeparatedByDelimiters.split("[" + regex + "]"))
                .mapToInt(Integer::parseInt)
                .sum();
    }
}
