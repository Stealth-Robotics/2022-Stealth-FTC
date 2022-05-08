package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.ArmCommands;
import org.firstinspires.ftc.teamcode.commands.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.commands.DuckCommands;
import org.firstinspires.ftc.teamcode.commands.ExtensionCommands;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.DuckSpinnerSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DuckWheel;
import org.firstinspires.ftc.teamcode.subsystems.Extension;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrivetrain;
import org.stealthrobotics.library.Alliance;

public abstract class TeleOpBase extends CommandOpMode {

    // Subsystems
    MecanumDrivetrain drive;
    DuckSpinnerSubsystem duckSpinner;
    Intake intake;
    DuckWheel duckWheel;
    Arm arm;
    Extension extension;

    // Game controllers
    GamepadEx driveGamepad;
    GamepadEx mechGamepad;

    @Override
    public void initialize() {
        drive = new MecanumDrivetrain(hardwareMap, true);
        duckSpinner = new DuckSpinnerSubsystem(hardwareMap);
        intake = new Intake(hardwareMap);
        duckWheel = new DuckWheel(hardwareMap);
        arm = new Arm(hardwareMap, telemetry);
        extension = new Extension(hardwareMap, telemetry);
        register(drive, intake, duckWheel, arm, extension);

        driveGamepad = new GamepadEx(gamepad1);
        mechGamepad = new GamepadEx(gamepad2);

        arm.initialize();

        // Retrieve our pose from the end of the last Autonomous mode, if any.
//        drive.setPoseEstimate(AutoToTeleStorage.finalAutoPose);

        drive.setDefaultCommand(
                new DefaultDriveCommand(
                        drive,
                        () -> driveGamepad.gamepad.left_stick_y,
                        () -> driveGamepad.gamepad.left_stick_x,
                        () -> driveGamepad.gamepad.right_stick_x,
                        () -> driveGamepad.gamepad.right_stick_button
                )
        );

        driveGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(() -> System.out.println("Oh hai"));
        driveGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(
                Alliance.select(
                        () -> System.out.println("Oh hai red"),
                        () -> System.out.println("Oh hai blue")
                )
        );

        // Duck Wheel
        driveGamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new DuckCommands.SpinForward(duckWheel)).whenReleased(new DuckCommands.Stop(duckWheel));
        driveGamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new DuckCommands.SpinBackwards(duckWheel)).whenReleased(new DuckCommands.Stop(duckWheel));
        driveGamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new DuckCommands.DeliverSingleDuck(duckWheel));

        // Arm
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenHeld(new ArmCommands.ArmUp(arm));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenHeld(new ArmCommands.ArmDown(arm));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new ArmCommands.ArmNextPreset(arm, ArmCommands.ArmNextPreset.Direction.UP));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new ArmCommands.ArmNextPreset(arm, ArmCommands.ArmNextPreset.Direction.DOWN));

        // Arm Extension System
        driveGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenHeld(new ExtensionCommands.Extend(extension));
        driveGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whileHeld(new ExtensionCommands.Retract(extension));
    }

    // Ideally your red vs. blue opmodes are nothing more than this. Keep code shared between
    // them, and take different actions based on the alliance color.
    @SuppressWarnings("unused")
    @TeleOp(name = "RED | Tele-Op", group = "Red")
    public static class RedTeleOp extends TeleOpBase {
        public RedTeleOp() {
            Alliance.set(Alliance.RED);
        }
    }

    @SuppressWarnings("unused")
    @TeleOp(name = "BLUE | Tele-Op", group = "Blue")
    public static class BlueTeleOp extends TeleOpBase {
        public BlueTeleOp() {
            Alliance.set(Alliance.BLUE);
        }
    }
}
