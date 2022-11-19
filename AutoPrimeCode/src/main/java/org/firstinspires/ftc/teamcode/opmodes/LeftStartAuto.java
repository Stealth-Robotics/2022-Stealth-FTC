package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.DriveForwardInchesCommand;
import org.firstinspires.ftc.teamcode.commands.GripperCloseCommand;
import org.firstinspires.ftc.teamcode.commands.GripperOpenCommand;
import org.firstinspires.ftc.teamcode.commands.MoveElevatorPercentage;
import org.firstinspires.ftc.teamcode.commands.StrafeForInches;
import org.firstinspires.ftc.teamcode.commands.TurnInDegrees;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.pipelines.SleeveDetection;
import org.stealthrobotics.library.opmodes.StealthOpMode;

/**
 * This is a sample auto command that drives forward a bit, spins a wheel, then drives right a bit.
 * <p>
 * The @Autonomous annotation says that this is an autonomous opmode. The name and group show up
 * on the driver station so you can select the right mode. If you have "blue" or "red" in either
 * name then your Alliance color will be set correctly for use throughout.
 */
@SuppressWarnings("unused")
@Autonomous(name = "LeftSideStart", preselectTeleOp = "BLUE | Tele-Op")
public class LeftStartAuto extends StealthOpMode {

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
     * This will be called when your opmode is over so we can remember which way the robot is facing.
     * This helps us with things like field-centric driving in teleop afterwards.
     *
     * @return heading in radians
     */
    @Override
    public double getFinalHeading() {
        //return drive.getHeading();
        return 0;
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
        SleeveDetection.ParkingPosition position = SleeveDetection.ParkingPosition.RIGHT; //camera.getPosition();

        if (position == SleeveDetection.ParkingPosition.LEFT) {
            return new SequentialCommandGroup(
                    new GripperOpenCommand(gripper),
                    new GripperCloseCommand(gripper),
                    new MoveElevatorPercentage(elevator, 0.08),
                    new DriveForwardInchesCommand(drive, 24),
                    new TurnInDegrees(drive, 30),
                    new MoveElevatorPercentage(elevator, 0.68),
                    new DriveForwardInchesCommand(drive, 10).withTimeout(4000),
                    new GripperOpenCommand(gripper),
                    new DriveForwardInchesCommand(drive, -8),
                    new MoveElevatorPercentage(elevator, 0.0),
                    new TurnInDegrees(drive, 0),
                    new ParallelCommandGroup(
                            new GripperCloseCommand(gripper),
                            new StrafeForInches(drive, 32)
                    )
            );
        } else if (position == SleeveDetection.ParkingPosition.CENTER) {
            return new SequentialCommandGroup(
                    new GripperOpenCommand(gripper),
                    new GripperCloseCommand(gripper),
                    new MoveElevatorPercentage(elevator, 0.08),
                    new DriveForwardInchesCommand(drive, 24),
                    new TurnInDegrees(drive, 30),
                    new MoveElevatorPercentage(elevator, 0.68),
                    new DriveForwardInchesCommand(drive, 10).withTimeout(4000),
                    new GripperOpenCommand(gripper),
                    new DriveForwardInchesCommand(drive, -8),
                    new MoveElevatorPercentage(elevator, 0.0),
                    new TurnInDegrees(drive, 0),
                    new ParallelCommandGroup(
                            new GripperCloseCommand(gripper),
                            new StrafeForInches(drive, 8)
                    )
            );
        } else { // RIGHT
            return new SequentialCommandGroup(
                    new GripperOpenCommand(gripper),
                    new GripperCloseCommand(gripper),
                    new MoveElevatorPercentage(elevator, 0.08),
                    new DriveForwardInchesCommand(drive, 24),
                    new TurnInDegrees(drive, 30),
                    new MoveElevatorPercentage(elevator, 0.68),
                    new DriveForwardInchesCommand(drive, 10).withTimeout(4000),
                    new GripperOpenCommand(gripper),
                    new DriveForwardInchesCommand(drive, -8),
                    new MoveElevatorPercentage(elevator, 0.0),
                    new TurnInDegrees(drive, 0),
                    new ParallelCommandGroup(
                            new GripperCloseCommand(gripper),
                            new StrafeForInches(drive, -24)

                    )
            );

        }
    }

    public void periodic() {
        telemetry.addData("Web-Camera Color: ", SleeveDetection.ParkingPosition.values());

    }
}
