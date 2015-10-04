package jpac.express.client.service;

import jpac.express.domain.Account;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

	void doLogin(Account account, AsyncCallback<Boolean> callback);

	void doLogout(AsyncCallback<Boolean> callback);

	void isLogin(AsyncCallback<Boolean> callback);

}