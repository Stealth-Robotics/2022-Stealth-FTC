package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ExtenderSubsystem;

import java.util.function.DoubleSupplier;

public class ExtenderDefault extends CommandBase {

    private final ExtenderSubsystem extender;

    //private double negativeSpeed;
    //private double positiveSpeed;
    private final DoubleSupplier positveSpeed;
    private final DoubleSupplier negativeSpeed;


    public ExtenderDefault(ExtenderSubsystem extender, DoubleSupplier negativeSpeed, DoubleSupplier positiveSpeed) {
        this.extender = extender;
        this.negativeSpeed = negativeSpeed;
        this.positveSpeed = positiveSpeed;

    }

    @Override
    public void execute() {
        extender.setSpeed((-1 * negativeSpeed.getAsDouble()) + positveSpeed.getAsDouble());
    }
}