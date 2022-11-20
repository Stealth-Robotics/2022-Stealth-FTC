package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.DriveForwardInchesCommand;
import org.firstinspires.ftc.teamcode.commands.GripperCloseCommand;
import org.firstinspires.ftc.teamcode.commands.GripperOpenCommand;
import org.firstinspires.ftc.teamcode.commands.ResetElevatorCommand;
import org.firstinspires.ftc.teamcode.commands.SetElevatorPercentageCommand;
import org.firstinspires.ftc.teamcode.commands.StrafeForInchesCommand;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.pipelines.ParkingPosition;
import org.stealthrobotics.library.AutoToTeleStorage;
import org.stealthrobotics.library.commands.EndOpModeCommand;
import org.stealthrobotics.library.commands.SaveAutoHeadingCommand;
import org.stealthrobotics.library.opmodes.StealthOpMode;

@SuppressWarnings("unused")
@Autonomous(name = "Marco Auto #1", preselectTeleOp = "BLUE | Tele-Op")
public class MarcoAutoOne extends StealthOpMode {

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

        // Automatically reset the elevator all the way down when we init
        schedule(new ResetElevatorCommand(elevator));
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
        // Reset our saved heading from the last auto run. It was updated by SaveAutoHeadingCommand.
        AutoToTeleStorage.finalAutoHeading = 0;

        ParkingPosition position = camera.getPosition();
        camera.stop();

        // This was Marco's auto from the first match.
        // I've re-arranged it to separate the common and unique portions, and to add the epilogue.
        SequentialCommandGroup autoCmd = new SequentialCommandGroup(
                new GripperCloseCommand(gripper),
                new SetElevatorPercentageCommand(elevator, 0.05),
                new DriveForwardInchesCommand(drive, 24),
                new StrafeForInchesCommand(drive, 13.5),
                new SetElevatorPercentageCommand(elevator, 0.65),
                new DriveForwardInchesCommand(drive, 3.5).withTimeout(1000),
                new GripperOpenCommand(gripper),
                new DriveForwardInchesCommand(drive, -3.5),
                new ParallelCommandGroup(
                        new SetElevatorPercentageCommand(elevator, 0.0),
                        new DriveForwardInchesCommand(drive, -2)
                )
        );

        if (position == ParkingPosition.LEFT) {
            autoCmd.addCommands(
                    new ParallelCommandGroup(
                            new GripperCloseCommand(gripper),
                            new StrafeForInchesCommand(drive, -40)
                    )
            );
        } else if (position == ParkingPosition.CENTER) {
            autoCmd.addCommands(
                    new ParallelCommandGroup(
                            new GripperCloseCommand(gripper),
                            new StrafeForInchesCommand(drive, -13.5)
                    )
            );
        } else { // RIGHT
            autoCmd.addCommands(
                    new ParallelCommandGroup(
                            new GripperCloseCommand(gripper),
                            new StrafeForInchesCommand(drive, 13.5)
                    )
            );
        }

        // Save the final robot heading so field-centric works well in the next teleop, then end
        // the opmode now.
        autoCmd.addCommands(
                new SaveAutoHeadingCommand(() -> drive.getHeading()),
                new EndOpModeCommand(this)
        );

        return autoCmd;
    }
}
