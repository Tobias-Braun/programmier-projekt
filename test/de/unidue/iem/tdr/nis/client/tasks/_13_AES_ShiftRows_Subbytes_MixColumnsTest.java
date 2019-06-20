package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.Aes;
import de.unidue.iem.tdr.nis.client.util.MathUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static de.unidue.iem.tdr.nis.client.util.MathUtils.transform;

class _13_AES_ShiftRows_Subbytes_MixColumnsTest {

    @Test
    void solve() {
        TaskObject task = new TaskObject();
        task.setStringArray(new String[]{"3085b849d1547370864a964a9d05998a"});
        String expected = "86919d40c89b620c087704694737ad4d";

        String input_str = transform(task.getStringArray(0));
        int[] input = Aes.parseHexStringToBytes(input_str);
        int[] sub_input = Aes.subBytes(input);
        int[] shifted_rows = Aes.shiftRows(sub_input);
        int[] mixed_cols = Aes.mixColumns(shifted_rows);
        String result = transform(MathUtils.byteArrayToHexString(mixed_cols));
        Assertions.assertEquals(expected, result);
    }
}
