package de.unidue.iem.tdr.nis.client.util;

import java.util.Random;

import static de.unidue.iem.tdr.nis.client.util.MathUtils.decToHex;
import static de.unidue.iem.tdr.nis.client.util.MathUtils.hexToBin;

public class DiffieHellman {

    public static int get_rand_smaller_p(int p) {
        return new Random().nextInt(p - 2);
    }

    public static int pow_mod(int g, int a, int p) {
        int res = 1;
        for (int i = 0; i < a; i++) {
            res = (res * g) % p;
        }
        return res;
    }

    public static int[] decrypt(int[] cipher, int B, int a, int p) {
        int[] res = new int[cipher.length];
        int A = pow_mod(B, a, p);
        for (int i = 0; i < cipher.length; i++) {
            res[i] = cipher[i] ^ A;
        }
        return res;
    }
}
