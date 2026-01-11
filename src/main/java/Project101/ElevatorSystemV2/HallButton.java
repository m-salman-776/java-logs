package Project101.ElevatorSystemV2;

public class HallButton implements Button{
    boolean pressed;
    public HallButton(){
        this.pressed = false;
    }

    @Override
    public void pressButton() {
        this.pressed = true;
    }

    @Override
    public void reset() {
        this.pressed = false;
    }

    @Override
    public boolean isPressed() {
        return this.pressed;
    }
}
