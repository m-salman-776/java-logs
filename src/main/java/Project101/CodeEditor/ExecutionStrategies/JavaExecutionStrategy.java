package Project101.CodeEditor.ExecutionStrategies;

import Project101.CodeEditor.ExecutionResult;
import Project101.CodeEditor.Submission;

public class JavaExecutionStrategy implements ExecutionStrategy{
    @Override
    public ExecutionResult execute(Submission submission, Sandbox sandbox) {
        String []command = new String[]{""};
        return sandbox.run(command,submission.getCodeSnippet(),12,123);
    }
}
