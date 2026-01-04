package Project101.CodeEditor.ExecutionStrategies;

import Project101.CodeEditor.ExecutionResult;
import Project101.CodeEditor.Submission;

public interface ExecutionStrategy {
    ExecutionResult execute(Submission submission, Sandbox sandbox);
}
