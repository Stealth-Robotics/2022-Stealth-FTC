package org.stealthrobotics.library.commands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.stealthrobotics.library.AutoToTeleStorage;

import java.util.function.DoubleSupplier;

/**
 * Saves the final heading from autonomous mode for use in the next teleop mode.
 */
public class SaveAutoHeadingCommand extends InstantCommand {
    /**
     * Create a new command to save the bot heading at the end of autonomous
     *
     * @param finalHeadingSupplier a snippet of code to get the bot's heading. Ex: "() -> drive.getHeading()"
     */
    public SaveAutoHeadingCommand(DoubleSupplier finalHeadingSupplier) {
        super(() -> AutoToTeleStorage.finalAutoHeading = finalHeadingSupplier.getAsDouble());
    }
}