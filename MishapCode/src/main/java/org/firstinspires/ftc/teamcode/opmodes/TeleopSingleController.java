package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.ArmPresetCommands;
import org.firstinspires.ftc.teamcode.commands.ArmResetMinCommand;
import org.firstinspires.ftc.teamcode.commands.DefaultArmCommand;
import org.firstinspires.ftc.teamcode.commands.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeInCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeOutCommand;
import org.firstinspires.ftc.teamcode.commands.QuackWheelManualSlow;
import org.firstinspires.ftc.teamcode.commands.QuackWheelManualSuper;
import org.firstinspires.ftc.teamcode.commands.QuackWheelSingle;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.QuackSpinnerSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumSubsystem;
import org.stealthrobotics.library.opmodes.StealthOpMode;

import java.util.function.BooleanSupplier;

public abstract class TeleopSingleController extends StealthOpMode {

    // Subsystems
    SimpleMecanumSubsystem drive;
    QuackSpinnerSubsystem duckSpinner;
    ArmSubsystem arm;
    IntakeSubsystem intake;

    // Game controllers
    GamepadEx driveGamepad;

    // Changing the mode changes how some controls work
    boolean mode = false;

    @Override
    public void initialize() {
        drive = new SimpleMecanumSubsystem(hardwareMap, telemetry);
        duckSpinner = new QuackSpinnerSubsystem(hardwareMap);
        arm = new ArmSubsystem(hardwareMap, telemetry);
        intake = new IntakeSubsystem(hardwareMap);
        register(drive, duckSpinner, arm, intake);

        driveGamepad = new GamepadEx(gamepad1);

        schedule(new ArmResetMinCommand(arm).withTimeout(4000));

        drive.setDefaultCommand(
                new DefaultDriveCommand(
                        drive,
                        () -> driveGamepad.gamepad.left_stick_y,
                        () -> driveGamepad.gamepad.left_stick_x,
                        () -> driveGamepad.gamepad.right_stick_x,
                        () -> driveGamepad.gamepad.right_trigger > 0.1
                )
        );

        arm.setDefaultCommand(
                new DefaultArmCommand(
                        arm,
                        () -> false,
                        () -> mode && driveGamepad.gamepad.right_bumper && !driveGamepad.gamepad.left_bumper,
                        () -> mode && driveGamepad.gamepad.left_bumper
                )
        );

        Trigger driveRT = new Trigger(() -> driveGamepad.gamepad.right_trigger > 0.1);
        driveRT.whenActive(() -> mode = true).whenInactive(() -> mode = false);

        driveGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_STICK_BUTTON).whenPressed(new InstantCommand(() -> drive.resetHeading()));
        driveGamepad.getGamepadButton(GamepadKeys.Button.START).whenPressed(new InstantCommand(() -> drive.setFieldCentricDriving(driveGamepad.gamepad.a)));

        // Ducks!!
        BooleanSupplier quackWheelReverse = () -> mode;
        driveGamepad.getGamepadButton(GamepadKeys.Button.X).whileHeld(new QuackWheelManualSlow(duckSpinner, quackWheelReverse));
        driveGamepad.getGamepadButton(GamepadKeys.Button.Y).whileHeld(new QuackWheelManualSuper(duckSpinner, quackWheelReverse));
        driveGamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new QuackWheelSingle(duckSpinner, quackWheelReverse));

        // Arm
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new ArmPresetCommands.High(arm));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new ArmPresetCommands.Middle(arm));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new ArmPresetCommands.Low(arm));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new ArmPresetCommands.Safe(arm));
        driveGamepad.getGamepadButton(GamepadKeys.Button.LEFT_STICK_BUTTON).whenPressed(new ArmResetMinCommand(arm));

        // @TOOO: these Trigger objects deserve some better explanation.
        new Trigger(() -> driveGamepad.gamepad.left_trigger > 0.1).whenActive(new ArmPresetCommands.Drive(arm));
        new Trigger(() -> driveGamepad.gamepad.left_trigger > 0.1 && driveGamepad.gamepad.right_trigger > 0.1).whenActive(new ArmPresetCommands.Intake(arm));

        // Intake
        driveGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).toggleWhenActive(new IntakeInCommand(intake));
        driveGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).toggleWhenActive(new IntakeOutCommand(intake));
    }

    // Ideally your red vs. blue opmodes are nothing more than this. Keep code shared between
    // them, and take different actions based on the alliance color.
    @SuppressWarnings("unused")
    @TeleOp(name = "7760 RED | Single Limited Tele-Op", group = "Red")
    public static class RedTeleopDualController extends TeleopSingleController {
    }

    @SuppressWarnings("unused")
    @TeleOp(name = "7760 BLUE | Single Limited Tele-Op", group = "Blue")
    public static class BlueTeleopDualController extends TeleopSingleController {
    }
}
