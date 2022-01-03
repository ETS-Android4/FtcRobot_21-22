package org.firstinspires.ftc.teamcode.BEAST_2021;

import android.hardware.Sensor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.checkerframework.checker.units.qual.degrees;

public class Pushbot2021
{

    /* Public OpMode members. */
    public DcMotor frontLeft, frontRight, backRight, backLeft;
    public DcMotor slide, turret, duckSpinner;
    public Servo grabber, linkage, pivot;
    public TouchSensor slideSensor;
    double spinDiameter = 1;
    double diameter = 3.6;
    double circumference = diameter * 3.14;
    int tetrixEncoderTics = 1440;
    //int andyMark40Tics = 515;
    int andyMark40Tics = 1120;
    double andyMark20Tics = 537.6;
    public static final String VUFORIA_KEY =
            "Afctxlz/////AAABmSWf4jOsTUHcsOYa/JfaZlRo+3yiPN8cCUH4BDLpIZ8FAt0tEVLJ/mxWUyd7f0gqd+a7JRTMYP9+A9s1nojOs9B1ZGOFsvr84RZnbVN8cGP7RFKNP4Mg0Pr/6vIUmHGFx/jrOrXz/YJXwVXvPpqr1uDm8xpBZOE4j+CtQcKW2Y2zjVWHWRTkmb6ve/R91k3jfjaH4PErbZMcvD7Xy5IesqSet3/pjeUXWSnlHmPwH7fgUcHSkAf0Fj2nLvZ7zmpT8vh9rSKri9XD3A64WBNRO+6+SGH/C/eS3mWLmdi5ZMbSK66WuvNhAPT0SHCzzqAlAf2P6asrrrAuw+aQ0B2HV0mPtGdNPe62djhu5Afa/rL+";

    double ticToDegree = (andyMark20Tics*(20.0/7.0))/360;
    double ticToInch = (andyMark20Tics/(spinDiameter*3.14));

    boolean turretMove = false, slideMove = false;
    double targetDeg = 0, targetHeight = 0;

    /* local OpMode members. */

    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        frontLeft  = hwMap.get(DcMotor.class, "frontLeft");
        frontRight = hwMap.get(DcMotor.class, "frontRight");
        backLeft  = hwMap.get(DcMotor.class, "backLeft");
        backRight = hwMap.get(DcMotor.class, "backRight");
        slide = hwMap.get(DcMotor.class, "slide");
        turret = hwMap.get(DcMotor.class, "turret");
        duckSpinner = hwMap.get(DcMotor.class, "duckSpinner");

        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        turret.setDirection(DcMotorSimple.Direction.FORWARD);
        slide.setDirection(DcMotorSimple.Direction.FORWARD);
        duckSpinner.setDirection(DcMotorSimple.Direction.FORWARD);

        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turret.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        // Set all motors to zero power
        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
        slide.setPower(0);
        turret.setPower(0);
        duckSpinner.setPower(0);



        //Define Servos
        grabber = hwMap.get(Servo.class, "grabber");
        linkage = hwMap.get(Servo.class, "linkage");
        pivot = hwMap.get(Servo.class, "pivot");



        //set servo positions to zero
        linkage.setPosition(.53);
        grabber.setPosition(0);
        pivot.setPosition(0);

