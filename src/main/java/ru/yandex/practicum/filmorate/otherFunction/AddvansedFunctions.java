package ru.yandex.practicum.filmorate.otherFunction;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

public class AddvansedFunctions {

    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static String stringToBlueColor(String text) {
        return ANSI_BLUE + text + ANSI_RESET;
    }

    public static String stringToGreenColor(String text) {
        return ANSI_GREEN + text + ANSI_RESET;
    }


}
