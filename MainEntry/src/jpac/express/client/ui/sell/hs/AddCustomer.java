package jpac.express.client.ui.sell.hs;

import java.util.LinkedHashMap;

import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.domain.Customer;
import jpac.express.shared.Constants;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormLayoutType;
import com.smartgwt.client.types.KeyNames;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

public class AddCustomer extends CustomWindow {

	private static final String name = "เพิ่มรายละเอียดลูกค้า";
	private HLayout main;
	private VStack mainLayout;

	private static CustomerTitleNameList customerTitleNameList;
	private static NotesList customerNotesList;
	private static CustomerTypeList customerTypeList;
	private static CustomerAccountList customerAccountList;
	private static SalesList salesList;
	private static SalesAreaList salesAreaList;
	private static TransportMethodList transportMethodList;
	
	private static AddCustomer instance;

	private HLayout controlLayout;

	private static DynamicForm headerForm;
	private static DynamicForm bodyForm;
	private static DynamicForm footerForm;

	private IButton submit;
	private IButton cancel;

	private static Record customerRecord;

	public AddCustomer() {
		super("AddCustomer",name, 800, 460, true);
		customerRecord = new Record();
		customerRecord.setAttribute("customer", new Record());
		customerRecord.getAttributeAsRecord("customer").setAttribute("sales", new Record());
		customerRecord.getAttributeAsRecord("customer").setAttribute("notes", new Record());

		customerTitleNameList = CustomerTitleNameList.getInstance();
		customerNotesList = NotesList.getInstance();
		customerTypeList = CustomerTypeList.getInstance();
		customerAccountList = CustomerAccountList.getInstance();
		salesList = SalesList.getInstance();
		salesAreaList = SalesAreaList.getInstance();
		transportMethodList = TransportMethodList.getInstance();
		initWidgets();
		hide();
	}
	
	public static AddCustomer getInstance() {
		if(instance==null) {
			instance = new AddCustomer();
		}
		return instance;
	}
	
	public static DynamicForm getHeaderForm() {
		return headerForm;
	}

	public void showAddCustomer(Customer customer) {
		centerInPage();
		show();
		this.focus();
	}

