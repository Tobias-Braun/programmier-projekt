package de.unidue.iem.tdr.nis.client.util;

public class RC4 {

    public RC4(int[] state) {
        this.state = state;
    }

    private int i = 0;
    private int j = 0;
    private int[] state;

    public void init() {
        j = 0;
        i = 0;
    }

    public int gen_loop() {
        i = (i + 1) % state.length;
        j = (j + state[i]) % state.length;
        int temp = state[j];
        state[j] = state[i];
        state[i] = temp;
        return state[(state[i] + state[j]) % state.length];
    }

    public int[] get_state() {
        return state;
    }

    public int[] getCharCodeArrayFromString(String input) {
        int[] charCodeBytes = new int[input.length()];
        for (int i = 0; i < charCodeBytes.length; i++) {
            charCodeBytes[i] = (int) input.charAt(i);
        }
        return charCodeBytes;
    }

    public void key_schedule(int[] key) {
        for (int i = 0; i < state.length; i++) {
            state[i] = i;
        }
        int j = 0;
        for (int i = 0; i < state.length; i++) {
            j = (j + key[i % key.length] + state[i]) % state.length;
            int temp = state[j];
            state[j] = state[i];
            state[i] = temp;
        }
    }
}
