package jpac.express.client.datasource;

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceSequenceField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class AccountDataSource extends DataSource {

	private static final String name = "account";

	private static AccountDataSource instance = null;

	public static AccountDataSource getInstance() {
		if (instance == null) {
			instance = new AccountDataSource();
		}
		return instance;
	}

	public AccountDataSource() {
		super();
		setID(name);
		DataSourceSequenceField accountID = new DataSourceSequenceField("accountID");
		accountID.setPrimaryKey(true);
		accountID.setHidden(true);

		DataSourceIntegerField accountRole = new DataSourceIntegerField("accountRole", "ประเภท");
		Map<Integer, String> roleMap = new HashMap<Integer, String>();
		roleMap.put(1, "ผู้ดูแลระบบ");
		roleMap.put(2, "ผู้บริหาร");
		roleMap.put(3, "ลูกค้า");
		roleMap.put(4, "พนักงาน");
		accountRole.setValueMap(roleMap);

		DataSourceTextField accountUsername = new DataSourceTextField("accountUsername", "ชื่อผู้ใช้");
		DataSourceTextField accountPassword = new DataSourceTextField("accountPassword", "รหัสผ่าน");

		DataSourceField accountDetails = new DataSourceField();
		accountDetails.setName("accountDetails");
		accountDetails.setHidden(true);
		accountDetails.setMultiple(false);
		accountDetails.setTypeAsDataSource(AccountDetailsDataSource.getInstance());

		DataSourceField contact = new DataSourceField();
		contact.setName("contact");
		contact.setHidden(true);
		contact.setMultiple(true);
		contact.setTypeAsDataSource(ContactDataSource.getInstance());

		setFields(accountID, accountRole, accountUsername, accountPassword, accountDetails, contact);
		// setDataURL("ds/test_data/account.data.xml");
		// setClientOnly(true);
	}
}
