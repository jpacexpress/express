package jpac.express.client.datasource;

import java.util.HashMap;
import java.util.Map;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class AccountDetailsDataSource extends DataSource {

	private static final String name = "accountDetails";

	private static AccountDetailsDataSource instance = null;

	public static AccountDetailsDataSource getInstance() {
		if (instance == null) {
			instance = new AccountDetailsDataSource();
		}
		return instance;
	}

	public AccountDetailsDataSource() {
		super();
		setID(name);

		DataSourceDateField accountRegisteredDate = new DataSourceDateField("accountRegisteredDate", "วันที่สมัครใช้งาน");
		DataSourceEnumField accountTitleName = new DataSourceEnumField("accountTitleName", "คำนำหน้าชื่อ");
		Map<Integer, String> titleMap = new HashMap<Integer, String>();
		titleMap.put(1, "นาย");
		titleMap.put(2, "นาง");
		titleMap.put(3, "นางสาว");
		accountTitleName.setValueMap(titleMap);
		DataSourceTextField accountFirstName = new DataSourceTextField("accountFirstName", "ชื่อจริง");
		DataSourceTextField accountLastName = new DataSourceTextField("accountLastName", "นามสกุล");

		setFields(accountRegisteredDate, accountTitleName, accountFirstName, accountLastName);
	}
}
