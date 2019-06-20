package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.GF8;
import de.unidue.iem.tdr.nis.client.util.StringHelper;

import static de.unidue.iem.tdr.nis.client.util.MathUtils.decToHex;
import static de.unidue.iem.tdr.nis.client.util.MathUtils.hexToDec;

public class _10_Multiplikation_in_GF8 implements TaskSolver {
    @Override
    public String solve(TaskObject task) {
        int first = hexToDec(task.getStringArray(0));
        int sec = hexToDec(task.getStringArray(1));
        StringHelper hexVal = decToHex(GF8.gf8_mult(first, sec));
        return hexVal.length() == 1 ? "0" + hexVal : hexVal.toString();
    }
}
