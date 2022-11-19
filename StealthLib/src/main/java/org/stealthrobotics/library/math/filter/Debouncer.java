// Copied from WPILIB and modified to remove dependencies on WPIUtilJNI
// https://raw.githubusercontent.com/wpilibsuite/allwpilib/aa9dfabde2fdaf32541697de2159878faa801fa1/wpimath/src/main/java/edu/wpi/first/math/filter/Debouncer.java

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package org.stealthrobotics.library.math.filter;

/**
 * A simple debounce filter for boolean streams. Requires that the boolean change value from
 * baseline for a specified period of time before the filtered value changes.
 */
public class Debouncer {
    public enum DebounceType {
        kRising,
        kFalling,
        kBoth
    }

    private final double m_debounceTimeSeconds;
    private final DebounceType m_debounceType;
    private boolean m_baseline;

    private double m_prevTimeSeconds;

    /**
     * Creates a new Debouncer.
     *
     * @param debounceTime The number of seconds the value must change from baseline for the filtered
     *                     value to change.
     * @param type         Which type of state change the debouncing will be performed on.
     */
    public Debouncer(double debounceTime, DebounceType type) {
        m_debounceTimeSeconds = debounceTime;
        m_debounceType = type;

        resetTimer();

        switch (m_debounceType) {
            case kBoth: // fall-through
            case kRising:
                m_baseline = false;
                break;
            case kFalling:
                m_baseline = true;
                break;
            default:
                throw new IllegalArgumentException("Invalid debounce type!");
        }
    }

    /**
     * Creates a new Debouncer. Baseline value defaulted to "false."
     *
     * @param debounceTime The number of seconds the value must change from baseline for the filtered
     *                     value to change.
     */
    public Debouncer(double debounceTime) {
        this(debounceTime, DebounceType.kRising);
    }

    private void resetTimer() {
        // nanoTime gives us nanoseconds (1 billionth of a second), but we prefer to work in seconds
        // here. We can convert by dividing by 1000000000 which we can more easily express in
        // scientific notation as 1e9. We can also multiply by the inverse, 1e-9.
        m_prevTimeSeconds = System.nanoTime() * 1e-9;
    }

    private boolean hasElapsed() {
        return (System.nanoTime() * 1e-9) - m_prevTimeSeconds >= m_debounceTimeSeconds;
    }

    /**
     * Applies the debouncer to the input stream.
     *
     * @param input The current value of the input stream.
     * @return The debounced value of the input stream.
     */
    public boolean calculate(boolean input) {
        if (input == m_baseline) {
            resetTimer();
        }

        if (hasElapsed()) {
            if (m_debounceType == DebounceType.kBoth) {
                m_baseline = input;
                resetTimer();
            }
            return input;
        } else {
            return m_baseline;
        }
    }
}
