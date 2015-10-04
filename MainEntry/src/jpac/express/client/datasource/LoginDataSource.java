package jpac.express.client.datasource;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class LoginDataSource extends DataSource {

	private static final String name = "login";

	private static LoginDataSource instance = null;

	public static LoginDataSource getInstance() {
		if (instance == null) {
			instance = new LoginDataSource();
		}
		return instance;
	}

	public LoginDataSource() {
		super();
		setID(name);

		DataSourceTextField accountUsername = new DataSourceTextField("accountUsername", "ชื่อผู้ใช้");
		DataSourceTextField accountPassword = new DataSourceTextField("accountPassword", "รหัสผ่าน");
		setFields(accountUsername, accountPassword);
	}

}
