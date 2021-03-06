package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.StringHelper;

import static de.unidue.iem.tdr.nis.client.util.DiffieHellman.pow_mod;

public class _19_RSA_Verschlüsselung implements TaskSolver {
    @Override
    public String solve(TaskObject task) {
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
        return result;
    }
}
