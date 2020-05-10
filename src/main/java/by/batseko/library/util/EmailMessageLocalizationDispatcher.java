package by.batseko.library.util;

import by.batseko.library.command.SupportedLocaleStorage;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class EmailMessageLocalizationDispatcher {
    private static final String RESOURCE_NAME = "EmailMessages";

    public String getLocalizedMessage(String key, String... messageArgs) {
        StringBuilder message = new StringBuilder();
        for (SupportedLocaleStorage locale : SupportedLocaleStorage.values()) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_NAME, locale.getLocale());
            String[] localizedArgs = getLocalizedMessageArgs(resourceBundle, messageArgs);
            String formattedMessageFragment = String.format(resourceBundle.getString(key), localizedArgs);
            message.append(formattedMessageFragment).append('\n');
        }
        return message.toString();
    }

    private String[] getLocalizedMessageArgs(ResourceBundle resourceBundle, String... messageArgs) {
        String[] localizedArgs = new String[messageArgs.length];
        for (int i = 0; i < localizedArgs.length; i++) {
            try {
                String arg = resourceBundle.getString(messageArgs[i]);
                localizedArgs[i] = arg;
            } catch (MissingResourceException e) {
                localizedArgs[i] = messageArgs[i];
            }
        }
        return localizedArgs;
    }
}
