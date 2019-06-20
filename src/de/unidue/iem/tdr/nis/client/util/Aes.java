package de.unidue.iem.tdr.nis.client.util;

import static de.unidue.iem.tdr.nis.client.util.GF8.gf8_mult;
import static de.unidue.iem.tdr.nis.client.util.MathUtils.hexToDec;
import static de.unidue.iem.tdr.nis.client.util.MathUtils.xorBytes;

public class Aes {

    private static final int[][] s_box = new int[][]{
            {0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76},
            {0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0},
            {0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15},
            {0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75},
            {0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84},
            {0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf},
            {0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8},
            {0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2},
            {0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73},
            {0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb},
            {0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79},
            {0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08},
            {0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a},
            {0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e},
            {0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf},
            {0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16}
    };

    private static final int[] c_m = new int[]{
            2, 3, 1, 1, 1, 2, 3, 1, 1, 1, 2, 3, 3, 1, 1, 2
    };

    private static final int[] rcon = new int[]{
            0x00, 0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36
    };

    public static int[] rotWord(int[] word) {
        assert word.length == 4;
        return new int[]{word[1], word[2], word[3], word[0]};
    }

    public static int[] subBytes(int[] bytes) {
        int[] result = new int[bytes.length];
        for (int i = 0; i < bytes.length; i++) { // for each byte do
            result[i] = subByte(bytes[i]);
        }
        return result;
    }

    public static int subByte(int byte_) {
        int row = byte_ / 16;
        int col = byte_ % 16;
        return s_box[row][col];
    }

    public static int[] shiftRows(int[] state) {
        assert state.length == 16;
        return new int[]{
                state[0], state[1], state[2], state[3],
                state[5], state[6], state[7], state[4],
                state[10], state[11], state[8], state[9],
                state[15], state[12], state[13], state[14]
        };
    }

    public static int[][] generateRoundKeys(int[] key) {
        int[][] w = new int[11][16];
        for (int i = 0; i < 4; i++) {
            w[0][i * 4] = key[4 * i];
            w[0][i * 4 + 1] = key[4 * i + 1];
            w[0][i * 4 + 2] = key[4 * i + 2];
            w[0][i * 4 + 3] = key[4 * i + 3];
        }
        for (int i = 1; i < 11; i++) {
            int[] word_i_1_old = new int[]{w[i - 1][12], w[i - 1][13], w[i - 1][14], w[i - 1][15]};
            int[] word_i_2 = new int[]{w[i - 1][8], w[i - 1][9], w[i - 1][10], w[i - 1][11]};
            int[] word_i_3 = new int[]{w[i - 1][4], w[i - 1][5], w[i - 1][6], w[i - 1][7]};
            int[] word_i_4 = new int[]{w[i - 1][0], w[i - 1][1], w[i - 1][2], w[i - 1][3]};
            int[] word_i_1 = rcon_xor(subBytes(rotWord(word_i_1_old)), rcon[i]);
            int[] word_0 = word_xor(word_i_4, word_i_1);
            int[] word_1 = word_xor(word_i_3, word_0);
            int[] word_2 = word_xor(word_i_2, word_1);
            int[] word_3 = word_xor(word_i_1_old, word_2);
            w[i][0] = word_0[0];
            w[i][1] = word_0[1];
            w[i][2] = word_0[2];
            w[i][3] = word_0[3];
            w[i][4] = word_1[0];
            w[i][5] = word_1[1];
            w[i][6] = word_1[2];
            w[i][7] = word_1[3];
            w[i][8] = word_2[0];
            w[i][9] = word_2[1];
            w[i][10] = word_2[2];
            w[i][11] = word_2[3];
            w[i][12] = word_3[0];
            w[i][13] = word_3[1];
            w[i][14] = word_3[2];
            w[i][15] = word_3[3];
        }
        return w;
    }

    private static int[] word_xor(int[] word1, int[] word2) {
        return new int[]{word1[0] ^ word2[0], word1[1] ^ word2[1], word1[2] ^ word2[2], word1[3] ^ word2[3]};
    }

    private static int[] rcon_xor(int[] word, int rcon) {
        return new int[]{word[0] ^ rcon, word[1], word[2], word[3]};
    }

    public static int[] parseHexStringToBytes(String key) {
        int[] result = new int[16];
        for (int i = 0; i < 16; i++) {
            result[i] = hexToDec(key.substring(i * 2, i * 2 + 2));
        }
        return result;
    }

    public static int[] mixColumns(int[] input) {
        assert input.length == 16;
        int[] result = new int[16];
        for (int i = 0; i < 16; i++) {
            int row = i / 4;
            int col = i % 4;
            int m1 = gf8_mult(c_m[row * 4], input[col]);
            int m2 = gf8_mult(c_m[row * 4 + 1], input[col + 4]);
            int m3 = gf8_mult(c_m[row * 4 + 2], input[col + 8]);
            int m4 = gf8_mult(c_m[row * 4 + 3], input[col + 12]);
            result[i] = m1 ^ m2 ^ m3 ^ m4;
        }
        var a = "31c32c5809297af1202ed0cb1fdc2af5";
        var b = "c60feaa3a6ca18d6c3f79f98246ef1b3";
        return result;
    }

    public static int[] stdRound(int[] input, int[] roundKey) {
        assert input.length == 16;
        assert roundKey.length == 16;
        return xorBytes(mixColumns(shiftRows(subBytes(input))), roundKey);
    }


}
