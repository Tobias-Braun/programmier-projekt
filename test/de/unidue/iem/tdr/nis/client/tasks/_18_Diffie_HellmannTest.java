package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.DiffieHellman;
import de.unidue.iem.tdr.nis.client.util.StringHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static de.unidue.iem.tdr.nis.client.util.DiffieHellman.get_rand_smaller_p;
import static de.unidue.iem.tdr.nis.client.util.DiffieHellman.pow_mod;
import static org.junit.jupiter.api.Assertions.*;

class _18_Diffie_HellmannTest {

    @Test
    void solve() {
        String input = "99_104_101_116_127_118_114_111_105_104";
        String expected = "encryption";

        TaskObject task = new TaskObject();
        task.setIntArray(new int[]{29, 19});
        task.setDoubleArray(new double[]{27});

        _18_Diffie_Hellmann.p = task.getIntArray(0);
        _18_Diffie_Hellmann.g = task.getIntArray(1);
        _18_Diffie_Hellmann.B = (int) task.getDoubleArray(0);
        _18_Diffie_Hellmann.a = 6;

        String[] input_str = input.split("_");
        int[] input_bytes = new int[input_str.length];
        for (int i = 0; i < input_bytes.length; i++) {
            input_bytes[i] = Integer.parseInt(input_str[i]);
        }
        int[] decrypted = DiffieHellman.decrypt(input_bytes, _18_Diffie_Hellmann.B, _18_Diffie_Hellmann.a, _18_Diffie_Hellmann.p);
        StringHelper res = StringHelper.empty();
        for (int i = 0; i < decrypted.length; i++) {
            res.append((char) decrypted[i]);
        }
        String result = res.toString();
        Assertions.assertEquals(expected, result);
    }

    @Test
    void getMoreParams() {
        TaskObject task = new TaskObject();
        task.setIntArray(new int[]{29, 19});
        task.setDoubleArray(new double[]{27});
        String expected = "22";

        _18_Diffie_Hellmann.p = task.getIntArray(0);
        _18_Diffie_Hellmann.g = task.getIntArray(1);
        _18_Diffie_Hellmann.B = (int) task.getDoubleArray(0);
        _18_Diffie_Hellmann.a = get_rand_smaller_p(_18_Diffie_Hellmann.p);
        int A = pow_mod(_18_Diffie_Hellmann.g, _18_Diffie_Hellmann.a, _18_Diffie_Hellmann.p);
        System.out.println("a: " + _18_Diffie_Hellmann.a);

        Assertions.assertEquals(expected, "" + A);
    }
}
