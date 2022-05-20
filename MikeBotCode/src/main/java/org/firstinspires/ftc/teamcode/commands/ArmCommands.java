package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;

import java.util.Arrays;

public class ArmCommands {

    public static class Up extends CommandBase {
        final ArmSubsystem arm;

        public Up(ArmSubsystem arm) {
            this.arm = arm;
            addRequirements(arm);
        }

        @Override
        public void initialize() {
            arm.setPosition(ArmSubsystem.MAX_POSITION);
        }

        @Override
        public void end(boolean isInterrupted) {
            int p = arm.getCurrentPositionNoCache();
            // Aim a little farther up than we sense to give a softer landing.
            arm.setPosition(Math.min(ArmSubsystem.MAX_POSITION, p + 100));
        }
    }

    public static class Down extends CommandBase {
        final ArmSubsystem arm;

        public Down(ArmSubsystem arm) {
            this.arm = arm;
            addRequirements(arm);
        }

        @Override
        public void initialize() {
            arm.setPosition(ArmSubsystem.MIN_POSITION);
        }

        @Override
        public void end(boolean isInterrupted) {
            int p = arm.getCurrentPositionNoCache();
            // Aim a little farther down than we sense to give a softer landing.
            arm.setPosition(Math.max(ArmSubsystem.MIN_POSITION, p - 100));
        }
    }

    public static class NextPreset extends CommandBase {
        //        private static final int[] presetPositions = {0, 200, 2200, 2800, 3500};
        private static final int[] presetPositions = {20, 200, 400, 600, 800, 1000, 1200, 1400, 1600};

        // Factor in a small tolerance to the current position so we don't go from 1999 to 2000.
        private static final int TOLERANCE = 50;

        public enum Direction {UP, DOWN}

        final ArmSubsystem arm;
        final Direction direction;

        public NextPreset(ArmSubsystem arm, Direction direction) {
            this.arm = arm;
            this.direction = direction;
        }

        @Override
        public void initialize() {
            int pos = arm.getCurrentPositionNoCache();

            if (direction == Direction.UP) {
                // nb: read the doc for binarySearch to understand the negative coding for the
                // insertion point.
                int point = Arrays.binarySearch(presetPositions, pos + TOLERANCE);
                point = Math.abs(point + 1);
                if (point < presetPositions.length) {
                    arm.setPosition(presetPositions[point]);
                }
            } else {
                int point = Arrays.binarySearch(presetPositions, pos - TOLERANCE);
                if (point >= 0) {
                    point -= 1;
                } else {
                    point = Math.abs(point + 1) - 1;
                }
                if (point >= 0) {
                    arm.setPosition(presetPositions[point]);
                }
            }
        }

        @Override
        public final boolean isFinished() {
            return true;
        }
    }

    public static class ResetPosition extends CommandBase {
        final ArmSubsystem arm;

        public ResetPosition(ArmSubsystem a) {
            arm = a;
        }

        @Override
        public void initialize() {
            arm.setPosition(-5000, 0.5); // ~5000 encoder ticks in a full revolution.
        }

        @Override
        public void end(boolean interrupted) {
            arm.resetLowerPosition();
        }

        @Override
        public boolean isFinished() {
            // Experiment w/ using motor current alerts instead of a limit switch.
            return arm.isOverCurrent();
//            return arm.isArmAtLowerLimit();
        }
    }
}
