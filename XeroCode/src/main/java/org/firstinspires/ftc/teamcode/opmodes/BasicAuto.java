package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.DriveForwardInchesCommand;
import org.firstinspires.ftc.teamcode.commands.TurnToDegreesCommand;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.GripperSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;
import org.stealthrobotics.library.AutoToTeleStorage;
import org.stealthrobotics.library.commands.EndOpModeCommand;
import org.stealthrobotics.library.commands.SaveAutoHeadingCommand;
import org.stealthrobotics.library.opmodes.StealthOpMode;

@SuppressWarnings("unused")
@Autonomous(name = "BLUE | Wallace Is #1", group = "Blue Auto", preselectTeleOp = "BLUE | Tele-Op")
public class BasicAuto extends StealthOpMode {

    // Subsystems
    SimpleMecanumDriveSubsystem drive;
    ElevatorSubsystem elevator;
    GripperSubsystem gripper;

    /**
     * Executed when you init the selected opmode. This is where you setup your hardware.
     */
    @Override
    public void initialize() {
        // Setup and register all of your subsystems here
        drive = new SimpleMecanumDriveSubsystem(hardwareMap);
        elevator = new ElevatorSubsystem(hardwareMap);
        gripper = new GripperSubsystem(hardwareMap);
        register(drive, elevator, gripper);
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

        return new SequentialCommandGroup(
                new InstantCommand(() -> drive.turbomodeon()),
                new DriveForwardInchesCommand(drive, 26.0),
                new SaveAutoHeadingCommand(() -> drive.getHeading()),
                new EndOpModeCommand(this)
        );
    }
}
