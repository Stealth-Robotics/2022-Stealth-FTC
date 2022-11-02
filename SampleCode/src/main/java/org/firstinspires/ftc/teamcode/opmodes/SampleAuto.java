package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.DriveForTicksCommand;
import org.firstinspires.ftc.teamcode.commands.TwoSpeedWheelCommand;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.WheelSubsystem;
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
@Autonomous(name = "BLUE | Sample Auto", group = "Blue Auto", preselectTeleOp = "BLUE | Tele-Op")
public abstract class SampleAuto extends StealthOpMode {

    // Subsystems
    SimpleMecanumDriveSubsystem drive;
    WheelSubsystem wheel;

    /**
     * Executed when you init the selected opmode. This is where you setup your hardware.
     */
    @Override
    public void initialize() {
        drive = new SimpleMecanumDriveSubsystem(hardwareMap);
        wheel = new WheelSubsystem(hardwareMap);
        register(drive, wheel);
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
                // Drive forward at half speed for 1000 ticks
                new DriveForTicksCommand(drive, 0.5, 0.0, 0.0, 1000),

                // Play with the wheel!
                new TwoSpeedWheelCommand(wheel),

                // Strafe right at half speed for 1000 ticks, or until 2 seconds have passed
                new DriveForTicksCommand(drive, 0.0, 0.5, 0.0, 1000).withTimeout(2000),

                // Save our final heading, so field-centric driving will work in teleop
                new SaveAutoHeadingCommand(() -> drive.getHeading()),

                // End the opmode now, so we don't have to wait the rest of the 30s
                new EndOpModeCommand(this)

        );
    }
}
