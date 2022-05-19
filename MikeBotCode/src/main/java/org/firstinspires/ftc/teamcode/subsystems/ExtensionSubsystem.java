package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.stealthrobotics.library.opmodes.StealthOpMode;

import java.util.List;

public class ExtensionSubsystem extends SubsystemBase {
    final DcMotorEx extensionMotor;
    List<LynxModule> lynxModules;

    public static final int MIN_POSITION = 0;
    public static final int MAX_POSITION = 1600;

    public ExtensionSubsystem(HardwareMap hardwareMap) {
        extensionMotor = hardwareMap.get(DcMotorEx.class, "extMotor");
        extensionMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extensionMotor.setTargetPosition(0);
        extensionMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        extensionMotor.setTargetPositionTolerance(20);
        extensionMotor.setDirection(DcMotor.Direction.FORWARD);
        extensionMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        extensionMotor.setVelocityPIDFCoefficients(5.0, 0.01, 0.0, 7.0);

        // TODO: feels like this ought to be somewhere more general. A overall robot class or subsystem?
        lynxModules = hardwareMap.getAll(LynxModule.class);
    }

    @Override
    public void periodic() {
        StealthOpMode.telemetry.addData("Extension", "Position: %d", extensionMotor.getCurrentPosition());
    }

    public void setPosition(int position) {
        extensionMotor.setTargetPosition(position);
        extensionMotor.setPower(1.0);
    }

    public int getCurrentPosition() {
        return extensionMotor.getCurrentPosition();
    }

    // Used when concurrent commands may have populated the bulk cache with an old motor position.
    public int getCurrentPositionNoCache() {
        resetPositionCache();
        return extensionMotor.getCurrentPosition();
    }

    private void resetPositionCache() {
        for (LynxModule module : lynxModules) {
            module.clearBulkCache();
        }
    }
}
