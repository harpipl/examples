package pl.harpi.samples.mssql.lock.ejb;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.concurrent.TimeUnit;

@Local
@Stateless
public class BusinessServiceBean {
    @Inject
    private LockMsSqlServiceBean lockService;

    public Integer doSomething(String resource, Integer timeout, Long workTime) {
        Integer result = lockService.tryLock(resource, timeout);

        try {
            TimeUnit.MILLISECONDS.sleep(workTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lockService.unlock(resource);

        return result;
    }
}
