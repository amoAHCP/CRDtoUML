package org.jacpfx.process;

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


    @Override
    public String toString() {
        return "ProcessResult{" +
                "status=" + status +
                ", result='" + result + '\'' +
                '}';
    }
}
