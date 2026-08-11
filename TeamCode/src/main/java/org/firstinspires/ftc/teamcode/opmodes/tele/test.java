package org.firstinspires.ftc.teamcode.opmodes.tele;
// FTC annotation that makes this class appear as a TeleOp on the Driver Station.
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
// NextFTC base class for an OpMode.
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
// NextFTC mecanum driving command.
import dev.nextftc.hardware.driving.MecanumDriverControlled;
// Our Robot class.
import org.firstinspires.ftc.teamcode.robot;


@TeleOp(name = "dt1", group = "Test")
public class test extends NextFTCOpMode {
    private robot robot;

    // INITIALIZATION
    // onInit() runs when the driver presses INIT.
    @Override public void onInit() {
        // Create our Robot object.
        robot = new robot();
    }

    // START TELEOP
    // Runs when the driver presses PLAY.
    @Override public void onStartButtonPressed() {
        // Create the mecanum driver.
        // robot.drivetrain.getMotors()
        // gives us the four drivetrain motors.
        // gamepadManager.gamepad1
        // gives the mecanum driver Gamepad 1,
        // which is controlled by the driver.
        double strafeSpeed = 0.7;
        double turnSpeed = 0.5;
        MecanumDriverControlled driverControlled =
                new MecanumDriverControlled(
                        robot.dt.getLeftFrontMotor(),
                        robot.dt.getRightFrontMotor(),
                        robot.dt.getLeftBackMotor(),
                        robot.dt.getRightBackMotor(),

                        Gamepads.gamepad1().leftStickY().negate(),
                        Gamepads.gamepad1().leftStickX().map(x -> x * strafeSpeed),
                        Gamepads.gamepad1().rightStickX().map(x -> x * turnSpeed)
                );

        driverControlled.schedule();
    }


}
