package de.unidue.iem.tdr.nis.client.util;

public class MathUtils {

    public static String getPrimeFactors(int num) {
        Logger.logEnter("MathUtils.getPrimeFactors", num);
        System.out.println(num);
        final StringHelper str = StringHelper.of("");
        int i = 2;
        while (i <= num) {
            if (num % i != 0) {
                i += 1;
            } else {
                num /= i;
                str.append("*" + i);
            }
        }
        str.deleteCharAt(0);
        Logger.logExit("MathUtils.getPrimeFactors", num, str);
        System.out.println(str);
        return str.toString();
    }

    public static int mod(int a, int b) {
        while (a >= b) {
            a = a - b;
        }
        return a;
    }

    public static String binaryXor(StringHelper a, StringHelper b) {
        Logger.logEnter("MathUtils.hexXor", a, b);
        final StringHelper result = new StringHelper();
        prependZerosIfStringsUnEqualInSize(a, b);
        xorEqualLengthBinaryStrings(a, b, result);
        Logger.logExit("MathUtils.hexXor", a, b, result);
        return result.toString();
    }

    public static String hexXor(StringHelper a, StringHelper b) {
        hexToBin(a);
        hexToBin(b);
        return binaryXor(a, b);
    }

    private static void xorEqualLengthBinaryStrings(StringHelper a, StringHelper b, StringHelper result) {
        Logger.logEnter("MathUtils.xorEqualLengthBinaryStrings", a, b, result);
        for (int i = 0; i < a.length(); i++) {
            result.append(a.charAt(i) == b.charAt(i)? '0' : '1');
        }
        Logger.logExit("MathUtils.xorEqualLengthBinaryStrings", a, b, result);
    }

    private static void prependZerosIfStringsUnEqualInSize(StringHelper a, StringHelper b) {
        Logger.logEnter("MathUtils.prependZerosIfStringsUnEqualInSize", a, b);
        int difference = a.length() - b.length();
        if (difference < 0) {
            appendZero(-difference, a);
        } else if (difference > 0) {
            appendZero(difference, b);
        }
        Logger.logExit("MathUtils.prependZerosIfStringsUnEqualInSize", a, b);
    }

    private static void appendZero(int difference, StringHelper str) {
        Logger.logEnter("MathUtils.appendZero", difference, str);
        for (int i = 0; i < difference; i++) {
            str.insert(0, '0');
        }
    }



    public static StringHelper binToHex(StringHelper bin) {
        Logger.logEnter("MathUtils.binToHex",bin);

        bin.repeatForBlockSize(4, MathUtils::convertBinBlockToHex);
        Logger.logExit("MathUtils.binToHex", bin);
        return bin;
    }

    private static StringHelper convertBinBlockToHex(StringHelper in) {
        switch (in.toString()) {
            case "0000": return StringHelper.of("0");
            case "0001": return StringHelper.of("1");
            case "0010": return StringHelper.of("2");
            case "0011": return StringHelper.of("3");
            case "0100": return StringHelper.of("4");
            case "0101": return StringHelper.of("5");
            case "0110": return StringHelper.of("6");
            case "0111": return StringHelper.of("7");
            case "1000": return StringHelper.of("8");
            case "1001": return StringHelper.of("9");
            case "1010": return StringHelper.of("a");
            case "1011": return StringHelper.of("b");
            case "1100": return StringHelper.of("c");
            case "1101": return StringHelper.of("d");
            case "1110": return StringHelper.of("e");
            case "1111": return StringHelper.of("f");
            default: throw new RuntimeException();
        }
    }

    public static int binToDec(String bin) {
        int value = 0;
        for (int i = bin.length() - 1; i >= 0; i--) {
            value += bin.charAt(i) == '1' ? Math.pow(2, (bin.length() - 1 - i)) : 0;
        }
        return value;
    }

    public static int hexToDec(String hex) {
        StringHelper h = StringHelper.of(hex);
        hexToBin(h);
        return binToDec(h.toString());
    }

