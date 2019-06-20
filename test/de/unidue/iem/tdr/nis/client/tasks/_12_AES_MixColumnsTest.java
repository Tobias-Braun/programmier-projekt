package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.Aes;
import de.unidue.iem.tdr.nis.client.util.MathUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static de.unidue.iem.tdr.nis.client.util.MathUtils.transform;

class _12_AES_MixColumnsTest {

    private static final int[] alternative_input = new int[]{
            0xD4, 0xE0, 0xB8, 0x1E, 0xBF, 0xB4, 0x41, 0x27, 0x5D, 0x52, 0x11, 0x98, 0x30, 0xAE, 0xF1, 0xE5
    };

    @Test
    public void solve() {
        TaskObject task = new TaskObject();
        task.setStringArray(new String[]{"60866a0a1a4624d36ccdb602aa73a762"});
        String expected = "31c32c5809297af1202ed0cb1fdc2af5";
        String transformed_str = transform(task.getStringArray(0));
        int[] input = Aes.parseHexStringToBytes(transformed_str);
        int[] result = Aes.mixColumns(input);
        String hex_str = MathUtils.byteArrayToHexString(result);
        Assertions.assertEquals(expected, transform(hex_str));
    }

}
