package de.unidue.iem.tdr.nis.client.util;

public class GF8 {

    static final int rindael_pol = 0b100011011;

    public static int multiply(int a, int b) {
        Logger.logEnter("GF8.multiply", a, b);
        int pre_res = a * b;
        int current_digit = (int) (Math.log(pre_res) / Math.log(2));
        while (pre_res > 255) {
            if (pre_res >= Math.pow(2, current_digit)) pre_res = pre_res ^ (rindael_pol << (current_digit - 8));
            current_digit -= 1;
        }
        Logger.logExit("GF8.multiply", a, b);
        return pre_res;
    }

    public static void main(String[] args) {
        System.out.println(0x53);
        System.out.println(0xca);
        System.out.println(0b100011011);
        System.out.println(0x53 * 0xca);
        System.out.println((0x53* 0xca / rindael_pol));
        System.out.println((0x53* 0xca / rindael_pol) * rindael_pol);
        System.out.println((0x53 * 0xca) % 0b100011011);
        int result = multiply(5, 283);
        System.out.println(result);
    }

}
