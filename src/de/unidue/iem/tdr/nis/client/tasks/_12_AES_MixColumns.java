package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.Aes;
import de.unidue.iem.tdr.nis.client.util.MathUtils;
import de.unidue.iem.tdr.nis.client.util.StringHelper;

import static de.unidue.iem.tdr.nis.client.util.MathUtils.decToHex;

public class _12_AES_MixColumns implements TaskSolver {
    @Override
    public String solve(TaskObject task) {
        String transformed_str = MathUtils.transform(task.getStringArray(0));
        int[] input = Aes.parseHexStringToBytes(transformed_str);
        int[] result = Aes.mixColumns(input);
        StringHelper hex_str = StringHelper.empty();
        for (int byte_ : result) {
            hex_str.append(decToHex(byte_));
        }
        var helper = StringHelper.empty();
        for (int i = 0; i < 8; i += 2) {
            helper.append(hex_str.substring(i, i + 2));
            helper.append(hex_str.substring(i + 8, i + 8 + 2));
            helper.append(hex_str.substring(i + 16, i + 16 + 2));
            helper.append(hex_str.substring(i + 24, i + 24 + 2));
        }
        return helper.toString();
    }

}
