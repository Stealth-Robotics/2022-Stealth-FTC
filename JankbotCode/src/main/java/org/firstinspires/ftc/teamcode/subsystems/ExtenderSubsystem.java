package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

@Config
public class ExtenderSubsystem extends SubsystemBase {
    private DcMotor armMotorA;
    private DcMotor armMotorB;
    DigitalChannel ls;

    private PIDFController extenderController;
    private double speedConstraints = 0.15;

    public static double extenderP= 0.01;
    public static double extenderI=0;
    public static double extenderD=0;
    public static double extenderF=0;

    private double extenderTolerance;

    public ExtenderSubsystem(HardwareMap hardwareMap) {

        extenderController = new PIDFController(
                extenderP,
                extenderI,
                extenderD,
                extenderF

        );

        extenderController.setTolerance(extenderTolerance);


        armMotorA = hardwareMap.get(DcMotorEx.class, "armMotorA");
        armMotorA.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotorA.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armMotorA.setDirection(DcMotorSimple.Direction.REVERSE);

        armMotorB = hardwareMap.get(DcMotorEx.class, "frontEncoder");
        armMotorB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotorB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armMotorB.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void setTargetPosition(int targetPosition, double speed) {
        speedConstraints = speed;
        extenderController.setSetPoint(targetPosition);
    }

    public void setSpeed(double speed) {
        armMotorA.setPower(speed);
        armMotorB.setPower(speed);
    }

    public boolean atTargetPosition() {
        return extenderController.atSetPoint();
    }

    public int getPosition() {
        return armMotorA.getCurrentPosition();
    }

    public double update() {
        extenderController.setPIDF(extenderP,extenderI,extenderD,extenderF);
        return Math.max(Math.min(extenderController.calculate(getPosition()), speedConstraints), -speedConstraints);
    }

    public double getTarget() {
        return extenderController.getSetPoint();
    }

    public double getLastError() {
        return extenderController.getPositionError();
    }

    @Override
    public void periodic() {
        telemetry.addData("Elevator Target", getTarget());
        telemetry.addData("Current Elevator Position", getPosition());
    }
}