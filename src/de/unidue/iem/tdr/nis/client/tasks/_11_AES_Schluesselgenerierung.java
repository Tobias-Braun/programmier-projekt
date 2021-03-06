package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.Aes;
import de.unidue.iem.tdr.nis.client.util.StringHelper;

import static de.unidue.iem.tdr.nis.client.util.Aes.parseHexStringToBytes;
import static de.unidue.iem.tdr.nis.client.util.MathUtils.decToHex;

public class _11_AES_Schluesselgenerierung implements TaskSolver {
    @Override
    public String solve(TaskObject task) {
        int[] key = parseHexStringToBytes(task.getStringArray(0));
        int[][] roundkeys = Aes.generateRoundKeys(key);
        StringHelper result = StringHelper.empty();
        for (int i = 0; i < 11; i++) {
            var hex_key_string = StringHelper.empty();
            for (int j = 0; j < 16; j++) {
                hex_key_string.append(decToHex(roundkeys[i][j]));
            }
            if (i < 3) {
                result.append(hex_key_string);
            }
            if (i < 2) {
                result.append("_");
            }
        }
        return result.toString();
    }
}
