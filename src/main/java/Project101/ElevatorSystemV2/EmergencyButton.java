package Project101.ElevatorSystemV2;

public class EmergencyButton implements Button{
    boolean pressed;
    public EmergencyButton(){
        this.pressed = false;
    }
    @Override
    public void pressButton() {
        this.pressed = true;
        System.out.println("Emergency Button Pressed");
    }

    @Override
    public void reset() {
        this.pressed = false;
        System.out.println("Emergency Resolve");
    }

    @Override
    public boolean isPressed() {
        return this.pressed;
    }
}
