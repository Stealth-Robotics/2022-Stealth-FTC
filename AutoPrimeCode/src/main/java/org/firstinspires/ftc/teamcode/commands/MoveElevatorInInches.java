package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;


public class MoveElevatorInInches extends CommandBase {
    final ElevatorSubsystem elevator;
    double distance;
    int end_ticks;
    public static double TICKS_PER_REVOLUTION = 537.7;
    public static double WHEEL_DIAMETER_MM = 96;
    public static double MM_PER_REVOLUTION = WHEEL_DIAMETER_MM * Math.PI;
    public static double IN_PER_REVOLUTION = MM_PER_REVOLUTION / 25.4;
    public static double TICKS_PER_IN = TICKS_PER_REVOLUTION / IN_PER_REVOLUTION;

    public MoveElevatorInInches(ElevatorSubsystem elevator, double distance) {
        this.elevator = elevator;
        this.distance = distance;
        addRequirements(elevator);
    }

    @Override
    public void initialize() {
        end_ticks = elevator.getTicks() + (int) (distance * TICKS_PER_IN);
        double drive_power = 0.5; //Todo positive or negative?
        if (distance < 0) {
            drive_power *= -1;
        }
        elevator.drive(drive_power);
    }

    @Override
    public void end(boolean interrupted) {
        elevator.stop();
    }

    @Override
    public boolean isFinished() {
        if (distance > 0) {
            if (elevator.getTicks() > end_ticks) {
                return true;
            }
        }
        return false;
    }
}

