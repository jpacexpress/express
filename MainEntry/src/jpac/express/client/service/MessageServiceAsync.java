package jpac.express.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MessageServiceAsync {

	void getChannel(AsyncCallback<String> callback);

	void sendMessageToAllClient(String message, AsyncCallback<Boolean> callback);

	void addClientToList(String clientId, AsyncCallback<Boolean> callback);

	void getInformation(String constant, AsyncCallback<String> callback);

	void getUserCount(AsyncCallback<Integer> callback);

}
