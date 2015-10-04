package jpac.express.client.ui;

import jpac.express.client.ui.custom.CenteredLayout;

import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormLayoutType;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;

public class AccountManagement extends CenteredLayout {

	private ListGrid accountList;
	private ListGrid contactList;
	private DynamicForm accountDetailsForm;

	private HLayout accountLayout;
	private HLayout contactLayout;
	private HLayout accountDetailsLayout;

	private HLayout addNewContactLayout;
	private HLayout addNewContactInnerLayout;
	private HLayout addNewAccountLayout;
	private HLayout addNewAccountInnerLayout;
	private HLayout saveAccountDetailsLayout;
	private HLayout accountDetailsInnerLayout;

	private IButton addNewAccountBtn;
	private IButton addNewContactBtn;

	public AccountManagement() {
		super(900);
		configWidgets();

		accountList.addSelectionChangedHandler(new SelectionChangedHandler() {
			@Override
			public void onSelectionChanged(SelectionEvent event) {

				if (event.getState()) {
					Criteria contactCriteria = new Criteria();
					String accountID = event.getSelectedRecord().getAttributeAsString("accountID");
					contactCriteria.addCriteria("accountID", accountID);
					contactList.fetchData(contactCriteria);

					Criteria accountDetailsCriteria = new Criteria();
					accountDetailsCriteria.addCriteria("accountID", event.getSelectedRecord().getAttribute("accountID"));
					accountDetailsForm.fetchData(accountDetailsCriteria);
				}

			}
		});
	}

	private void configWidgets() {

		setItemWidth(800);

		accountLayout = new HLayout();
		accountLayout.setHeight(150);
		contactLayout = new HLayout();
		contactLayout.setHeight(150);
		accountDetailsLayout = new HLayout();
		accountDetailsLayout.setHeight(150);

		accountList = new ListGrid();
		contactList = new ListGrid();
		accountDetailsForm = new DynamicForm();

		accountList.setIsGroup(true);
		accountList.setGroupTitle("ข้อมูลบัญชีผู้ใช้");
		accountList.setDataSource(DataSource.get("account"));
		accountList.setAutoFetchData(true);
		accountList.setCanRemoveRecords(false);

		contactList.setIsGroup(true);
		contactList.setGroupTitle("ข้อมูลติดต่อ");
		contactList.setDataSource(DataSource.get("contact"));
		contactList.setCanRemoveRecords(true);
		contactList.setAutoFetchData(false);
		contactList.setSaveLocally(false);
		contactList.setSaveByCell(true);

		accountDetailsForm.setDataSource(DataSource.get("accountDetails"));
		accountDetailsForm.setAutoFetchData(false);
		accountDetailsForm.setWidth100();
		accountDetailsForm.setTitleWidth(300);
		accountDetailsForm.setItemLayout(FormLayoutType.TABLE);
		accountDetailsForm.setNumCols(6);
		accountDetailsForm.setIsGroup(true);
		accountDetailsForm.setGroupTitle("รายละเอียดเพิ่มเติม");
		TextItem fristNameItem = new TextItem();
		fristNameItem.setName("accountFirstName");
		TextItem lastNameItem = new TextItem();
		lastNameItem.setName("accountLastName");
		DateTimeItem registeredDateItem = new DateTimeItem();
		registeredDateItem.setName("accountRegisteredDate");
		SelectItem titleNameItem = new SelectItem();
		titleNameItem.setName("accountTitleName");

		accountDetailsInnerLayout = new HLayout();
		accountDetailsInnerLayout.setBackgroundColor("grey");
		accountDetailsInnerLayout.setAlign(Alignment.RIGHT);
		accountDetailsInnerLayout.setHeight(10);
		saveAccountDetailsLayout = new HLayout();
		saveAccountDetailsLayout.setHeight(10);
		IButton accountDetailsSaveBtn = new IButton("บันทึกข้อมูล");
		accountDetailsSaveBtn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				accountDetailsForm.setValue("accountID", accountList.getSelectedRecord().getAttribute("accountID").toString());
				accountDetailsForm.saveData();
			}
		});
		accountDetailsForm.setFields(titleNameItem, fristNameItem, lastNameItem, registeredDateItem);

		addNewContactInnerLayout = new HLayout();
		addNewContactInnerLayout.setBackgroundColor("grey");
		addNewContactInnerLayout.setAlign(Alignment.RIGHT);
		addNewContactLayout = new HLayout();
		addNewContactLayout.setHeight(10);
		addNewContactBtn = new IButton("เพิ่มที่อยู่");
		addNewContactBtn.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				Record record = new Record();
				record.setAttribute("accountID", accountList.getSelectedRecord().getAttributeAsString("accountID"));
				contactList.startEditingNew(record);
			}
		});

		addNewAccountInnerLayout = new HLayout();
		addNewAccountInnerLayout.setBackgroundColor("grey");
		addNewAccountInnerLayout.setAlign(Alignment.RIGHT);
		addNewAccountLayout = new HLayout();
		addNewAccountLayout.setHeight(10);
		addNewAccountBtn = new IButton("เพิ่มบัญชี");
		addNewAccountBtn.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				accountList.startEditingNew();
			}
		});

		accountDetailsInnerLayout.addMember(accountDetailsSaveBtn);
		saveAccountDetailsLayout.addMember(accountDetailsInnerLayout);
		addNewAccountInnerLayout.addMember(addNewAccountBtn);
		addNewAccountLayout.addMember(addNewAccountInnerLayout);
		addNewContactInnerLayout.addMember(addNewContactBtn);
		addNewContactLayout.addMember(addNewContactInnerLayout);

		accountLayout.addMember(accountList);
		contactLayout.addMember(contactList);
		accountDetailsLayout.addMember(accountDetailsForm);

		add(accountLayout);
		add(addNewAccountLayout);
		add(contactLayout);
		add(addNewContactLayout);
		add(accountDetailsLayout);
		add(saveAccountDetailsLayout);
	}

}
