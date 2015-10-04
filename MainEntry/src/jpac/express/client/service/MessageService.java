package jpac.express.client.service;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("messageservice")
public interface MessageService extends RemoteService {

	String getChannel();

	boolean sendMessageToAllClient(String message);

	boolean addClientToList(String clientId);

	String getInformation(String constant);

	Integer getUserCount();
	
}