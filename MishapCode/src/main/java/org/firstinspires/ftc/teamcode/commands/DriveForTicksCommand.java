package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumSubsystem;

public class DriveForTicksCommand extends CommandBase {
    // We're using the following motors from goBILDA for our drivetrain:
    //   - 5203 Series Yellow Jacket Planetary Gear Motor (19.2:1 Ratio, 24mm Length 8mm REX Shaft, 312 RPM, 3.3 - 5V Encoder)
    //   - https://www.gobilda.com/5203-series-yellow-jacket-planetary-gear-motor-19-2-1-ratio-24mm-length-8mm-rex-shaft-312-rpm-3-3-5v-encoder/
    //
    //   From the website:
    //     - Encoder Resolution: 537.7 PPR at the Output Shaft (PPR is Pulses Per Revolution, i.e., encoder ticks)
    //
    // Our mecanum wheels are 96mm diameter, thus the equation below.
    public static final double TICKS_PER_MM = 537.7 / (96 * Math.PI);

    final SimpleMecanumSubsystem drive;
    final double y, x, rx;
    final double ticks;
    final boolean positiveDir;
    double endTicks;

    public DriveForTicksCommand(SimpleMecanumSubsystem drive, double y, double x, double rx, double ticks) {
        this.drive = drive;
        this.y = y;
        this.x = x;
        this.rx = rx;
        this.ticks = ticks;
        this.positiveDir = ticks > 0;
    }

    @Override
    public void initialize() {
        endTicks = drive.getCurrentPosition() + ticks;
        drive.driveRaw(y, x, rx, false);
    }

    @Override
    public void end(boolean interrupted) {
        drive.stop();
    }

    @Override
    public boolean isFinished() {
        return (positiveDir && drive.getCurrentPosition() >= endTicks) ||
                (!positiveDir && drive.getCurrentPosition() <= endTicks);
    }
}
