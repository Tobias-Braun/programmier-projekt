package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.MathUtils;

public class _03_Modulo implements TaskSolver {
    @Override
    public String solve(TaskObject task) {
        return String.valueOf(MathUtils.mod(task.getIntArray(0), task.getIntArray(1)));
    }
}
