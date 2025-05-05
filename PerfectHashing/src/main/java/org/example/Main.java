package org.example;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.BitSet;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {

        String s = "cat";
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);

        BigInteger bigInteger = new BigInteger(bytes);



        System.out.println(bigInteger.toString(2));

        System.out.println("Hello, World!");


    }
}