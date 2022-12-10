package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ColorSensorSubsystem extends SubsystemBase {
    private final ColorSensor colorSensorLeft;
    private final ColorSensor colorSensorRight;

    public ColorSensorSubsystem(HardwareMap hardwareMap) {


        colorSensorLeft = hardwareMap.get(ColorSensor.class, "colorSensorLeft");
        colorSensorRight = hardwareMap.get(ColorSensor.class, "colorSensorRight");

    }





    @Override
    public void periodic() {
        telemetry.addData("Left Sensor Red Values", colorSensorLeft.red());
        telemetry.addData("Left Sensor Green Values", colorSensorLeft.green());
        telemetry.addData("Left Sensor Blue Values", colorSensorLeft.blue());
        telemetry.addData("Right Sensor Red Values", colorSensorRight.red());
        telemetry.addData("Right Sensor Green Values", colorSensorRight.green());
        telemetry.addData("Right Sensor Blue Values", colorSensorRight.blue());
    }
}
