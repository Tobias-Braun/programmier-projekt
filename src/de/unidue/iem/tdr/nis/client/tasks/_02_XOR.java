package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.MathUtils;

public class _02_XOR implements TaskSolver {
    @Override
    public String solve(TaskObject task) {
        return MathUtils.hexXor(task.getString(0), task.getString(1));
    }
}
