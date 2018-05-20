package com.company;

import java.util.Random;

public class Resurce_var {
    static Random random = new Random();
    public Resurce_var() {
    }
    byte aByte = 0;
    short aShort = 0;
    int anInt= 0;
    long aLong = 0;
    char aChar = '0';
    boolean aBoolean = false;
    double aDouble= 0;
    float aFloat = 0;
    public void consume(){
        System.out.println("CR2 var consume:");
        System.out.println(aShort + aChar + aDouble + aFloat + aLong + "");
    }
    public void produce(){
        aByte = 1;
        anInt= random.nextInt();
        aLong = random.nextLong();
        aChar = 'd';
        aBoolean = random.nextBoolean();
        aDouble= random.nextDouble();
        aFloat = random.nextFloat();
        System.out.println("val produce:");
        System.out.println(aShort + aChar + aDouble + aFloat + aLong + "");
    }
}
