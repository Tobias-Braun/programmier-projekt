package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.RC4;
import de.unidue.iem.tdr.nis.client.util.StringHelper;

public class _16_RC4_Keyscheduling implements TaskSolver {
    @Override
    public String solve(TaskObject task) {
        String[] key_str = task.getStringArray(0).split("_");
        int[] key = new int[key_str.length];
        for (int i = 0; i < key.length; i++) {
            key[i] = Integer.parseInt(key_str[i]);
        }

        RC4 rc4 = new RC4(new int[key.length]); // state is not important key array only has the same length
        rc4.key_schedule(key);
        int[] state = rc4.get_state();
        StringHelper res_str = StringHelper.empty();
        for (int i = 0; i < state.length; i++) {
            res_str.append(state[i]);
            if (i < state.length - 1) res_str.append("_");
        }
        String result = res_str.toString();
        return result;
    }
}
