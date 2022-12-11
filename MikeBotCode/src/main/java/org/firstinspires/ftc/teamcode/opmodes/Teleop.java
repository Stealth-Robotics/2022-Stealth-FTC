package org.firstinspires.ftc.teamcode.opmodes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.AlignToTapeCommand;
import org.firstinspires.ftc.teamcode.commands.DefaultElevatorCommand;
import org.firstinspires.ftc.teamcode.commands.DefaultMecanumDriveCommand;
import org.firstinspires.ftc.teamcode.commands.DriveForwardInchesPIDMotionProfileCommand;
import org.firstinspires.ftc.teamcode.commands.ResetElevatorCommand;
import org.firstinspires.ftc.teamcode.commands.StrafeForInchesCommand;
import org.firstinspires.ftc.teamcode.commands.TurnToDegreesPIDCommand;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GroundSensorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;
import org.stealthrobotics.library.gamepad.Gamepad;
import org.stealthrobotics.library.opmodes.StealthOpMode;

// mmmfixme
//   - StealthOpMode changes for next time
//     - base class for Teleop vs. auto
//       - auto can reset the auto-to-tele storage, ask for the auto cmd, pump the scheduler loop during init, etc.
//       - tele can have a separate method to bind controller buttons vs. init, so we can schedule cmds during init but not bind buttons.


public abstract class Teleop extends StealthOpMode {

    // Subsystems
    SimpleMecanumDriveSubsystem drive;
    ElevatorSubsystem elevator;
    GripperSubsystem gripper;
    GroundSensorSubsystem groundSensor;

    @Override
    public void initialize() {
        // Setup and register all of your subsystems here
        drive = new SimpleMecanumDriveSubsystem(hardwareMap);
        elevator = new ElevatorSubsystem(hardwareMap);
        gripper = new GripperSubsystem(hardwareMap);
        groundSensor = new GroundSensorSubsystem(hardwareMap);
        register(drive, elevator, gripper, groundSensor);

        // Note: I don't recommend leaving this enabled. As of release 0.4.6 there is a bad
        // memory leak if you disable the dashboard via the opmode, which will cause your opmode
        // to crash after a short period of time. Use with caution until there's a new release.
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        // Game controllers
        Gamepad driveGamepad = new Gamepad(gamepad1);
        Gamepad mechGamepad = new Gamepad(gamepad2);

        // Automatically reset the elevator all the way down when we init
        schedule(new ResetElevatorCommand(elevator));

        // A subsystem's default command runs all the time. Great for drivetrains and such.
        drive.setDefaultCommand(
                new DefaultMecanumDriveCommand(
                        drive,
                        driveGamepad.left_stick_y,
                        driveGamepad.left_stick_x,
                        driveGamepad.right_stick_x,
                        driveGamepad.right_trigger
                )
        );

        elevator.setDefaultCommand(
                new DefaultElevatorCommand(elevator,
                        mechGamepad.left_trigger,
                        mechGamepad.right_trigger
                )
        );

        // Setup all of your controllers' buttons and triggers here
        driveGamepad.back.whenActive(() -> drive.togglefieldcentric());
        driveGamepad.y.whenActive(() -> drive.resetHeading());

        mechGamepad.left_bumper.whenActive(() -> gripper.open());
        mechGamepad.right_bumper.whenActive(() -> gripper.close());

        mechGamepad.x.whenActive(new ResetElevatorCommand(elevator));
        mechGamepad.dpad_down.whenActive(() -> elevator.setTargetLocation(0.0));
        mechGamepad.dpad_left.whenActive(() -> elevator.setTargetLocation(0.37));
        mechGamepad.dpad_right.whenActive(() -> elevator.setTargetLocation(0.65));
        mechGamepad.dpad_up.whenActive(() -> elevator.setTargetLocation(1.0));

        // Random testing
        driveGamepad.dpad_left.whenActive(new AlignToTapeCommand(drive, groundSensor, AlignToTapeCommand.Direction.LEFT));
        driveGamepad.dpad_right.whenActive(new AlignToTapeCommand(drive, groundSensor, AlignToTapeCommand.Direction.RIGHT));
        driveGamepad.dpad_up.whenActive(new StrafeForInchesCommand(drive, 2.0));
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
