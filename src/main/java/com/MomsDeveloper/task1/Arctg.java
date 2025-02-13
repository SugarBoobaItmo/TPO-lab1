package com.MomsDeveloper.task1;

public class Arctg {
    public static double arctg(double x, double terms) {
        if (Math.abs(x) > 1) {
            throw new IllegalArgumentException("Argument must be in range [-1, 1]");
        }
        double result = 0;
        for (int i = 0; i < terms; i++) {
            double term = Math.pow(-1, i) * Math.pow(x, 2 * i + 1) / (2 * i + 1);
            result += term;
        }
        return result;
    }
}