package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Robot;

import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;

@Autonomous(name = "auto1", group = "Test")
public class FirstAuto extends NextFTCOpMode {

    {
        addComponents(
                new SubsystemComponent(
                        Robot.INSTANCE.dt,
                        Robot.INSTANCE.odom
                )
        );
    }

    @Override
    public void onStartButtonPressed() {


        Robot.INSTANCE.odom.init(hardwareMap, telemetry);

        Robot.INSTANCE.odom.resetPosition();

        // Create the command
        LambdaCommand driveForward = new LambdaCommand("Drive Forward 200mm")

                // Runs repeatedly while the command is active
                .setUpdate(() -> {

                    double power = 0.3;

                    Robot.INSTANCE.dt.getLeftFrontMotor().setPower(power);
                    Robot.INSTANCE.dt.getRightFrontMotor().setPower(power);
                    Robot.INSTANCE.dt.getLeftBackMotor().setPower(power);
                    Robot.INSTANCE.dt.getRightBackMotor().setPower(power);
                })

                // Command finishes when Y reaches 200 mm
                .setIsDone(() ->
                        Robot.INSTANCE.odom
                                .getPinpoint()
                                .getPosX(DistanceUnit.MM) >= 200
                )

                // Stop all motors
                .setStop(interrupted -> {

                    Robot.INSTANCE.dt.getLeftFrontMotor().setPower(0);
                    Robot.INSTANCE.dt.getRightFrontMotor().setPower(0);
                    Robot.INSTANCE.dt.getLeftBackMotor().setPower(0);
                    Robot.INSTANCE.dt.getRightBackMotor().setPower(0);
                })

                // This command controls the drivetrain
                .requires(Robot.INSTANCE.dt);

        // Start the command
        driveForward.schedule();

        LambdaCommand driveBack = new LambdaCommand("Drive Backwards 200mm")

                // Runs repeatedly while the command is active
                .setUpdate(() -> {

                    double power = -0.3;

                    Robot.INSTANCE.dt.getLeftFrontMotor().setPower(power);
                    Robot.INSTANCE.dt.getRightFrontMotor().setPower(power);
                    Robot.INSTANCE.dt.getLeftBackMotor().setPower(power);
                    Robot.INSTANCE.dt.getRightBackMotor().setPower(power);
                })

                // Command finishes when Y reaches 200 mm
                .setIsDone(() ->
                        Robot.INSTANCE.odom
                                .getPinpoint()
                                .getPosX(DistanceUnit.MM) <= 0
                )

                // Stop all motors
                .setStop(interrupted -> {

                    Robot.INSTANCE.dt.getLeftFrontMotor().setPower(0);
                    Robot.INSTANCE.dt.getRightFrontMotor().setPower(0);
                    Robot.INSTANCE.dt.getLeftBackMotor().setPower(0);
                    Robot.INSTANCE.dt.getRightBackMotor().setPower(0);
                })

                // This command controls the drivetrain
                .requires(Robot.INSTANCE.dt);

        driveBack.schedule();
    }
}