package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.ElevatorBackwardManualCommand;
import org.firstinspires.ftc.teamcode.commands.ElevatorForwardManualCommand;
import org.firstinspires.ftc.teamcode.commands.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.commands.DefaultElevatorCommand;
import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ArmMotorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GrabberSubsystem;
import org.stealthrobotics.library.opmodes.StealthOpMode;
@Config
public abstract class Teleop extends StealthOpMode {

    // Subsystems
    DriveSubsystem drive;
    ArmMotorSubsystem armMotors;

    // Game controllers
    GamepadEx driveGamepad;
    GamepadEx mechGamepad;

    CameraSubsystem camera;
    ElevatorSubsystem elevator;
    GrabberSubsystem grabber;

    double armPositionA = 0;
    double armPositionB = 0.5;


    @Override
    public void initialize() {
        drive = new DriveSubsystem(new SampleMecanumDrive(hardwareMap),hardwareMap);
        camera = new CameraSubsystem(hardwareMap);
        elevator = new ElevatorSubsystem(hardwareMap);
        grabber = new GrabberSubsystem(hardwareMap);
        armMotors = new ArmMotorSubsystem(hardwareMap);
        register(drive, armMotors, camera, elevator, grabber);

        driveGamepad = new GamepadEx(gamepad1);
        mechGamepad = new GamepadEx(gamepad2);


        drive.setDefaultCommand(
                new DefaultDriveCommand(
                        drive,
                        () -> driveGamepad.gamepad.left_stick_y,
                        () -> driveGamepad.gamepad.left_stick_x,
                        () -> driveGamepad.gamepad.right_stick_x,
                        () -> driveGamepad.gamepad.left_bumper
                )
        );

        grabber.closeGripper();

        elevator.setDefaultCommand(new DefaultElevatorCommand(elevator));
        /*
        mechGamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new InstantCommand(() -> elevator.setTargetLocation(0.0)));
        mechGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new InstantCommand(() -> elevator.setTargetLocation(0.39)));
        mechGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new InstantCommand(() -> elevator.setTargetLocation(1.0)));
        mechGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new InstantCommand(() -> elevator.setTargetLocation(0.67)));
        */
        driveGamepad.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER).whenPressed(new InstantCommand(() -> grabber.toggleOpen()));
        //driveGamepad.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER).whenPressed(new InstantCommand(() -> grabber.toggleOpen()));


        //TODO: MAKE BETTER TRAJECTORY SIDD SUGESTED FOR 1 and 3 PARKING SPOT

        //mechGamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new InstantCommand(() -> grabber.armToPosition(0.5)));
        //mechGamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new InstantCommand(() -> grabber.armToPosition(0)));
        //mechGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand(() -> grabber.armToPosition(1)));

        driveGamepad.getGamepadButton(GamepadKeys.Button.B).whenPressed(new InstantCommand(() -> grabber.toggleArm()));
        driveGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand(() -> grabber.toggleArmDownCone()));
        driveGamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new InstantCommand(() -> grabber.toggleArmHigher()));
        driveGamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new InstantCommand(() -> grabber.toggleArmLowest()));
        //mechGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand(() -> grabber.rotaterToPosition(0)));
        //mechGamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new InstantCommand(() -> grabber.rotaterToPosition(0.5)));
        //mechGamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new InstantCommand(() -> grabber.rotaterToPosition(1)));

        //driveGamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(() -> System.out.println("UwU"));
        driveGamepad.getGamepadButton(GamepadKeys.Button.START).whenPressed(new InstantCommand(() -> drive.resetHeading()));
        //driveGamepad.getGamepadButton(GamepadKeys.Button.START).whenPressed(new InstantCommand(() -> drive.toggleRobotCentric()));
        //driveGamepad.getGamepadButton(GamepadKeys.Button.A).whenHeld(new ElevatorForwardManualCommand(armMotors));
        //driveGamepad.getGamepadButton(GamepadKeys.Button.B).whenHeld(new ElevatorBackwardManualCommand(armMotors));
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
