package by.batseko.library.validatior;

import java.util.concurrent.TimeUnit;

public class CacheValidator {
    private static final long MIN_TIMEOUT = TimeUnit.MINUTES.toMillis(1);

    public CacheValidator(){}

    public boolean isLessThanMinTimeout(long value) {
        return value < MIN_TIMEOUT;
    }
}
