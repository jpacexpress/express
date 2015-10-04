package jpac.express.client.service;

import jpac.express.domain.Account;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("accountservice")
public interface AccountService extends RemoteService {

	Account doRegister(Account account);

}
