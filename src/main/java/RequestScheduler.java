import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
@AllArgsConstructor
class Request{
    int time;
    String name;
    int source;
    String direction;
    int destination;
}

public class RequestScheduler {
    static int totalTime = 0;
    static List<Request> processRequest(List<Request> requests){
        List<Request> delayedRequest = new ArrayList<>();
        List<Request> outList = new ArrayList<>();
        requests.sort((a,b) -> Integer.compare(a.source,b.source));
        int requestCount = 0;
        for (Request request : requests){
            int currTime = request.source - 1 + requestCount;
            totalTime += request.source - 1 + requestCount;
            if (totalTime >= request.time) {
                requestCount += 1;
                System.out.println(request.source + " " + request.name + " IN ");
                outList.add(request);
            }else{
                delayedRequest.add(request);
            }
        }
//        totalTime += 19 + (requests.size() - delayedRequest.size());
        outList.sort((a,b) -> Integer.compare(a.destination,b.destination));
        for (Request request : outList){
            System.out.println(request.destination + " " + request.name + " OUT ");
        }
        return delayedRequest;
    }
    static List<Request> solve(List<Request> requestList){
        List<Request> remainingRequest = new ArrayList<>();

        List<Request> upRequest = new ArrayList<>();
        List<Request> downRequest = new ArrayList<>();
        for (Request request : requestList){
            if (request.direction.equals("UP")){
                upRequest.add(request);
            }else{
                downRequest.add(request);
            }
        }

        List<Request> delayedUp = processRequest(upRequest);
        List<Request> delayedDown = processRequest(downRequest);
        remainingRequest.addAll(delayedDown);
        remainingRequest.addAll(delayedUp);
        return remainingRequest;
    }
    public static void main(String[] args) {
        List<Request> requestsList = new ArrayList<>();
        // T0, A, 5, UP, 10
//        Request request = new Request(0,"A",5,"UP",10);

//        T0, A, 5, UP, 12
//        T1, B, 3, UP, 8

//        Request request1 = new Request(0,"A",5,"UP",12);
//        Request request2 = new Request(0,"B",3,"UP",8);

//        T0, A, 2, UP, 10
//        T5, B, 8, DOWN, 4
//        Request request1 = new Request(0,"A",2,"UP",10);
//        Request request2 = new Request(5,"B",8,"DOWN",4);


//        T0, A, 15, DOWN, 6
//        T2, B, 4, UP, 12

//        Request request1 = new Request(0,"A",15,"DOWN",6);
//        Request request2 = new Request(6,"B",4,"UP",12);



//        T0, A, 15, DOWN, 6
//        T6, B, 4, UP, 12
//        Request request1 = new Request(0,"A",15,"DOWN",6);
//        Request request2 = new Request(6,"B",4,"UP",12);


//        T0, A, 15, DOWN, 6
//        T2, B, 4, UP, 12



//        T0, A, 4, UP, 14
//        T3, B, 8, DOWN, 3
//        T7, C, 5, UP, 8
//        T8, D, 16, DOWN, 1

        Request request1 = new Request(0,"A",4,"UP",14);
        Request request2 = new Request(3,"B",8,"DOWN",3);
        Request request3 = new Request(7,"C",5,"UP",8);
        Request request4 = new Request(8,"D",16,"DOWN",1);
        requestsList.add(request1);
        requestsList.add(request2);
        requestsList.add(request3);
        requestsList.add(request4);
        Collections.reverse(requestsList);
        while (requestsList.size() > 0){
            requestsList =  solve(requestsList);
        }
    }
}
