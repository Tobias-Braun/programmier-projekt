package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.DiffieHellman;
import de.unidue.iem.tdr.nis.client.util.StringHelper;

import static de.unidue.iem.tdr.nis.client.util.DiffieHellman.get_rand_smaller_p;
import static de.unidue.iem.tdr.nis.client.util.DiffieHellman.pow_mod;

public class _18_Diffie_Hellmann implements TaskSolver {

    static int a;
    static int p;
    static int g;
    static int B;

    @Override
    public String solve(TaskObject task) {
        String[] input_str = task.getStringArray(0).split("_");
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
        return result;
    }

    public static String[] getMoreParams(TaskObject task) {
        _18_Diffie_Hellmann.p = task.getIntArray(0);
        _18_Diffie_Hellmann.g = task.getIntArray(1);
        _18_Diffie_Hellmann.B = (int) task.getDoubleArray(0);
        _18_Diffie_Hellmann.a = get_rand_smaller_p(_18_Diffie_Hellmann.p);
        int A = pow_mod(_18_Diffie_Hellmann.g, _18_Diffie_Hellmann.a, _18_Diffie_Hellmann.p);
        System.out.println("a: " + _18_Diffie_Hellmann.a);

        return new String[]{"" + A};
    }
}
