package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.commands.DuckBackwardsCommand;
import org.firstinspires.ftc.teamcode.commands.DuckForwardCommand;
import org.firstinspires.ftc.teamcode.commands.ExampleCommand;
import org.firstinspires.ftc.teamcode.subsystems.DuckSpinnerSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumSubsystem;
import org.stealthrobotics.library.AllianceColor;

public abstract class TeleOpBase extends CommandOpMode {

    // Set for us by the red and blue teleop modes
    final AllianceColor allianceColor;

    // Subsystems
    SimpleMecanumSubsystem drive;
    DuckSpinnerSubsystem duckSpinner;

    // Game controllers
    GamepadEx driveGamepad;
    GamepadEx mechGamepad;

    @Override
    public void initialize() {
        drive = new SimpleMecanumSubsystem(hardwareMap);
        duckSpinner = new DuckSpinnerSubsystem(hardwareMap, allianceColor);
        register(drive, duckSpinner);

        driveGamepad = new GamepadEx(gamepad1);
        mechGamepad = new GamepadEx(gamepad2);

        drive.setDefaultCommand(
                new DefaultDriveCommand(
                        drive,
                        () -> driveGamepad.gamepad.left_stick_y,
                        () -> driveGamepad.gamepad.left_stick_x,
                        () -> driveGamepad.gamepad.right_stick_x
                )
        );

        driveGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(() -> System.out.println("Oh hai"));

        driveGamepad.getGamepadButton(GamepadKeys.Button.X).whileHeld(new DuckForwardCommand(duckSpinner));
        driveGamepad.getGamepadButton(GamepadKeys.Button.Y).whileHeld(new DuckBackwardsCommand(duckSpinner));

        driveGamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new ExampleCommand("I can haz?"));
//        driveGamepad.getGamepadButton(GamepadKeys.Button.B).whileHeld(new ExampleCommand("I can haz?"));
//        driveGamepad.getGamepadButton(GamepadKeys.Button.B).whenReleased(new ExampleCommand("I can haz?"));
    }

    TeleOpBase(AllianceColor allianceColor) {
        this.allianceColor = allianceColor;
    }

    // Ideally your red vs. blue opmodes are nothing more than this. Keep code shared between
    // them, and take different actions based on the alliance color.
    @SuppressWarnings("unused")
    @TeleOp(name = "RED | Tele-Op", group = "Red")
    public static class RedTeleOp extends TeleOpBase {
        public RedTeleOp() {
            super(AllianceColor.RED);
        }
    }

    @SuppressWarnings("unused")
    @TeleOp(name = "BLUE | Tele-Op", group = "Blue")
    public static class BlueTeleOp extends TeleOpBase {
        public BlueTeleOp() {
            super(AllianceColor.BLUE);
        }
    }
}
