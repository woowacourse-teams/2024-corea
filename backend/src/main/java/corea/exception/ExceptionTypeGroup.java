package corea.exception;

import java.util.EnumSet;
import java.util.Set;

public enum ExceptionTypeGroup {

    INTERNAL_SERVER_ERROR(EnumSet.of(ExceptionType.SERVER_ERROR));
    Set<ExceptionType> exceptionTypes;

    ExceptionTypeGroup(final Set<ExceptionType> errorTypes) {
        this.exceptionTypes = errorTypes;
    }

    public Set<ExceptionType> getErrorTypes() {
        return exceptionTypes;
    }
}
