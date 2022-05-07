package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

public class ExampleCommand extends CommandBase {

    final String something;
    int loopCount;

    public ExampleCommand(String somethingToSay) {
        this.something = somethingToSay;
    }

    @Override
    public void initialize() {
        loopCount = 0;
        System.out.println("Initialize, loopCount=" + loopCount);
    }

    @Override
    public void execute() {
        loopCount++;
        System.out.println("Execute, loopCount=" + loopCount);
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("End, interrupted=" + interrupted + ", loopCount=" + loopCount);
    }

    @Override
    public boolean isFinished() {
        System.out.println("isFinished, loopCount=" + loopCount);
        if (loopCount > 10) {
            return true;
        } else {
            return false;
        }
    }
}
