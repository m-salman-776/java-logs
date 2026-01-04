package Project101.CarRental.Ispectiion;

import java.util.ArrayList;
import java.util.List;

public class InspectionSession {
    int id;
    String vin;
    InspectionStatus status = InspectionStatus.DRAFT;
    List<InspectionResult> inspectionResultList = new ArrayList<>();
    int finalScore = 100;
    String grade = "PENDING";
    public InspectionSession(int id,String vin){
        this.id = id;
        this.vin = vin;
    }
}
