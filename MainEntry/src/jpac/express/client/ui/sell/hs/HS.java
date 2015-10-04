package jpac.express.client.ui.sell.hs;

import java.util.Date;
import java.util.LinkedHashMap;

import jpac.express.client.Application;
import jpac.express.client.MainEntry;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.client.ui.startup.DocumentsNumberList;
import jpac.express.domain.Customer;
import jpac.express.shared.ApplicationUtils;
import jpac.express.shared.Constants;

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormLayoutType;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.EventHandler;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.grid.HeaderSpan;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellContextClickHandler;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public class HS extends CustomWindow {

	private String className = this.getClass().getSimpleName();
	
	private static HS instance;
	
	private static CustomerList customerList;
	private static DepartmentList departmentList;
	private static NotesList notesList;
	private static SalesOrderList salesOrderList;
	private static SalesList salesList;
	private static SalesAreaList salesAreaList;
	private static TransportMethodList transportMethodList;
	private static TransportAddressList transportAddressList;
	private static PledgeList pledgeList;
	private static SalesTypeList salesTypeList;
	private static DocumentsNumberList documentNumberList;
	private static GoodsList goodsItemList;
	
	
	private static ListGrid hsList;

	public static final String name = "ขายเงินสด";

	private HLayout headerLayout;
	private HLayout footerLayout;

	private VLayout paymentGridStack;
	private HLayout paymentGridControlLayout;
	private HLayout paymentGridLayout;

	private HLayout transportAddressFormLayout;
	private HLayout priceFormLayout;
	private HLayout customerFormLayout;
	private HLayout saleFormLayout;

	private static DynamicForm customerForm;
	private static DynamicForm saleForm;
	private static DynamicForm transportAddressForm;
	private static DynamicForm priceForm;

	private DynamicForm paymentGridEditForm;

	private static ListGrid hsGoodsList;
	private ListGrid moreDetailsList;
	private ListGrid paymentList;
	private ListGrid saleTaxList;
	
	public HS() {
		super("HS",name, 1120, 600, false);
		hsList = new ListGrid();
		hsList.setAutoFetchData(true);
		hsList.setDataSource(DataSource.get("hs"));
		hsList.fetchData(new Criteria(), new DSCallback(){
			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				if(dsResponse.getData().length>0) {
					hsList.selectSingleRecord(dsResponse.getData().length-1);
				}
			}
		});

		enableCustomControlMenu();
		enableCustomDataTabSet();
		customerList = CustomerList.getInstance();
		departmentList = DepartmentList.getInstance();
		notesList = NotesList.getInstance();
		salesOrderList = SalesOrderList.getInstance();
		salesList = SalesList.getInstance();
		salesAreaList = SalesAreaList.getInstance();
		transportMethodList = TransportMethodList.getInstance();
		transportAddressList = TransportAddressList.getInstance();
		pledgeList = PledgeList.getInstance();
		salesTypeList = SalesTypeList.getInstance();
		documentNumberList = DocumentsNumberList.getInstance();
		goodsItemList = GoodsList.getInstance();

		initWidgets();
		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAltKeyDown() && event.getKeyName().equals("A")) {
					GoodsList.getInstance().showGoodsList();
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("E")) {
					GoodsList.getInstance().showGoodsList();
				}
			}
		});

		addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler(){

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				instance.bringToFront();
			}
			
		});

		show();
	}
	
	public static HS getInstance() {
		if (instance == null) {
			instance = new HS();
		}
		return instance;
	}
	
	public void showHS(Customer customer) {
		show();
		Application.setOpeningWindow(instance.menuItem);
	}

	private void initWidgets() {
		headerLayout = new HLayout();
		initControlMenu();
		initCustomDataTabSet();
		footerLayout = new HLayout();

		transportAddressFormLayout = new HLayout();
		transportAddressFormLayout.setWidth("50%");
		transportAddressFormLayout.setAlign(Alignment.LEFT);
		priceFormLayout = new HLayout();
		priceFormLayout.setWidth("50%");
		priceFormLayout.setAlign(Alignment.RIGHT);
		customerFormLayout = new HLayout();
		customerFormLayout.setWidth("50%");
		saleFormLayout = new HLayout();
		saleFormLayout.setWidth("50%");

		paymentGridEditForm.setItemLayout(FormLayoutType.TABLE);
		paymentGridEditForm.setTitleSuffix("");
		paymentGridEditForm.setNumCols(6);
		StaticTextItem overPayment = new StaticTextItem();
		overPayment.setColSpan(2);
		overPayment.setShowTitle(false);
		overPayment.setValue("ยอดเงินที่รับเกิน");
		overPayment.setAlign(Alignment.RIGHT);
		StaticTextItem cashPayment = new StaticTextItem();
		cashPayment.setValue("ยอดเงินที่รับด้วยเงินสด");
		cashPayment.setAlign(Alignment.RIGHT);
		cashPayment.setShowTitle(false);
		StaticTextItem otherPayment = new StaticTextItem();
		otherPayment.setValue("ชำระโดยอื่นๆ (ด้านล่าง)");
		otherPayment.setAlign(Alignment.RIGHT);
		otherPayment.setShowTitle(false);
		StaticTextItem taxPayment = new StaticTextItem();
		taxPayment.setValue("ภาษีถูกหัก ณ ที่จ่าย");
		taxPayment.setAlign(Alignment.RIGHT);
		taxPayment.setShowTitle(false);
		StaticTextItem commisionPayment = new StaticTextItem();
		commisionPayment.setValue("คอมมิชชั่น");
		commisionPayment.setAlign(Alignment.RIGHT);
		commisionPayment.setShowTitle(false);

		ButtonItem editPayment = new ButtonItem("แก้ไขยอดเงิน");
		editPayment.setRowSpan(2);
		editPayment.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				SC.say("แก้ไขยอดเงิน");
			}
		});

		TextItem overPaymentEdit = new TextItem();
		overPaymentEdit.setShowTitle(false);
		TextItem cashPaymentEdit = new TextItem();
		cashPaymentEdit.setShowTitle(false);
		TextItem otherPaymentEdit = new TextItem();
		otherPaymentEdit.setShowTitle(false);
		TextItem taxPaymentEdit = new TextItem();
		taxPaymentEdit.setShowTitle(false);
		TextItem comissionPaymentEdit = new TextItem();
		comissionPaymentEdit.setShowTitle(false);

		paymentGridEditForm.setFields(overPayment, cashPayment, otherPayment, taxPayment, commisionPayment, editPayment, overPaymentEdit, cashPaymentEdit, otherPaymentEdit, taxPaymentEdit, comissionPaymentEdit);

		setCustomerForm(new DynamicForm());
		getCustomerForm().setDataSource(DataSource.get("customer"));
		getCustomerForm().setCanEdit(true);
		getCustomerForm().setTitleSuffix("");
		getCustomerForm().setWidth("50%");
		getCustomerForm().setTitleWidth(100);
		PickerIcon customerIDSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				customerList.showCustomerList();
			}
		});
		TextItem customerId = new TextItem();
		customerId.setTitle("รหัสลูกค้า");
		customerId.setName("customerIdentify");
		customerId.setIcons(customerIDSearch);

		StaticTextItem customerAddressLine1 = new StaticTextItem();
		customerAddressLine1.setTitle("");
		customerAddressLine1.setName("customerAddressLine1");
		customerAddressLine1.setHeight(customerId.getHeight());

		StaticTextItem customerAddressLine2 = new StaticTextItem();
		customerAddressLine2.setTitle("");
		customerAddressLine2.setName("customerAddressLine2");
		customerAddressLine2.setHeight(customerId.getHeight());

		StaticTextItem customerAddressLine3 = new StaticTextItem("");
		customerAddressLine3.setTitle("");
		customerAddressLine3.setName("customerAddressLine3");
		customerAddressLine3.setHeight(customerId.getHeight());

		StaticTextItem customerPostalCode = new StaticTextItem();
		customerPostalCode.setTitle("");
		customerPostalCode.setName("customerPostalCode");
		customerPostalCode.setHeight(customerId.getHeight());

		StaticTextItem customerTel = new StaticTextItem();
		customerTel.setTitle("โทร");
		customerTel.setName("customerTel");
		customerTel.setHeight(customerId.getHeight());
		PickerIcon customerNoteSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				notesList.showNotesList(Constants.HS);
			}
		});
		TextItem customerNotes = new TextItem();
		customerNotes.setName(Constants.NOTES_NOTESDETAILS);
		customerNotes.setTitle("อ้างอิง");
		customerNotes.setWidth("*");
		customerNotes.setIcons(customerNoteSearch);
		getCustomerForm().setFields(customerId, customerAddressLine1, customerAddressLine2, customerAddressLine3, customerPostalCode, customerTel, customerNotes);

		saleForm = new DynamicForm();
		saleForm.setTitleSuffix("");
		saleForm.setWidth("50%");
		saleForm.setTitleWidth(80);
		saleForm.setItemLayout(FormLayoutType.TABLE);
		saleForm.setNumCols(4);
		PickerIcon departmentNameSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				departmentList.showDepartmentList();
			}
		});
		TextItem departmentName = new TextItem();
		departmentName.setName(Constants.DEPARTMENT_DEPARTMENTID);
		departmentName.setTitle("แผนก");
		departmentName.setHint("");
		departmentName.setColSpan(3);
		departmentName.setIcons(departmentNameSearch);

		TextItem documentNumber = new TextItem();
		documentNumber.setTitle("เลขที่บิลเงินสด");
		documentNumber.setName("documentNumber");
		
		DateTimeItem sellDateTime = new DateTimeItem();
		sellDateTime.setTitle("วันที่");
		sellDateTime.setName("sellDateTime");
		sellDateTime.setValue(new Date());

		PickerIcon departmentSellDocumentSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				salesOrderList.showSalesOrderList();
			}
		});
		TextItem departmentSellDocument = new TextItem("ใบสั่งขาย");
		departmentSellDocument.setIcons(departmentSellDocumentSearch);
		departmentSellDocument.setRowSpan(2);
		departmentSellDocument.setColSpan(2);

		ButtonItem departmentSellDocumentNote = new ButtonItem("หมายเหตุของใบสั่งขาย");

		StaticTextItem emptyItem = new StaticTextItem("");
		emptyItem.setHeight(departmentName.getHeight());

		PickerIcon saleNameSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				salesList.showSalesList();
			}
		});
		TextItem saleName = new TextItem();
		saleName.setName("salesIdentify");
		saleName.setTitle("พนักงานขาย");
		saleName.setIcons(saleNameSearch);
		StaticTextItem saleNameOut = new StaticTextItem();
		saleNameOut.setName("salesName");
		saleNameOut.setTitle("");

		PickerIcon saleAreaSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				salesAreaList.showSalesAreaList();
			}
		});
		TextItem saleArea = new TextItem();
		saleArea.setName("salesArea");
		saleArea.setTitle("เขตการขาย");
		saleArea.setWidth(50);
		saleArea.setIcons(saleAreaSearch);
		saleArea.setHint("");

		PickerIcon saleTransportBySearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				transportMethodList.showTransportMethodList();
			}
		});
		TextItem saleTransportBy = new TextItem();
		saleTransportBy.setName("transportMethodId");
		saleTransportBy.setTitle("ขนส่งโดย");
		saleTransportBy.setWidth(50);
		saleTransportBy.setHint("");
		saleTransportBy.setIcons(saleTransportBySearch);

		SelectItem saleTaxType = new SelectItem();
		saleTaxType.addChangedHandler(new ChangedHandler(){
			@Override
			public void onChanged(ChangedEvent event) {
				calculateTotalPrice();
			}
		});
		saleTaxType.setName("saleTaxType");
		saleTaxType.setTitle("ประเภทราคา");
		saleTaxType.setDefaultValue(0);
		LinkedHashMap<Integer, String> saleTaxTypeValueMap = new LinkedHashMap<Integer, String> ();
		saleTaxTypeValueMap.put (0, "0 - ไม่มี VAT");
		saleTaxTypeValueMap.put (1, "1 - รวม VAT");
		saleTaxTypeValueMap.put (2, "2 - แยก VAT");
		saleTaxType.setValueMap(saleTaxTypeValueMap);
		
		saleForm.setFields(departmentName, documentNumber, sellDateTime, departmentSellDocument, departmentSellDocumentNote, emptyItem, emptyItem, saleName, saleNameOut, saleArea, saleTransportBy, saleTaxType);

		transportAddressForm = new DynamicForm();
		transportAddressForm.setTitleSuffix("");
		transportAddressForm.setTitleWidth(100);
		transportAddressForm.setWidth("50%");
		transportAddressForm.setItemLayout(FormLayoutType.TABLE);
		transportAddressForm.setNumCols(4);

		PickerIcon saleTransportAddressSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				transportAddressList.showTransportAddressList(Constants.HS);
			}
		});
		TextItem transportTransportAddressId = new TextItem();
		transportTransportAddressId.setTitle("สถานที่ส่งของ");
		transportTransportAddressId.setColSpan(3);
		transportTransportAddressId.setName("transportAddressId");
		transportTransportAddressId.setIcons(saleTransportAddressSearch);

		StaticTextItem transportAddressLine1 = new StaticTextItem();
		transportAddressLine1.setTitle("");
		transportAddressLine1.setName("transportAddressLine1");
		transportAddressLine1.setColSpan(3);
		transportAddressLine1.setHeight(transportTransportAddressId.getHeight());

		StaticTextItem transportAddressLine2 = new StaticTextItem();
		transportAddressLine2.setTitle("");
		transportAddressLine2.setName("transportAddressLine2");
		transportAddressLine2.setColSpan(3);
		transportAddressLine2.setHeight(transportTransportAddressId.getHeight());

		StaticTextItem transportAddressLine3 = new StaticTextItem();
		transportAddressLine3.setTitle("");
		transportAddressLine3.setName("transportAddressLine3");
		transportAddressLine3.setHeight(transportTransportAddressId.getHeight());

		StaticTextItem transportPostalCode = new StaticTextItem();
		transportPostalCode.setTitle("");
		transportPostalCode.setName("transportPostalCode");
		transportPostalCode.setHeight(transportTransportAddressId.getHeight());

		transportAddressForm.setFields(transportTransportAddressId, transportAddressLine1, transportAddressLine2, transportAddressLine3, transportPostalCode);
		transportAddressFormLayout.addMember(transportAddressForm);

		priceForm = new DynamicForm();
		priceForm.setTitleSuffix("");
		priceForm.setTitleWidth(100);
		priceForm.setItemLayout(FormLayoutType.TABLE);
		priceForm.setNumCols(6);

		priceForm.setWidth("50%");
		TextItem sumAllGoodsPrice = new TextItem();
		sumAllGoodsPrice.setName("sumAllGoodsPrice");
		sumAllGoodsPrice.setTitle("จำนวนเงิน");
		sumAllGoodsPrice.setTitleColSpan(5);
		sumAllGoodsPrice.setCanEdit(false);
		
		TextItem discountPrice = new TextItem();
		discountPrice.setTitleColSpan(3);
		discountPrice.setName("discountPrice");
		discountPrice.setTitle("หักส่วนลด");
		
		TextItem discountPrice2 = new TextItem("");
		discountPrice2.setShowTitle(false);
		discountPrice2.setName("discountPrice2");
		discountPrice2.setCanEdit(false);
		
		TextItem sumDiscountPrice = new TextItem("");
		sumDiscountPrice.setName("sumDiscountPrice");
		sumDiscountPrice.setShowTitle(false);
		sumDiscountPrice.setCanEdit(false);

		PickerIcon pledgePriceSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				pledgeList.showPledgeList();
			}
		});

		TextItem pledgePrice = new TextItem();
		pledgePrice.setName("pledgePrice");
		pledgePrice.setTitle("หักเงินมัดจำ");
		pledgePrice.setIcons(pledgePriceSearch);
		pledgePrice.setTitleColSpan(3);
		
		TextItem pledgePrice2 = new TextItem("");
		pledgePrice2.setName("pledgePrice2");
		pledgePrice2.setShowTitle(false);
		pledgePrice2.setCanEdit(false);
		
		TextItem sumPledgePrice = new TextItem("");
		sumPledgePrice.setShowTitle(false);
		sumPledgePrice.setName("sumPledgePrice");
		sumPledgePrice.setCanEdit(false);
		
		TextItem taxPercent = new TextItem();
		taxPercent.setName("taxPercent");
		taxPercent.setTitle("ภาษีมูลค่าเพิ่ม");
		taxPercent.setTitleColSpan(4);
		taxPercent.setHint("%");
		
		TextItem sumTax = new TextItem("");
		sumTax.setShowTitle(false);
		sumTax.setName("sumTax");
	
		TextItem sumPrice = new TextItem();
		sumPrice.setTitle("จำนวนเงินรวมทั้งสิ้น");
		sumPrice.setName("sumPrice");
		sumPrice.setTitleColSpan(5);
		sumPrice.setCanEdit(false);
		
		priceForm.setFields(sumAllGoodsPrice, discountPrice, discountPrice2, sumDiscountPrice, pledgePrice, pledgePrice2, sumPledgePrice, taxPercent, sumTax, sumPrice);
		priceFormLayout.addMember(priceForm);

		customerFormLayout.addMember(getCustomerForm());
		customerFormLayout.setBorder("1px solid white");
		customerFormLayout.setMargin(5);
		saleFormLayout.addMember(saleForm);
		saleFormLayout.setBorder("1px solid white");
		saleFormLayout.setMargin(5);
		saleFormLayout.setHeight100();
		headerLayout.addMembers(customerFormLayout, saleFormLayout);
		footerLayout.addMembers(transportAddressFormLayout, priceFormLayout);

		addHeaderSection(headerLayout);
		addFooterSection(footerLayout);
	}

	protected Menu getGoodListContextMenu() {
		final Menu menu = new Menu();
		MenuItem add = new MenuItem(Constants.BUTTON_TITLE_ADD);
		add.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				goodsItemList.showGoodsList();
				goodsItemList.moveTo(EventHandler.getX(), EventHandler.getY());
			}
		});
		MenuItem edit = new MenuItem(Constants.BUTTON_TITLE_EDIT);
		edit.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				hsGoodsList.startEditing(hsGoodsList.getRecordIndex(hsGoodsList.getSelectedRecord()));
			}
		});
		MenuItem delete = new MenuItem(Constants.BUTTON_TITLE_DELETE);
		delete.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {

				hsGoodsList.removeData(hsGoodsList.getSelectedRecord());
			}
		});
		menu.addItem(add);
		menu.addItem(edit);
		menu.addItem(delete);
		return menu;
	}

	private void initGoodListGrid() {
		hsGoodsList = new ListGrid();
		hsGoodsList.setSaveLocally(true);
		hsGoodsList.setCanEdit(true);
		hsGoodsList.setShowCellContextMenus(true);
		//hsGoodsList.setShowComplexFields(true);
		hsGoodsList.setCanSelectCells(true);
		hsGoodsList.setShowRowNumbers(true);
		hsGoodsList.setShowAllRecords(true);
		hsGoodsList.setCanFocusInEmptyGrid(true);
		hsGoodsList.setAutoSaveEdits(true);
		//hsGoodsList.setDataSource(DataSource.get("goodsItem"));
		hsGoodsList.setContextMenu(getGoodListContextMenu());

		ListGridField goodsItemId = new ListGridField();
		goodsItemId.setTitle("รหัส");
		goodsItemId.setWidth("20%");
		goodsItemId.setName("goodsItemId");
		goodsItemId.setCanEdit(false);
		ListGridField goodsItemNameTH = new ListGridField();
		goodsItemNameTH.setTitle("รายละเอียด");
		goodsItemNameTH.setWidth("40%");
		goodsItemNameTH.setName("goodsItemNameTH");
		ListGridField goodsItemStorage = new ListGridField();
		goodsItemStorage.setTitle("คลัง");
		goodsItemStorage.setWidth(45);
		goodsItemStorage.setName("goodsItemStorage");
		goodsItemStorage.setCanEdit(false);
		ListGridField goodsItemAmount = new ListGridField();
		goodsItemAmount.setTitle("จำนวน");
		goodsItemAmount.setAlign(Alignment.RIGHT);
		goodsItemAmount.setDefaultValue(1);
		goodsItemAmount.setName("goodsItemAmount");
		ListGridField goodsItemUnit = new ListGridField();
		goodsItemUnit.setTitle("");
		goodsItemUnit.setWidth(25);
		goodsItemUnit.setName("unitsSmall");
		ListGridField goodsItemPrice = new ListGridField();
		goodsItemPrice.setAlign(Alignment.RIGHT);
		goodsItemPrice.setTitle("ราคาต่อหน่วย");
		goodsItemPrice.setName("goodsItemSellPrice1");
		ListGridField goodsItemDiscount = new ListGridField();
		goodsItemDiscount.setTitle("ส่วนลด");
		goodsItemDiscount.setAlign(Alignment.RIGHT);
		goodsItemDiscount.setName("goodsItemDiscount");
		ListGridField goodsItemTotalAmount = new ListGridField();
		goodsItemTotalAmount.setTitle("จำนวนเงิน");
		goodsItemTotalAmount.setAlign(Alignment.RIGHT);
		goodsItemTotalAmount.setName("goodsItemTotalAmount");
		hsGoodsList.setFields(goodsItemId, goodsItemNameTH, goodsItemStorage, goodsItemAmount, goodsItemUnit, goodsItemPrice, goodsItemDiscount, goodsItemTotalAmount);

		hsGoodsList.addShowContextMenuHandler(new ShowContextMenuHandler() {
			@Override
			public void onShowContextMenu(ShowContextMenuEvent event) {
				event.cancel();
				getGoodListContextMenu().showContextMenu();
			}
		});
		hsGoodsList.addCellContextClickHandler(new CellContextClickHandler() {
			@Override
			public void onCellContextClick(final CellContextClickEvent rightClickEvent) {
				getGoodListContextMenu().showContextMenu();
			}
		});
		hsGoodsList.addEditCompleteHandler(new EditCompleteHandler(){
			@Override
			public void onEditComplete(EditCompleteEvent event) {
				
			}
		});
		hsGoodsList.addCellSavedHandler(new CellSavedHandler(){
			@Override
			public void onCellSaved(CellSavedEvent event) {
				int amount = Integer.parseInt(event.getRecord().getAttribute("goodsItemAmount").toString());
				double sellPrice1 = Double.parseDouble(event.getRecord().getAttribute("goodsItemSellPrice1").toString());
				double price = (amount*sellPrice1);
				event.getRecord().setAttribute("goodsItemTotalAmount", price);
				hsGoodsList.refreshFields();
				
				calculateTotalPrice();
			}
		});
	}
	
	private void addRecordToTaxList() {
		
		if(ApplicationUtils.isInteger(saleForm.getField("saleTaxType").getValue().toString())&&Integer.parseInt(saleForm.getField("saleTaxType").getValue().toString())==0) {
			saleTaxList.selectAllRecords();
			saleTaxList.removeSelectedData();
		} else if(ApplicationUtils.isInteger(saleForm.getField("saleTaxType").getValue().toString())){
			Record record = new Record();
			record.setAttribute("taxPeriod", DateTimeFormat.getFormat("MM/yy").format((Date) saleForm.getField("sellDateTime").getValue()));
			record.setAttribute("taxPeriodDate", DateTimeFormat.getFormat("dd/MM/yy").format((Date) saleForm.getField("sellDateTime").getValue()));
			record.setAttribute("taxId", saleForm.getField("documentNumber").getValue().toString());
			record.setAttribute("taxDepartment", saleForm.getField(Constants.DEPARTMENT_DEPARTMENTID).getValue().toString());
			record.setAttribute("taxSpec", "ขายสดให้ "+customerForm.getValue("customerName").toString());
			record.setAttribute("taxGoodsPrice", (Double.parseDouble(priceForm.getField("sumPrice").getValue().toString())-Double.parseDouble(priceForm.getField("sumTax").getValue().toString())));
			record.setAttribute("taxPrice", priceForm.getField("sumTax").getValue().toString());
			record.setAttribute("taxSumPrice", 0);
			saleTaxList.selectAllRecords();
			saleTaxList.removeSelectedData();
			saleTaxList.addData(record);
		}
		
	}
	
	private void initTaxListGrid() {
		saleTaxList = new ListGrid();
		saleTaxList.setSaveLocally(true);
		saleTaxList.setCanEdit(false);
		saleTaxList.setShowCellContextMenus(true);
		saleTaxList.setShowComplexFields(true);
		saleTaxList.setCanSelectCells(true);
		saleTaxList.setShowRowNumbers(true);
		saleTaxList.setShowAllRecords(true);
		saleTaxList.setCanFocusInEmptyGrid(true);
		saleTaxList.setContextMenu(null);
		saleTaxList.setHeaderHeight(46);
		
		ListGridField taxPeriod = new ListGridField();
		taxPeriod.setTitle("ยื่นรวมในงวด");
		taxPeriod.setWidth("7%");
		taxPeriod.setName("taxPeriod");
		taxPeriod.setAlign(Alignment.CENTER);
		
		ListGridField taxPeriodDate = new ListGridField();
		taxPeriodDate.setTitle("วันที่");
		taxPeriodDate.setWidth("6.5%");
		taxPeriodDate.setName("taxPeriodDate");
		taxPeriodDate.setAlign(Alignment.CENTER);
		
		ListGridField taxId = new ListGridField();
		taxId.setTitle("เลขที่");
		taxId.setWidth("7.5%");
		taxId.setName("taxId");
		taxId.setAlign(Alignment.CENTER);
		
		ListGridField taxDepartment = new ListGridField();
		taxDepartment.setTitle("แผนก");
		taxDepartment.setWidth("7%");
		taxDepartment.setName("taxDepartment");
		
		ListGridField taxSpec = new ListGridField();
		taxSpec.setTitle("รายการ");
		taxSpec.setWidth("37%");
		taxSpec.setName("taxSpec");
		taxSpec.setAlign(Alignment.CENTER);
		
		ListGridField taxGoodsPrice = new ListGridField();
		taxGoodsPrice.setTitle("มูลค่าสินค้าหรือบริการ");
		taxGoodsPrice.setWidth("11%");
		taxGoodsPrice.setName("taxGoodsPrice");
		taxGoodsPrice.setAlign(Alignment.CENTER);
		
		ListGridField taxPrice = new ListGridField();
		taxPrice.setTitle("จำนวนเงินภาษี");
		taxPrice.setWidth("11%");
		taxPrice.setName("taxPrice");
		taxPrice.setAlign(Alignment.CENTER);
		
		ListGridField taxSumPrice = new ListGridField();
		taxSumPrice.setTitle("มูลค่าสินค้าอัตราศูนย์");
		taxSumPrice.setWidth("11%");
		taxSumPrice.setName("taxSumPrice");
		taxSumPrice.setAlign(Alignment.CENTER);
		
		saleTaxList.setFields(taxPeriod,taxPeriodDate,taxId,taxDepartment,taxSpec,taxGoodsPrice,taxPrice,taxSumPrice);
		
		saleTaxList.setHeaderSpans(  
                new HeaderSpan("ใบกำกับภาษี", new String[]{"taxPeriodDate", "taxId"})
		);  
	}
	
	private void calculateTotalPrice() {
		
		double totalPrice = 0.0;
		for(Record goodRecordFromList:hsGoodsList.getRecords()) {
			totalPrice += Double.parseDouble(goodRecordFromList.getAttribute("goodsItemTotalAmount").toString());
		}
		priceForm.getField("sumAllGoodsPrice").setValue(totalPrice);
	
		double discountPrice = 0.0;
		if(priceForm.getField("discountPrice").getValue().toString().trim().charAt(priceForm.getField("discountPrice").getValue().toString().trim().length()-1)=='%') {
			discountPrice = Double.parseDouble(priceForm.getField("discountPrice").getValue().toString().trim().substring(0,priceForm.getField("discountPrice").getValue().toString().trim().length()-1));
			discountPrice = (totalPrice * discountPrice)/100;
			totalPrice = totalPrice - discountPrice;
		} else {
			discountPrice = Double.parseDouble(priceForm.getField("discountPrice").getValue().toString().trim());
			totalPrice = totalPrice - discountPrice;
		}
		priceForm.getField("discountPrice2").setValue(discountPrice);
		priceForm.getField("sumDiscountPrice").setValue(totalPrice);
		
		if(!saleForm.getField("saleTaxType").getValue().toString().equals("")) {
			if(ApplicationUtils.isInteger(saleForm.getField("saleTaxType").getValue().toString())&&Integer.parseInt(saleForm.getField("saleTaxType").getValue().toString())==0) {
				priceForm.getField("sumTax").setValue(0);
				priceForm.getField("sumPrice").setValue(totalPrice);
				addRecordToTaxList();
			} else if(ApplicationUtils.isInteger(saleForm.getField("saleTaxType").getValue().toString())&&Integer.parseInt(saleForm.getField("saleTaxType").getValue().toString())==1) {
				double taxPrice = (Double.parseDouble(priceForm.getField("taxPercent").getValue().toString())*totalPrice)/(100+Double.parseDouble(priceForm.getField("taxPercent").getValue().toString()));
				priceForm.getField("sumTax").setValue(taxPrice);
				priceForm.getField("sumPrice").setValue(totalPrice);
				addRecordToTaxList();
			} else if(ApplicationUtils.isInteger(saleForm.getField("saleTaxType").getValue().toString())&&Integer.parseInt(saleForm.getField("saleTaxType").getValue().toString())==2) {
				double taxPrice = (Double.parseDouble(priceForm.getField("taxPercent").getValue().toString())*totalPrice)/100;
				priceForm.getField("sumTax").setValue(taxPrice);
				totalPrice = totalPrice + taxPrice;
				priceForm.getField("sumPrice").setValue(totalPrice);
				addRecordToTaxList();
			}
		}

	}
	
	private void initCustomDataTabSet() {
		initGoodListGrid();
		initTaxListGrid();
		
		moreDetailsList = new ListGrid();
		paymentList = new ListGrid();
		paymentList.setHeight100();
		
		paymentGridEditForm = new DynamicForm();
		paymentGridEditForm.setHeight(25);
		paymentGridEditForm.setWidth(50);
		paymentGridStack = new VLayout();
		paymentGridStack.setHeight("*");
		paymentGridStack.setAlign(VerticalAlignment.TOP);
		paymentGridLayout = new HLayout();
		paymentGridControlLayout = new HLayout();
		paymentGridControlLayout.setHeight(25);
		paymentGridControlLayout.addMember(paymentGridEditForm);
		paymentGridLayout.addMember(paymentList);
		paymentGridStack.addMembers(paymentGridControlLayout, paymentGridLayout);

		getCustomDataTabSet().newCustomTab("goodsItem", "รายการสินค้า");
		getCustomDataTabSet().getTab("goodsItem").setPane(hsGoodsList);
		getCustomDataTabSet().newCustomTab("moredetails", "รายละเอียดอื่น");
		getCustomDataTabSet().getTab("moredetails").setPane(moreDetailsList);
		getCustomDataTabSet().newCustomTab("payment", "รายการรับชำระ");
		getCustomDataTabSet().getTab("payment").setPane(paymentGridStack);
		getCustomDataTabSet().newCustomTab("tax", "รายการภาษีจ่าย");
		getCustomDataTabSet().getTab("tax").setPane(saleTaxList);
	}

	private void initControlMenu() {

		getCustomControlMenu().newButton("new"+className, "new", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				getCustomControlMenu().getButton("edit"+className).disable();
				getCustomControlMenu().getButton("void"+className).disable();
				getCustomControlMenu().getButton("trash"+className).disable();
				
				getCustomControlMenu().getButton("save"+className).enable();
				getCustomControlMenu().getButton("gotofirst"+className).disable();
				getCustomControlMenu().getButton("gotoprevious"+className).disable();
				getCustomControlMenu().getButton("gotonext"+className).disable();
				getCustomControlMenu().getButton("gotolast"+className).disable();
				getCustomControlMenu().getButton("search"+className).disable();
				getCustomControlMenu().getButton("print"+className).disable();
				//int documentNextNumber = Integer.parseInt(documentNumberList.getDocumentNextNumber(getID()).getAttributeAsRecord("documentNumber").getAttributeAsString("documentNextNumber"));
				customerForm.clearValues();
				saleForm.clearValues();
				transportAddressForm.clearValues();
				priceForm.clearValues();
				saleForm.getField("documentNumber").setValue("**NEW**");
			}
		});

		getCustomControlMenu().newButton("edit"+className, "edit", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				getCustomControlMenu().getButton("new"+className).disable();
				getCustomControlMenu().getButton("void"+className).disable();
				getCustomControlMenu().getButton("trash"+className).disable();
				
				getCustomControlMenu().getButton("save"+className).enable();
				getCustomControlMenu().getButton("gotofirst"+className).disable();
				getCustomControlMenu().getButton("gotoprevious"+className).disable();
				getCustomControlMenu().getButton("gotonext"+className).disable();
				getCustomControlMenu().getButton("gotolast"+className).disable();
				getCustomControlMenu().getButton("search"+className).disable();
				getCustomControlMenu().getButton("print"+className).disable();
			}
		});

		getCustomControlMenu().newButton("void"+className, "void", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				SC.say("void");
			}
		});

		getCustomControlMenu().newButton("trash"+className, "trash", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				getCustomControlMenu().getButton("new"+className).disable();
				getCustomControlMenu().getButton("edit"+className).disable();
				getCustomControlMenu().getButton("void"+className).disable();

				getCustomControlMenu().getButton("gotofirst"+className).disable();
				getCustomControlMenu().getButton("gotoprevious"+className).disable();
				getCustomControlMenu().getButton("gotonext"+className).disable();
				getCustomControlMenu().getButton("gotolast"+className).disable();
				getCustomControlMenu().getButton("search"+className).disable();
				getCustomControlMenu().getButton("print"+className).disable();
			}
		});

		getCustomControlMenu().newButton("cancel"+className, "cancel", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				getCustomControlMenu().getButton("new"+className).enable();
				getCustomControlMenu().getButton("edit"+className).enable();
				getCustomControlMenu().getButton("void"+className).enable();
				getCustomControlMenu().getButton("trash"+className).enable();
				
				//getCustomControlMenu().getButton("save"+className).disable();
				getCustomControlMenu().getButton("gotofirst"+className).enable();
				getCustomControlMenu().getButton("gotoprevious"+className).enable();
				getCustomControlMenu().getButton("gotonext"+className).enable();
				getCustomControlMenu().getButton("gotolast"+className).enable();
				getCustomControlMenu().getButton("search"+className).enable();
				getCustomControlMenu().getButton("print"+className).enable();
			}
		});

		getCustomControlMenu().newButton("save"+className, "save", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				getCustomControlMenu().getButton("new"+className).enable();
				getCustomControlMenu().getButton("edit"+className).enable();
				getCustomControlMenu().getButton("void"+className).enable();
				getCustomControlMenu().getButton("trash"+className).enable();
				
				//getCustomControlMenu().getButton("save"+className).disable();
				getCustomControlMenu().getButton("gotofirst"+className).enable();
				getCustomControlMenu().getButton("gotoprevious"+className).enable();
				getCustomControlMenu().getButton("gotonext"+className).enable();
				getCustomControlMenu().getButton("gotolast"+className).enable();
				getCustomControlMenu().getButton("search"+className).enable();
				getCustomControlMenu().getButton("print"+className).enable();
				
				if(saleForm.getField("documentNumber").getValue().equals("**NEW**")) {
					Record documentNumber = documentNumberList.getDocumentNextNumber(getID());
					int documentNextNumber = Integer.parseInt(documentNumber.getAttributeAsRecord("documentNumber").getAttributeAsString("documentNextNumber"))+1;
					documentNumber.getAttributeAsRecord("documentNumber").setAttribute("documentNextNumber", documentNextNumber);
					
					DataSource.get("documentNumber").updateData(documentNumber.getAttributeAsRecord("documentNumber"));
					
					Record[] goodsItem = hsGoodsList.getRecords();
					int[] goodsItemId = new int[goodsItem.length];
					for(int count=0;count<goodsItem.length;count++) {
						goodsItemId[count] = goodsItem[count].getAttributeAsInt("goodsItemId");
					}
					
					Record hsRecord = new Record();
					//hsRecord.setAttribute("customer", CustomerList.getCustomerList().getSelectedRecord().getAttribute("customerId"));
					hsRecord.setAttribute("documentNumber", documentNumber.getAttributeAsRecord("documentNumber").getAttribute("documentNextNumber"));
					hsRecord.setAttribute("customer", customerForm.getValue("customerId"));
					hsRecord.setAttribute("goodsItem", goodsItemId);
					hsList.addData(hsRecord);

					Record customerRecord = new Record();
					customerRecord.setAttribute("customerId", customerForm.getValue("customerId"));
					customerRecord.setAttribute("sales", saleForm.getValue("salesId"));
					/*
					customerRecord.setAttribute("transportAddress", TransportAddressList.getTransportAddressList().getRecords());
					*/
					CustomerList.getCustomerList().updateData(customerRecord);
					
					Record salesRecord = new Record();
					salesRecord.setAttribute("salesId", saleForm.getValue("salesId"));
					salesRecord.setAttribute("salesArea", saleForm.getValue("salesAreaId"));
					SalesList.getSalesList().updateData(salesRecord);

				} else {
				
					Record hsRecord = new Record();
					String documentNumber  = saleForm.getField("documentNumber").getValue().toString();
					documentNumber = documentNumber.substring(documentNumber.indexOf('S')+1);
					
					Record[] goodsItem = hsGoodsList.getRecords();
					int[] goodsItemId = new int[goodsItem.length];
					for(int count=0;count<goodsItem.length;count++) {
						goodsItemId[count] = goodsItem[count].getAttributeAsInt("goodsItemId");
					}
					
					hsRecord.setAttribute("hsId", hsList.getSelectedRecord().getAttribute("hsId"));
					hsRecord.setAttribute("documentNumber", documentNumber);
					hsRecord.setAttribute("customer", customerForm.getValue("customerId"));
					hsRecord.setAttribute("goodsItem", goodsItemId);
					hsList.updateData(hsRecord);

					Record customerRecord = new Record();
					customerRecord.setAttribute("customerId", customerForm.getValue("customerId"));
					customerRecord.setAttribute("sales", saleForm.getValue("salesId"));
					customerRecord.setAttribute("transportAddress", TransportAddressList.getTransportAddressList().getRecords());
					CustomerList.getCustomerList().updateData(customerRecord);
					
					Record salesRecord = new Record();
					salesRecord.setAttribute("salesId", saleForm.getValue("salesId"));
					salesRecord.setAttribute("salesArea", saleForm.getValue("salesAreaId"));
					SalesList.getSalesList().updateData(salesRecord);
					
				}
				saleForm.getField("documentNumber").setValue("");
			}
		});

		getCustomControlMenu().newButton("gotofirst"+className, "gotofirst", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (hsList.getRecords().length >= 0) {
					hsList.selectSingleRecord(0);
					setCustomerAsRecord(hsList.getSelectedRecord());
					saleForm.getField("documentNumber").setValue(getID()+""+hsList.getSelectedRecord().getAttribute("documentNumber"));
				}
			}
		});

		getCustomControlMenu().newButton("gotoprevious"+className, "gotoprevious", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (hsList.getRecords().length >= 0) {
					if ((hsList.getRecordIndex(hsList.getSelectedRecord()) - 1) >= 0) {
						hsList.selectSingleRecord((hsList.getRecordIndex(hsList.getSelectedRecord()) - 1));
						setCustomerAsRecord(hsList.getSelectedRecord());
						saleForm.getField("documentNumber").setValue(getID()+""+hsList.getSelectedRecord().getAttribute("documentNumber"));
					}
				}
			}
		});

		getCustomControlMenu().newButton("gotonext"+className, "gotonext", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (hsList.getRecords().length >= 0) {
					if ((hsList.getRecordIndex(hsList.getSelectedRecord()) + 1) <= (hsList.getRecords().length - 1)) {
						hsList.selectSingleRecord((hsList.getRecordIndex(hsList.getSelectedRecord()) + 1));
						setCustomerAsRecord(hsList.getSelectedRecord());
						saleForm.getField("documentNumber").setValue(getID()+""+hsList.getSelectedRecord().getAttribute("documentNumber"));
					}
				}
			}
		});

		getCustomControlMenu().newButton("gotolast"+className, "gotolast", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (hsList.getRecords().length >= 0) {
					hsList.selectSingleRecord((hsList.getRecords().length - 1));
					setCustomerAsRecord(hsList.getSelectedRecord());
					saleForm.getField("documentNumber").setValue(getID()+""+hsList.getSelectedRecord().getAttribute("documentNumber"));
				}
			}
		});

		getCustomControlMenu().newButton("search"+className, "search", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				SC.say("search");
			}
		});

		getCustomControlMenu().newButton("print"+className, "print", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				SC.say("print");
			}
		});
		
	}

	public static void setNotesAsRecord(Record record) {
		notesList.hide();
		customerForm.getField(Constants.NOTES_NOTESDETAILS).setValue(record.getAttributeAsRecord("customer").getAttributeAsRecord("notes").getAttribute(Constants.NOTES_NOTESDETAILS));
	}

	public static void setTransportMethodAsRecord(Record record) {
		transportMethodList.hide();
		saleForm.getField("transportMethodId").setValue(record.getAttributeAsRecord("customer").getAttributeAsRecord("transportMethod").getAttribute("transportMethodId"));
		saleForm.getField("transportMethodId").setHint(record.getAttributeAsRecord("customer").getAttributeAsRecord("transportMethod").getAttribute("transportMethodShortNameTH"));
	}

	public static void setSalesAreaAsRecord(Record record) {
		salesAreaList.hide();
		
		if(ApplicationUtils.isInteger(record.getAttribute("salesArea"))) {
			//id
			Criteria salesAreaId = new Criteria();
			salesAreaId.addCriteria("salesAreaId", record.getAttribute("salesArea"));
			DataSource.get("salesArea").fetchData(salesAreaId, new DSCallback(){
				@Override
				public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
					if(dsResponse.getData().length>0) {
						Record salesAreaRecord = dsResponse.getData()[dsResponse.getData().length-1];
						saleForm.getField("salesArea").setValue(salesAreaRecord.getAttribute("salesAreaShortNameTH"));
						saleForm.getField("salesArea").setHint(salesAreaRecord.getAttribute("salesAreaDetailsTH"));
						saleForm.setValue("salesAreaId", salesAreaRecord.getAttribute("salesAreaId"));
					} else {
						saleForm.getField("salesArea").setValue("");
						saleForm.getField("salesArea").setHint("");
						saleForm.setValue("salesAreaId", "");
					}
				}
			});
		} else {
			//record
			if(record.getAttributeAsRecord("salesArea").getAttribute("salesAreaShortNameTH")==null) {
				saleForm.getField("salesArea").setValue("");
				saleForm.getField("salesArea").setHint("");
				saleForm.setValue("salesAreaId", "");
			} else {
				saleForm.getField("salesArea").setValue(record.getAttributeAsRecord("salesArea").getAttribute("salesAreaShortNameTH"));
				saleForm.getField("salesArea").setHint(record.getAttributeAsRecord("salesArea").getAttribute("salesAreaDetailsTH"));
				saleForm.setValue("salesAreaId", record.getAttributeAsRecord("salesArea").getAttribute("salesAreaId"));
			}
		}

	}

	public static void setDepartmentAsRecord(Record record) {
		departmentList.hide();
		saleForm.getField(Constants.DEPARTMENT_DEPARTMENTID).setValue(record.getAttributeAsRecord("department").getAttribute(Constants.DEPARTMENT_DEPARTMENTID));
		saleForm.getField(Constants.DEPARTMENT_DEPARTMENTID).setHint(record.getAttributeAsRecord("department").getAttribute(Constants.DEPARTMENT_DEPARTMENTSHORTNAME_TH));
	}

	public static void setTransportAddressAsRecord(Record record) {
		transportAddressList.hide();
		Record[] transportAddress = record.getAttributeAsRecordArray("transportAddress");
		if(transportAddress.length>0) {
			transportAddressForm.setValues(transportAddress[transportAddress.length-1].toMap());
		} else {
			transportAddressForm.clearValues();
		}
		//transportAddressForm.setValues(record.getAttributeAsRecord("customer").getAttributeAsRecord("transportAddress").toMap());
	}

	public static void setSalesAsRecord(Record record) {
		salesList.hide();
		
		if(ApplicationUtils.isInteger(record.getAttribute("sales"))) {
			//id
			Criteria salesId = new Criteria();
			salesId.addCriteria("salesId", record.getAttribute("sales"));
			DataSource.get("sales").fetchData(salesId, new DSCallback(){
				@Override
				public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
					if(dsResponse.getData().length>0) {
						Record salesRecord = dsResponse.getData()[dsResponse.getData().length-1];
						saleForm.getField("salesIdentify").setValue(salesRecord.getAttribute("salesIdentify"));
						saleForm.getField("salesName").setValue(salesRecord.getAttribute("salesName"));
						saleForm.setValue("salesId", salesRecord.getAttribute("salesId"));
						setSalesAreaAsRecord(salesRecord);
					} else {
						saleForm.getField("salesIdentify").setValue("");
						saleForm.getField("salesName").setValue("");
						saleForm.setValue("salesId", "");
						saleForm.setValue("salesAreaId", "");
					}
				}
			});
			
		} else {
			//record
			if(record.getAttributeAsRecord("sales").getAttribute("salesIdentify")==null) {
				saleForm.setValue("salesId", "");
				saleForm.setValue("salesAreaId", "");
				saleForm.getField("salesIdentify").setValue("");
				saleForm.getField("salesName").setValue("");
				saleForm.getField("salesArea").setValue("");
				saleForm.getField("salesArea").setHint("");
			} else {
				saleForm.getField("salesIdentify").setValue(record.getAttributeAsRecord("sales").getAttribute("salesIdentify"));
				saleForm.getField("salesName").setValue(record.getAttributeAsRecord("sales").getAttribute("salesName"));
				saleForm.setValue("salesId", record.getAttributeAsRecord("sales").getAttribute("salesId"));
				setSalesAreaAsRecord(record.getAttributeAsRecord("sales"));
			}
		}
	}

	
	public static void setCustomerAsRecord(Record record) {
		customerList.hide();
		
		if(ApplicationUtils.isInteger(record.getAttribute("customer"))) {
			//id
			Criteria customerId = new Criteria();
			customerId.addCriteria("customerId", record.getAttribute("customer"));
			DataSource.get("customer").fetchData(customerId, new DSCallback(){
				@Override
				public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
					if(dsResponse.getData().length>0) {
						Record customerRecord = dsResponse.getData()[dsResponse.getData().length-1];
						customerForm.setValues(customerRecord.toMap());
						customerForm.setValue("customerName", customerRecord.getAttribute("customerName"));
						customerForm.setValue("customerId", customerRecord.getAttribute("customerId"));
						setSalesAsRecord(customerRecord);
						setTransportAddressAsRecord(customerRecord);
					} else {
						customerForm.clearValues();
					}
				}
			});
			
		} else {
			//record
			if(record.getAttributeAsRecord("customer").getAttribute("customerIdentify")==null) {
				customerForm.clearValues();
				saleForm.setValue("salesId", "");
				saleForm.setValue("salesAreaId", "");
				saleForm.getField("salesIdentify").setValue("");
				saleForm.getField("salesName").setValue("");
				saleForm.getField("salesArea").setValue("");
				saleForm.getField("salesArea").setHint("");
			} else {
				customerForm.setValues(record.getAttributeAsRecord("customer").toMap());
				customerForm.setValue("customerName", record.getAttributeAsRecord("customer").getAttribute("customerName"));
				customerForm.setValue("customerId", record.getAttributeAsRecord("customer").getAttribute("customerId"));
				setSalesAsRecord(record.getAttributeAsRecord("customer"));
				setTransportAddressAsRecord(record.getAttributeAsRecord("customer"));
			}
		}
	}

	public static void addGoodsToListAsRecord(Record record) {
		record.getAttributeAsRecord("goodsItem").setAttribute("goodsItemAmount", 1);
		int amount = Integer.parseInt(record.getAttributeAsRecord("goodsItem").getAttribute("goodsItemAmount").toString());
		double sellPrice1 = Double.parseDouble(record.getAttributeAsRecord("goodsItem").getAttribute("goodsItemSellPrice1").toString());
		double price = (amount*sellPrice1);
		record.getAttributeAsRecord("goodsItem").setAttribute("goodsItemTotalAmount", price);
		hsGoodsList.startEditingNew(record.getAttributeAsRecord("goodsItem"));
		
		hsGoodsList.refreshFields();
		
		double totalPrice = 0.0;
		for(Record goodRecordFromList:hsGoodsList.getRecords()) {
			double goodTotalPrice = Double.parseDouble(goodRecordFromList.getAttribute("goodsItemTotalAmount").toString());
			totalPrice += goodTotalPrice;
		}
		priceForm.getField("sumAllGoodsPrice").setValue(totalPrice);
		
	}

	public static DynamicForm getCustomerForm() {
		return customerForm;
	}

	public static void setCustomerForm(DynamicForm customerForm) {
		HS.customerForm = customerForm;
	}

}
