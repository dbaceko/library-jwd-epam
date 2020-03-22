package by.batseko.library.validatior;

import by.batseko.library.exception.ValidatorException;

public class PoolValidator {

    public boolean isLessOrEqualsZero(int value) {
        return value <= 0;
    }

    public void checkContainsNull(Object... elements) throws ValidatorException {
        for (Object element: elements) {
            if (element == null) {
                throw new ValidatorException("Value is null");
            }
        }
    }
}
