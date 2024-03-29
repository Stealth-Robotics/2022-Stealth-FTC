package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
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
@Autonomous(name = "RightSideStart", preselectTeleOp = "BLUE | Tele-Op")
public class RightStartAuto extends StealthOpMode {

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

        camera.setRightSide();
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
        SleeveDetection.ParkingPosition position = camera.getPosition();

        if (position == SleeveDetection.ParkingPosition.LEFT) {
            return new SequentialCommandGroup(
                    new GripperCloseCommand(gripper),
                    new MoveElevatorPercentage(elevator, 0.1),
                    new StrafeForInches(drive, -18),
                    new DriveForwardInchesCommand(drive, 24),
                    new StrafeForInches(drive, 24),
                    new StrafeForInches(drive, -10),
                    new TurnToDegrees(drive, -48),
                    new MoveElevatorPercentage(elevator, 0.63),
                    new DriveForwardInchesCommand(drive, 9).withTimeout(1000),
                    new WaitCommand(100),
                    new MoveElevatorPercentage(elevator, 0.6),
                    new GripperOpenCommand(gripper),
                    new MoveElevatorPercentage(elevator, 0.64),
                    new ParallelCommandGroup(
                            new MoveElevatorPercentage(elevator, 0.0),
                            new DriveForwardInchesCommand(drive, -9)
                    ),
                    new TurnToDegrees(drive, 0),
                    new DriveForwardInchesCommand(drive, 20),
                    new TurnToDegrees(drive, 90),
                    new StrafeForInches(drive, 2),
                    new DriveForwardInchesCommand(drive, 20),
                    new AlignToTapeCommand(drive, colorSensor, AlignToTapeCommand.Direction.RIGHT).withTimeout(1000),
                    new WaitCommand(100),
                    new MoveElevatorPercentage(elevator, 0.095),
                    new WaitCommand(100),
                    new DriveForwardInchesCommand(drive, 8).withTimeout(1000),
                    new GripperCloseCommand(gripper),
                    new MoveElevatorPercentage(elevator, 0.3),
                    new DriveForwardInchesCommand(drive, -24),
                    new TurnToDegrees(drive, -90),
                    new MoveElevatorPercentage(elevator, 0.63),
                    new TurnToDegrees(drive, -125),
                    new DriveForwardInchesCommand(drive, 10),
                    new MoveElevatorPercentage(elevator, 0.61),
                    new GripperOpenCommand(gripper),
                    new ParallelCommandGroup(
                            new MoveElevatorPercentage(elevator, 0),
                            new DriveForwardInchesCommand(drive, -9.5)
                    ),
                    new TurnToDegrees(drive, 180),
                    new DriveForwardInchesCommand(drive, 24),
                    new StrafeForInches(drive, 24),
                    new ParallelCommandGroup(
                            new TurnToDegrees(drive, 0),
                            new MoveElevatorPercentage(elevator, 0)
                    ),
                    new DriveForwardInchesCommand(drive, 2)

                    );

        } else if (position == SleeveDetection.ParkingPosition.CENTER) {
            return new SequentialCommandGroup(
                    new GripperCloseCommand(gripper),
                    new MoveElevatorPercentage(elevator, 0.1),
                    new StrafeForInches(drive, -18),
                    new DriveForwardInchesCommand(drive, 24),
                    new StrafeForInches(drive, 24),
                    new StrafeForInches(drive, -10),
                    new TurnToDegrees(drive, -48),
                    new MoveElevatorPercentage(elevator, 0.63),
                    new DriveForwardInchesCommand(drive, 9).withTimeout(1000),
                    new WaitCommand(100),
                    new MoveElevatorPercentage(elevator, 0.6),
                    new GripperOpenCommand(gripper),
                    new MoveElevatorPercentage(elevator, 0.64),
                    new ParallelCommandGroup(
                            new MoveElevatorPercentage(elevator, 0.0),
                            new DriveForwardInchesCommand(drive, -9)
                    ),
                    new TurnToDegrees(drive, 0),
                    new DriveForwardInchesCommand(drive, 20),
                    new TurnToDegrees(drive, 90),
                    new StrafeForInches(drive, 2),
                    new DriveForwardInchesCommand(drive, 20),
                    new AlignToTapeCommand(drive, colorSensor, AlignToTapeCommand.Direction.RIGHT).withTimeout(1000),
                    new WaitCommand(100),
                    new MoveElevatorPercentage(elevator, 0.095),
                    new WaitCommand(100),
                    new DriveForwardInchesCommand(drive, 8).withTimeout(1000),
                    new GripperCloseCommand(gripper),
                    new MoveElevatorPercentage(elevator, 0.3),
                    new DriveForwardInchesCommand(drive, -24),
                    new TurnToDegrees(drive, -90),
                    new MoveElevatorPercentage(elevator, 0.63),
                    new TurnToDegrees(drive, -125),
                    new DriveForwardInchesCommand(drive, 10),
                    new MoveElevatorPercentage(elevator, 0.61),
                    new GripperOpenCommand(gripper),
                    new ParallelCommandGroup(
                            new MoveElevatorPercentage(elevator, 0),
                            new DriveForwardInchesCommand(drive, -9.5)
                    ),
                    new TurnToDegrees(drive, 180),
                    new DriveForwardInchesCommand(drive, 24),
                    new ParallelCommandGroup(
                            new TurnToDegrees(drive, 0),
                            new MoveElevatorPercentage(elevator, 0)
                    ),
                    new DriveForwardInchesCommand(drive, 2)

            );
        } else { // RIGHT
            return new SequentialCommandGroup(
                    new GripperCloseCommand(gripper),
                    new MoveElevatorPercentage(elevator, 0.1),
                    new StrafeForInches(drive, -18),
                    new DriveForwardInchesCommand(drive, 24),
                    new StrafeForInches(drive, 24),
                    new StrafeForInches(drive, -10),
                    new TurnToDegrees(drive, -48),
                    new MoveElevatorPercentage(elevator, 0.63),
                    new DriveForwardInchesCommand(drive, 9).withTimeout(1000),
                    new WaitCommand(100),
                    new MoveElevatorPercentage(elevator, 0.6),
                    new GripperOpenCommand(gripper),
                    new MoveElevatorPercentage(elevator, 0.64),
                    new ParallelCommandGroup(
                            new MoveElevatorPercentage(elevator, 0.0),
                            new DriveForwardInchesCommand(drive, -9)
                    ),
                    new TurnToDegrees(drive, 0),
                    new DriveForwardInchesCommand(drive, 20),
                    new TurnToDegrees(drive, 90),
                    new StrafeForInches(drive, 2),
                    new DriveForwardInchesCommand(drive, 20),
                    new AlignToTapeCommand(drive, colorSensor, AlignToTapeCommand.Direction.RIGHT).withTimeout(1000),
                    new WaitCommand(100),
                    new MoveElevatorPercentage(elevator, 0.095),
                    new WaitCommand(100),
                    new DriveForwardInchesCommand(drive, 8).withTimeout(1000),
                    new GripperCloseCommand(gripper),
                    new MoveElevatorPercentage(elevator, 0.3),
                    new DriveForwardInchesCommand(drive, -24),
                    new TurnToDegrees(drive, -90),
                    new MoveElevatorPercentage(elevator, 0.63),
                    new TurnToDegrees(drive, -125),
                    new DriveForwardInchesCommand(drive, 10),
                    new MoveElevatorPercentage(elevator, 0.61),
                    new GripperOpenCommand(gripper),
                    new ParallelCommandGroup(
                            new MoveElevatorPercentage(elevator, 0),
                            new DriveForwardInchesCommand(drive, -9.5)
                    ),
                    new TurnToDegrees(drive, 180),
                    new DriveForwardInchesCommand(drive, 24),
                    new StrafeForInches(drive, -24),
                    new ParallelCommandGroup(
                            new TurnToDegrees(drive, 0),
                            new MoveElevatorPercentage(elevator, 0)
                    ),
                    new DriveForwardInchesCommand(drive, 2)

            );
        }

    }
}
