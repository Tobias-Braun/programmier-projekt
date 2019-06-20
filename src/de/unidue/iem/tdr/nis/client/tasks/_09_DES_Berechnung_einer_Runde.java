package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.DesEncryption;
import de.unidue.iem.tdr.nis.client.util.MathUtils;
import de.unidue.iem.tdr.nis.client.util.StringHelper;

public class _09_DES_Berechnung_einer_Runde implements TaskSolver {
    @Override
    public String solve(TaskObject task) {
        String l_block = task.getStringArray(0);
        String r_block = task.getStringArray(1);
        String key = task.getStringArray(2);
        int round = task.getIntArray(0);
        String roundkey = DesEncryption.getRoundKey(round, key);
        String expansion = DesEncryption.expansion(r_block);
        String xorResult = MathUtils.binaryXor(StringHelper.of(expansion), StringHelper.of(roundkey));
        String after_s_boxes = DesEncryption.apply_s_boxes(xorResult);
        String permuted = DesEncryption.permute(after_s_boxes);
        String newRBlock = MathUtils.binaryXor(StringHelper.of(permuted), StringHelper.of(l_block));
        l_block = r_block;
        r_block = newRBlock;
        return l_block + r_block;
    }
}
