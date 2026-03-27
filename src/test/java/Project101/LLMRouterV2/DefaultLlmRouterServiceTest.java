package Project101.LLMRouterV2;

import Project101.LLMRouterV2.LLMClients.LlmClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class DefaultLlmRouterServiceTest {

    private LlmClient mockClaudeClient;
    private LlmClient mockGptClient;
    private DefaultLlmRouterService routerService;

    private final int FAILURE_THRESHOLD = 3;
    private final int SUCCESS_THRESHOLD = 2;

    @BeforeEach
    void setUp() {
        mockClaudeClient = Mockito.mock(LlmClient.class);
        mockGptClient = Mockito.mock(LlmClient.class);

        when(mockClaudeClient.getProviderType()).thenReturn(LlmProvider.CLAUDE);
        when(mockGptClient.getProviderType()).thenReturn(LlmProvider.GPT);

        Map<LlmProvider, LlmClient> clients = new HashMap<>();
        clients.put(LlmProvider.CLAUDE, mockClaudeClient);
        clients.put(LlmProvider.GPT, mockGptClient);

        routerService = new DefaultLlmRouterService(FAILURE_THRESHOLD, SUCCESS_THRESHOLD, clients);
    }

    @Test
    void initialState_shouldRouteToClaude() {
        when(mockClaudeClient.execute(Mockito.anyString())).thenReturn(new LlmResponse("Claude success", true));

        LlmResponse response = routerService.execute("test");

        assertEquals("Claude success", response.getContent());
        Mockito.verify(mockClaudeClient, Mockito.times(1)).execute(Mockito.anyString());
        Mockito.verify(mockGptClient, Mockito.never()).execute(Mockito.anyString());
    }

    @Test
    void givenClaudeOnlyState_whenClaudeFailsThresholdTimes_shouldSwitchToGptPreferred() throws Exception {
        // Report 3 failures for Claude to trigger the state change
        reportResult(LlmProvider.CLAUDE, false, FAILURE_THRESHOLD);

        // In the new state, we expect GPT to be called (with high probability).
        // To make the test deterministic, we check the internal state.
        LlmProvider provider = getRoute();
        assertEquals(LlmProvider.GPT, provider);
    }

    @Test
    void givenGptPreferredState_whenGptFailsThresholdTimes_shouldSwitchToFailFastMode() throws Exception {
        // First, switch to GPT_PREFERRED state
        reportResult(LlmProvider.CLAUDE, false, FAILURE_THRESHOLD);

        // Now, fail GPT 3 times to trigger FAIL_FAST_MODE
        reportResult(LlmProvider.GPT, false, FAILURE_THRESHOLD);

        // In the new state, we expect FAIL_FAST (with high probability).
        LlmProvider provider = getRoute();
        assertEquals(LlmProvider.FAIL_FAST, provider);
    }


    @Test
    void givenFailFastMode_whenSuccessThresholdMet_shouldSwitchToGptPreferred() throws Exception {
        // Switch to GPT_PREFERRED
        reportResult(LlmProvider.CLAUDE, false, FAILURE_THRESHOLD);
        // Switch to FAIL_FAST_MODE
        reportResult(LlmProvider.GPT, false, FAILURE_THRESHOLD);

        // Now, report 2 successes to recover to GPT_PREFERRED
        reportResult(LlmProvider.GPT, true, SUCCESS_THRESHOLD);

        // Check that the state has recovered to GPT_PREFERRED
        LlmProvider provider = getRoute();
        assertEquals(LlmProvider.GPT, provider);
    }

    @Test
    void givenGptPreferredState_whenClaudeSuccessThresholdMet_shouldSwitchToClaudeOnly() throws Exception {
        // Switch to GPT_PREFERRED
        reportResult(LlmProvider.CLAUDE, false, FAILURE_THRESHOLD);

        // Now, report 2 successes on Claude to recover
        reportResult(LlmProvider.CLAUDE, true, SUCCESS_THRESHOLD);

        // Check that the state has recovered to CLAUDE_ONLY
        LlmProvider provider = getRoute();
        assertEquals(LlmProvider.CLAUDE, provider);
    }

    @Test
    void aSingleSuccess_shouldResetFailureCounter() throws Exception {
        // Report 2 failures
        reportResult(LlmProvider.CLAUDE, false, 2);
        // Report 1 success, which should reset the counter
        reportResult(LlmProvider.CLAUDE, true, 1);
        // Report 1 more failure
        reportResult(LlmProvider.CLAUDE, false, 1);

        // The state should still be CLAUDE_ONLY because the failure counter was reset
        LlmProvider provider = getRoute();
        assertEquals(LlmProvider.CLAUDE, provider);
    }

    // Helper method to call the private reportResult method using reflection
    private void reportResult(LlmProvider provider, boolean success, int times) throws Exception {
        Method reportResultMethod = DefaultLlmRouterService.class.getDeclaredMethod("reportResult", LlmProvider.class, boolean.class);
        reportResultMethod.setAccessible(true);
        for (int i = 0; i < times; i++) {
            reportResultMethod.invoke(routerService, provider, success);
        }
    }

    // Helper method to get the current route using reflection to avoid randomness issues in tests
    private LlmProvider getRoute() throws Exception {
        Method getRouteMethod = DefaultLlmRouterService.class.getDeclaredMethod("getRoute");
        getRouteMethod.setAccessible(true);
        // To get a deterministic result for probabilistic states, we'll simulate a few runs
        // and check if the most common outcome is the expected one.
        Map<LlmProvider, Integer> counts = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            LlmProvider provider = (LlmProvider) getRouteMethod.invoke(routerService);
            counts.put(provider, counts.getOrDefault(provider, 0) + 1);
        }
        // Return the provider with the highest count
        return counts.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
    }
}
