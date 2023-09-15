package ru.mts.tariffparser.util;

import java.time.format.DateTimeFormatter;

public class Constants {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final String TARIFFS_LIST_URL =
            "https://moskva.mts.ru/personal/mobilnaya-svyaz/tarifi/vse-tarifi/mobile-tv-inet";
    public static final String BASE_TARIFF_URL = "https://moskva.mts.ru/personal/mobilnaya-svyaz/tarifi/vse-tarifi/";
}
