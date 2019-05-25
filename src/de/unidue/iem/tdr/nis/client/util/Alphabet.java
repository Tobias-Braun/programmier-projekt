package de.unidue.iem.tdr.nis.client.util;

public class Alphabet {

    public static char getCharNum(int num) {
        return (char)(num % 26 + 65);
    }

    public static int charMinus(char a, char b) {
        if (b > a) return a-b + 26;
        else return a - b;
    }
}
