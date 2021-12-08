public class PID_Test {
    
    int P, I, D = 1;
    int integral, previous_error, setpoint = 0;
    Gyro gyro;
    DifferentialDrive robotDrive;

    public PID_Test(Gyro gyro) {
        this.gyro = gyro;
    }

    public void setSetpoint(int setpoint) {
        this.setpoint = setpoint;
    }

    public void PID() {
        error = setpoint - gyro.getAngle();
        this.integral += (error * .02);
        derivative = (error - this.previous_error) / .02;
        this.rcw = P * error + I * this.integral + D * derivative;
    }

    public void execute() {
        PID();
        robotDrive.arcadeDrive(0, rcw);
    }

}
