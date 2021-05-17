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
            int startIndexDelimiter = START_DELIMITER_WITH.length();
            int endIndexDelimiter = numbers.indexOf(END_DELIMITER_WITH);
            String delimiters = numbers.substring(startIndexDelimiter,endIndexDelimiter);
            Arrays.stream(getDelimiters(delimiters)).
                    forEach(delimiter -> regex.append("|"+Pattern.quote(delimiter)) );
            numbersSeparatedByDelimiterStartEnd = numbers.substring(endIndexDelimiter + 1);
        }
        String [] numbersSeparated = numbersSeparatedByDelimiterStartEnd.split(regex.toString());
        AtomicReference<Integer> sum = new AtomicReference<>(0);
        IntStream.range(0,numbersSeparated.length)
                .forEach(index -> {
                   Integer number = Integer.parseInt(numbersSeparated[index]);
                   if (number < 0 ){
                       String negativeNumbers = getNegativeNumbers(Arrays.copyOfRange( numbersSeparated,index,numbersSeparated.length));
                       throw  new IllegalArgumentException("Negative Number Not Allowed " + negativeNumbers);
                   }
                   if (number <= 1000){
                    sum.updateAndGet(v -> v + number);
                   }
                });
        return  sum.get();
    }

    public String getNegativeNumbers(String [] numbers){
        StringBuilder negativeNumbers = new StringBuilder();
        Arrays.stream(numbers)
                .mapToInt(Integer::parseInt)
                .filter(i -> i < 0)
                .forEach(negativeNumbers::append);
        return  negativeNumbers.toString() ;
    }
    public String  [] getDelimiters(String delimiters){
        return  delimiters
                .replaceAll("]\\[","|")
                .replaceAll("[\\[\\]]","")
                .split("[\\|]");
    }

}
