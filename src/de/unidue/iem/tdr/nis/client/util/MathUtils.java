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

    public static String xor(StringHelper a, StringHelper b) {
        Logger.logEnter("MathUtils.xor", a, b);
        hexToBin(a);
        hexToBin(b);
        final StringHelper result = new StringHelper();
        prependZerosIfStringsUnEqualInSize(a, b);
        xorEqualLengthBinaryStrings(a, b, result);
        Logger.logExit("MathUtils.xor", a, b, result);
        return result.toString();
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

    public static void hexToBin(StringHelper hex) {
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
    }
}
