package de.ngloader.asps.core.i18n;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class I18nLocale {

	private Locale locale;

	private Map<String, String> messages = new HashMap<>();

	public I18nLocale(Locale locale) {
		this.locale = locale;
	}

	public String getMessage(String key) {
		return this.messages.getOrDefault(key, I18n.TRANSLATION_NOT_FOUND);
	}

	public Locale getLocale() {
		return this.locale;
	}
}
