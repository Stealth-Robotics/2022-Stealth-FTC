package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.DriveForwardInchesCommand;
import org.firstinspires.ftc.teamcode.commands.ElevatorToPosition;
import org.firstinspires.ftc.teamcode.commands.StrafeForInches;
import org.firstinspires.ftc.teamcode.commands.TurnToDegreesCommand;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.pipelines.SleeveDetection;
import org.stealthrobotics.library.AutoToTeleStorage;
import org.stealthrobotics.library.commands.SaveAutoHeadingCommand;
import org.stealthrobotics.library.opmodes.StealthOpMode;

@SuppressWarnings("unused")
@Autonomous(name = "CycleLEFT| Wallace The Funky", group = "Blue Auto", preselectTeleOp = "BLUE | Tele-Op")
public class FunkyAuto extends StealthOpMode {

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
//        SleeveDetection.ParkingPosition positionFromCamera = SleeveDetection.ParkingPosition.LEFT;

        if (positionFromCamera == SleeveDetection.ParkingPosition.LEFT) {
            return new SequentialCommandGroup(
                    new InstantCommand(() -> drive.turbomodeon()),
                    new InstantCommand(() -> gripper.close()),
                    new WaitCommand(500),
                    new ElevatorToPosition(elevator, 0.02),

                    new DriveForwardInchesCommand(drive, 36.0),
                    new DriveForwardInchesCommand(drive, -2),

                    new TurnToDegreesCommand(drive, 90, 0.5),
                    new DriveForwardInchesCommand(drive, 22.5),
                    new TurnToDegreesCommand(drive, 45),  // face poll

                    new ElevatorToPosition(elevator, 1.0),
                    new DriveForwardInchesCommand(drive, 7.8),

                    new InstantCommand(() -> gripper.open()), // drop and backup
                    new WaitCommand(500),
                    new DriveForwardInchesCommand(drive, -5.0),

                    new ParallelCommandGroup(
                            new ElevatorToPosition(elevator, 0.10),
                            new TurnToDegreesCommand(drive, -90)
                    ),

                    new DriveForwardInchesCommand(drive, 21),
                    new StrafeForInches(drive, 36),
                    new StrafeForInches(drive, -2),
                    new DriveForwardInchesCommand(drive, 26),   //get to cone stack

                    new InstantCommand(() -> gripper.close()),
                    new ElevatorToPosition(elevator, 0.25),

                    new DriveForwardInchesCommand(drive, -24),
                    new StrafeForInches(drive, -24),
                    new DriveForwardInchesCommand(drive, -21),   //get back to pole

                    new TurnToDegreesCommand(drive, 45),  //face poll 2.0

                    new ElevatorToPosition(elevator, 1.0),
                    new DriveForwardInchesCommand(drive, 7.3),

                    new InstantCommand(() -> gripper.open()), // drop and backup
                    new WaitCommand(500),
                    new DriveForwardInchesCommand(drive, -5.0),

                    new ParallelCommandGroup(
                            new ElevatorToPosition(elevator, 0.10),
                            new TurnToDegreesCommand(drive, -90)
                    ),


                    new ElevatorToPosition(elevator, 0),

                    new SaveAutoHeadingCommand(() -> drive.getHeading())
//                new EndOpModeCommand(this)
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
                    new TurnToDegreesCommand(drive, 45),  //face poll

                    new ElevatorToPosition(elevator, 1.0),
                    new DriveForwardInchesCommand(drive, 7.3),

                    new ParallelCommandGroup(
                            new ElevatorToPosition(elevator, 0.4),
                            new SequentialCommandGroup(
                                    new WaitCommand(750),
                                    new InstantCommand(() -> gripper.open())
                            )
                    ),

                    new DriveForwardInchesCommand(drive, -5.0),
                    new ElevatorToPosition(elevator, 0.10),

                    new TurnToDegreesCommand(drive, -90),
                    new DriveForwardInchesCommand(drive, 24),
                    new StrafeForInches(drive, 30),
                    new StrafeForInches(drive, -4),
                    new DriveForwardInchesCommand(drive, 24),   //get to cone stack

                    new InstantCommand(() -> gripper.close()),
                    new ElevatorToPosition(elevator, 0.15),

                    new DriveForwardInchesCommand(drive, -48),
                    new StrafeForInches(drive, -14),
                    new DriveForwardInchesCommand(drive, -12),   //get back to pole

                    new TurnToDegreesCommand(drive, 45), //face poll 2.0

                    new ElevatorToPosition(elevator, 1.0),
                    new DriveForwardInchesCommand(drive, 7.3),

                    new ParallelCommandGroup(
                            new ElevatorToPosition(elevator, 0.4),
                            new SequentialCommandGroup(
                                    new WaitCommand(750),
                                    new InstantCommand(() -> gripper.open()),
                                    new DriveForwardInchesCommand(drive, -5.0)
                            )
                    ),

                    new DriveForwardInchesCommand(drive, -5.0),
                    new ElevatorToPosition(elevator, 0),

                    new TurnToDegreesCommand(drive, 87),
                    new DriveForwardInchesCommand(drive, -23.0),

                    new SaveAutoHeadingCommand(() -> drive.getHeading())
//                new EndOpModeCommand(this)
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
                    new TurnToDegreesCommand(drive, 45),  //face poll

                    new ElevatorToPosition(elevator, 1.0),
                    new DriveForwardInchesCommand(drive, 7.3),

                    new ParallelCommandGroup(
                            new ElevatorToPosition(elevator, 0.4),
                            new SequentialCommandGroup(
                                    new WaitCommand(750),
                                    new InstantCommand(() -> gripper.open())
                            )
                    ),

                    new DriveForwardInchesCommand(drive, -5.0),
                    new ElevatorToPosition(elevator, 0.10),

                    new TurnToDegreesCommand(drive, -90),
                    new DriveForwardInchesCommand(drive, 24),
                    new StrafeForInches(drive, 30),
                    new StrafeForInches(drive, -4),
                    new DriveForwardInchesCommand(drive, 24),   //get to cone stack

                    new InstantCommand(() -> gripper.close()),
                    new ElevatorToPosition(elevator, 0.15),

                    new DriveForwardInchesCommand(drive, -48),
                    new StrafeForInches(drive, -14),
                    new DriveForwardInchesCommand(drive, -12),   //get back to pole

                    new TurnToDegreesCommand(drive, 45),  //face poll 2.0

                    new ElevatorToPosition(elevator, 1.0),
                    new DriveForwardInchesCommand(drive, 7.3),

                    new ParallelCommandGroup(
                            new ElevatorToPosition(elevator, 0.4),
                            new SequentialCommandGroup(
                                    new WaitCommand(750),
                                    new InstantCommand(() -> gripper.open()),
                                    new DriveForwardInchesCommand(drive, -5.0)
                            )
                    ),

                    new DriveForwardInchesCommand(drive, -5.0),
                    new ElevatorToPosition(elevator, 0),

                    new TurnToDegreesCommand(drive, 90),
                    new DriveForwardInchesCommand(drive, 45.0),

                    new SaveAutoHeadingCommand(() -> drive.getHeading())
//                new EndOpModeCommand(this)
            );

        }
    }
}
