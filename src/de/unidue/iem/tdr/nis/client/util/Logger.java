package de.unidue.iem.tdr.nis.client.util;

public class Logger {

    private static final String RESET_COLOR = (char) 27 + "[37;49m";
    private static int level = -1;
    private static int CALL_LEVEL_MAX = 5;
    private static final int DBLARROW_LEFT = 0x21C9;
    private static final int DBLARROW_RIGHT = 0x21C7;

    public static void logEnter(String methodName, Object... params) {
        level += 1;
        if (level > CALL_LEVEL_MAX) return;
        StringHelper helper = StringHelper.of(colorString(32, " IN  "));
        appendLevelTabs(helper);
        printMethodName(methodName, helper);
        printParams(helper, params);
        System.out.println(helper.toString());
    }

    public static void logExit(String methodName, Object... params) {
        if (level > CALL_LEVEL_MAX) return;
        StringHelper helper = StringHelper.of(colorString(31, "OUT  "));
        appendLevelTabs(helper);
        printMethodName(methodName, helper);
        printParams(helper, params);
        level -= 1;
        System.out.println(helper.toString());
    }

    private static void printParams(StringHelper helper, Object[] params) {
        int i = 35;
        for (Object param : params) {
            helper.append(" ");
            helper.append(colorString(i++, format(param)));
        }
    }

    private static void printMethodName(String methodName, StringHelper helper) {
        String[] methodStrings = methodName.split("\\.");
        int i = 33;
        for (String methodString : methodStrings) {
            helper.append(colorString(i++, methodString));
            helper.append(".");
        }
        helper.replace(helper.length() - 1, helper.length(), "()");
    }

    private static String colorString(int color, Object s) {
        return (char) 27 + "[" + color + "m" + s + RESET_COLOR;
    }

    private static void appendLevelTabs(StringHelper helper) {
        for (int i = 0; i < level; i++) {
            helper.append("    ");
        }
    }

    public static void logCorrect(int task, String taskName) {
        logTask(task, taskName, 32, "CORRECT");
    }

    public static void logWrong(int task, String taskName) {
        logTask(task, taskName, 31, "BAD RESULT");
    }

    public static void logTask(int task, String taskName, int color, String message) {
        System.out.println(colorString(color, (char)DBLARROW_LEFT + " TASK " + task + " - " + taskName + " - " + message + " " + (char)DBLARROW_RIGHT));
    }

    public static void printTaskEnd() {
        System.out.println();
        System.out.println("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -");
        System.out.println();
    }

    public static void printResults(boolean[] results, String[] taskNames) {
        System.out.println("###         RESULTS         ###");
        System.out.println();
        for (int i = 0; i < results.length; i++) {
            if (results[i]) {
                logCorrect(i + 1, taskNames[i]);
            } else {
                logWrong(i + 1, taskNames[i]);
            }
        }
    }

    private static String format(Object o) {
        StringHelper s = StringHelper.of(String.valueOf(o));
        for (Formatters formatter : Formatters.values()) {
            if (s.toString().matches(formatter.formatMatch)) return formatter.formatFunction.apply(s).toString();
        }
        return s.toString();
    }
}
