package com.foodmatching.database.converter;

import com.google.common.base.Strings;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by shin on 2017. 11. 6..
 */
@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, String> {
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    @Override
    public String convertToDatabaseColumn(LocalDate localDate) {
        return localDate == null ? null : localDate.format(FORMATTER);
    }

    @Override
    public LocalDate convertToEntityAttribute(String s) {
        return Strings.isNullOrEmpty(s) ? null : LocalDate.parse(s,FORMATTER);
    }
}
