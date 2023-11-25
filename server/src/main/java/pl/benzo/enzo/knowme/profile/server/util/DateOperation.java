package pl.benzo.enzo.knowme.profile.server.util;

import java.time.LocalDateTime;

public final class DateOperation {
    public static LocalDateTime addHoursToDate(LocalDateTime date,long hours){
        return date.plusHours(hours);
    }
}
