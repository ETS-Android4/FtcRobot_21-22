package org.firstinspires.ftc.teamcode.BEAST_2021;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp

public class Drive2021 extends LinearOpMode {
    Pushbot2021 robot = new Pushbot2021();

    public void runOpMode() {
        robot.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            double drive = gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            if (drive != 0){
                robot.frontLeft.setPower(-drive);
                robot.frontRight.setPower(-drive);
                robot.backRight.setPower(drive);
                robot.backLeft.setPower(drive);
            }

            if (turn != 0){
                //Turn left or right
                robot.frontLeft.setPower(turn);
                robot.frontRight.setPower(-turn);
                robot.backLeft.setPower(-turn);
                robot.backRight.setPower(turn);
            }

            if (drive == 0 && turn == 0) {
                robot.frontLeft.setPower(0);
                robot.frontRight.setPower(0);
                robot.backRight.setPower(0);
                robot.backLeft.setPower(0);
            }


            robot.slide.setPower(gamepad2.left_stick_y / 4);

            if (gamepad1.a == true){
                //turn turret to right
                robot.turretMove = true;
                robot.targetDeg = 10.0;
            }
            if (gamepad1.b == true){
                //turn turret to left
                robot.turretMove = true;
                robot.targetDeg = 270.0;
            }
            if (gamepad1.a) {
                 robot.duckSpinner.setPower(.7);
            } else if (gamepad1.b){
                robot.duckSpinner.setPower(-.7);
            } else{
                robot.duckSpinner.setPower(0);
            }

            if (gamepad2.right_bumper){
                //turn turret to right
                robot.turret.setPower(-1);
            }

            if (gamepad2.left_bumper){
                //turn turret to left
                robot.turret.setPower(1);
            }

            if (gamepad2.left_trigger != 0){
                //close grabber
                robot.grabber.setPosition(1);
            } else if (gamepad2.right_trigger != 0) {
                //Open grabber
                robot.grabber.setPosition(0);
            } /*else {
                //Stop grabber
                robot.grabber.setPosition(.5);
            }*/

            if (gamepad2.y){
                //linkage open
                robot.linkage.setPosition(0);
            }

            if (gamepad2.x){
                //linkage closed - counter clockwise to 1
                robot.linkage.setPosition(.53);
            }

            if (gamepad2.a){
                robot.pivot.setPosition(0);
            }

            if (gamepad2.b){
                robot.pivot.setPosition(.2);
            }

            while(robot.slideSensor.isPressed()){
                robot.slide.setPower(Math.abs(gamepad2.left_stick_y));
            }
        }
    }
}