package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.ArmPresetCommands;
import org.firstinspires.ftc.teamcode.commands.ArmResetMinCommand;
import org.firstinspires.ftc.teamcode.commands.DefaultArmCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeInCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeOutCommand;
import org.firstinspires.ftc.teamcode.commands.QuackWheelManualSlow;
import org.firstinspires.ftc.teamcode.commands.QuackWheelManualSuper;
import org.firstinspires.ftc.teamcode.commands.QuackWheelSingle;
import org.firstinspires.ftc.teamcode.commands.TeleopDriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.QuackSpinnerSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumSubsystem;
import org.stealthrobotics.library.opmodes.StealthOpMode;

import java.util.function.BooleanSupplier;

public abstract class TeleopDualController extends StealthOpMode {

    // Subsystems
    SimpleMecanumSubsystem drive;
    QuackSpinnerSubsystem duckSpinner;
    ArmSubsystem arm;
    IntakeSubsystem intake;

    // Game controllers
    GamepadEx driveGamepad;
    GamepadEx mechGamepad;

    @Override
    public void initialize() {
        drive = new SimpleMecanumSubsystem(hardwareMap);
        duckSpinner = new QuackSpinnerSubsystem(hardwareMap);
        arm = new ArmSubsystem(hardwareMap);
        intake = new IntakeSubsystem(hardwareMap);
        register(drive, duckSpinner, arm, intake);

        driveGamepad = new GamepadEx(gamepad1);
        mechGamepad = new GamepadEx(gamepad2);

        schedule(new ArmResetMinCommand(arm).withTimeout(4000));

        drive.setDefaultCommand(
                new TeleopDriveCommand(
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
                        () -> mechGamepad.gamepad.right_bumper && !mechGamepad.gamepad.left_bumper,
                        () -> mechGamepad.gamepad.left_bumper
                )
        );

        driveGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_STICK_BUTTON).whenPressed(new InstantCommand(() -> drive.resetHeading()));
        driveGamepad.getGamepadButton(GamepadKeys.Button.START).whenPressed(new InstantCommand(() -> drive.setFieldCentricDriving(driveGamepad.gamepad.a)));

        // Ducks!!
        BooleanSupplier quackWheelReverse = () -> mechGamepad.gamepad.right_trigger <= 0.1;
        mechGamepad.getGamepadButton(GamepadKeys.Button.X).whileHeld(new QuackWheelManualSlow(duckSpinner, quackWheelReverse));
        mechGamepad.getGamepadButton(GamepadKeys.Button.Y).whileHeld(new QuackWheelManualSuper(duckSpinner, quackWheelReverse));
        mechGamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new QuackWheelSingle(duckSpinner, quackWheelReverse));

        // Arm
        mechGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new ArmPresetCommands.High(arm));
        mechGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new ArmPresetCommands.Middle(arm));
        mechGamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new ArmPresetCommands.Low(arm));
        mechGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new ArmPresetCommands.Safe(arm));
        mechGamepad.getGamepadButton(GamepadKeys.Button.LEFT_STICK_BUTTON).whenPressed(new ArmResetMinCommand(arm));

        // @TOOO: these Trigger objects deserve some better explanation.
        new Trigger(() -> mechGamepad.gamepad.left_trigger > 0.1).whenActive(new ArmPresetCommands.Drive(arm));
        new Trigger(() -> mechGamepad.gamepad.left_trigger > 0.1 && mechGamepad.gamepad.right_trigger > 0.1).whenActive(new ArmPresetCommands.Intake(arm));

        // Intake
        Trigger driveRTHeld = new Trigger(() -> driveGamepad.gamepad.right_trigger > 0.1);
        Trigger driveRTNotHeld = driveRTHeld.negate();
        driveRTNotHeld.and(driveGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)).toggleWhenActive(new IntakeInCommand(intake));
        driveRTNotHeld.and(driveGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)).toggleWhenActive(new IntakeOutCommand(intake));
        driveRTHeld.and(driveGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)).whileActiveOnce(new IntakeInCommand(intake));
        driveRTHeld.and(driveGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)).whileActiveOnce(new IntakeOutCommand(intake));
    }

    /**
     * Ideally your red vs. blue opmodes are nothing more than this. Keep code shared between
     * them, and take different actions based on the alliance color.
     *
     * @see org.stealthrobotics.library.Alliance
     */

    @SuppressWarnings("unused")
    @TeleOp(name = "7760 RED | Dual Tele-Op", group = "Red")
    public static class RedTeleopDualController extends TeleopDualController {
    }

    @SuppressWarnings("unused")
    @TeleOp(name = "7760 BLUE | Dual Tele-Op", group = "Blue")
    public static class BlueTeleopDualController extends TeleopDualController {
    }
}
