package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.DriveForwardInchesPID;
import org.firstinspires.ftc.teamcode.commands.ElevatorResetCommand;
import org.firstinspires.ftc.teamcode.commands.ElevatorToPositionCommand;
import org.firstinspires.ftc.teamcode.commands.RotateToDegreesPID;
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
@Autonomous(name = "BLUE | Right Score Auto", group = "Blue Auto", preselectTeleOp = "BLUE | Tele-Op")
public class RightScoreAuto extends StealthOpMode {

    // Subsystems
    SimpleMecanumDriveSubsystem drive;
    ElevatorSubsystem elevator;
    GripperSubsystem gripper;
    CameraSubsystem camera;

    /**
     * Executed when you init the selected opmode. This is where you setup your hardware.
     */
    @Override
    public void initialize() {
        // Setup and register all of your subsystems here
        drive = new SimpleMecanumDriveSubsystem(hardwareMap);
        elevator = new ElevatorSubsystem(hardwareMap);
        gripper = new GripperSubsystem(hardwareMap);
        camera = new CameraSubsystem(hardwareMap);
        register(drive, elevator, gripper, camera);
    }

    @Override
    public void whileWaitingToStart() {
        CommandScheduler.getInstance().run();
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
        AutoToTeleStorage.finalAutoHeading = 0.0;
        SleeveDetection.ParkingPosition park = camera.getPosition();
        switch (park) {
            case LEFT:
                return new SequentialCommandGroup(
                        new ElevatorResetCommand(elevator),
                        new InstantCommand(() -> gripper.close()),
                        new WaitCommand(500),
                        new ElevatorToPositionCommand(elevator, 0.06),
                        new DriveForwardInchesPID(drive, 5, 0),
                        new RotateToDegreesPID(drive, -90),
                        new DriveForwardInchesPID(drive, 21.0, 0),
                        new RotateToDegreesPID(drive, 0),
                        new DriveForwardInchesPID(drive, 34.3, 0),
                        new RotateToDegreesPID(drive, -90),
                        new ElevatorToPositionCommand(elevator, 1.0),
                        new DriveForwardInchesPID(drive, 4, 0),
                        new ElevatorToPositionCommand(elevator, 0.8),
                        new InstantCommand(() -> gripper.open()),
                        new WaitCommand(500),
                        new DriveForwardInchesPID(drive, -4, 0),
                        new ElevatorToPositionCommand(elevator,0),
                        new RotateToDegreesPID(drive, 180),
                        new DriveForwardInchesPID(drive, 12, 0),
                        new SaveAutoHeadingCommand(() -> drive.getHeading()),
                        new EndOpModeCommand(this)
                );
            case RIGHT:
                    return new SequentialCommandGroup(
                    new ElevatorResetCommand(elevator),
                    new InstantCommand(() -> gripper.close()),
                    new WaitCommand(500),
                    new ElevatorToPositionCommand(elevator, 0.06),
                    new DriveForwardInchesPID(drive, 5, 0),
                    new RotateToDegreesPID(drive, -90),
                    new DriveForwardInchesPID(drive, 21.0, 0),
                    new RotateToDegreesPID(drive, 0),
                    new DriveForwardInchesPID(drive, 34.3, 0),
                    new RotateToDegreesPID(drive, -90),
                    new ElevatorToPositionCommand(elevator, 1.0),
                    new DriveForwardInchesPID(drive, 4, 0),
                    new ElevatorToPositionCommand(elevator, 0.8),
                    new InstantCommand(() -> gripper.open()),
                    new WaitCommand(500),
                    new DriveForwardInchesPID(drive, -4, 0),
                    new ElevatorToPositionCommand(elevator,0),
                    new RotateToDegreesPID(drive, 180),
                    new DriveForwardInchesPID(drive, 12, 0),
                    new RotateToDegreesPID(drive, 90),
                    new DriveForwardInchesPID(drive, 48,0),
                    new SaveAutoHeadingCommand(() -> drive.getHeading()),
                    new EndOpModeCommand(this)
                );
            default: // CENTER
                return new SequentialCommandGroup(
                        new ElevatorResetCommand(elevator),
                        new InstantCommand(() -> gripper.close()),
                        new WaitCommand(500),
                        new ElevatorToPositionCommand(elevator, 0.06),
                        new DriveForwardInchesPID(drive, 5, 0),
                        new RotateToDegreesPID(drive, -90),
                        new DriveForwardInchesPID(drive, 21.0, 0),
                        new RotateToDegreesPID(drive, 0),
                        new DriveForwardInchesPID(drive, 34.3, 0),
                        new RotateToDegreesPID(drive, -90),
                        new ElevatorToPositionCommand(elevator, 1.0),
                        new DriveForwardInchesPID(drive, 4, 0),
                        new ElevatorToPositionCommand(elevator, 0.8),
                        new InstantCommand(() -> gripper.open()),
                        new WaitCommand(500),
                        new DriveForwardInchesPID(drive, -4, 0),
                        new ElevatorToPositionCommand(elevator,0),
                        new RotateToDegreesPID(drive, 180),
                        new DriveForwardInchesPID(drive, 12, 0),
                        new RotateToDegreesPID(drive, 90),
                        new DriveForwardInchesPID(drive, 24,0),
                        new SaveAutoHeadingCommand(() -> drive.getHeading()),
                        new EndOpModeCommand(this)
                );
        }
    }
}