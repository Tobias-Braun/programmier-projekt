package de.unidue.iem.tdr.nis.client.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MathUtilsTest {

    @Test
    public void dectoBin() {
        int value = 13;
        String bin = MathUtils.dectoBin("" + value);
        Assertions.assertEquals("1101", bin);
    }

    @Test
    public void xor() {
        String a = "78eabf";
        String b = "41f8a7";
    }
}
