package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.Alphabet;
import de.unidue.iem.tdr.nis.client.util.StringHelper;

public class _05_Viginere implements TaskSolver{
    @Override
    public String solve(TaskObject task) {
        final StringHelper cipher = StringHelper.of(task.getStringArray(0).toUpperCase());
        final StringHelper key = StringHelper.of(task.getStringArray(1).toUpperCase());
        StringHelper solution = StringHelper.applyForEachChar(cipher, key,
                (c, k) -> Alphabet.getCharNum(Alphabet.charMinus(c, k)));
        return solution.toString();
    }

    public static void main(String[] args) {
        System.out.println(StringHelper.applyForEachChar(StringHelper.of("XBSFZPCMIPM"), StringHelper.of("TESTKEY"), (c, k) -> Alphabet.getCharNum(Alphabet.charMinus(c, k))
        ));
    }
}
