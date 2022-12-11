package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Config
public class GroundSensorSubsystem extends SubsystemBase {
    final RevColorSensorV3 centerSensor;

    public static double TAPE_PRESENT_THRESHOLD = 0.005;

    public GroundSensorSubsystem(HardwareMap hardwareMap) {
        centerSensor = hardwareMap.get(RevColorSensorV3.class, "revcsv3");
    }

    public boolean centerColorDetected() {
        NormalizedRGBA rgba = centerSensor.getNormalizedColors();

        return rgba.red > TAPE_PRESENT_THRESHOLD || rgba.blue > TAPE_PRESENT_THRESHOLD;
    }

    public void periodic() {
        NormalizedRGBA rgba = centerSensor.getNormalizedColors();
        telemetry.addData("Red", rgba.red);
        telemetry.addData("Green", rgba.green);
        telemetry.addData("Blue", rgba.blue);
        telemetry.addData("center color detected", centerColorDetected());
    }
}
