package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumSubsystem;

public class DriveForMMCommand extends DriveForTicksCommand {
    public DriveForMMCommand(SimpleMecanumSubsystem drive, double y, double x, double rx, double mm) {
        super(drive, y, x, rx, mm * TICKS_PER_MM);
    }
}
