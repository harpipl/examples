package pl.harpi.samples.redisson.lock.userexit;

public interface UserExitHandler<REQUEST extends UserExitRequest, RESPONSE extends UserExitResponse> {
    RESPONSE invoke(REQUEST request);
}
