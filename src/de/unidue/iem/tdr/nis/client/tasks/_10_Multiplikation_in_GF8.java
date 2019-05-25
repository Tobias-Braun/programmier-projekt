package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.GF8;
import de.unidue.iem.tdr.nis.client.util.Logger;
import de.unidue.iem.tdr.nis.client.util.MathUtils;
import de.unidue.iem.tdr.nis.client.util.StringHelper;

public class _10_Multiplikation_in_GF8 implements TaskSolver {
    @Override
    public String solve(TaskObject task) {
        int first = Integer.parseInt(task.getStringArray(0));
        int sec = Integer.parseInt(task.getStringArray(1));
        return MathUtils.binToHex(StringHelper.of(String.valueOf(GF8.multiply(first, sec)))).toString();
    }
}
