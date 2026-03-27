package Project101.LLMRouterV2.LLMClients;

import Project101.LLMRouterV2.LlmProvider;
import Project101.LLMRouterV2.LlmResponse;

import java.util.concurrent.atomic.AtomicInteger;

public class ClaudeLlmClient implements LlmClient {

    // Simulate Claude failing after a few successful calls
    private final AtomicInteger requestCount = new AtomicInteger(0);

    @Override
    public LlmResponse execute(String request) {
        System.out.println("  [Claude] Executing request...");
        // Simulate 3 successful calls, then start failing
        boolean success = requestCount.incrementAndGet() <= 3;
        if (success) {
            return new LlmResponse("Response from Claude", true);
        } else {
            return new LlmResponse("Error from Claude", false);
        }
    }

    @Override
    public LlmProvider getProviderType() {
        return LlmProvider.CLAUDE;
    }
}
