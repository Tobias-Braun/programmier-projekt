package de.unidue.iem.tdr.nis.client.util;

public class MutableString {

    private String value;

    public static MutableString of(String str) {
        return new MutableString(str);
    }

    public MutableString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
