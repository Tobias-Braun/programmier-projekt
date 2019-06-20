package de.unidue.iem.tdr.nis.client.tasks;

import de.unidue.iem.tdr.nis.client.TaskObject;
import de.unidue.iem.tdr.nis.client.util.DesEncryption;
import de.unidue.iem.tdr.nis.client.util.MathUtils;
import de.unidue.iem.tdr.nis.client.util.StringHelper;

public class _08_DES_Feistel_Funktion implements TaskSolver {
    @Override
    public String solve(TaskObject task) {
        final String input = task.getStringArray(0);
        String key = task.getStringArray(1);
        String l_block = input.substring(0, 32);
        String r_block = input.substring(32, 64);
        String permuted = "";
        String expansion = DesEncryption.expansion(r_block);
        String xorResult = MathUtils.binaryXor(StringHelper.of(expansion), StringHelper.of(key));
        String after_s_boxes = DesEncryption.apply_s_boxes(xorResult);
        permuted = DesEncryption.permute(after_s_boxes);
        String newRBlock = MathUtils.binaryXor(StringHelper.of(permuted), StringHelper.of(l_block));
        l_block = r_block;
        r_block = newRBlock;
        return r_block;
    }
}
