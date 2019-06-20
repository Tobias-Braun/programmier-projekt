package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.Aes;

import static de.unidue.iem.tdr.nis.client.util.MathUtils.byteArrayToHexString;
import static de.unidue.iem.tdr.nis.client.util.MathUtils.transform;

public class _13_AES_ShiftRows_Subbytes_MixColumns implements TaskSolver {
    @Override
    public String solve(TaskObject task) {
        String input_str = transform(task.getStringArray(0));
        int[] input = Aes.parseHexStringToBytes(input_str);
        int[] sub_input = Aes.subBytes(input);
        int[] shifted_rows = Aes.shiftRows(sub_input);
        int[] mixed_cols = Aes.mixColumns(shifted_rows);
        String result = transform(byteArrayToHexString(mixed_cols));
        return result;
    }
}
