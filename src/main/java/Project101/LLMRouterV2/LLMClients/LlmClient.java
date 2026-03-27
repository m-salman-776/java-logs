package Project101.LLMRouterV2.LLMClients;

import Project101.LLMRouterV2.LlmProvider;
import Project101.LLMRouterV2.LlmResponse;

public interface LlmClient {
    /**
     * Executes a request to the LLM.
     *
     * @param request The request payload (e.g., a prompt).
     * @return The {@link LlmResponse} from the provider.
     */
    LlmResponse execute(String request);

    /**
     * Gets the provider type for this client.
     *
     * @return The {@link LlmProvider}.
     */
    LlmProvider getProviderType();
}
