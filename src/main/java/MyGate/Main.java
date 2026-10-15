package MyGate;
/*
* Design a Visitor Management System (VMS) for a gated society/apartment community.
* The system is used primarily by security guards at the gate and residents inside the society to
* manage and control visitor entry.

1. A security guard should be able to record the arrival of a person at the gate for a
   specific flat/resident, along with the purpose.
2. The resident should be able to respond to the request by choosing one of:
   * allow entry
   * deny entry
   * choices can be extended in future ()
3. The system should present the resident only the choices that are valid for that arrival type.
4. the guard should be able to mark the person as entered.
5. the guard should be able to mark exit when the person leaves.
6. The system must prevent invalid operations (e.g., cannot mark entry if resident denied;
   cannot mark exit before entry).
*
*
*
*
* GuestType :
*   - VISITOR
*   - DELIVERY
*
* OPTIONS :
*   ALLOW_ENTRY
*   DENY_ENTRY
*   LEAVE_WITH_SECURITY
*
* ALLOWED-OPS ; []
*
* Request
*   - TYPE
*
* */


import java.util.HashMap;
import java.util.Map;

public class Main {
    static void test1(){
        VisitorManagementSystem vms = new VisitorManagementSystem();
        Request request = vms.createVisitRequest("Salman to want chill at your appartment ",12,GuestType.VISITOR);
        int requestId = request.id;
        vms.actOnRequest(requestId,Options.ALLOW);
        vms.closeVisitRequest(requestId);
    }
    public static void main(String[] args) {
        test1();
        Map<Character,Integer> count = new HashMap<>();
        char ch = 'a';
        count.compute(ch,(k,v) -> v  == null ? 1 : v + 1);
        count.merge(ch,1,Integer::sum);

        System.out.println("CHE");
    }
}
