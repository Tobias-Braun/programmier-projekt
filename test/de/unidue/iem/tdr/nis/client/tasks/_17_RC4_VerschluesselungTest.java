package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.RC4;
import de.unidue.iem.tdr.nis.client.util.StringHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static de.unidue.iem.tdr.nis.client.util.MathUtils.decToHex;
import static de.unidue.iem.tdr.nis.client.util.MathUtils.hexToBin;
import static org.junit.jupiter.api.Assertions.*;

class _17_RC4_VerschluesselungTest {

    @Test
    void solve() {
        TaskObject task = new TaskObject();
        task.setStringArray(new String[]{"7_11_13_14_20_22_4_1_15_1_24_2_20_23_13_2_13_20_4_21_23_3_9_7",
                "encryption"});
        String expected = "01100110011000100111000001100100011110110111101001111000011001110110110" +
                "001100100";

        String[] key_str = task.getStringArray(0).split("_");
        int[] key = new int[key_str.length];
        for (int i = 0; i < key.length; i++) {
            key[i] = Integer.parseInt(key_str[i]);
        }
        RC4 rc4 = new RC4(new int[key.length]);
        rc4.key_schedule(key);
        int[] charCodes = rc4.getCharCodeArrayFromString(task.getStringArray(1));
        int[] res_arr = new int[charCodes.length];
        for (int i = 0; i < res_arr.length; i++) {
            res_arr[i] = charCodes[i] ^ rc4.gen_loop();
        }
        StringHelper res_helper = StringHelper.empty();
        for (int i = 0; i < res_arr.length; i++) {
            StringHelper hex = decToHex(res_arr[i]);
            hexToBin(hex);
            res_helper.append(hex);
        }
        String result = res_helper.toString();

        Assertions.assertEquals(expected, result);
    }
}
