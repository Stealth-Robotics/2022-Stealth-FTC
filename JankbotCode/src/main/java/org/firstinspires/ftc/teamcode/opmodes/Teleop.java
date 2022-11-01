package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.ArmExtenderBackwardManualCommand;
import org.firstinspires.ftc.teamcode.commands.ArmExtenderForwardManualCommand;
import org.firstinspires.ftc.teamcode.commands.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ArmMotorSubsystem;
import org.stealthrobotics.library.AutoToTeleStorage;
import org.stealthrobotics.library.opmodes.StealthOpMode;

public abstract class Teleop extends StealthOpMode {

    // Subsystems
    DriveSubsystem drive;
    ArmMotorSubsystem armMotors;

    // Game controllers
    GamepadEx driveGamepad;
    GamepadEx mechGamepad;

    CameraSubsystem camera;

    @Override
    public void initialize() {
        drive = new DriveSubsystem(new SampleMecanumDrive(hardwareMap),hardwareMap);
        camera = new CameraSubsystem(hardwareMap);

        armMotors = new ArmMotorSubsystem(hardwareMap);
        register(drive, armMotors, camera);

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
        //driveGamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(() -> System.out.println("UwU"));
        driveGamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new InstantCommand(() -> drive.resetHeading()));
        driveGamepad.getGamepadButton(GamepadKeys.Button.START).whenPressed(new InstantCommand(() -> drive.toggleRobotCentric()));
        driveGamepad.getGamepadButton(GamepadKeys.Button.A).whenHeld(new ArmExtenderForwardManualCommand(armMotors));
        driveGamepad.getGamepadButton(GamepadKeys.Button.B).whenHeld(new ArmExtenderBackwardManualCommand(armMotors));
    }

    /**
     * Ideally your red vs. blue opmodes are nothing more than this. Keep code shared between
     * them, and take different actions based on the alliance color.
     *
     * @see org.stealthrobotics.library.Alliance
     */

    @SuppressWarnings("unused")
    @TeleOp(name = "RED | Tele-Op", group = "Red")
    public static class RedTeleop extends Teleop {
    }

    @SuppressWarnings("unused")
    @TeleOp(name = "BLUE | Tele-Op", group = "Blue")
    public static class BlueTeleop extends Teleop {
    }
}
