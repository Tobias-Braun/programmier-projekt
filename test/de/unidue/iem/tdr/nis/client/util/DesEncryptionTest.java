package de.unidue.iem.tdr.nis.client.util;

import org.junit.Assert;
import org.junit.Test;

public class DesEncryptionTest {

    public void initTest() {
    }

    @Test
    public void pc_1() {
        String key = "0001001100110100010101110111100110011011101111001101111111110001";
        Assert.assertEquals("11110000110011001010101011110101010101100110011110001111", DesEncryption.pc_1(key));
    }

    @Test
    public void pc_2() {
    }

    @Test
    public void leftShift() {
    }
}
