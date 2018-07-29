package pl.harpi.samples.mssql.lock.api;

public interface LockDbService {
    Integer tryLock(String resource, Integer timeout);

    void unlock(final String resource);
}
