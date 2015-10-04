package jpac.express.client.service;

import jpac.express.domain.Account;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface AccountServiceAsync {

	void doRegister(Account account, AsyncCallback<Account> callback);
}
