package jpac.express.server.datasource;

/*
import java.util.List;

import jpac.express.domain.Units;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import net.sf.gilead.core.hibernate.HibernateUtil;
*/

import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DSResponse;
import com.isomorphic.hibernate.HibernateDataSource;


@SuppressWarnings("serial")
public final class UnitsList extends HibernateDataSource {

	//private HibernateUtil gileadHibernateUtil = new HibernateUtil();

    @Override
    public DSResponse executeAdd(DSRequest req) throws Exception {
        final DSResponse resp = super.executeAdd(req);

        return resp;
    }

    @Override
    public DSResponse executeRemove(DSRequest req) throws Exception {
        final DSResponse resp = super.executeRemove(req);

        return resp;
    }

    @Override
    public DSResponse executeUpdate(DSRequest req) throws Exception {
        final DSResponse resp = super.executeUpdate(req);

        return resp;
    }

    @Override
    public DSResponse executeFetch(DSRequest req) throws Exception {
    	/*
    	long startRow = (int) req.getStartRow();
        long endRow = (int) req.getEndRow();

    	gileadHibernateUtil.setSessionFactory(jpac.express.util.HibernateUtil.getSessionFactory());
    	Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
    	Transaction transaction = session.beginTransaction();
    	Criteria criteria = session.createCriteria(Units.class);
    	
    	String unitsId = (String) req.getFieldValue("unitsId");
        Criterion unitsIdRestriction = null;
        if (unitsId != null) {
        	unitsIdRestriction = Restrictions.like("unitsId", unitsId, MatchMode.ANYWHERE);
            criteria.add(unitsIdRestriction);
        }
    	
        // determine total available rows - we return this to the client so the ListGrid can
        // correctly size the scrollbar
        criteria.setProjection(Projections.rowCount());
        Object rowCount = criteria.uniqueResult();
        long totalRows = 0;
        // Later versions of Hibernate return a Long rather than an Integer here, for all
        // those occasions when a fetch returns more than 2.1 billion rows...
        if (rowCount instanceof Integer) {
            totalRows = ((Integer)rowCount).intValue();
        } else if (rowCount instanceof Long) {
            totalRows = ((Long)rowCount).longValue();
        }

        // clamp endRow to available rows and slice out requested range
        endRow = Math.min(endRow, totalRows);

        // rebuilt the criteria minus the rowCount projection
        criteria = session.createCriteria(Units.class);
        if (unitsId != null) criteria.add(unitsIdRestriction);

        // limit number of rows returned to just what the ListGrid asked for
        criteria.setFirstResult((int) startRow);
        criteria.setMaxResults((int)(endRow - startRow));
        List matchingUnits = criteria.list();

        DSResponse resp = new DSResponse();
        // DataSource protocol: return matching item beans
        resp.setData(matchingUnits);
        // tell client what rows are being returned, and what's available
        resp.setStartRow(startRow);
        resp.setEndRow(endRow);
        resp.setTotalRows(totalRows);
        
        //
        //transaction.commit();
        //session.close();
         */
    	final DSResponse resp = super.executeFetch(req);
        return resp;
    }
}
