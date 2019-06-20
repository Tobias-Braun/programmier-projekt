package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.DesEncryption;
import de.unidue.iem.tdr.nis.client.util.Logger;
import de.unidue.iem.tdr.nis.client.util.StringHelper;

public class _06_DES_Rundenschlüssel_Berechnung implements TaskSolver {
    @Override
    public String solve(TaskObject task) {
        Logger.logEnter("06.solve", task.getStringArray(0));
        String key = task.getStringArray(0);
        key = DesEncryption.pc_1(key);
        String c = key.substring(0, 28);
        String d = key.substring(28, 56);
        String roundKey = "";
        for (int i = 1; i <= task.getIntArray(0); i++) {
            System.out.println(i);
            c = DesEncryption.leftShift(c);
            d = DesEncryption.leftShift(d);
            if (i != 1 && i != 2 && i != 9 && i != 16) {
                c = DesEncryption.leftShift(c);
                d = DesEncryption.leftShift(d);
            }
            roundKey = DesEncryption.pc_2(c + d);
        }
        Logger.logExit("06.solve", roundKey);
        return roundKey;
    }
}
