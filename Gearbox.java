package org.firstinspires.ftc.robotcontroller.external.samples;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Demonstrates empty OpMode
 */
@TeleOp(name = "Gearbox", group = "Concept")
public class Gearbox extends OpMode {

  private ElapsedTime runtime = new ElapsedTime();
  private DcMotor leftFront;
  private DcMotor leftBack;
  private DcMotor rightFront;
  private DcMotor rightBack;

  @Override
  public void init() {
    leftFront = hardwareMap.get(DcMotor.class, "leftFront");
    leftBack = hardwareMap.get(DcMotor.class, "leftBack");
    rightFront = hardwareMap.get(DcMotor.class, "rightFront");
    rightBack = hardwareMap.get(DcMotor.class, "rightBack");
  }
    @Override
    public void init_loop () {
    }

    @Override
    public void start () {
      runtime.reset();
    }

    @Override
    public void loop () {
      leftFront.setPower(gamepad1.left_stick_y);
      rightFront.setPower(gamepad1.left_stick_y);
      leftBack.setPower(gamepad1.left_stick_y);
      rightBack.setPower(gamepad1.left_stick_y);
    }
  }


