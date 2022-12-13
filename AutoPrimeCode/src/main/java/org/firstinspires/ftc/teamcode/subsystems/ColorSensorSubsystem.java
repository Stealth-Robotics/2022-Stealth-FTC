package org.firstinspires.ftc.teamcode.subsystems;

import static org.stealthrobotics.library.opmodes.StealthOpMode.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.rev.RevColorSensorV3;
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

    // TODO: change to use this type for your sensors: RevColorSensorV3

    public static int TAPE_RED_THRESHOLD_L = 280;
    public static int TAPE_BLUE_THRESHOLD_L = 340;

    public static int TAPE_RED_THRESHOLD_R = 280;
    public static int TAPE_BLUE_THRESHOLD_R = 340;

    public ColorSensorSubsystem(HardwareMap hardwareMap) {
        colorSensorLeft = hardwareMap.get(ColorSensor.class, "colorSensorLeft");
        colorSensorRight = hardwareMap.get(ColorSensor.class, "colorSensorRight");
    }

    // TODO: change to call getNormalizedColors() once per sensor. Perhaps an update() method?

    public boolean doesLeftSensorSeeTape() {
        int r = colorSensorLeft.red();
        int b = colorSensorLeft.blue();
        return r > TAPE_RED_THRESHOLD_L || b > TAPE_BLUE_THRESHOLD_L;
    }

    public boolean doesRightSensorSeeTape() {
        int r = colorSensorRight.red();
        int b = colorSensorRight.blue();
        return r > TAPE_RED_THRESHOLD_R || b > TAPE_BLUE_THRESHOLD_R;
    }


    @Override
    public void periodic() {
//        telemetry.addData("L Color Sensor Red Values", colorSensorLeft.red());
//        telemetry.addData("L Color Sensor Green Values", colorSensorLeft.green());
//        telemetry.addData("L Color Sensor Blue Values", colorSensorLeft.blue());
//
//        telemetry.addData("R Color Sensor Red Values", colorSensorRight.red());
//        telemetry.addData("R Color Sensor Green Values", colorSensorRight.green());
//        telemetry.addData("R Color Sensor Blue Values", colorSensorRight.blue());
    }
}


