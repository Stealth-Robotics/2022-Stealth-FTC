package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.subsystems.CameraSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SimpleMecanumDriveSubsystem;
import org.stealthrobotics.library.commands.IfCommand;
import org.stealthrobotics.library.opmodes.StealthOpMode;

/**
 * This is a sample auto command that uses a webcam to decide where to drive.
 * <p>
 * The @Autonomous annotation says that this is an autonomous opmode. The name and group show up
 * on the driver station so you can select the right mode. If you have "blue" or "red" in either
 * name then your Alliance color will be set correctly for use throughout.
 */
@SuppressWarnings("unused")
@Autonomous(name = "BLUE | Sample Camera Auto", group = "Blue Auto", preselectTeleOp = "BLUE | Tele-Op")
public class SampleCameraAuto extends StealthOpMode {

    // Subsystems
    SimpleMecanumDriveSubsystem drive;
    CameraSubsystem camera;

    /**
     * Executed when you init the selected opmode. This is where you setup your hardware.
     */
    @Override
    public void initialize() {
        drive = new SimpleMecanumDriveSubsystem(hardwareMap);
        camera = new CameraSubsystem(hardwareMap);
        register(drive, camera);
    }

    /**
     * This will be called when your opmode is over so we can remember which way the robot is facing.
     * This helps us with things like field-centric driving in teleop afterwards.
     *
     * @return heading in radians
     */
    @Override
    public double getFinalHeading() {
        return drive.getHeading();
    }

    /**
     * This is where we create the one command we want to run in our autonomous opmode.
     * <p>
     * You create a SequentialCommandGroup, which is a list of commands that will run one after
     * another. This can be as simple or as complex as you'd like, and there are many ways to
     * group your commands in sequence, parallel, or conditionally.
     * <p>
     * Check out these links for more details:
     * - https://docs.ftclib.org/ftclib/command-base/command-system/convenience-commands
     * - https://docs.ftclib.org/ftclib/command-base/command-system/command-groups
     */
    @Override
    public Command getAutoCommand() {
        // This is called when we press "Play" on the driver station. The camera has been working
        // since we pressed "Init" and it's had enough time to give us a solid answer. So we ask
        // the camera for the position now and use that to decide how to build the rest of our
        // autonomous command.
//        int positionFromCamera = camera.getPosition();

        return new SequentialCommandGroup(
                // Drive forward at half speed some distance based on what the camera saw.
                //new DriveForTicksCommand(drive, 0.5, 0.0, 0.0, 1000 * positionFromCamera).withTimeout(2000),

                // Strafe right at half speed for 1000 ticks, or until 2 seconds have passed, but
                // only if the camera saw our target in position #2.
                //new IfCommand(() -> positionFromCamera == 2,
                        //new DriveForTicksCommand(drive, 0.0, 0.5, 0.0, 1000).withTimeout(2000))
        );
    }
}