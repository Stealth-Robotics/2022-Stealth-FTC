package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.DefaultElevatorCommand;
import org.firstinspires.ftc.teamcode.commands.DefaultGrabberCommand;
import org.firstinspires.ftc.teamcode.commands.DefaultMecanumDriveCommand;
import org.firstinspires.ftc.teamcode.commands.GrabberDown;
import org.firstinspires.ftc.teamcode.commands.GrabberRotateLeft;
import org.firstinspires.ftc.teamcode.commands.GrabberRotateRight;
import org.firstinspires.ftc.teamcode.commands.GrabberUp;
import org.firstinspires.ftc.teamcode.commands.LiftDown;
import org.firstinspires.ftc.teamcode.commands.LiftUp;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LiftSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.stealthrobotics.library.opmodes.StealthOpMode;

public abstract class Teleop extends StealthOpMode {

    // Subsystems
    DriveSubsystem drive;
    GrabberSubsystem grabber;
    ElevatorSubsystem lift;

    // Game controllers
    GamepadEx driveGamepad;
    GamepadEx mechGamepad;

    SampleMecanumDrive mecanumDrive;

    @Override
    public void initialize() {
        mecanumDrive = new SampleMecanumDrive(hardwareMap);
        // Setup and register all of your subsystems here
        drive = new DriveSubsystem(mecanumDrive, hardwareMap);
        grabber = new GrabberSubsystem(hardwareMap);
        lift = new ElevatorSubsystem(hardwareMap);
        CameraSubsystem camera = new CameraSubsystem(hardwareMap);
        register(drive, grabber, lift, camera);

        grabber.setLiftPos(0.5);
        grabber.setPos(0);

        driveGamepad = new GamepadEx(gamepad1);
        mechGamepad = new GamepadEx(gamepad2);

        // A subsystem's default command runs all the time. Great for drivetrains and such.
        drive.setDefaultCommand(
                new DefaultMecanumDriveCommand(
                        drive,
                        () -> driveGamepad.gamepad.left_stick_y,
                        () -> driveGamepad.gamepad.left_stick_x,
                        () -> driveGamepad.gamepad.right_stick_x,
                        () -> driveGamepad.gamepad.right_bumper
                )
        );
        grabber.setDefaultCommand(new DefaultGrabberCommand(
                grabber,
                () -> -mechGamepad.gamepad.left_stick_y,
                () -> -mechGamepad.gamepad.left_stick_x
        ));
        lift.setDefaultCommand(new DefaultElevatorCommand(lift));

        // Setup all of your controllers' buttons and triggers here
        driveGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand(() -> drive.resetHeading()));
        driveGamepad.getGamepadButton(GamepadKeys.Button.START).whenPressed(new InstantCommand(() -> drive.toggleRobotCentric()));

        mechGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(new InstantCommand(() -> grabber.toggleOpen()));
//        mechGamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenHeld(new GrabberDown(grabber));
//        mechGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenHeld(new GrabberUp(grabber));
//        mechGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenHeld(new GrabberRotateLeft(grabber));
//        mechGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenHeld(new GrabberRotateRight(grabber));
        mechGamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new InstantCommand(() -> lift.setTarget(0)));
        mechGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand(() -> lift.setTarget(2500)));
        mechGamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new InstantCommand(() -> lift.limitSwitchReset()));
        mechGamepad.getGamepadButton(GamepadKeys.Button.B).whenHeld(new LiftDown(lift));
        mechGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenHeld(new LiftUp(lift));
        mechGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new InstantCommand(() -> grabber.right()));
        mechGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new InstantCommand(() -> grabber.left()));

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
