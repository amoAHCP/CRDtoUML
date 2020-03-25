package org.jacpfx.deployment.process;

public class ProcessResult {
    private final int status;
    private final String result;

    public ProcessResult(int status, String result) {
        this.status = status;
        this.result = result;
    }

    public int getStatus() {
        return status;
    }

    public String getResult() {
        return result;
    }
}
