package MyGate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class VisitorManagementSystem {
    private final Map<Integer, Request> visitRequest;
    private final AtomicInteger requestIdGen;

    public VisitorManagementSystem() {
        this.requestIdGen = new AtomicInteger(1);
        this.visitRequest = new ConcurrentHashMap<>();
    }

    public Request createVisitRequest(String purpose, int flatId, GuestType guestType) {
        int requestId = requestIdGen.getAndIncrement();
        List<Options> options = getAllowedOptionForGuestType(guestType);
        Request request = new Request(requestId, purpose, options, flatId);
        this.visitRequest.put(requestId, request);
        System.out.println("Request with ID: " + requestId + " is created.");
        return request;
    }

    public void actOnRequest(int requestId, Options option) {
        Request request = this.visitRequest.get(requestId);
        if (request == null) {
            System.out.println("No such request available with ID: " + requestId);
            return;
        }

        synchronized (request) { // in case when multiple resident of same flat try to ACCEPT / REJECT
            if (request.status == Status.CLOSED) {
                System.out.println("Request " + requestId + " is already closed and cannot be acted upon.");
                return;
            }

            if (!request.allowedOptions.contains(option)) {
                System.out.println("Invalid option '" + option + "' for this request type.");
                return;
            }

            if (option == Options.DENY) {
                request.updateStatus(Status.REJECTED);
                System.out.println("Request with ID: " + requestId + " is REJECTED.");
            } else {
                request.updateStatus(Status.ACCEPTED);
                System.out.println("Request with ID: " + requestId + " is ACCEPTED.");
            }
            char []arr = new char[234];
            int val = Integer.valueOf(String.valueOf(arr));
            char []rev = String.valueOf(val).toCharArray();
            String[] str = new String[]{"abc","df","fsda"};
            List<Integer> ls = List.of(0,1,3);
            Collections.sort(ls,(a,b) -> Integer.compare(a,b));
            TreeMap<Integer,Boolean> map = new TreeMap<>();
            map.floorKey(1);
            TreeSet<Integer> st = new TreeSet<>();
            int ar[] = new int[]{1,24};
            Arrays.sort(ar);
        }
    }

    public void closeVisitRequest(int requestId) {
        Request request = this.visitRequest.get(requestId);
        if (request == null) {
            System.out.println("No such request available with ID: " + requestId);
            return;
        }

        synchronized (request) {
            if (request.status != Status.ACCEPTED) {
                System.out.println("Request " + requestId + " cannot be closed because it was not accepted.");
                return;
            }
            request.updateStatus(Status.CLOSED);
            System.out.println("Request with ID: " + requestId + " is closed.");
        }
    }

    private List<Options> getAllowedOptionForGuestType(GuestType guestType) {
        switch (guestType) {
            case DELIVERY:
                return List.of(Options.ALLOW, Options.DENY, Options.LEAVE_WITH_SECURITY);
            default:
                return List.of(Options.ALLOW, Options.DENY);
        }
    }
}
