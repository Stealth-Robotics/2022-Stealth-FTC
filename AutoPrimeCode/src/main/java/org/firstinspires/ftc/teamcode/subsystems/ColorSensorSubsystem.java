package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;


//  ------------------------------------------
//            |||||||||||||||
//            |||||||||||||||
//            |||||||||||||||
//            |||||||||||||||
//            |||||||||||||||
//                     L <----  2.5"  ----> R
//            |||||||||||||||
//            |||||||||||||||
//            |||||||||||||||
//            |||||||||||||||
//            |||||||||||||||
//            |||||||||||||||



@Config
public class ColorSensorSubsystem extends SubsystemBase {
    private final ColorSensor colorSensorLeft;
    private final ColorSensor colorSensorRight;

    public static int TAPE_THRESHOLD = 2000;

    public ColorSensorSubsystem(HardwareMap hardwareMap) {
        colorSensorLeft = hardwareMap.get(ColorSensor.class, "revcsv3");
        colorSensorRight = hardwareMap.get(ColorSensor.class, "revcsv3-right");
    }

    private boolean doesSensorSeeTape(ColorSensor s) {
        int r = s.red();
        int b = s.blue();
        return r > TAPE_THRESHOLD || b > TAPE_THRESHOLD;
    }

    public boolean doesLeftSensorSeeTape() {
        return doesSensorSeeTape(colorSensorLeft);
    }

    public boolean doesRightSensorSeeTape() {
        return doesSensorSeeTape(colorSensorRight);
    }


    @Override
    public void periodic() {
        telemetry.addData("Color Sensor Red Values", colorSensorLeft.red());
        telemetry.addData("Color Sensor Green Values", colorSensorLeft.green());
        telemetry.addData("Color Sensor Blue Values", colorSensorLeft.blue());
    }
}


