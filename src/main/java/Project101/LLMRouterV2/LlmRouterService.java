package Project101.LLMRouterV2;

public interface LlmRouterService {

    /**
     * Executes the request by routing it to the appropriate LLM provider
     * based on the current routing strategy.
     *
     * @param request The request payload (e.g., a prompt).
     * @return The {@link LlmResponse} from the chosen provider.
     */
    LlmResponse execute(String request);
}
