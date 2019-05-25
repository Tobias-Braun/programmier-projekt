package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.MathUtils;

public class _04_Faktorisierung implements TaskSolver {
    @Override
    public String solve(TaskObject task) {
        return MathUtils.getPrimeFactors(task.getIntArray(0));
    }
}
