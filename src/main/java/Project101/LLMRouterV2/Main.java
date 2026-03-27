package Project101.LLMRouterV2;

import Project101.LLMRouterV2.LLMClients.ClaudeLlmClient;
import Project101.LLMRouterV2.LLMClients.GptLlmClient;
import Project101.LLMRouterV2.LLMClients.LlmClient;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        // Create the LLM clients
        Map<LlmProvider, LlmClient> clients = new HashMap<>();
        clients.put(LlmProvider.CLAUDE, new ClaudeLlmClient());
        clients.put(LlmProvider.GPT, new GptLlmClient());

        // Create the router service with the clients
        DefaultLlmRouterService routerService = new DefaultLlmRouterService(3, 2, clients);

        System.out.println("--- Simulating API Calls through Router Proxy ---");

        // Simulate a series of calls to show normal operation
        for (int i = 1; i <= 10; i++) {
            System.out.println("\nExecuting request #" + i);
            LlmResponse response = routerService.execute("This is a test prompt.");
            System.out.println("Response received: " + response);
        }

        System.out.println("\n\n--- Validating Percentage Distribution ---");
        validatePercentageDistribution();

        System.out.println("\nDONE");
    }

    private static void validatePercentageDistribution() throws Exception {
        System.out.println("This test runs 10,000 simulations in each state to verify routing percentages.");
        Map<LlmProvider, LlmClient> clients = new HashMap<>();
        DefaultLlmRouterService routerForTest = new DefaultLlmRouterService(3, 2, clients);

        // Use reflection to access the private getRoute method
        Method getRouteMethod = DefaultLlmRouterService.class.getDeclaredMethod("getRoute");
        getRouteMethod.setAccessible(true);

        // --- Test 1: GPT_PREFERRED state (95% GPT, 5% Claude) ---
        // Force the state to GPT_PREFERRED by simulating 3 Claude failures
        forceStateChange(routerForTest, LlmProvider.CLAUDE, false, 3);

        Map<LlmProvider, Integer> gptPreferredCounts = runSimulation(routerForTest, getRouteMethod, 10000);
        System.out.println("\n[Test 1] State: GPT_PREFERRED");
        printDistribution(gptPreferredCounts);


        // --- Test 2: FAIL_FAST_MODE state (90% Fail, 5% GPT, 5% Claude) ---
        // Force the state to FAIL_FAST_MODE by simulating 3 GPT failures
        forceStateChange(routerForTest, LlmProvider.GPT, false, 3);

        Map<LlmProvider, Integer> failFastCounts = runSimulation(routerForTest, getRouteMethod, 10000);
        System.out.println("\n[Test 2] State: FAIL_FAST_MODE");
        printDistribution(failFastCounts);
    }

    private static void forceStateChange(DefaultLlmRouterService router, LlmProvider provider, boolean success, int times) throws Exception {
        Method reportResultMethod = DefaultLlmRouterService.class.getDeclaredMethod("reportResult", LlmProvider.class, boolean.class);
        reportResultMethod.setAccessible(true);
        for (int i = 0; i < times; i++) {
            reportResultMethod.invoke(router, provider, success);
        }
    }

    private static Map<LlmProvider, Integer> runSimulation(DefaultLlmRouterService router, Method getRouteMethod, int totalRequests) throws Exception {
        Map<LlmProvider, Integer> counts = new HashMap<>();
        for (int i = 0; i < totalRequests; i++) {
            LlmProvider provider = (LlmProvider) getRouteMethod.invoke(router);
            counts.put(provider, counts.getOrDefault(provider, 0) + 1);
        }
        return counts;
    }

    private static void printDistribution(Map<LlmProvider, Integer> counts) {
        int total = counts.values().stream().mapToInt(Integer::intValue).sum();
        System.out.printf("  Total requests: %d\n", total);
        counts.forEach((provider, count) -> {
            double percentage = (count * 100.0) / total;
            System.out.printf("  - %s: %d requests (%.2f%%)\n", provider, count, percentage);
        });
    }
}
