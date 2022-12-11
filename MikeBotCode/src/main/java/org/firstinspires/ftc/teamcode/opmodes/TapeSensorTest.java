package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.AlignToTapeCommand;
import org.firstinspires.ftc.teamcode.commands.DriveForwardInchesPIDLimitedHeadingCommand;
import org.firstinspires.ftc.teamcode.commands.ResetElevatorCommand;
import org.firstinspires.ftc.teamcode.commands.SetElevatorPercentageCommand;
import org.firstinspires.ftc.teamcode.commands.StrafeForInchesCommand;
import org.firstinspires.ftc.teamcode.commands.TurnToDegreesPIDCommand;
import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GroundSensorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.pipelines.ParkingPosition;
import org.stealthrobotics.library.AutoToTeleStorage;
import org.stealthrobotics.library.commands.EndOpModeCommand;
import org.stealthrobotics.library.commands.SaveAutoHeadingCommand;
import org.stealthrobotics.library.opmodes.StealthOpMode;

import java.util.function.Supplier;

@SuppressWarnings("unused")
@Autonomous(name = "Tape Sensor Test", preselectTeleOp = "BLUE | Tele-Op")
public class TapeSensorTest extends StealthOpMode {

    // Subsystems
    SimpleMecanumDriveSubsystem drive;
    ElevatorSubsystem elevator;
    GripperSubsystem gripper;
    CameraSubsystem camera;
    GroundSensorSubsystem groundSensor;

    @Override
    public void initialize() {
        drive = new SimpleMecanumDriveSubsystem(hardwareMap);
        elevator = new ElevatorSubsystem(hardwareMap);
        gripper = new GripperSubsystem(hardwareMap);
        camera = new CameraSubsystem(hardwareMap);
        groundSensor = new GroundSensorSubsystem(hardwareMap);
        register(drive, elevator, gripper, camera, groundSensor);

        // Automatically reset the elevator all the way down when we init
        schedule(new ResetElevatorCommand(elevator));
    }

    @Override
    public void whileWaitingToStart() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public Command getAutoCommand() {
        AutoToTeleStorage.finalAutoHeading = 0;

        ParkingPosition position = camera.getPosition();
        camera.stop();

        Supplier<Command> cycleSequence = () -> new SequentialCommandGroup(
                new DriveForwardInchesPIDLimitedHeadingCommand(drive, 20),
                new AlignToTapeCommand(drive, groundSensor, AlignToTapeCommand.Direction.LEFT),
                new DriveForwardInchesPIDLimitedHeadingCommand(drive, 6),

                new SetElevatorPercentageCommand(elevator, 0.5), // pickup

                new DriveForwardInchesPIDLimitedHeadingCommand(drive, -26),
                new TurnToDegreesPIDCommand(drive, -135),
                new DriveForwardInchesPIDLimitedHeadingCommand(drive, 6),

                new SetElevatorPercentageCommand(elevator, 0.0), // drop

                new DriveForwardInchesPIDLimitedHeadingCommand(drive, -6),
                new TurnToDegreesPIDCommand(drive, 90),

                new StrafeForInchesCommand(drive, 1.5), // err to the right

                new InstantCommand() // noop
        );

        SequentialCommandGroup autoCmd = new SequentialCommandGroup(
                // Starting position to beginning of cycle sequence
                new DriveForwardInchesPIDLimitedHeadingCommand(drive, 22),
                new TurnToDegreesPIDCommand(drive, 90),

                cycleSequence.get(),
                cycleSequence.get(),
                cycleSequence.get(),
                cycleSequence.get(),
                cycleSequence.get(),
                cycleSequence.get(),
                cycleSequence.get(),
                cycleSequence.get(),
                cycleSequence.get(),
                cycleSequence.get(),

                new InstantCommand() // noop
        );

//        if (position == ParkingPosition.LEFT) {
//            autoCmd.addCommands(
//                    new ParallelCommandGroup(
//                            new GripperCloseCommand(gripper),
//                            new StrafeForInchesCommand(drive, -40)
//                    )
//            );
//        } else if (position == ParkingPosition.CENTER) {
//            autoCmd.addCommands(
//                    new ParallelCommandGroup(
//                            new GripperCloseCommand(gripper),
//                            new StrafeForInchesCommand(drive, -13.5)
//                    )
//            );
//        } else { // RIGHT
//            autoCmd.addCommands(
//                    new ParallelCommandGroup(
//                            new GripperCloseCommand(gripper),
//                            new StrafeForInchesCommand(drive, 13.5)
//                    )
//            );
//        }

        // Save the final robot heading so field-centric works well in the next teleop, then end
        // the opmode now.
        autoCmd.addCommands(
                new SaveAutoHeadingCommand(() -> drive.getHeading()),
                new EndOpModeCommand(this)
        );

        return autoCmd;
    }
}
