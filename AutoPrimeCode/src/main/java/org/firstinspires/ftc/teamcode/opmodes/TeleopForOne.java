package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.DefaultElevatorCommand;
import org.firstinspires.ftc.teamcode.commands.DefaultMecanumDriveCommand;
import org.firstinspires.ftc.teamcode.commands.ResetElevatorCommand;
import org.firstinspires.ftc.teamcode.subsystems.ColorSensorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;
import org.stealthrobotics.library.opmodes.StealthOpMode;

public abstract class TeleopForOne extends StealthOpMode {

    // Subsystems
    SimpleMecanumDriveSubsystem drive;
    ElevatorSubsystem elevator;
    GripperSubsystem gripper;
    ColorSensorSubsystem colorsensor;

    // Game controllers
    GamepadEx driveGamepad;
    GamepadEx mechGamepad;


    @Override
    public void initialize() {
        // Setup and register all of your subsystems here
        drive = new SimpleMecanumDriveSubsystem(hardwareMap);
        elevator = new ElevatorSubsystem(hardwareMap);
        gripper = new GripperSubsystem(hardwareMap);
        colorsensor = new ColorSensorSubsystem(hardwareMap);

        register(drive, elevator, gripper, colorsensor);

        driveGamepad = new GamepadEx(gamepad1);
        mechGamepad = new GamepadEx(gamepad2);

        // Automatically reset the elevator all the way down when we init
//        schedule(new ResetElevatorCommand(elevator));

        // A subsystem's default command runs all the time. Great for drivetrains and such.
        drive.setDefaultCommand(
                new DefaultMecanumDriveCommand(
                        drive,
                        () -> driveGamepad.gamepad.left_stick_y,
                        () -> driveGamepad.gamepad.left_stick_x,
                        () -> driveGamepad.gamepad.right_stick_x
                )
        );

        elevator.setDefaultCommand(new DefaultElevatorCommand(elevator,
                () -> driveGamepad.gamepad.left_trigger,
                () -> driveGamepad.gamepad.right_trigger
                )
        );


        // Setup all of your controllers' buttons and triggers here
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new InstantCommand(() -> elevator.setTargetLocation(0.0)));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new InstantCommand(() -> elevator.setTargetLocation(0.65)));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new InstantCommand(() -> elevator.setTargetLocation(0.97)));
        driveGamepad.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new InstantCommand(() -> elevator.setTargetLocation(0.37)));
        driveGamepad.getGamepadButton(GamepadKeys.Button.BACK).whenPressed(new ResetElevatorCommand(elevator));

        driveGamepad.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand(() -> gripper.toggle()));
        driveGamepad.getGamepadButton(GamepadKeys.Button.X).whenPressed(new InstantCommand(() -> drive.togglefieldcentric()));
        driveGamepad.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new InstantCommand(() -> drive.resetHeading()));
    }

    /**
     * Ideally your red vs. blue opmodes are nothing more than this. Keep code shared between
     * them, and take different actions based on the alliance color.
     *
     * @see org.stealthrobotics.library.Alliance
     */

    @SuppressWarnings("unused")
    @TeleOp(name = "RED | Tele-Op For One", group = "Red")
    public static class RedTeleop extends TeleopForOne {
    }

    @SuppressWarnings("unused")
    @TeleOp(name = "BLUE | Tele-Op For One", group = "Blue")
    public static class BlueTeleop extends TeleopForOne {
    }

    @Override
    public double getFinalHeading() {
        return drive.getHeading();
    }

}