    public static StringHelper decToHex(int dec) {
        Logger.logEnter("MathUtils.decToHex", dec);
        StringHelper bin = StringHelper.of(dectoBin("" + dec));
        while (bin.length() % 4 != 0) {
            bin.insert(0, "0");
        }
        var res = binToHex(bin);
        if (res.length() == 1) res.insert(0, "0");
        Logger.logExit("MathUtils.decToHex", dec, res);
        return res;
    }

    public static String byteArrayToHexString(int[] input) {
        final StringHelper hex_str = StringHelper.empty();
        for (int byte_ : input) {
            hex_str.append(decToHex(byte_));
        }
        return hex_str.toString();
    }

    public static String dectoBin(String dec) {
        int dec_int = Integer.parseInt(dec);
        StringHelper value = StringHelper.empty();
        while (dec_int != 0) {
            value.insert(0, dec_int % 2);
            dec_int /= 2;
        }
        while (value.length() < 4) {
            value.insert(0, "0");
        }
        return value.toString();
    }

    public static int greatest_common_div(int a, int b) {
        /*if (a == 0)
            return new int[]{b, 1, 0};

        int[] values = gcd(q, Constants.modulo(p, q));
        int d = values[0];
        int a = values[2];
        int b = values[1] - (p / q) * values[2];
        return new int[]{d, a, b}; */
        return 0;
    }

    public static StringHelper hexToBin(StringHelper hex) {
        Logger.logEnter("MathUtils.hexToBin", hex);
        char[] hexChars = hex.toString().toCharArray();
        for (int i = 0; i < hexChars.length; i++) {
            switch (hex.charAt(i * 4)) {
                case '0':
                    hex.replace(i * 4, i * 4 + 1, "0000");
                    break;
                case '1':
                    hex.replace(i * 4, i * 4 + 1, "0001");
                    break;
                case '2':
                    hex.replace(i * 4, i * 4 + 1, "0010");
                    break;
                case '3':
                    hex.replace(i * 4, i * 4 + 1, "0011");
                    break;
                case '4':
                    hex.replace(i * 4, i * 4 + 1, "0100");
                    break;
                case '5':
                    hex.replace(i * 4, i * 4 + 1, "0101");
                    break;
                case '6':
                    hex.replace(i * 4, i * 4 + 1, "0110");
                    break;
                case '7':
                    hex.replace(i * 4, i * 4 + 1, "0111");
                    break;
                case '8':
                    hex.replace(i * 4, i * 4 + 1, "1000");
                    break;
                case '9':
                    hex.replace(i * 4, i * 4 + 1, "1001");
                    break;
                case 'a':
                    hex.replace(i * 4, i * 4 + 1, "1010");
                    break;
                case 'b':
                    hex.replace(i * 4, i * 4 + 1, "1011");
                    break;
                case 'c':
                    hex.replace(i * 4, i * 4 + 1, "1100");
                    break;
                case 'd':
                    hex.replace(i * 4, i * 4 + 1, "1101");
                    break;
                case 'e':
                    hex.replace(i * 4, i * 4 + 1, "1110");
                    break;
                case 'f':
                    hex.replace(i * 4, i * 4 + 1, "1111");
                    break;
                default:
                    throw new RuntimeException("wrong value in hex string: " + hex + ": " + hex.charAt(i));
            }
        }
        Logger.logExit("MathUtils.hexToBin", hex);
        return hex;
    }

    public static int[] xorBytes(int[] byte1, int[] byte2) {
        assert byte1.length == byte2.length;
        int[] res = new int[byte1.length];
        for (int i = 0; i < byte1.length; i++) {
            res[i] = byte1[i] ^ byte2[i];
        }
        return res;
    }

    public static String transform(String hex) {
        var helper = StringHelper.empty();
        for (int i = 0; i < 8; i += 2) {
            helper.append(hex.substring(i, i + 2));
            helper.append(hex.substring(i + 8, i + 8 + 2));
            helper.append(hex.substring(i + 16, i + 16 + 2));
            helper.append(hex.substring(i + 24, i + 24 + 2));
        }
        return helper.toString();
    }
}
