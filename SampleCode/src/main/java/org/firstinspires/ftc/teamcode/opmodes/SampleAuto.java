package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.DriveForTicksCommand;
import org.firstinspires.ftc.teamcode.commands.TwoSpeedWheelCommand;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.WheelSubsystem;
import org.stealthrobotics.library.opmodes.StealthOpMode;

@SuppressWarnings("unused")
@Autonomous(name = "BLUE | Sample Auto", group = "Blue Auto", preselectTeleOp = "BLUE | Tele-Op")
public abstract class SampleAuto extends StealthOpMode {

    // Subsystems
    SimpleMecanumDriveSubsystem drive;
    WheelSubsystem wheel;

    @Override
    public void initialize() {
        drive = new SimpleMecanumDriveSubsystem(hardwareMap);
        wheel = new WheelSubsystem(hardwareMap);
        register(drive, wheel);
    }

    @Override
    public double getFinalHeading() {
        return drive.getHeading();
    }

    @Override
    public Command getAutoCommand() {
        return new SequentialCommandGroup(
                // Drive forward at half speed for 1000 ticks
                new DriveForTicksCommand(drive, 0.5, 0.0, 0.0, 1000),

                // Play with the wheel!
                new TwoSpeedWheelCommand(wheel),

                // Strafe right at half speed for 1000 ticks, or until 2 seconds have passed
                new DriveForTicksCommand(drive, 0.0, 0.5, 0.0, 1000).withTimeout(2000)
        );
    }

}
