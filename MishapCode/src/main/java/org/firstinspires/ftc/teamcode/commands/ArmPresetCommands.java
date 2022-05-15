package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;

public class ArmPresetCommands {

    public static class High extends InstantCommand {
        public High(ArmSubsystem arm) {
            super(() -> arm.setTargetLocation(1125), arm);
        }
    }

    public static class Middle extends InstantCommand {
        public Middle(ArmSubsystem arm) {
            super(() -> arm.setTargetLocation(1350), arm);
        }
    }

    public static class Low extends InstantCommand {
        public Low(ArmSubsystem arm) {
            super(() -> arm.setTargetLocation(1530), arm);
        }
    }

    public static class Safe extends InstantCommand {
        public Safe(ArmSubsystem arm) {
            super(() -> arm.setTargetLocation(800), arm);
        }
    }

    public static class Drive extends InstantCommand {
        public Drive(ArmSubsystem arm) {
            super(() -> arm.setTargetLocation(0), arm);
        }
    }

    public static class Intake extends InstantCommand {
        public Intake(ArmSubsystem arm) {
            super(() -> arm.setTargetLocation(120), arm);
        }
    }

}
