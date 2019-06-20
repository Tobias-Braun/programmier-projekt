package de.unidue.iem.tdr.nis.client.util;


import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.tasks.TaskSolver;
import de.unidue.iem.tdr.nis.client.tasks._07_DES_RBlock_Berechnung;
import de.unidue.iem.tdr.nis.client.tasks._08_DES_Feistel_Funktion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DesEncryptionTest {

    public void initTest() {
    }

    @Test
    public void pc_1() {
        String key = "0001001100110100010101110111100110011011101111001101111111110001";
        Assertions.assertEquals("11110000110011001010101011110101010101100110011110001111", DesEncryption.pc_1(key));
    }

    @Test
    public void pc_2() {
    }

    @Test
    public void leftShift() {
    }

    @Test
    public void initial_permutation() {
    }

    @Test
    public void expansion() {
        String r_block = "11111101100010000100001001011011";
        String expansion = DesEncryption.expansion(r_block);
        Assertions.assertEquals("111111111011110001010000001000000100001011110111", expansion);
    }

    @Test
    public void apply_s_boxes() {
        String expand = "111111111011110001010000001000000100001011110111";
        String after_s_boxes = DesEncryption.apply_s_boxes(expand);
        Assertions.assertEquals("11010101010000010111101010010000", after_s_boxes);
    }

    @Test
    public void ip_negative() {
    }

    @Test
    public void permute() {
        String after_s_box = "11010101010000010111101010010000";
        String permuted = DesEncryption.permute(after_s_box);
        Assertions.assertEquals("10110010101001011100000010010011", permuted);
    }

    @Test
    public void testDes() {
        TaskObject task = new TaskObject();
        task.setStringArray(new String[]{"1001001001011111110001001010011110000010110100011000111011100100"});
        task.setIntArray(new int[]{4});
        _07_DES_RBlock_Berechnung solver = new _07_DES_RBlock_Berechnung();
        String output = solver.solve(task);
        Assertions.assertEquals("11100001011111101110110101111010", output);
    }

    @Test
    public void testFeistel() {
        TaskObject task = new TaskObject();
        task.setStringArray(new String[]{"0000011100111001101001010010100111010110110110011011111001011000", "101010000001000100111101111000000111110001101110"});
        _08_DES_Feistel_Funktion solver = new _08_DES_Feistel_Funktion();
        String out = solver.solve(task);
        Assertions.assertEquals("00100001111000110110011101111011", out);
    }


}
