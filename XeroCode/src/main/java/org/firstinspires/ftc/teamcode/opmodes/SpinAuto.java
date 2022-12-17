package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.DriveForwardInchesCommand;
import org.firstinspires.ftc.teamcode.commands.ElevatorToPosition;
import org.firstinspires.ftc.teamcode.commands.TurnToDegreesCommand;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.pipelines.SleeveDetection;
import org.stealthrobotics.library.AutoToTeleStorage;
import org.stealthrobotics.library.commands.EndOpModeCommand;
import org.stealthrobotics.library.commands.SaveAutoHeadingCommand;
import org.stealthrobotics.library.opmodes.StealthOpMode;

@SuppressWarnings("unused")
@Autonomous(name = "TallLEFT| Wallace The Dancer", group = "Blue Auto", preselectTeleOp = "BLUE | Tele-Op")
public class SpinAuto extends StealthOpMode {

    // Subsystems
    SimpleMecanumDriveSubsystem drive;
    ElevatorSubsystem elevator;
    GripperSubsystem gripper;
    CameraSubsystem cameraSubsystem;

    /**
     * Executed when you init the selected opmode. This is where you setup your hardware.
     */
    @Override
    public void initialize() {
        // Setup and register all of your subsystems here
        drive = new SimpleMecanumDriveSubsystem(hardwareMap);
        elevator = new ElevatorSubsystem(hardwareMap);
        gripper = new GripperSubsystem(hardwareMap);
        cameraSubsystem = new CameraSubsystem(hardwareMap);
        register(drive, elevator, gripper, cameraSubsystem);
    }


    /**
     * This is where we create the one command we want to run in our autonomous opmode.
     * <p>
     * You create a SequentialCommandGroup, which is a list of commands that will run one after
     * another. This can be as simple or as complex as you'd like, and there are many ways to
     * group your commands in sequence, parallel, or conditionally.
     * <p>
     * Check out these links for more details:
     * - https://docs.ftclib.org/ftclib/command-base/command-system/convenience-commands
     * - https://docs.ftclib.org/ftclib/command-base/command-system/command-groups
     */
    @Override
    public Command getAutoCommand() {
        AutoToTeleStorage.clear();

        SleeveDetection.ParkingPosition positionFromCamera = cameraSubsystem.getPosition();

        if (positionFromCamera == SleeveDetection.ParkingPosition.LEFT) {
            return new SequentialCommandGroup(
                    new InstantCommand(() -> drive.turbomodeon()),
                    new InstantCommand(() -> gripper.close()),
                    new WaitCommand(500),
                    new ElevatorToPosition(elevator, 0.02),

                    new DriveForwardInchesCommand(drive, 36.0),
                    new DriveForwardInchesCommand(drive, -0.6),

                    new TurnToDegreesCommand(drive, 90),
                    new DriveForwardInchesCommand(drive, 23.0),
                    new TurnToDegreesCommand(drive, 37),

                    new ElevatorToPosition(elevator, 1.0),
                    new DriveForwardInchesCommand(drive, 8.75),

                    new WaitCommand(1000),
                    new InstantCommand(() -> gripper.open()),
                    new WaitCommand(1000),

                    new DriveForwardInchesCommand(drive, -5.0),
                    new ElevatorToPosition(elevator, 0),

                    new TurnToDegreesCommand(drive, 87),
                    new DriveForwardInchesCommand(drive, -23.0),

                    new SaveAutoHeadingCommand(() -> drive.getHeading()),
                    new EndOpModeCommand(this)
            );
        } else if (positionFromCamera == SleeveDetection.ParkingPosition.CENTER) {
            return new SequentialCommandGroup(
                    new InstantCommand(() -> drive.turbomodeon()),
                    new InstantCommand(() -> gripper.close()),
                    new WaitCommand(500),
                    new ElevatorToPosition(elevator, 0.02),

                    new DriveForwardInchesCommand(drive, 36.0),
                    new DriveForwardInchesCommand(drive, -0.6),

                    new TurnToDegreesCommand(drive, 90),
                    new DriveForwardInchesCommand(drive, 23.0),
                    new TurnToDegreesCommand(drive, 37),

                    new ElevatorToPosition(elevator, 1.0),
                    new DriveForwardInchesCommand(drive, 8.75),

                    new WaitCommand(1000),
                    new InstantCommand(() -> gripper.open()),
                    new WaitCommand(1000),

                    new DriveForwardInchesCommand(drive, -5.0),
                    new ElevatorToPosition(elevator, 0),

                    new TurnToDegreesCommand(drive, 87),
                    new DriveForwardInchesCommand(drive, -23.0),

                    new SaveAutoHeadingCommand(() -> drive.getHeading()),
                    new EndOpModeCommand(this)
            );
        } else { // RIGHT
            return new SequentialCommandGroup(
                    new InstantCommand(() -> drive.turbomodeon()),
                    new InstantCommand(() -> gripper.close()),
                    new WaitCommand(500),
                    new ElevatorToPosition(elevator, 0.02),

                    new DriveForwardInchesCommand(drive, 36.0),
                    new DriveForwardInchesCommand(drive, -0.6),

                    new TurnToDegreesCommand(drive, 90),
                    new DriveForwardInchesCommand(drive, 23.0),
                    new TurnToDegreesCommand(drive, 37),

                    new ElevatorToPosition(elevator, 1.0),
                    new DriveForwardInchesCommand(drive, 8.75),

                    new WaitCommand(1000),
                    new InstantCommand(() -> gripper.open()),
                    new WaitCommand(1000),

                    new DriveForwardInchesCommand(drive, -5.0),
                    new ElevatorToPosition(elevator, 0),

                    new TurnToDegreesCommand(drive, -87),
                    new DriveForwardInchesCommand(drive, -23.0),

                    new SaveAutoHeadingCommand(() -> drive.getHeading())
//                new EndOpModeCommand(this)
            );

        }
    }
}