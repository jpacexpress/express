package jpac.express.client.service;

import jpac.express.domain.Account;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("loginservice")
public interface LoginService extends RemoteService {

	boolean doLogin(Account account);

	boolean doLogout();

	boolean isLogin();

}
