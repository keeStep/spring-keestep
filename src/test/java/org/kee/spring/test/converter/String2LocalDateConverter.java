package org.kee.spring.test.converter;

import org.kee.spring.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 *
 * @author Eric
 * @date 2023/10/13 10:42 下午
 */
public class String2LocalDateConverter implements Converter<String, LocalDate> {

    private final DateTimeFormatter DATE_TIME_FORMATTER;

    public String2LocalDateConverter(String pattern) {
        this.DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public LocalDate convert(String localDate) {
        return LocalDate.parse(localDate, DATE_TIME_FORMATTER);
    }
}
