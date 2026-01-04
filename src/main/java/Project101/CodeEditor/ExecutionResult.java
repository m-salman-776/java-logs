package Project101.CodeEditor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExecutionResult {
    int submissionId;
    String stdout;
    String stderr;
    long timeTaken;
    long memoryUsed;
}
