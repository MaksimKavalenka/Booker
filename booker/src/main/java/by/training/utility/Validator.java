package by.training.utility;

public abstract class Validator {

    public static boolean allNotNull(Object... args) {
        for (Object arg : args) {
            if (arg == null) {
                return false;
            }
        }
        return true;
    }

}
