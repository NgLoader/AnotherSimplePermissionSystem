package de.ngloader.asps.core.database.converter;

import java.util.Locale;

import javax.persistence.AttributeConverter;

public class LocaleConverter implements AttributeConverter<Locale, String> {

	@Override
	public String convertToDatabaseColumn(Locale attribute) {
		return attribute.toLanguageTag();
	}

	@Override
	public Locale convertToEntityAttribute(String dbData) {
		return Locale.forLanguageTag(dbData);
	}
}