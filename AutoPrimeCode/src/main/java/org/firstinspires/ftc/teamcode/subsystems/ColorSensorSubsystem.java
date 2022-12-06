package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ColorSensorSubsystem extends SubsystemBase {
    private final ColorSensor colorSensor;

    public ColorSensorSubsystem(HardwareMap hardwareMap) {


        colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
    }





    @Override
    public void periodic() {
        telemetry.addData("Color Sensor Red Values", colorSensor.red());
        telemetry.addData("Color Sensor Green Values", colorSensor.green());
        telemetry.addData("Color Sensor Blue Values", colorSensor.blue());
    }
}
