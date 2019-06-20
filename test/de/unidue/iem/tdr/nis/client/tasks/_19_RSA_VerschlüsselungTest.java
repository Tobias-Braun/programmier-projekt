package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.DiffieHellman;
import de.unidue.iem.tdr.nis.client.util.StringHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static de.unidue.iem.tdr.nis.client.util.DiffieHellman.pow_mod;
import static org.junit.jupiter.api.Assertions.*;

class _19_RSA_VerschlüsselungTest {

    @Test
    void solve() {
        TaskObject task = new TaskObject();
        task.setStringArray(new String[]{"wiretapping"});
        task.setIntArray(new int[]{377, 253});
        String expected = "119_105_114_101_116_97_112_112_105_110_103";

        int[] plain_bytes = new int[task.getStringArray(0).length()];
        for (int i = 0; i < plain_bytes.length; i++) {
            plain_bytes[i] = (int) task.getStringArray(0).charAt(i);
        }
        StringHelper res_str = StringHelper.empty();
        for (int i = 0; i < plain_bytes.length; i++) {
            res_str.append(pow_mod(plain_bytes[i], task.getIntArray(1), task.getIntArray(0)));
            if (i < plain_bytes.length - 1) res_str.append("_");
        }

        String result = res_str.toString();


        Assertions.assertEquals(expected, result);
    }
}
