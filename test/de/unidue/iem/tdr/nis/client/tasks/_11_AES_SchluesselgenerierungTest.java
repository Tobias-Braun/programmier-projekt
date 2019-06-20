package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.Aes;
import de.unidue.iem.tdr.nis.client.util.StringHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static de.unidue.iem.tdr.nis.client.util.Aes.parseHexStringToBytes;
import static de.unidue.iem.tdr.nis.client.util.MathUtils.decToHex;

class _11_AES_SchluesselgenerierungTest {

    @Test
    void solve() {
        TaskObject task = new TaskObject();
        task.setStringArray(new String[]{"cb628baeeeba913288654ea330413248"});
        String expected = "cb628baeeeba913288654ea330413248_4941d9aaa7fb48982f9e063b1fdf3473_d559566a72a21ef25d3c18c942e32cba";
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
        Assertions.assertEquals(expected, result.toString());
    }
}
