package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumSubsystem;

public class DriveForFathomsCommand extends DriveForTicksCommand {
    public DriveForFathomsCommand(SimpleMecanumSubsystem drive, double y, double x, double rx, double fathoms) {
        super(drive, y, x, rx, fathoms * 72 * 25.4 * TICKS_PER_MM);
    }
}
