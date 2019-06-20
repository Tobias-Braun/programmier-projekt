package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;

public class _20_RSA_Verschlüsselung implements TaskSolver {

    static int p = 71;
    static int q = 73;
    static int n = p * q;
    static int e = 29;
    static int phi = (p - 1) * (q - 1);
    @Override
    public String solve(TaskObject task) {
        return null;
    }

    public static String[] gen_key_pair() {
        e = 23;
        phi = (p - 1) * (q - 1);
        return new String[]{Integer.toString(n), Integer.toString(e)};

    }
}
