package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.Aes;
import de.unidue.iem.tdr.nis.client.util.StringHelper;

import static de.unidue.iem.tdr.nis.client.util.Aes.parseHexStringToBytes;
import static de.unidue.iem.tdr.nis.client.util.Aes.stdRound;
import static de.unidue.iem.tdr.nis.client.util.MathUtils.byteArrayToHexString;
import static de.unidue.iem.tdr.nis.client.util.MathUtils.transform;
import static de.unidue.iem.tdr.nis.client.util.MathUtils.xorBytes;

public class _14_AES_Initiale_und_zwei_weitere_Runden implements TaskSolver {
    @Override
    public String solve(TaskObject task) {
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
        return result;
    }
}
