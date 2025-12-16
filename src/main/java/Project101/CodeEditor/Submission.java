package Project101.CodeEditor;

public class Submission {
    private final int id;
    private final String problemId; // e.g., "two-sum"
    private final String codeSnippet;
    private final Language language;

    public Submission(int id, String problemId, String codeSnippet, Language language) {
        this.id = id;
        this.problemId = problemId;
        this.codeSnippet = codeSnippet;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public String getProblemId() {
        return problemId;
    }

    public String getCodeSnippet() {
        return codeSnippet;
    }

    public Language getLanguage() {
        return language;
    }
}
