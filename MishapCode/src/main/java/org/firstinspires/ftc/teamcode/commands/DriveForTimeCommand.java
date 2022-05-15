package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.ParallelRaceGroup;
import com.arcrobotics.ftclib.command.WaitCommand;

import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumSubsystem;

public class DriveForTimeCommand extends ParallelRaceGroup {
    public DriveForTimeCommand(SimpleMecanumSubsystem drive, double y, double x, double rx, double seconds) {
        super(
                new DriveCommand(drive, y, x, rx, false),
                new WaitCommand((long) (seconds * 1000))
        );
    }
}
