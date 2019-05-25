package de.unidue.iem.tdr.nis.client.util;

import java.util.function.Function;

public enum Formatters {
    BINARY_NUM("(?:[01^2-9^a-f][01][01][01])+", (target) -> target.repeatForBlockSize(4, string -> string.append(" "))),
    DECIMAL_NUM("(?:[0-9])+", (target) -> target.reverse().repeatForBlockSize(3, string -> string.append(" ")).reverse()),
    HEX_NUM("(?:[0-9a-f][0-9a-f])+", (target) -> target.repeatForBlockSize(2, string -> string.append(" ")));

    public String formatMatch;
    public Function<StringHelper, StringHelper> formatFunction;

    Formatters(String formatMatch, Function<StringHelper, StringHelper> formatFunction) {
        this.formatMatch = formatMatch;
        this.formatFunction = formatFunction;
    }
}
