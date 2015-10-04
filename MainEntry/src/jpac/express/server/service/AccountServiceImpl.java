package jpac.express.server.service;

import org.hibernate.Session;

import jpac.express.client.service.AccountService;
import jpac.express.domain.Account;
import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.core.hibernate.HibernateUtil;
import net.sf.gilead.core.store.stateless.StatelessProxyStore;
import net.sf.gilead.gwt.PersistentRemoteService;

public class AccountServiceImpl extends PersistentRemoteService implements AccountService {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8199299160639194248L;
	private HibernateUtil gileadHibernateUtil = new HibernateUtil();

	public AccountServiceImpl() {
		gileadHibernateUtil.setSessionFactory(jpac.express.util.HibernateUtil.getSessionFactory());

		PersistentBeanManager persistentBeanManager = new PersistentBeanManager();
		persistentBeanManager.setPersistenceUtil(gileadHibernateUtil);
		persistentBeanManager.setProxyStore(new StatelessProxyStore());

		setBeanManager(persistentBeanManager);
	}

	@Override
	public Account doRegister(Account account) {
		Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();

		session.beginTransaction();
		session.save(account);
		session.getTransaction().commit();

		return account;
	}

}
