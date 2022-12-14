package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

@Config
public class GroundSensorSubsystem extends SubsystemBase {
    final RevColorSensorV3 leftSensor;
    final RevColorSensorV3 rightSensor;

    public static double TAPE_PRESENT_THRESHOLD = 0.005;

    private NormalizedRGBA leftColors;
    private NormalizedRGBA rightColors;

    public GroundSensorSubsystem(HardwareMap hardwareMap) {
        leftSensor = hardwareMap.get(RevColorSensorV3.class, "revcsv3l");
        rightSensor = hardwareMap.get(RevColorSensorV3.class, "revcsv3r");
    }

    public void update() {
        leftColors = leftSensor.getNormalizedColors();
        rightColors = rightSensor.getNormalizedColors();
    }

    public boolean leftColorDetected() {
        return leftColors.red > TAPE_PRESENT_THRESHOLD || leftColors.blue > TAPE_PRESENT_THRESHOLD;
    }

    public boolean rightColorDetected() {
        return rightColors.red > TAPE_PRESENT_THRESHOLD || rightColors.blue > TAPE_PRESENT_THRESHOLD;
    }

    public void periodic() {
//        update();
//        telemetry.addData("LR", leftColors.red);
//        telemetry.addData("LG", leftColors.green);
//        telemetry.addData("LB", leftColors.blue);
//        telemetry.addData("RR", rightColors.red);
//        telemetry.addData("RG", rightColors.green);
//        telemetry.addData("RB", rightColors.blue);
//        telemetry.addData("left color detected", leftColorDetected());
//        telemetry.addData("right color detected", rightColorDetected());
    }
}
