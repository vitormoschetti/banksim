package com.banksim.domain.shared.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class InstantUtil {

    public static LocalDate toLocalDate(Instant instant) {
        return LocalDate.ofInstant(instant, ZoneId.of("America/Sao_Paulo"));
    }

}
