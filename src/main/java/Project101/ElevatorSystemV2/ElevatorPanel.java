package Project101.ElevatorSystemV2;

import java.util.ArrayList;
import java.util.List;

public class ElevatorPanel {
    List<ElevatorButton> elevatorButtons = new ArrayList<>();
    OpenButton openButton = new OpenButton();
    CloseButton closeButton = new CloseButton();
    public ElevatorPanel(int buttonCount){
        for (int i=1 ;i <= buttonCount;i++){
            elevatorButtons.add(new ElevatorButton(i));
        }
    }
}
