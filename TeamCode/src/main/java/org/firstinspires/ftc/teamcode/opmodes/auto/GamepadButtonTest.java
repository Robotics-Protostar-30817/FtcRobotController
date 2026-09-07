package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Gamepad Button Test",group="Test")
public class GamepadButtonTest extends OpMode {
    @Override
    public void init(){
        telemetry.addLine("Gamepad test ready");
    }

    @Override
    public void loop(){
        telemetry.addData("gamepad1 A",gamepad1.a);
        telemetry.addData("gamepad1 B",gamepad1.b);
        telemetry.addData("gamepad1 X", gamepad1.x);
        telemetry.addData("gamepad1 Y",gamepad1.y);

        telemetry.addData("gamepad2 A",gamepad2.a);
        telemetry.addData("gamepad2 B",gamepad2.b);
        telemetry.addData("gamepad2 X", gamepad2.x);
        telemetry.addData("gamepad2 Y",gamepad2.y);

        telemetry.update();
    }
}
