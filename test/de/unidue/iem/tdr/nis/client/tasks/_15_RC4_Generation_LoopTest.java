package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.RC4;
import de.unidue.iem.tdr.nis.client.util.StringHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class _15_RC4_Generation_LoopTest {

    @Test
    void solve() {
        TaskObject task = new TaskObject();
        task.setStringArray(new String[]{"1_0_11_9_12_6_3_8_5_10_7_4_2", "Datenschutz"});
        String expected = "1412457212947";

        int t = task.getStringArray(1).length();
        String[] numbers = task.getStringArray(0).split("_");
        int[] state = new int[numbers.length];
        for (int i = 0; i < state.length; i++) {
            state[i] = Integer.parseInt(numbers[i]);
        }
        RC4 rc4 = new RC4(state);
        rc4.init();
        int[] resultArray = new int[t];
        for (int i = 0; i < t; i++) {
            resultArray[i] = rc4.gen_loop();
        }
        StringHelper result_helper = StringHelper.empty();
        for (int i = 0; i < t; i++) {
            result_helper.append(resultArray[i]);
        }
        String result = result_helper.toString();

        Assertions.assertEquals(expected, result);
    }
}
