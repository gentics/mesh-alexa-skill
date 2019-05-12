package com.gentics.mesh.alexa.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class I18NUtil {

	private static final Logger log = LoggerFactory.getLogger(I18NUtil.class);

	public static final String BUNDLENAME = "translations";

	public static final Locale DEFAULT_LOCALE = new Locale("de", "DE");

	/**
	 * Return the i18n string for the given bundle, local and i18n key.
	 * 
	 * @param locale
	 *            Locale used to determine the language
	 * @param key
	 *            I18n key
	 * @return Localized string
	 */
	public static String i18n(Locale locale, String key, String... parameters) {
		if (locale == null) {
			locale = DEFAULT_LOCALE;
		}
		log.info("Using locale: " + locale.getLanguage() + "_" + locale.getCountry());
		String i18nMessage = "";
		try {
			ResourceBundle labels = ResourceBundle.getBundle("i18n." + BUNDLENAME, locale);
			MessageFormat formatter = new MessageFormat("");
			formatter.setLocale(locale);
			formatter.applyPattern(labels.getString(key));
			i18nMessage = formatter.format(parameters);
		} catch (Exception e) {
			log.error("Could not format i18n message for key {" + key + "}", e);
			i18nMessage = key;
		}
		return i18nMessage;
	}
}
