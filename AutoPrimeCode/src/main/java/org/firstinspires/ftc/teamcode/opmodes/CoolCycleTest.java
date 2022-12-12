package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.AlignToTapeCommand;
import org.firstinspires.ftc.teamcode.commands.DriveForwardInchesCommand;
import org.firstinspires.ftc.teamcode.commands.GripperCloseCommand;
import org.firstinspires.ftc.teamcode.commands.GripperOpenCommand;
import org.firstinspires.ftc.teamcode.commands.MoveElevatorPercentage;
import org.firstinspires.ftc.teamcode.commands.StrafeForInches;
import org.firstinspires.ftc.teamcode.commands.TurnToDegrees;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ColorSensorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.pipelines.SleeveDetection;
import org.stealthrobotics.library.AutoToTeleStorage;
import org.stealthrobotics.library.commands.EndOpModeCommand;
import org.stealthrobotics.library.commands.SaveAutoHeadingCommand;
import org.stealthrobotics.library.opmodes.StealthOpMode;

/**
 * This is a sample auto command that drives forward a bit, spins a wheel, then drives right a bit.
 * <p>
 * The @Autonomous annotation says that this is an autonomous opmode. The name and group show up
 * on the driver station so you can select the right mode. If you have "blue" or "red" in either
 * name then your Alliance color will be set correctly for use throughout.
 */
@SuppressWarnings("unused")
@Autonomous(name = "LeftSideStart Cycle-Test", preselectTeleOp = "BLUE | Tele-Op")
public class CoolCycleTest extends StealthOpMode {

    // Subsystems
    SimpleMecanumDriveSubsystem drive;
    ElevatorSubsystem elevator;
    GripperSubsystem gripper;
    CameraSubsystem camera;
    ColorSensorSubsystem colorSensor;

    /**
     * Executed when you init the selected opmode. This is where you setup your hardware.
     */
    @Override
    public void initialize() {
        drive = new SimpleMecanumDriveSubsystem(hardwareMap);
        elevator = new ElevatorSubsystem(hardwareMap);
        gripper = new GripperSubsystem(hardwareMap);
        camera = new CameraSubsystem(hardwareMap);
        colorSensor = new ColorSensorSubsystem(hardwareMap);
        register(drive, elevator, gripper, camera, colorSensor);
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
        AutoToTeleStorage.finalAutoHeading = 0;
        SleeveDetection.ParkingPosition position = SleeveDetection.ParkingPosition.LEFT;

        if (position == SleeveDetection.ParkingPosition.LEFT) {
            return new SequentialCommandGroup(
                    new AlignToTapeCommand(drive, colorSensor)





            );
        } else if (position == SleeveDetection.ParkingPosition.CENTER) {
            return new SequentialCommandGroup(
                    new GripperCloseCommand(gripper),
                    new MoveElevatorPercentage(elevator, 0.1),
                    new StrafeForInches(drive, 12),
                    new DriveForwardInchesCommand(drive, 24),
                    new StrafeForInches(drive, -12),
                    new TurnToDegrees(drive, 46),
                    new MoveElevatorPercentage(elevator, 0.63),
                    new DriveForwardInchesCommand(drive, 9.5).withTimeout(4000),
                    new GripperOpenCommand(gripper),
                    new ParallelCommandGroup(
                            new MoveElevatorPercentage(elevator, 0.0),
                            new DriveForwardInchesCommand(drive, -10)
                    ),
                    new TurnToDegrees(drive, 2),
                    new ParallelCommandGroup(
                            new GripperCloseCommand(gripper)
                    ),
                    new SaveAutoHeadingCommand(() -> drive.getHeading()),
                    new EndOpModeCommand(this)
            );
        } else { // RIGHT
            return new SequentialCommandGroup(
                    new GripperCloseCommand(gripper),
                    new MoveElevatorPercentage(elevator, 0.1),
                    new StrafeForInches(drive, 12),
                    new DriveForwardInchesCommand(drive, 24),
                    new StrafeForInches(drive, -12),
                    new TurnToDegrees(drive, 46),
                    new MoveElevatorPercentage(elevator, 0.63),
                    new DriveForwardInchesCommand(drive, 9.5).withTimeout(4000),
                    new GripperOpenCommand(gripper),
                    new ParallelCommandGroup(
                            new MoveElevatorPercentage(elevator, 0.0),
                            new DriveForwardInchesCommand(drive, -10)
                    ),
                    new TurnToDegrees(drive, 2),
                    new ParallelCommandGroup(
                            new GripperCloseCommand(gripper),
                            new StrafeForInches(drive, -24)
                    ),
                    new SaveAutoHeadingCommand(() -> drive.getHeading()),
                    new EndOpModeCommand(this)
            );
        }

    }
}
