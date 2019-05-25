package de.unidue.iem.tdr.nis.client.util;

public class DesEncryption {

    private static int[] PC_1 = new int[]{
            57, 49, 41, 33, 25, 17, 9, 1,
            58, 50, 42, 34, 26, 18, 10, 2,
            59, 51, 43, 35, 27, 19, 11, 3,
            60, 52, 44, 36, 63, 55, 47, 39,
            31, 23, 15, 7, 62, 54, 46, 38,
            30, 22, 14, 6, 61, 53, 45, 37,
            29, 21, 13, 5, 28, 20, 12, 4 };

    private static int[] PC_2 = new int[]{
            14, 17, 11, 24, 1, 5, 3, 28,
            15, 6, 21, 10, 23, 19, 12, 4,
            26, 8, 16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55, 30, 40,
            51, 45, 33, 48, 44, 49, 39, 56,
            34, 53, 46, 42, 50, 36, 29, 32
    };

    public static String pc_1(String str) {
        byte test = 100;
        assert str.length() == 64;
        return StringHelper.of(str).substituteBits(PC_1);
    }

    public static String pc_2(String str) {
        assert str.length() == 56;
        return StringHelper.of(str).substituteBits(PC_2);
    }

    public static String leftShift(String str) {
        return StringHelper.of(str).append(str.charAt(0)).deleteCharAt(0).toString();
    }
}
