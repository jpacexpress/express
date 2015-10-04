package jpac.express.client.datasource;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceSequenceField;
import com.smartgwt.client.data.fields.DataSourceTextField;

public class ContactDataSource extends DataSource {

	private static final String name = "contact";

	private static ContactDataSource instance = null;

	public static ContactDataSource getInstance() {
		if (instance == null) {
			instance = new ContactDataSource();
		}
		return instance;
	}

	public ContactDataSource() {
		super();
		setID(name);

		DataSourceSequenceField contactID = new DataSourceSequenceField("contactID");
		contactID.setPrimaryKey(true);
		contactID.setHidden(true);
		DataSourceTextField contactAddress = new DataSourceTextField("contactAddress", "ที่อยู่");
		DataSourceTextField contactEmail = new DataSourceTextField("contactEmail", "ที่อยู่อีเมล");
		DataSourceTextField contactTel = new DataSourceTextField("contactTel", "เบอร์โทรศัพท์");

		setFields(contactID, contactAddress, contactEmail, contactTel);
	}

}
