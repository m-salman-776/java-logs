package Project101.LLMRouterV2.LLMClients;

import Project101.LLMRouterV2.LlmProvider;
import Project101.LLMRouterV2.LlmResponse;

import java.util.concurrent.atomic.AtomicInteger;

public class GptLlmClient implements LlmClient {

    // Simulate GPT being unstable
    private final AtomicInteger requestCount = new AtomicInteger(0);

    @Override
    public LlmResponse execute(String request) {
        System.out.println("  [GPT] Executing request...");
        // Simulate 1 successful call, then 3 failures, then repeat
        boolean success = (requestCount.incrementAndGet() % 4) == 1;
        if (success) {
            return new LlmResponse("Response from GPT", true);
        } else {
            return new LlmResponse("Error from GPT", false);
        }
    }

    @Override
    public LlmProvider getProviderType() {
        return LlmProvider.GPT;
    }
}