	private void initWidgets() {
		disableMaximizeButton();
		disableMinimizeButton();
		disableHeaderSection();
		disableControlSection();
		disableCanResize();
		disableFooterSection();

		main = new HLayout();
		mainLayout = new VStack();
		mainLayout.setWidth100();
		mainLayout.setHeight100();

		controlLayout = new HLayout();
		controlLayout.setWidth100();
		controlLayout.setHeight(25);
		controlLayout.setMargin(5);

		headerForm = new DynamicForm();
		headerForm.setHeight(50);
		headerForm.setWidth100();
		bodyForm = new DynamicForm();
		bodyForm.setWidth100();
		footerForm = new DynamicForm();
		footerForm.setWidth100();

		submit = new IButton(Constants.BUTTON_TITLE_SUBMIT);
		cancel = new IButton(Constants.BUTTON_TITLE_CANCEL);

		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});

		submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				int notesId = -1;
				Record[] notesList = NotesList.getNotesList().getRecords();
				for(Record record:notesList) {
					if(record.getAttributeAsString("notesDetails").equals(customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("notes").getAttributeAsString("notesDetails"))) {
						notesId = record.getAttributeAsInt("notesId");
						break;
					}
				}
				
				int salesId = -1;
				Record[] salesList = SalesList.getSalesList().getRecords();
				for(Record record:salesList) {
					if(record.getAttributeAsString("salesIdentify").equals(customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("sales").getAttributeAsString("salesIdentify"))) {
						salesId = record.getAttributeAsInt("salesId");
						break;
					}
				}
				
				Record notes = customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("notes");
				notes.setAttribute("notesId", notesId);
				Record sales = customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("sales");
				sales.setAttribute("salesId", salesId);

				Record.copyAttributesInto(customerRecord.getAttributeAsRecord("customer"), headerForm.getValuesAsRecord(), headerForm.getValuesAsRecord().getAttributes());
				Record.copyAttributesInto(customerRecord.getAttributeAsRecord("customer"), bodyForm.getValuesAsRecord(), bodyForm.getValuesAsRecord().getAttributes());
				Record.copyAttributesInto(customerRecord.getAttributeAsRecord("customer"), footerForm.getValuesAsRecord(), footerForm.getValuesAsRecord().getAttributes());
				
				if(notesId>-1) {
					customerRecord.getAttributeAsRecord("customer").setAttribute("notes",notesId);
				}
				if(salesId>-1) {
					customerRecord.getAttributeAsRecord("customer").setAttribute("sales",salesId);
				}
				
				CustomerList.addCustomerToListAsRecord(customerRecord,notes,sales);
				customerRecord = new Record();
				customerRecord.setAttribute("customer", new Record());
				customerRecord.getAttributeAsRecord("customer").setAttribute("sales", new Record());
				headerForm.clearValues();
				bodyForm.clearValues();
				footerForm.clearValues();
				
				hide();

			}
		});
		
		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {
					int notesId = -1;
					Record[] notesList = NotesList.getNotesList().getRecords();
					for(Record record:notesList) {
						if(record.getAttributeAsString("notesDetails").equals(customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("notes").getAttributeAsString("notesDetails"))) {
							notesId = record.getAttributeAsInt("notesId");
							break;
						}
					}
					
					int salesId = -1;
					Record[] salesList = SalesList.getSalesList().getRecords();
					for(Record record:salesList) {
						if(record.getAttributeAsString("salesIdentify").equals(customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("sales").getAttributeAsString("salesIdentify"))) {
							salesId = record.getAttributeAsInt("salesId");
							break;
						}
					}
					
					Record notes = customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("notes");
					notes.setAttribute("notesId", notesId);
					Record sales = customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("sales");
					sales.setAttribute("salesId", salesId);

					Record.copyAttributesInto(customerRecord.getAttributeAsRecord("customer"), headerForm.getValuesAsRecord(), headerForm.getValuesAsRecord().getAttributes());
					Record.copyAttributesInto(customerRecord.getAttributeAsRecord("customer"), bodyForm.getValuesAsRecord(), bodyForm.getValuesAsRecord().getAttributes());
					Record.copyAttributesInto(customerRecord.getAttributeAsRecord("customer"), footerForm.getValuesAsRecord(), footerForm.getValuesAsRecord().getAttributes());
					
					if(notesId>-1) {
						customerRecord.getAttributeAsRecord("customer").setAttribute("notes",notesId);
					}
					if(salesId>-1) {
						customerRecord.getAttributeAsRecord("customer").setAttribute("sales",salesId);
					}

					CustomerList.addCustomerToListAsRecord(customerRecord,notes,sales);
					customerRecord = new Record();
					customerRecord.setAttribute("customer", new Record());
					customerRecord.getAttributeAsRecord("customer").setAttribute("sales", new Record());
					headerForm.clearValues();
					bodyForm.clearValues();
					footerForm.clearValues();
					hide();
					/*
					Record.copyAttributesInto(customerRecord.getAttributeAsRecord("customer"), headerForm.getValuesAsRecord(), headerForm.getValuesAsRecord().getAttributes());
					Record.copyAttributesInto(customerRecord.getAttributeAsRecord("customer"), bodyForm.getValuesAsRecord(), bodyForm.getValuesAsRecord().getAttributes());
					Record.copyAttributesInto(customerRecord.getAttributeAsRecord("customer"), footerForm.getValuesAsRecord(), footerForm.getValuesAsRecord().getAttributes());
					CustomerList.addCustomerToListAsRecord(customerRecord);

					customerRecord = new Record();
					customerRecord.setAttribute("customer", new Record());
					customerRecord.getAttributeAsRecord("customer").setAttribute("sales", new Record());
					headerForm.clearValues();
					bodyForm.clearValues();
					footerForm.clearValues();
					hide();
					*/
				}
			}
		});

		TextItem customerId = new TextItem();
		customerId.setName("customerIdentify");
		customerId.setTitle("รหัสลูกค้า");
		customerId.setWidth("*");
		PickerIcon customerTitleSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				customerTitleNameList.showCustomerTitleNameList();
			}
		});
		TextItem customerTitle = new TextItem();
		customerTitle.setName(Constants.TITLENAME_CUSTOMERTITLENAME_TH);
		customerTitle.setTitle(Constants.TITLENAME_CUSTOMERTITLENAME_TH_TITLE);
		customerTitle.setColSpan(3);
		customerTitle.setIcons(customerTitleSearch);

		TextItem customerName = new TextItem();
		customerName.setName("customerName");
		customerName.setTitle("ชื่อ");
		customerName.setColSpan(3);
		customerName.setWidth("*");
		customerName.setTitleColSpan(3);
		headerForm.setItemLayout(FormLayoutType.TABLE);
		headerForm.setTitleSuffix("");
		headerForm.setTitleWidth(120);
		headerForm.setMargin(5);
		headerForm.setNumCols(6);
		headerForm.setFields(customerId, customerTitle, customerName);

		StaticTextItem emptyItem = new StaticTextItem();
		emptyItem.setShowTitle(false);
		emptyItem.setWidth(100);

		TextItem customerAddressLine1 = new TextItem();
		customerAddressLine1.setName("customerAddressLine1");
		customerAddressLine1.setTitle("ที่อยู่");
		customerAddressLine1.setWidth("*");
		customerAddressLine1.setColSpan(4);

		TextItem customerAddressLine2 = new TextItem();
		customerAddressLine2.setName("customerAddressLine2");
		customerAddressLine2.setTitle("");
		customerAddressLine2.setWidth("*");
		customerAddressLine2.setColSpan(4);

		TextItem customerAddressLine3 = new TextItem();
		customerAddressLine3.setName("customerAddressLine3");
		customerAddressLine3.setTitle("");
		customerAddressLine3.setWidth("*");
		customerAddressLine3.setColSpan(2);

		TextItem customerPostalCode = new TextItem();
		customerPostalCode.setName("customerPostalCode");
		customerPostalCode.setTitle("รหัสไปรษณีย์");
		customerPostalCode.setWidth("*");

		TextItem customerTel = new TextItem();
		customerTel.setName("customerTel");
		customerTel.setTitle("โทรศัพท์");
		customerTel.setWidth("*");
		customerTel.setColSpan(4);

		TextItem customerContactName = new TextItem();
		customerContactName.setName("customerContactName");
		customerContactName.setTitle("ชื่อผู้ติดต่อ");
		customerContactName.setWidth("*");
		customerContactName.setColSpan(4);

		PickerIcon customerNoteSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				customerNotesList.showNotesList(Constants.ADDCUSTOMER);
			}
		});
		TextItem customerNote = new TextItem();
		customerNote.setName("customerNote");
		customerNote.setTitle("หมายเหตุ");
		customerNote.setIcons(customerNoteSearch);
		customerNote.setWidth("*");
		customerNote.setColSpan(4);

		TextItem customerTaxID = new TextItem();
		customerTaxID.setName("customerTaxID");
		customerTaxID.setTitle("เลขประจำตัวผู้เสียภาษี");
		customerTaxID.setColSpan(5);
		customerTaxID.setWidth("30%");

		bodyForm.setItemLayout(FormLayoutType.TABLE);
		bodyForm.setTitleSuffix("");
		bodyForm.setBorder("1px solid white");
		bodyForm.setTitleWidth(120);
		bodyForm.setMargin(5);
		bodyForm.setNumCols(6);
		bodyForm.setFields(customerAddressLine1, emptyItem, customerAddressLine2, emptyItem, customerAddressLine3, customerPostalCode, emptyItem, customerTel, emptyItem, customerContactName, emptyItem, customerNote, emptyItem, customerTaxID);

		PickerIcon customerTypeSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				customerTypeList.showCustomerTypeList();
			}
		});
		TextItem customerType = new TextItem();
		customerType.setName("customerTypeId");
		customerType.setTitle("ประเภทลูกค้า");
		customerType.setIcons(customerTypeSearch);
		customerType.setWidth(50);
		StaticTextItem customerTypeOut = new StaticTextItem();
		customerTypeOut.setName("customerTypeDetailsTH");
		customerTypeOut.setWidth(100);
		customerTypeOut.setShowTitle(false);
		customerTypeOut.setColSpan(2);
		TextItem customerCredit = new TextItem();
		customerCredit.setName("customerCredit");
		customerCredit.setTitle("เครดิต");
		customerCredit.setWidth(40);
		customerCredit.setHint("วัน");

		PickerIcon customerAccoutNoSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				customerAccountList.showCustomerAccountList();
			}
		});
		TextItem customerAccountId = new TextItem();
		customerAccountId.setName("customerAccountId");
		customerAccountId.setTitle("เลขที่บัญชี");
		customerAccountId.setIcons(customerAccoutNoSearch);
		customerAccountId.setWidth("*");
		
		StaticTextItem customerAccoutNoOut = new StaticTextItem();
		customerAccoutNoOut.setName("customerAccountNameTH");
		customerAccoutNoOut.setWidth(100);
		customerAccoutNoOut.setShowTitle(false);
		customerAccoutNoOut.setColSpan(2);

		TextItem customerConditionPayment = new TextItem();
		customerConditionPayment.setName("customerConditionPayment");
		customerConditionPayment.setTitle("เงื่อนไขการชำระเงิน");
		customerConditionPayment.setWidth("*");
		//customerConditionPayment.setTitleColSpan(3);

		PickerIcon customerSaleSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				salesList.showSalesList();
			}
		});
		TextItem customerSale = new TextItem();
		customerSale.setName("salesIdentify");
		customerSale.setTitle("พนักงานขาย");
		customerSale.setIcons(customerSaleSearch);
		customerSale.setWidth("*");

		StaticTextItem customerSaleOut = new StaticTextItem();
		customerSaleOut.setName("salesName");
		customerSaleOut.setWidth(100);
		customerSaleOut.setShowTitle(false);
		customerSaleOut.setColSpan(2);

		SelectItem customerPriceTable = new SelectItem();
		customerPriceTable.setWidth("*");
		customerPriceTable.setName("customerPriceTable");
		customerPriceTable.setTitle("ตารางราคา");
		LinkedHashMap<String, String> customerPriceTableValueMap = new LinkedHashMap<String, String> ();
		customerPriceTableValueMap.put ("0", "ราคาขายล่าสุด");
		customerPriceTableValueMap.put ("1", "ราคาขายที่ 1");
		customerPriceTableValueMap.put ("2", "ราคาขายที่ 2");
		customerPriceTableValueMap.put ("3", "ราคาขายที่ 3");
		customerPriceTableValueMap.put ("4", "ราคาขายที่ 4");
		customerPriceTableValueMap.put ("5", "ราคาขายที่ 5");
		customerPriceTable.setValueMap(customerPriceTableValueMap);

		PickerIcon customerSalesAreaSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				salesAreaList.showSalesAreaList();
			}
		});
		TextItem customerSalesArea = new TextItem();
		customerSalesArea.setTitle("เขตการขาย");
		customerSalesArea.setName("salesAreaIdentify");
		customerSalesArea.setIcons(customerSalesAreaSearch);
		customerSalesArea.setWidth(90);
		
		StaticTextItem customerSalesAreaOut = new StaticTextItem();
		customerSalesAreaOut.setName("salesAreaDetailsTH");
		customerSalesAreaOut.setWidth(100);
		customerSalesAreaOut.setShowTitle(false);
		customerSalesAreaOut.setColSpan(2);

		TextItem customerDiscount = new TextItem();
		customerDiscount.setName("customerDiscount");
		customerDiscount.setTitle("ส่วนลด");
		customerDiscount.setWidth(100);
		//customerDiscount.setTitleColSpan(3);

		PickerIcon customerTransportBySearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				transportMethodList.showTransportMethodList();
			}
		});
		TextItem customerTransportMethod = new TextItem();
		customerTransportMethod.setTitle("ขนส่งโดย");
		customerTransportMethod.setName("transportMethodId");
		customerTransportMethod.setIcons(customerTransportBySearch);
		customerTransportMethod.setWidth(50);
		
		StaticTextItem customerTransportMethodOut = new StaticTextItem();
		customerTransportMethodOut.setName("transportMethodShortNameTH");
		customerTransportMethodOut.setWidth(100);
		customerTransportMethodOut.setShowTitle(false);
		customerTransportMethodOut.setColSpan(2);

		TextItem customerCreditLimit = new TextItem();
		customerCreditLimit.setName("customerCreditLimit");
		customerCreditLimit.setTitle("วงเงินอนุมัติ");
		customerCreditLimit.setWidth(100);
		//customerCreditLimit.setTitleColSpan(3);

		footerForm.setItemLayout(FormLayoutType.TABLE);
		footerForm.setTitleSuffix("");
		footerForm.setBorder("1px solid white");
		footerForm.setTitleWidth(120);
		footerForm.setMargin(5);
		footerForm.setNumCols(6);

		footerForm.setFields(customerType, customerTypeOut, customerCredit, customerAccountId,customerAccoutNoOut, customerConditionPayment, customerSale, customerSaleOut, customerPriceTable, customerSalesArea, customerSalesAreaOut, customerDiscount, customerTransportMethod, customerTransportMethodOut, customerCreditLimit);

		controlLayout.addMembers(submit, cancel);
		controlLayout.setAlign(Alignment.CENTER);

		mainLayout.addMembers(headerForm, bodyForm, footerForm, controlLayout);

		main.addMember(mainLayout);
		addDataSection(main);
	}

	public static void setSalesAreaAsRecord(Record record) {
		salesAreaList.hide();
		Record.copyAttributesInto(customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("sales"), record, record.getAttributes());

		//SC.say(record.getAttributeAsRecord("salesArea").getAttribute("salesAreaShortNameTH"));
		
		footerForm.getField("salesAreaIdentify").setValue(customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("sales").getAttributeAsRecord("salesArea").getAttribute("salesAreaShortNameTH"));
		footerForm.getField("salesAreaDetailsTH").setValue(customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("sales").getAttributeAsRecord("salesArea").getAttribute("salesAreaDetailsTH"));
	}

	public static void setTransportMethodAsRecord(Record record) {
		transportMethodList.hide();
		Record.copyAttributesInto(customerRecord.getAttributeAsRecord("customer"), record.getAttributeAsRecord("customer"), record.getAttributeAsRecord("customer").getAttributes());

		footerForm.getField("transportMethodId").setValue(customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("transportMethod").getAttribute("transportMethodId"));
		footerForm.getField("transportMethodShortNameTH").setValue(customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("transportMethod").getAttribute("transportMethodShortNameTH"));
	}

	public static void setSalesAsRecord(Record record) {
		salesList.hide();
		Record.copyAttributesInto(customerRecord.getAttributeAsRecord("customer"), record, record.getAttributes());
		
		footerForm.getField("salesIdentify").setValue(customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("sales").getAttribute("salesIdentify"));
		footerForm.getField("salesName").setValue(customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("sales").getAttribute("salesName"));
	
		if(customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("sales").getAttributeAsRecord("salesArea")!=null) {
			footerForm.getField("salesAreaIdentify").setValue(customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("sales").getAttributeAsRecord("salesArea").getAttribute("salesAreaIdentify"));
			footerForm.getField("salesAreaDetailsTH").setHint(customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("sales").getAttributeAsRecord("salesArea").getAttribute("salesAreaDetailsTH"));
		} else {
			footerForm.getField("salesAreaIdentify").setValue("");
			footerForm.getField("salesAreaDetailsTH").setHint("");
		}
		/*
		if(customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("sales").getAttributeAsRecordArray("salesArea").length>0) {
			footerForm.getField("salesAreaIdentify").setValue(customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("sales").getAttributeAsRecordArray("salesArea")[customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("sales").getAttributeAsRecordArray("salesArea").length-1].getAttribute("salesAreaIdentify"));
			footerForm.getField("salesAreaDetailsTH").setHint(customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("sales").getAttributeAsRecordArray("salesArea")[customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("sales").getAttributeAsRecordArray("salesArea").length-1].getAttribute("salesAreaDetailsTH"));
		} else {
			footerForm.getField("salesAreaIdentify").setValue("");
			footerForm.getField("salesAreaDetailsTH").setHint("");
		}
		*/
		
	}

	public static void setNotesAsRecord(Record record) {
		customerNotesList.hide();
		Record.copyAttributesInto(customerRecord.getAttributeAsRecord("customer"), record.getAttributeAsRecord("customer"), record.getAttributeAsRecord("customer").getAttributes());
		
		bodyForm.getField("customerNote").setValue(customerRecord.getAttributeAsRecord("customer").getAttributeAsRecord("notes").getAttribute(Constants.NOTES_NOTESDETAILS));
	}

	public static void setCustomerTitleNameAsRecord(Record record) {
		customerTitleNameList.hide();
		Record.copyAttributesInto(customerRecord.getAttributeAsRecord("customer"), record.getAttributeAsRecord("customer"), record.getAttributeAsRecord("customer").getAttributes());
		
		headerForm.getField(Constants.TITLENAME_CUSTOMERTITLENAME_TH).setValue(customerRecord.getAttributeAsRecord("customer").getAttribute(Constants.TITLENAME_CUSTOMERTITLENAME_TH));
	}

	public static void setCustomerTypeAsRecord(Record record) {
		customerTypeList.hide();
		Record.copyAttributesInto(customerRecord.getAttributeAsRecord("customer"), record.getAttributeAsRecord("customer"), record.getAttributeAsRecord("customer").getAttributes());
		
		footerForm.getField("customerTypeId").setValue(customerRecord.getAttributeAsRecord("customer").getAttribute("customerTypeId"));
		footerForm.getField("customerTypeDetailsTH").setValue(customerRecord.getAttributeAsRecord("customer").getAttribute("customerTypeDetailsTH"));
	}

	public static void setCustomerAccountAsRecord(Record record) {
		customerAccountList.hide();
		Record.copyAttributesInto(customerRecord.getAttributeAsRecord("customer"), record.getAttributeAsRecord("customer"), record.getAttributeAsRecord("customer").getAttributes());
		
		footerForm.getField("customerAccountId").setValue(customerRecord.getAttributeAsRecord("customer").getAttribute("customerAccountId"));
		footerForm.getField("customerAccountNameTH").setValue(customerRecord.getAttributeAsRecord("customer").getAttribute("customerAccountNameTH"));
		
	}
}
