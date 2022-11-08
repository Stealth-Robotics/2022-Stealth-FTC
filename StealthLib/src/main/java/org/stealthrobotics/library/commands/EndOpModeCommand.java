package org.stealthrobotics.library.commands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.stealthrobotics.library.opmodes.StealthOpMode;

/**
 * A simple command to end the current opmode now.
 * <p>
 * Use this as the last command in your auto sequence to end the opmode as soon as possible instead
 * of waiting for the rest of the 30s to expire.
 */
public class EndOpModeCommand extends InstantCommand {

    /**
     * Creates a new command to end the opmode.
     *
     * @param opMode the opmode to end
     */
    public EndOpModeCommand(StealthOpMode opMode) {
        super(() -> opMode.requestOpModeStop());
    }
}
