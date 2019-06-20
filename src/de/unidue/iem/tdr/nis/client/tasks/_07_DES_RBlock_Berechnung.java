package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.DesEncryption;
import de.unidue.iem.tdr.nis.client.util.MathUtils;
import de.unidue.iem.tdr.nis.client.util.StringHelper;

public class _07_DES_RBlock_Berechnung implements TaskSolver {
    @Override
    public String solve(TaskObject task) {
        final String input = task.getStringArray(0);
        final int rounds = task.getIntArray(0);
        final String key = "0000000000000000000000000000000000000000000000000000000000000000";
        final String ip = DesEncryption.initial_permutation(input);
        String l_block = ip.substring(0, 32);
        String r_block = ip.substring(32, 64);
        String c = key.substring(0, 28);
        String d = key.substring(28, 56);
        String roundKey = "";
        String permuted = "";
        for (int i = 1; i <= rounds; i++) {
            String expansion = DesEncryption.expansion(r_block);
            c = DesEncryption.leftShift(c);
            d = DesEncryption.leftShift(d);
            if (i != 1 && i != 2 && i != 9 && i != 16) {
                c = DesEncryption.leftShift(c);
                d = DesEncryption.leftShift(d);
            }
            roundKey = DesEncryption.pc_2(c + d);
            String xorResult = MathUtils.binaryXor(StringHelper.of(expansion), StringHelper.of(roundKey));
            String after_s_boxes = DesEncryption.apply_s_boxes(xorResult);
            permuted = DesEncryption.permute(after_s_boxes);
            String newRBlock = MathUtils.binaryXor(StringHelper.of(permuted), StringHelper.of(l_block));
            l_block = r_block;
            r_block = newRBlock;
        }
        return r_block;
    }
}
