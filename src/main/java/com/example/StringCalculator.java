package com.example;

import jdk.jfr.Unsigned;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class StringCalculator 
{
    public StringCalculator() {
    }

    private static final String START_DELIMITER_WITH = "//";
    private static final String END_DELIMITER_WITH = "\n";

    public int add(final String numbers) {
        if (numbers.equals("")) return 0;
        StringBuilder regex =  new StringBuilder("\n|,");
        String numbersSeparatedByDelimiterStartEnd = numbers;
        if (numbers.startsWith(START_DELIMITER_WITH)){
            int startIndexDelimiter = numbers.indexOf(START_DELIMITER_WITH) + START_DELIMITER_WITH.length();
            int endIndexDelimiter = numbers.indexOf(END_DELIMITER_WITH);
            String delimiter = numbers.substring(startIndexDelimiter,endIndexDelimiter);
            delimiter = delimiter.replaceAll("[\\[\\]]","");
            numbersSeparatedByDelimiterStartEnd = numbers.substring(endIndexDelimiter + 1);
            regex.append("|" + Pattern.quote(delimiter));
        }
        String [] numbersSeparated = numbersSeparatedByDelimiterStartEnd.split(regex.toString());
        AtomicReference<Integer> sum = new AtomicReference<>(0);
        IntStream.range(0,numbersSeparated.length)
                .forEach(index -> {
                   Integer number = Integer.parseInt(numbersSeparated[index]);
                   if (number < 0 ){
                       int [] negativeNumbers = getNegativeNumbers(Arrays.copyOfRange( numbersSeparated,index,numbersSeparated.length));
                       StringBuilder stringNumbers = new StringBuilder();
                       Arrays.stream(negativeNumbers)
                               .forEach(negativeNumber -> stringNumbers.append(negativeNumber));
                       throw  new IllegalArgumentException("Negative Number Not Allowed " + stringNumbers);

                   }
                   if (number <= 1000){
                    sum.updateAndGet(v -> v + number);
                   }
                });
        return  sum.get();
    }
    public int[] getNegativeNumbers(String [] numbers){
        return Arrays.stream(numbers)
                .mapToInt(Integer::parseInt)
                .filter(i -> i < 0)
                .toArray();
    }
}
