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
        //default delimiters "\n" ","
        StringBuilder regex =  new StringBuilder("\n|,");
        //temp storing numbers (assuming delimiter not specified)
        String numbersSeparatedByDelimitersExpression = numbers;
        if (numbers.startsWith(START_DELIMITER_WITH)){
            int startIndexDelimiter = START_DELIMITER_WITH.length();
            int endIndexDelimiter = numbers.indexOf(END_DELIMITER_WITH);
            //get delimiters substring
            String delimitersExpression = numbers.substring(startIndexDelimiter,endIndexDelimiter);
            //append regex  delimiters
            Arrays.stream(getDelimiters(delimitersExpression)).
                    forEach(delimiter -> regex.append("|"+Pattern.quote(delimiter)) );

            numbersSeparatedByDelimitersExpression = numbers.substring(endIndexDelimiter + END_DELIMITER_WITH.length());
        }
        String [] numbersSeparated = numbersSeparatedByDelimitersExpression.split(regex.toString());
        return  _add(numbersSeparated);
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
        /*
            step 1 if multiple delimiters replace "]["(followed by) to "|"
            step 2 replace "[" , "]" to ""
            step 3 split by |
            Example
                "[::][;;]"
                "[::|;;]"
                "::|;;"
                return ["::",";;"]
        */
       
        return  delimiters
                .replaceAll("]\\[","|")    //
                .replaceAll("[\\[\\]]","") //
                .split("[\\|]");              //
    }

    public int _add(String []  numbersSeparated) {

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


}
