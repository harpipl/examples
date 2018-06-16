package pl.harpi.samples.redisson.lock.userexit;

public class UserExitRequest extends UserExitCommon {
    private String method;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
