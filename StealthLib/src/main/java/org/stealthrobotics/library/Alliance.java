package org.stealthrobotics.library;

/**
 * Stores whether we're on the Red or Blue Alliance
 * <p>
 * This is usually set when the op-mode is initialized and then never changes.
 */
public enum Alliance {
    RED, BLUE;

    private static Alliance alliance = Alliance.RED;

    /**
     * Set the alliance once, at the beginning of your op-mode
     */
    public static void set(Alliance a) {
        alliance = a;
    }

    /**
     * @return the alliance we are on
     */
    public static Alliance get() {
        return alliance;
    }

    /**
     * Selects one of two values, depending on what alliance we're on, red or blue (in that order!)
     */
    public static <T> T select(T redValue, T blueValue) {
        return alliance == RED ? redValue : blueValue;
    }

    // mmmfixme: why?? Compiler failed to infer Runnable from lambda args.
    public static Runnable select(Runnable redValue, Runnable blueValue) {
        return alliance == RED ? redValue : blueValue;
    }

    /**
     * @return true if we're on the Red Alliance
     */
    public static boolean isRed() {
        return alliance == Alliance.RED;
    }

    /**
     * @return true if we're on the Blue Alliance
     */
    public static boolean isBlue() {
        return alliance == Alliance.BLUE;
    }
}
