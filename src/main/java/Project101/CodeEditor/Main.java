package Project101.CodeEditor;

import Project101.CodeEditor.ExecutionStrategies.ExecutionStrategy;
import Project101.CodeEditor.ExecutionStrategies.JavaExecutionStrategy;
import Project101.CodeEditor.Services.CodeExecutionService;

public class Main {
    public static void main(String[]args){
        ExecutionStrategy executionStrategy = new JavaExecutionStrategy();
        CodeExecutionService codeExecutionService = new CodeExecutionService(executionStrategy);
        Submission submission = new Submission(1,"01","print hell",Language.JAVA);
        ExecutionResult executionResult = codeExecutionService.processCode(submission);
        System.out.println("done");
    }
}
