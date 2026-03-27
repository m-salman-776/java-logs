package Project101.LLMRouterV2;

public class LlmResponse {
    private final String content;
    private final boolean isSuccess;

    public LlmResponse(String content, boolean isSuccess) {
        this.content = content;
        this.isSuccess = isSuccess;
    }

    public String getContent() {
        return content;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    @Override
    public String toString() {
        return "LlmResponse{" +
                "content='" + content + '\'' +
                ", isSuccess=" + isSuccess +
                '}';
    }
}
