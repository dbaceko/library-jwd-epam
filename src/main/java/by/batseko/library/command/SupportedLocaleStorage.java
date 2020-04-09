package by.batseko.library.command;

import by.batseko.library.exception.EnumCastException;

import java.util.Locale;

public enum SupportedLocaleStorage {
    ENG(Locale.US),
    RUS(new Locale("ru", "RU"));

    private final Locale locale;

    SupportedLocaleStorage(Locale locale) {
        this.locale = locale;
    }

    public static SupportedLocaleStorage getLocaleType(String inputLanguage) throws EnumCastException {
        for (SupportedLocaleStorage currentLocale: SupportedLocaleStorage.values()) {
            if (currentLocale.locale.getLanguage().equals(inputLanguage)) {
                return currentLocale;
            }
        }
        throw new EnumCastException(String.format("locale %s is not found", inputLanguage));
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public Locale getLocale() {
        return locale;
    }
}
