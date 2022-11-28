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

import org.stealthrobotics.library.math.filter.Debouncer;

@Config
public class ExtenderSubsystem extends SubsystemBase {
    private DcMotorEx armMotorA;
    private DcMotorEx armMotorB;
    DigitalChannel ls;

    private PIDFController extenderController;
    private double speedConstraints = 0.15;

    public static double extenderP = 0.01;
    public static double extenderI = 0;
    public static double extenderD = 0;
    public static double extenderF = 0;

    private double extenderTolerance = 10;

    private Debouncer stallDebouncer = new Debouncer(0.100, Debouncer.DebounceType.kRising);

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
        armMotorA.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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


    //check the elevator down
    public void setElevatorDownSlowly() {
        armMotorB.setPower(-.25);
        armMotorA.setPower(-.25);
        stallDebouncer.calculate(false);
    }

    public boolean checkVelocity() {

        return stallDebouncer.calculate(Math.abs(armMotorA.getVelocity()) < 1);
    }

    public void completeReset() {
        armMotorA.setPower(0);
        armMotorB.setPower(0);
        armMotorA.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotorB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armMotorA.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        armMotorB.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    //check the elevator downn
    @Override
    public void periodic() {
        telemetry.addData("Elevator Target", getTarget());
        telemetry.addData("Current Elevator Position", getPosition());
    }
}