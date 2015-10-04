package jpac.express.server.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import jpac.express.client.service.MessageService;
import jpac.express.domain.Account;
import net.sf.gilead.core.PersistentBeanManager;
import net.sf.gilead.core.hibernate.HibernateUtil;
import net.sf.gilead.core.store.stateless.StatelessProxyStore;
import net.sf.gilead.gwt.PersistentRemoteService;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;

public class MessageServiceImpl extends PersistentRemoteService implements MessageService {
	
	private ChannelService channelService = ChannelServiceFactory.getChannelService();
	public Map<String,Integer> clientList;
	public static MessageServiceImpl instance;

	private static final long serialVersionUID = -1132490025459572761L;
	private HibernateUtil gileadHibernateUtil = new HibernateUtil();

	public MessageServiceImpl() {
		
		gileadHibernateUtil.setSessionFactory(jpac.express.util.HibernateUtil.getSessionFactory());

		PersistentBeanManager persistentBeanManager = new PersistentBeanManager();
		persistentBeanManager.setPersistenceUtil(gileadHibernateUtil);
		persistentBeanManager.setProxyStore(new StatelessProxyStore());

		setBeanManager(persistentBeanManager);
		clientList = new HashMap<String,Integer>();
		instance = this;
	}

	@Override
	public String getChannel() {
		HttpSession httpSession = this.getThreadLocalRequest().getSession(false);
		Account account = (Account)httpSession.getAttribute("account");
		return channelService.createChannel(account.getAccountUsername());
	}

	@Override
	public boolean sendMessageToAllClient(String message) {

		for(String token : clientList.keySet()) {
			channelService.sendMessage(new ChannelMessage(token,message));
		}

		return false;
	}

	@Override
	public boolean addClientToList(String clientId) {
		clientList.put(clientId,0);
		sendMessageToAllClient("USERSCOUNT");
		return false;
	}
	
	@Override
	public String getInformation(String constant) {
		
		String information = "@start<br/><br/>@token("+constant+")<br/><br/>@clientId<br/>";
		int count = 1;
		for(String token : clientList.keySet()) {
			information += "<br/>#"+count+" ("+token+")<br/>";
			count++;
		}
		information += "<br/>@end";
		return information;
	}
	
	@Override
	public Integer getUserCount() {
		return clientList.size();
	}
}