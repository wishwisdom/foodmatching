package com.foodmatching.database.converter;

import com.google.common.base.Strings;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by shin on 2017. 11. 6..
 */
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, String> {
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    @Override
    public String convertToDatabaseColumn(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.format(FORMATTER);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String s) {
        return Strings.isNullOrEmpty(s) ? null : LocalDateTime.parse(s, FORMATTER);
    }
}
