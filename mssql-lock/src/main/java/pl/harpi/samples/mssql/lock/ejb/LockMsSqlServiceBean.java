package pl.harpi.samples.mssql.lock.ejb;

import org.hibernate.jpa.spi.StoredProcedureQueryParameterRegistration;
import pl.harpi.samples.mssql.lock.api.LockDbService;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

@Local
@Stateless
public class LockMsSqlServiceBean implements LockDbService {
    @PersistenceContext
    private EntityManager entityManager;

    public void unlock(final String resource) {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("sp_app_unlock");

        query.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);

        ((StoredProcedureQueryParameterRegistration) query.getParameter(3)).enablePassingNulls(true);

        query.setParameter(1, resource);
        query.setParameter(2, "Session");
        query.setParameter(3, "public");

        query.execute();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Integer tryLock(String resource, Integer timeout) {
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
        query.setParameter(5, "public");

        query.execute();

        return (Integer) query.getOutputParameterValue(6);
    }
}
