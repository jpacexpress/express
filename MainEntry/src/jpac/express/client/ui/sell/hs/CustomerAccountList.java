package jpac.express.client.ui.sell.hs;

import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class CustomerAccountList extends CustomWindow {

	private static CustomerAccountList instance = null;

	private static final String buttonSizeWidth = "85px";
	private static final String buttonSizeHeight = "42px";

	private AddCustomerAccount addCustomerAccount;

	private VLayout mainLayout;
	private HLayout gridLayout;
	private HLayout controlLayout;

	private static ListGrid customerAccountList;

	private IButton submit;
	private IButton cancel;
	private IButton search;
	private IButton sort;
	private IButton add;

	private static final String name = "";

	public CustomerAccountList() {
		super("CustomerAccountList",name, 600, 250, true);
		addCustomerAccount = new AddCustomerAccount();
		disableHeaderSection();
		disableControlSection();
		disableMaximizeButton();
		disableMinimizeButton();
		disableCloseButton();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public static CustomerAccountList getInstance() {
		if (instance == null) {
			instance = new CustomerAccountList();
		}
		return instance;
	}

	public void showCustomerAccountList() {
		centerInPage();
		show();
	}

	private void initWidgets() {
		mainLayout = new VLayout();
		gridLayout = new HLayout();
		gridLayout.setBackgroundColor("BurlyWood");
		gridLayout.setHeight100();
		gridLayout.setOverflow(Overflow.SCROLL);
		controlLayout = new HLayout();
		controlLayout.setMargin(10);
		controlLayout.setHeight(buttonSizeHeight);
		customerAccountList = new ListGrid();
		
		ListGridField customerAccountID = new ListGridField();
		customerAccountID.setName("customerAccountId");
		customerAccountID.setTitle("เลขที่บัญชี");
		
		ListGridField customerAccountNameTH = new ListGridField();
		customerAccountNameTH.setName("customerAccountNameTH");
		customerAccountNameTH.setTitle("ชื่อบัญชี");
		
		ListGridField customerAccountGroup = new ListGridField();
		customerAccountGroup.setName("customerAccountGroup");
		customerAccountGroup.setTitle("หมวด");
		
		ListGridField customerAccountLevel = new ListGridField();
		customerAccountLevel.setName("customerAccountLevel");
		customerAccountLevel.setTitle("ระดับ");
		
		ListGridField customerAccountType = new ListGridField();
		customerAccountType.setName("customerAccountType");
		customerAccountType.setTitle("ประเภท");
		
		ListGridField customerAccountControl = new ListGridField();
		customerAccountControl.setName("customerAccountControl");
		customerAccountControl.setTitle("บัญชีคุม");

		customerAccountList.setFields(customerAccountID,customerAccountNameTH,customerAccountGroup,customerAccountLevel,customerAccountType,customerAccountControl);
		
		submit = new IButton(Constants.BUTTON_TITLE_SUBMIT);
		submit.setMargin(1);
		cancel = new IButton(Constants.BUTTON_TITLE_CANCEL);
		cancel.setMargin(1);
		search = new IButton(Constants.BUTTON_TITLE_SEARCH);
		search.setMargin(1);
		sort = new IButton(Constants.BUTTON_TITLE_SORT);
		sort.setMargin(1);
		add = new IButton(Constants.BUTTON_TITLE_ADD);
		add.setMargin(1);

		submit.setSize(buttonSizeHeight, buttonSizeHeight);
		cancel.setSize(buttonSizeHeight, buttonSizeHeight);
		search.setSize(buttonSizeHeight, buttonSizeHeight);
		sort.setSize(buttonSizeWidth, buttonSizeHeight);
		add.setSize(buttonSizeWidth, buttonSizeHeight);

		add.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addCustomerAccount.showAddCustomerAccount(null);
			}
		});

		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Record record = new Record();
				record.setAttribute("customer", customerAccountList.getSelectedRecord());
				AddCustomer.setCustomerAccountAsRecord(record);
			}
		});

		gridLayout.addMember(customerAccountList);
		controlLayout.addMembers(submit, cancel, search, sort, add);
		mainLayout.addMembers(gridLayout, controlLayout);

		this.setDataSectionBackgroundColor("Grey");
		this.addDataSection(mainLayout);
	}

	public static void addCustomerAccountToListAsRecord(Record record) {
		customerAccountList.addData(record.getAttributeAsRecord("customer"));
		AddCustomer.setCustomerAccountAsRecord(record);
	}
}
