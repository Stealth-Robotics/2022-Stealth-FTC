package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.DriveForwardInchesCommand;
import org.firstinspires.ftc.teamcode.commands.GripperCloseCommand;
import org.firstinspires.ftc.teamcode.commands.GripperOpenCommand;
import org.firstinspires.ftc.teamcode.commands.SetElevatorPercentageCommand;
import org.firstinspires.ftc.teamcode.commands.StrafeForInchesCommand;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.pipelines.SleeveDetection;
import org.stealthrobotics.library.opmodes.StealthOpMode;

@SuppressWarnings("unused")
@Autonomous(name = "Test Auto", preselectTeleOp = "BLUE | Tele-Op")
public class TestAuto extends StealthOpMode {

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
        SleeveDetection.ParkingPosition position = camera.getPosition();

        if (position == SleeveDetection.ParkingPosition.LEFT) {
            return new SequentialCommandGroup(
                    new GripperCloseCommand(gripper),
                    new SetElevatorPercentageCommand(elevator, 0.05),
                    new DriveForwardInchesCommand(drive, -24),
                    new StrafeForInchesCommand(drive, -13.5),
                    new SetElevatorPercentageCommand(elevator, 0.65),
                    new DriveForwardInchesCommand(drive, -3.5).withTimeout(1000),
                    new GripperOpenCommand(gripper),
                    new DriveForwardInchesCommand(drive, 3.5),
                    new ParallelCommandGroup(
                            new SetElevatorPercentageCommand(elevator, 0.0),
                            new DriveForwardInchesCommand(drive, 2)
                    ),
                    new ParallelCommandGroup(
                            new GripperCloseCommand(gripper),
                            new StrafeForInchesCommand(drive, 40)
                    )
            );
        } else if (position == SleeveDetection.ParkingPosition.CENTER) {
            return new SequentialCommandGroup(
                    new GripperCloseCommand(gripper),
                    new SetElevatorPercentageCommand(elevator, 0.05),
                    new DriveForwardInchesCommand(drive, -24),
                    new StrafeForInchesCommand(drive, -13.5),
                    new SetElevatorPercentageCommand(elevator, 0.65),
                    new DriveForwardInchesCommand(drive, -3.5).withTimeout(1000),
                    new GripperOpenCommand(gripper),
                    new DriveForwardInchesCommand(drive, 3.5),
                    new ParallelCommandGroup(
                            new SetElevatorPercentageCommand(elevator, 0.0),
                            new DriveForwardInchesCommand(drive, 2)
                    ),
                    new ParallelCommandGroup(
                            new GripperCloseCommand(gripper),
                            new StrafeForInchesCommand(drive, 13.5)
                    )
            );
        } else { // RIGHT
            return new SequentialCommandGroup(
                    new GripperCloseCommand(gripper),
                    new SetElevatorPercentageCommand(elevator, 0.05),
                    new DriveForwardInchesCommand(drive, -24),
                    new StrafeForInchesCommand(drive, -13.5),
                    new SetElevatorPercentageCommand(elevator, 0.65),
                    new DriveForwardInchesCommand(drive, -3.5).withTimeout(1000),
                    new GripperOpenCommand(gripper),
                    new DriveForwardInchesCommand(drive, 3.5),
                    new ParallelCommandGroup(
                            new SetElevatorPercentageCommand(elevator, 0.0),
                            new DriveForwardInchesCommand(drive, 1)
                    ),
                    new ParallelCommandGroup(
                            new GripperCloseCommand(gripper),
                            new StrafeForInchesCommand(drive, -13.5)
                    )
            );
        }
    }
}
