package de.unidue.iem.tdr.nis.client.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static de.unidue.iem.tdr.nis.client.util.MathUtils.decToHex;
import static de.unidue.iem.tdr.nis.client.util.MathUtils.hexToDec;

public class GF8Test {

    @Test
    public void multiply() {
        String a = "02";
        String b = "0c";
        int result = GF8.gf8_mult(hexToDec(a), hexToDec(b));
        StringHelper result_str = decToHex(result);
        Assertions.assertEquals("18", result_str.toString());
    }
}
