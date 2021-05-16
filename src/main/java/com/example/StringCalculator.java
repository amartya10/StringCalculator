package com.example;

import jdk.jfr.Unsigned;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

public class StringCalculator 
{
    public StringCalculator() {
    }

    private static final String START_DELIMITER_WITH = "//";
    private static final String END_DELIMITER_WITH = "\n";

    public int add(final String numbers) {
        if (numbers.equals("")) return 0;
        StringBuilder regex =  new StringBuilder("\n,");
        String numbersSeparatedByDelimiterStartEnd = numbers;
        if (numbers.startsWith(START_DELIMITER_WITH)){
            int startIndexDelimiter = numbers.indexOf(START_DELIMITER_WITH) + START_DELIMITER_WITH.length();
            int endIndexDelimiter = numbers.indexOf(END_DELIMITER_WITH);
            String delimiter = numbers.substring(startIndexDelimiter,endIndexDelimiter);
            numbersSeparatedByDelimiterStartEnd = numbers.substring(endIndexDelimiter + 1);
            regex.append(delimiter);
        }
        String [] numbersSeparated = numbersSeparatedByDelimiterStartEnd.split("[" + regex + "]");

        AtomicReference<Integer> sum = new AtomicReference<>(0);
        IntStream.range(0,numbersSeparated.length)
                .forEach(index -> {
                   Integer number = Integer.parseInt(numbersSeparated[index]);
                   if (number < 0 ){
                       if (index  < numbersSeparated.length){
                         int [] negativeNumbers = getNegativeNumbers(Arrays.copyOfRange( numbersSeparated,index,numbersSeparated.length));
                         StringBuilder stringNumbers = new StringBuilder("");
                         Arrays.stream(negativeNumbers)
                                 .forEach(negativeNumber -> stringNumbers.append(negativeNumber));
                         throw  new IllegalArgumentException("Negative Number Not Allowed " + stringNumbers);
                       }
                   }
                   sum.updateAndGet(v -> v + number);
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
