package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.Aes;
import de.unidue.iem.tdr.nis.client.util.StringHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static de.unidue.iem.tdr.nis.client.util.Aes.parseHexStringToBytes;
import static de.unidue.iem.tdr.nis.client.util.Aes.stdRound;
import static de.unidue.iem.tdr.nis.client.util.MathUtils.byteArrayToHexString;
import static de.unidue.iem.tdr.nis.client.util.MathUtils.transform;
import static de.unidue.iem.tdr.nis.client.util.MathUtils.xorBytes;
import static org.junit.jupiter.api.Assertions.*;

class _14_AES_Initiale_und_zwei_weitere_RundenTest {

    @Test
    void solve() {
        TaskObject task = new TaskObject();
        task.setStringArray(new String[]{"8eac3d33c3bebc832228e55d5d988d64", "63d272d563c88719290287c3a59c493c", "176"});
        String expected = "ed7e4fe6a0763b9a0b2a629ef804c458_9e43056aa2baaa0f91d880693635a0f1_cb8c894" +
                "35c79973f3ae6d374bd031498";

        String input_str = task.getStringArray(0);
        String key_str = task.getStringArray(1);

        int[] input = parseHexStringToBytes(transform(input_str));
        int[] key = parseHexStringToBytes(key_str);
        int[][] roundKeys = Aes.generateRoundKeys(key);
        int[] key_r_1 = parseHexStringToBytes(transform(byteArrayToHexString(roundKeys[0])));
        int[] key_r_2 = parseHexStringToBytes(transform(byteArrayToHexString(roundKeys[1])));
        int[] key_r_3 = parseHexStringToBytes(transform(byteArrayToHexString(roundKeys[2])));

        int[] res_r_1 = xorBytes(input, key_r_1);
        int[] res_r_2 = stdRound(res_r_1, key_r_2);
        int[] res_r_3 = stdRound(res_r_2, key_r_3);
        StringHelper helper = StringHelper.empty();
        helper.append(transform(byteArrayToHexString(res_r_1)));
        helper.append("_");
        helper.append(transform(byteArrayToHexString(res_r_2)));
        helper.append("_");
        helper.append(transform(byteArrayToHexString(res_r_3)));
        String result = helper.toString();
        Assertions.assertEquals(expected, result);
    }
}
