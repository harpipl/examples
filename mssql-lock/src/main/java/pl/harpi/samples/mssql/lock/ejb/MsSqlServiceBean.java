package pl.harpi.samples.mssql.lock.ejb;

import org.hibernate.jpa.spi.StoredProcedureQueryParameterRegistration;

import javax.ejb.LocalBean;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
@LocalBean
public class MsSqlServiceBean {
    @PersistenceContext
    private EntityManager entityManager;

    private void unlock(final String resource, final String lockOwner, final String dbPrincipal) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_app_unlock");

        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);

        ((StoredProcedureQueryParameterRegistration) query.getParameter(3)).enablePassingNulls(true);

        query.setParameter(1, resource);
        query.setParameter(2, lockOwner);
        query.setParameter(3, dbPrincipal);

        query.execute();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Integer tryLock(String resource, Integer timeout, Long workTime, String dbPrincipal) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_app_try_lock");

        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(4, Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(6, Integer.class, ParameterMode.OUT);

        ((StoredProcedureQueryParameterRegistration) query.getParameter(5)).enablePassingNulls(true);

        query.setParameter(1, resource);
        query.setParameter(2, "Exclusive");
        query.setParameter(3, "Session");
        query.setParameter(4, timeout);
        query.setParameter(5, dbPrincipal);

        query.execute();

        if (workTime > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(workTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Integer result = (Integer) query.getOutputParameterValue(6);

        if (result.equals(0) || result.equals(1)) {
            unlock(resource, "Session", dbPrincipal);
        }

        return result;
    }
}
