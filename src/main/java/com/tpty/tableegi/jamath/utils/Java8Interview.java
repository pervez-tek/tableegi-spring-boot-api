package com.tpty.tableegi.jamath.utils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Java8Interview {

    public static void main(String[] args) {

        String input = "CABE";

        String output = input;

        int iter=(input+input).indexOf(input,1);
        System.out.println(iter);
        int count = 0;
        do {
            input = input.substring(1) + input.charAt(0);
            count++;
        } while (!input.equalsIgnoreCase(output));

        System.out.println(count);
    }
}