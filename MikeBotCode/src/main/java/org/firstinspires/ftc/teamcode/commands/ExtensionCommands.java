package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ExtensionSubsystem;

public class ExtensionCommands {

    public static class Extend extends CommandBase {
        final ExtensionSubsystem extension;

        public Extend(ExtensionSubsystem extension) {
            this.extension = extension;
            addRequirements(extension);
        }

        @Override
        public void initialize() {
            extension.setPosition(ExtensionSubsystem.MAX_POSITION);
        }

        @Override
        public void end(boolean isInterrupted) {
            int p = extension.getCurrentPositionNoCache();
            // Aim a little farther out than we sense to give a softer landing.
            extension.setPosition(Math.min(ExtensionSubsystem.MAX_POSITION, p + 100));
        }
    }

    public static class Retract extends CommandBase {
        final ExtensionSubsystem extension;

        public Retract(ExtensionSubsystem extension) {
            this.extension = extension;
            addRequirements(extension);
        }

        @Override
        public void initialize() {
            extension.setPosition(ExtensionSubsystem.MIN_POSITION);
        }

        @Override
        public void end(boolean isInterrupted) {
            int p = extension.getCurrentPositionNoCache();
            // Aim a little farther back than we sense to give a softer landing.
            extension.setPosition(Math.max(ExtensionSubsystem.MIN_POSITION, p - 100));
        }
    }
}
