package org.firstinspires.ftc.teamcode.opmodes.tele;
// FTC annotation that makes this class appear as a TeleOp on the Driver Station.
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
// NextFTC base class for an OpMode.
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
// NextFTC mecanum driving command.
import dev.nextftc.hardware.driving.MecanumDriverControlled;
// Our Robot class.
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Odom;


@TeleOp(name = "dt1", group = "Test")
public class Test extends NextFTCOpMode {

    {
        addComponents(
                BindingsComponent.INSTANCE,
                new SubsystemComponent(Robot.INSTANCE.odom, Robot.INSTANCE.dt)
        );
    }



    // START TELEOP
    // Runs when the driver presses PLAY.
    @Override public void onStartButtonPressed() {

        Robot.INSTANCE.odom.init(hardwareMap, telemetry);
        telemetry.addData("Odom", "Initialized");
        telemetry.update();
        // Create the mecanum driver.
        // robot.drivetrain.getMotors()
        // gives us the four drivetrain motors.
        // gamepadManager.gamepad1
        // gives the mecanum driver Gamepad 1,
        // which is controlled by the driver.
        double strafeSpeed = 0.5;
        double turnSpeed = 0.3;
        MecanumDriverControlled driverControlled =
                new MecanumDriverControlled(
                        Robot.INSTANCE.dt.getLeftFrontMotor(),
                        Robot.INSTANCE.dt.getRightFrontMotor(),
                        Robot.INSTANCE.dt.getLeftBackMotor(),
                        Robot.INSTANCE.dt.getRightBackMotor(),

                        Gamepads.gamepad1().leftStickY().negate().map(x -> x * strafeSpeed),
                        Gamepads.gamepad1().leftStickX().map(x -> x * strafeSpeed),
                        Gamepads.gamepad1().rightStickX().map(x -> x * turnSpeed)
                );

        driverControlled.schedule();

        Gamepads.gamepad1().a().whenTrue(
                new InstantCommand(Robot.INSTANCE.odom::resetPosition)
        );


    }




}