        //set sensors
        slideSensor = hwMap.get(TouchSensor.class, "slideSensor");

/*
        //This section makes the motors drive slowly - Don't use BRAKE
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
*/
    }
    public void turn(double degrees, double power) {

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double turnCircumference = 14 * 3.14;
        double totalRotations = turnCircumference / 360 * degrees;
        int rotationDistanceofWheel = (int) (andyMark40Tics * totalRotations);

        /*frontLeft.setTargetPosition((int) (andyMark40Tics / 360 * degrees));
        frontRight.setTargetPosition((int) (-andyMark40Tics / 360 * degrees));
*/


        boolean runRobot = true;
        while (runRobot) {
            if (Math.abs(frontRight.getCurrentPosition()) > Math.abs(rotationDistanceofWheel) || Math.abs(frontLeft.getCurrentPosition()) > Math.abs(rotationDistanceofWheel)
                    || Math.abs(backLeft.getCurrentPosition()) > Math.abs(rotationDistanceofWheel) || Math.abs(backRight.getCurrentPosition()) > Math.abs(rotationDistanceofWheel)) {
                frontLeft.setPower(0);
                backLeft.setPower(0);
                frontRight.setPower(0);
                backRight.setPower(0);
                runRobot = false;
            } else {
                frontLeft.setPower(power);
                frontRight.setPower(-power);
                backLeft.setPower(power);
                backRight.setPower(power);
            }
        }

    }
    public void MoveForwardInch(double Distance, double power) {

        double totalRotations = Distance / circumference;
        int rotationDistanceofWheel = (int) (andyMark40Tics * totalRotations);

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while (true) {
            if (Math.abs(frontRight.getCurrentPosition()) > Math.abs(rotationDistanceofWheel) || Math.abs(frontLeft.getCurrentPosition()) > Math.abs(rotationDistanceofWheel)
                    || Math.abs(backLeft.getCurrentPosition()) > Math.abs(rotationDistanceofWheel) || Math.abs(backRight.getCurrentPosition()) > Math.abs(rotationDistanceofWheel)) {
                frontLeft.setPower(0);
                backLeft.setPower(0);
                frontRight.setPower(0);
                backRight.setPower(0);
                break;
            } else {
                if (Distance > 0) {
                    frontLeft.setPower(power);
                    frontRight.setPower(power);
                    backRight.setPower(-power);
                    backLeft.setPower(-power);
                } else if (Distance < 0) {
                    frontLeft.setPower(-power);
                    frontRight.setPower(-power);
                    backRight.setPower(power);
                    backLeft.setPower(power);
                }
            }
        }
    }
    public void depositCube(double height){
        //move linear slide to correct level
        moveSlide(14, 1);

        //move linkage out above shipping hub
        linkage.setPosition(.5);

        //release starting cube
        grabber.setPosition(0);

        //move linkage into robot
        linkage.setPosition(0);

        moveSlide(0, .7);
    }
    public void pickupCube(double degrees, double distance){
        double height = 8;
        double length = distance;
        double hypotenuse = Math.sqrt((height*height)+(length*length));
        double theta = Math.asin(length / hypotenuse);
        double angle = theta * (1 / 180);
        double linkageMove = hypotenuse * ((1 / 180) * 9);

        moveTurret(degrees, .5);

        grabber.setPosition(.5);

        linkage.setPosition(linkageMove);

        pivot.setPosition(angle);

        grabber.setPosition(0);

        pivot.setPosition(0);

        linkage.setPosition(0);

        moveTurret(0, .5);
    }

    public double angleWrap(double currentAngle) {
        while (currentAngle < 0) {
            currentAngle += 360;
        }
        while (currentAngle > 360) {
            currentAngle -= 360;
        }

        return currentAngle;
    }

    public void moveTurret(double degrees, double power) {
        //double motorTics = (ticToDegree * degrees);
        //double startingPosition = robot.turret.getCurrentPosition();

        double currentDegree = angleWrap(turret.getCurrentPosition() / ticToDegree);

        double outputPower;

        if (Math.abs(degrees - currentDegree) < 30.0) {
            outputPower = 0.05;
        } else {
            outputPower = power;
        }

        if (Math.abs(degrees - currentDegree) < 2.0){
            turret.setPower(0);
            turretMove = false;
        } else if (currentDegree < degrees) {
            turret.setPower(outputPower);
        } else if (currentDegree > degrees){
            turret.setPower(-outputPower);
        }
    }
    public void moveSlide (double height, double power){
        /*double slideCirc = spinDiameter*3.14;
        double Rotations = height/slideCirc;
        double inches = andyMark20Tics * Rotations;*/

        double currentHeight = (slide.getCurrentPosition() / ticToInch);

        double outputPower;

        if (Math.abs(height - currentHeight) < 60.0) {
            outputPower = 0.05;
        } else {
            outputPower = power;
        }
        if (Math.abs(height - currentHeight) < 2.0){
            slide.setPower(0);
        } else if (height > currentHeight){
            slide.setPower(outputPower);
        } else if (height < currentHeight){
            slide.setPower(-outputPower);
        }
    }
}