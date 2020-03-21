package me.stev.complexcalcexc;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        ComplexCalculation complexCalculation = new ComplexCalculation();
        System.out.println(complexCalculation.calculateResult(new BigInteger("10"), new BigInteger("2"), new BigInteger("20"), new BigInteger("2")));
    }
}
