package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.Arm;

import java.util.Arrays;

public class ArmCommands {

    public static class ArmUp extends CommandBase {
        final Arm arm;

        public ArmUp(Arm arm) {
            this.arm = arm;
            addRequirements(arm);
        }

        @Override
        public void initialize() {
            arm.setPosition(Arm.MAX_POSITION);
        }

        @Override
        public void end(boolean isInterrupted) {
            int p = arm.getCurrentPositionNoCache();
            // Aim a little farther up than we sense to give a softer landing.
            arm.setPosition(Math.min(Arm.MAX_POSITION, p + 100));
        }
    }

    public static class ArmDown extends CommandBase {
        final Arm arm;

        public ArmDown(Arm arm) {
            this.arm = arm;
            addRequirements(arm);
        }

        @Override
        public void initialize() {
            arm.setPosition(Arm.MIN_POSITION);
        }

        @Override
        public void end(boolean isInterrupted) {
            int p = arm.getCurrentPositionNoCache();
            // Aim a little farther down than we sense to give a softer landing.
            arm.setPosition(Math.max(Arm.MIN_POSITION, p - 100));
        }
    }

    public static class ArmNextPreset extends CommandBase {
        //        private static final int[] presetPositions = {0, 200, 2200, 2800, 3500};
        private static final int[] presetPositions = {20, 200, 400, 600, 800, 1000, 1200, 1400, 1600};

        // Factor in a small tolerance to the current position so we don't go from 1999 to 2000.
        private static final int TOLERANCE = 50;

        public enum Direction {UP, DOWN}

        final Arm arm;
        final Direction direction;

        public ArmNextPreset(Arm arm, Direction direction) {
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


}
