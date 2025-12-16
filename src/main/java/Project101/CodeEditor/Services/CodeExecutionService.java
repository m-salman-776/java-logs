package Project101.CodeEditor.Services;

import Project101.CodeEditor.ExecutionResult;
import Project101.CodeEditor.ExecutionStrategies.DockerSandbox;
import Project101.CodeEditor.ExecutionStrategies.ExecutionStrategy;
import Project101.CodeEditor.ExecutionStrategies.Sandbox;
import Project101.CodeEditor.Submission;

public class CodeExecutionService {
    ExecutionStrategy executionStrategy ;
    Sandbox sandbox = new DockerSandbox();
    public CodeExecutionService(ExecutionStrategy executionStrategy){
        this.executionStrategy = executionStrategy;
    }
    public ExecutionResult processCode(Submission submission){
        return executionStrategy.execute(submission,sandbox);
    }
}
