package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeSubsystem extends SubsystemBase {

    private final CRServo intakeServo;

    public IntakeSubsystem(HardwareMap hardwareMap) {
        intakeServo = hardwareMap.get(CRServo.class, "intakeServo");
    }

    public void spinIntakeIn() {
        intakeServo.setPower(-1.0);
    }

    public void spinIntakeOut() {
        intakeServo.setPower(1.0);
    }

    public void stop() {
        intakeServo.setPower(0.0);
    }
}

