package Project101.ElevatorSystemV2;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[]args){
        // Configure the root logger to show FINE messages
        Logger rootLogger = Logger.getLogger("");
        rootLogger.setLevel(Level.FINE);
        for (Handler handler : rootLogger.getHandlers()) {
            if (handler instanceof ConsoleHandler) {
                handler.setLevel(Level.FINE);
            }
        }

        Logger logger = Logger.getLogger(Main.class.getName());
        ElevatorSystem elevatorSystem = new ElevatorSystem(new Building(10,3));
        elevatorSystem.addElevatorRequest(3,Direction.UP);
        elevatorSystem.addElevatorRequest(2,Direction.UP);
        
        // JUL uses {0} placeholders and requires .log() to pass parameters
        logger.log(Level.FINE, "fs {0}", "value");
    }
}
