package jpac.express.server.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import jpac.express.client.service.LoginService;
import jpac.express.domain.Account;
import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.core.hibernate.HibernateUtil;
import net.sf.gilead.core.store.stateless.StatelessProxyStore;
import net.sf.gilead.gwt.PersistentRemoteService;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class LoginServiceImpl extends PersistentRemoteService implements LoginService {

	private static final long serialVersionUID = -1132490025459572761L;
	private HibernateUtil gileadHibernateUtil = new HibernateUtil();

	public LoginServiceImpl() {
		gileadHibernateUtil.setSessionFactory(jpac.express.util.HibernateUtil.getSessionFactory());

		PersistentBeanManager persistentBeanManager = new PersistentBeanManager();
		persistentBeanManager.setPersistenceUtil(gileadHibernateUtil);
		persistentBeanManager.setProxyStore(new StatelessProxyStore());

		setBeanManager(persistentBeanManager);
	}

	@Override
	public boolean doLogin(Account account) {
		if (isLogin() == false) {
			if(account.getAccountUsername().equals("guest")) {
				HttpSession httpSession = this.getThreadLocalRequest().getSession(false);
				httpSession.setAttribute("account", account);
				return true;
			} else {
				Session session = gileadHibernateUtil.getSessionFactory().getCurrentSession();
				session.beginTransaction();
	
				Criteria criteria = session.createCriteria(Account.class);
				criteria.add(Restrictions.eq("accountUsername", account.getAccountUsername()));
				criteria.add(Restrictions.eq("accountPassword", account.getAccountPassword()));
	
				@SuppressWarnings("unchecked")
				List<Account> acc = criteria.list();
	
				HttpSession httpSession = this.getThreadLocalRequest().getSession(false);
				httpSession.setAttribute("account", account);
				
				if (acc.size() == 1) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean doLogout() {
		HttpSession httpSession = this.getThreadLocalRequest().getSession(false);
		httpSession.invalidate();
		return true;
	}

	@Override
	public boolean isLogin() {
		HttpSession httpSession = this.getThreadLocalRequest().getSession(false);
		if (httpSession.getAttribute("account") == null) {
			return false;
		} else {
			return true;
		}
	}

}