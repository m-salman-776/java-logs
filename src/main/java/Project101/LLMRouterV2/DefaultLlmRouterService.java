package Project101.LLMRouterV2;

import Project101.LLMRouterV2.LLMClients.LlmClient;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class DefaultLlmRouterService implements LlmRouterService {

    private enum State {
        CLAUDE_ONLY,
        GPT_PREFERRED,
        FAIL_FAST_MODE
    }

    private final int consecutiveFailuresThreshold;
    private final int consecutiveSuccessesThreshold;
    private final Map<LlmProvider, LlmClient> clients;

    private final AtomicReference<State> currentState = new AtomicReference<>(State.CLAUDE_ONLY);
    private final AtomicInteger consecutiveFailures = new AtomicInteger(0);
    private final AtomicInteger consecutiveSuccesses = new AtomicInteger(0);

    public DefaultLlmRouterService(int consecutiveFailuresThreshold, int consecutiveSuccessesThreshold, Map<LlmProvider, LlmClient> clients) {
        this.consecutiveFailuresThreshold = consecutiveFailuresThreshold;
        this.consecutiveSuccessesThreshold = consecutiveSuccessesThreshold;
        this.clients = clients;
    }

    @Override
    public LlmResponse execute(String request) {
        LlmProvider provider = getRoute();
        
        if (provider == LlmProvider.FAIL_FAST) {
            return new LlmResponse("Request failed fast as per routing policy.", false);
        }

        LlmClient client = clients.get(provider);
        if (client == null) {
            return new LlmResponse("No client configured for provider: " + provider, false);
        }

        LlmResponse response = client.execute(request);
        reportResult(provider, response.isSuccess());
        return response;
    }

    private LlmProvider getRoute() {
        State state = currentState.get();
        switch (state) {
            case CLAUDE_ONLY:
                return LlmProvider.CLAUDE;
            case GPT_PREFERRED:
                return ThreadLocalRandom.current().nextInt(100) < 95 ? LlmProvider.GPT : LlmProvider.CLAUDE;
            case FAIL_FAST_MODE:
                int roll = ThreadLocalRandom.current().nextInt(100);
                if (roll < 90) return LlmProvider.FAIL_FAST;
                if (roll < 95) return LlmProvider.GPT;
                return LlmProvider.CLAUDE;
            default:
                return LlmProvider.CLAUDE;
        }
    }

    private synchronized void reportResult(LlmProvider provider, boolean isSuccess) {
        if (isSuccess) {
            handleSuccess(provider);
        } else {
            handleFailure(provider);
        }
    }

    private void handleSuccess(LlmProvider provider) {
        consecutiveFailures.set(0);
        int currentSuccesses = consecutiveSuccesses.incrementAndGet();

        if (currentSuccesses >= consecutiveSuccessesThreshold) {
            if (currentState.get() == State.GPT_PREFERRED && provider == LlmProvider.CLAUDE) {
                System.out.println("State change: GPT_PREFERRED -> CLAUDE_ONLY");
                currentState.set(State.CLAUDE_ONLY);
                consecutiveSuccesses.set(0);
            } else if (currentState.get() == State.FAIL_FAST_MODE) {
                System.out.println("State change: FAIL_FAST_MODE -> GPT_PREFERRED");
                currentState.set(State.GPT_PREFERRED);
                consecutiveSuccesses.set(0);
            }
        }
    }

    private void handleFailure(LlmProvider provider) {
        consecutiveSuccesses.set(0);
        int currentFailures = consecutiveFailures.incrementAndGet();

        if (currentFailures >= consecutiveFailuresThreshold) {
            if (currentState.get() == State.CLAUDE_ONLY && provider == LlmProvider.CLAUDE) {
                System.out.println("State change: CLAUDE_ONLY -> GPT_PREFERRED");
                currentState.set(State.GPT_PREFERRED);
                consecutiveFailures.set(0);
            } else if (currentState.get() == State.GPT_PREFERRED && provider == LlmProvider.GPT) {
                System.out.println("State change: GPT_PREFERRED -> FAIL_FAST_MODE");
                currentState.set(State.FAIL_FAST_MODE);
                consecutiveFailures.set(0);
            }
        }
    }
}
