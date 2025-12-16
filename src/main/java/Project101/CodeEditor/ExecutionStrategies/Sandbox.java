package Project101.CodeEditor.ExecutionStrategies;

import Project101.CodeEditor.ExecutionResult;

public interface Sandbox {
    ExecutionResult run(String []command, String code, long maxTime, long maxMemory);
}
