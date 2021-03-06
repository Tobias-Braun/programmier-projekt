package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.RC4;
import de.unidue.iem.tdr.nis.client.util.StringHelper;

import static de.unidue.iem.tdr.nis.client.util.MathUtils.decToHex;
import static de.unidue.iem.tdr.nis.client.util.MathUtils.hexToBin;

public class _17_RC4_Verschluesselung implements TaskSolver {
    @Override
    public String solve(TaskObject task) {
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
        return result;
    }
}
