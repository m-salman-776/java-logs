package Project101.CodeEditor.ExecutionStrategies;

import Project101.CodeEditor.ExecutionResult;

public class DockerSandbox implements Sandbox {
    @Override
    public ExecutionResult run(String[] command, String code, long maxTime, long maxMemory) {
        try {
            Thread.sleep(500);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
        return new ExecutionResult(1,"hello world","",12,12);
    }
}
