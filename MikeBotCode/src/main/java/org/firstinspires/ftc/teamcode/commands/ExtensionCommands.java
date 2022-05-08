package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Extension;

public class ExtensionCommands {

    public static class Extend extends CommandBase {
        final Extension extension;

        public Extend(Extension extension) {
            this.extension = extension;
            addRequirements(extension);
        }

        @Override
        public void initialize() {
            extension.setPosition(Extension.MAX_POSITION);
        }

        @Override
        public void end(boolean isInterrupted) {
            int p = extension.getCurrentPositionNoCache();
            // Aim a little farther out than we sense to give a softer landing.
            extension.setPosition(Math.min(Extension.MAX_POSITION, p + 100));
        }
    }

    public static class Retract extends CommandBase {
        final Extension extension;

        public Retract(Extension extension) {
            this.extension = extension;
            addRequirements(extension);
        }

        @Override
        public void initialize() {
            extension.setPosition(Extension.MIN_POSITION);
        }

        @Override
        public void end(boolean isInterrupted) {
            int p = extension.getCurrentPositionNoCache();
            // Aim a little farther back than we sense to give a softer landing.
            extension.setPosition(Math.max(Extension.MIN_POSITION, p - 100));
        }
    }
}
