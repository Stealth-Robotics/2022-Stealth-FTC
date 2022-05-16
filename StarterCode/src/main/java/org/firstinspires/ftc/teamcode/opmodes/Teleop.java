package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.commands.DuckBackwardsCommand;
import org.firstinspires.ftc.teamcode.commands.DuckForwardCommand;
import org.firstinspires.ftc.teamcode.commands.ExampleCommand;
import org.firstinspires.ftc.teamcode.subsystems.DuckSpinnerSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumSubsystem;
import org.stealthrobotics.library.Alliance;
import org.stealthrobotics.library.opmodes.StealthOpMode;

public abstract class Teleop extends StealthOpMode {

    // Subsystems
    SimpleMecanumSubsystem drive;
    DuckSpinnerSubsystem duckSpinner;

    // Game controllers
    GamepadEx driveGamepad;
    GamepadEx mechGamepad;

    @Override
    public void initialize() {
        drive = new SimpleMecanumSubsystem(hardwareMap);
        duckSpinner = new DuckSpinnerSubsystem(hardwareMap);
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

        driveGamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new ExampleCommand("I can haz now?"));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whileHeld(new ExampleCommand("I can haz while?"));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenReleased(new ExampleCommand("I can haz after?"));
    }

    // Ideally your red vs. blue opmodes are nothing more than this. Keep code shared between
    // them, and take different actions based on the alliance color.
    @SuppressWarnings("unused")
    @TeleOp(name = "RED | Tele-Op", group = "Red")
    public static class RedTeleop extends Teleop {
        public RedTeleop() {
            Alliance.set(Alliance.RED);
        }
    }

    @SuppressWarnings("unused")
    @TeleOp(name = "BLUE | Tele-Op", group = "Blue")
    public static class BlueTeleop extends Teleop {
        public BlueTeleop() {
            Alliance.set(Alliance.BLUE);
        }
    }
}
