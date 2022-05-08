package org.stealthrobotics.library;

// mmmfixme: java docs for everyting in library.

// This stores the current alliance and provides simple methods to ask what alliance we're on,
// and to make choices based on our alliance.

public enum Alliance {
    RED, BLUE;

    private static Alliance alliance = Alliance.RED;

    public static void set(Alliance a) {
        alliance = a;
    }

    public static Alliance get() {
        return alliance;
    }

    public static <T> T select(T redValue, T blueValue) {
        return alliance == RED ? redValue : blueValue;
    }

    // mmmfixme: why??
    public static Runnable select(Runnable redValue, Runnable blueValue) {
        return alliance == RED ? redValue : blueValue;
    }

    public static boolean isRed() {
        return alliance == Alliance.RED;
    }

    public static boolean isBlue() {
        return alliance == Alliance.BLUE;
    }
}
