package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;

public class _01_Klartext implements TaskSolver {
    @Override
    public String solve(TaskObject task) {
        return task.getStringArray(0);
    }
}
