package jpac.express.client.ui.buy.rr;

import java.util.Date;
import java.util.LinkedHashMap;

import com.google.gwt.i18n.shared.DateTimeFormat;


import jpac.express.client.Application;
//import jpac.express.client.datasource.RRCreditTab2;
//import jpac.express.client.datasource.RRCreditTab4;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.FormLayoutType;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.EditorExitEvent;
import com.smartgwt.client.widgets.form.fields.events.EditorExitHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;  
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

public class RRCredit extends CustomWindow{
	private String className = this.getClass().getSimpleName();
	
	private VLayout paymentGridStack;
	//private HLayout paymentGridControlLayout;
	//private HLayout paymentGridLayout;
	
	private static Record vRecord;
	
	private static ListGrid goodsList;
	private static ListGrid moreDetailsList;
	private ListGrid listtab3;
	private static ListGrid outputTaxList;
	private ListGrid taxList;
	private ListGridField goodsStorage;
	
	//private static DynamicForm saleForm;
	//private DynamicForm paymentGridEditForm;
	private static DynamicForm formF7;
	private DynamicForm formLeftF7;
	
	private static DynamicForm priceForm;
	private static HLayout h1;
	private static HLayout h3;
	private static DynamicForm formh1;
	private static DynamicForm formh2;
	private static DateItem receiptDate;
	private static DateItem completeDateL;
	private static GoodsStorageTypesList goodsStorageTypesList;
	private static PledgeList pledgeList;
	private static TransportMethodList transportMethodList;
	private static DepartmentList departmentList;
	private static RemarkList remarkList;
	private static VendorList vendorList;
	
	//private static Date today = Calendar.getInstance().getTime();
	
	public static final String name = Constants.RR_TITLE;
	private static RRCredit instance = null;			
	
	public static RRCredit getInstance() {
		
		if(instance==null){
			instance = new RRCredit();
		}
		return instance;
	}
	
	public void showRR(/*Customer customer*/){ 
		String d = DateTimeFormat.getFormat(Constants.DD_MM_YY).format(new Date());
		receiptDate.setDefaultValue(d);
		completeDateL.setDefaultValue(d);
		show();	
		Application.setOpeningWindow(instance.menuItem);
	}
	
	public RRCredit(){
		super(Constants.RR_ID, name, 1120, 600, false);
		enableCustomControlMenu();
		setDismissOnEscape(false);
		enableCustomDataTabSet();
		//setHeight100();
		//setWidth100();	
		//setBackgroundColor("#f0f0f0");
		goodsStorageTypesList = GoodsStorageTypesList.getInstance();
		pledgeList = PledgeList.getInstance();
		vendorList = VendorList.getInstance();
		remarkList = RemarkList.getInstance();
		departmentList = DepartmentList.getInstance();		
		transportMethodList = TransportMethodList.getInstance();
		
		initWidgets();
		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAltKeyDown() && event.getKeyName().equals(Constants.HOT_KEY_A)) {
					GoodsList.getInstance().showGoodsList();
				}
				if (event.isAltKeyDown() && event.getKeyName().equals(Constants.HOT_KEY_E)) {
					GoodsList.getInstance().showGoodsList();
				}
			}
		});
		show();
	}
	private void initWidgets(){			
		initControlMenu();
		initCustomDataTabSet();		
				
		h1 = new HLayout();
		h1.setLayoutMargin(10);
		h1.setMembersMargin(10);
		h1.setPadding(10);
		h1.setHeight("28%");
		h1.setWidth100();	
		//h1.setBackgroundColor("#f0f0f0");
		h1.setAlign(Alignment.CENTER);
		//h1.setBorder("solid thin #000000");
		
		h3 = new HLayout();
	    h3.setHeight("20%");
	    h3.setWidth100();
	    h3.setAlign(Alignment.RIGHT);	   
	        
		/** Form Left **/	
		formh1 = new DynamicForm();
		formh1.setTitleSuffix("");
		formh1.setBorder(Constants.BORDER_WHITE_THIN);
		formh1.setPadding(5);
		formh1.setWidth("48%");
		formh1.setHeight(180);
		//formh1.setDataSource(RRCreditTopLeft.getInstance());		
		//formh1.setUseAllDataSourceFields(false);
		formh1.setItemLayout(FormLayoutType.TABLE);
		//formh1.setCellBorder(1);
		formh1.setNumCols(4);
	 	formh1.setColWidths(85,150,60,70);
	 	 	
	 	final TextItem vendorID = new TextItem();
	 	PickerIcon vendorPicker = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {  
            public void onFormItemClick(FormItemIconClickEvent event) {              	
            	vendorList.showVendorList(Constants.RR_ID);          
            		}   
        });	
        vendorID.setName(Constants.VENDOR_ADD_CODE);
        vendorID.setTitle(Constants.VENDOR_ADD_TITLE_CODE);
        vendorID.setIcons(vendorPicker);
        vendorID.setColSpan(4);       
        vendorID.setWrapTitle(false);
        
        StaticTextItem vendorName = new StaticTextItem(Constants.VENDOR_LIST_NAME,"");
        vendorName.setColSpan(4);
        vendorName.setHeight(vendorID.getHeight());
        //vendorName.setShowTitle(false);
        //vendorName.setValue("Digital co.,ltd.");
        
        StaticTextItem vendorAddress1 = new StaticTextItem(Constants.VENDOR_LIST_ADDRESS_1,"");
        vendorAddress1.setColSpan(4);
        vendorAddress1.setHeight(vendorID.getHeight());
        //vendorAddress1.setShowTitle(false);
        
        StaticTextItem vendorAddress2 = new StaticTextItem(Constants.VENDOR_LIST_ADDRESS_2,"");
        vendorAddress2.setHeight(vendorID.getHeight());
        //vendorAddress2.setShowTitle(false);
        
        StaticTextItem customerPostalCode = new StaticTextItem(Constants.VENDOR_LIST_ADD_POST,"");
        customerPostalCode.setHeight(vendorID.getHeight());
        //customerPostalCode.setShowTitle(false);
        //vendorAddress.setShowTitle(false); 
        //vendorAddress.setTextBoxStyle("textItem");
        //vendorAddress.setValue("100 m.3 chaofa rd. 83000");
        
        StaticTextItem vendorTel = new StaticTextItem(Constants.VENDOR_LIST_ADD_TEL, Constants.RR_SHORTTITLE_TEL);
        vendorTel.setColSpan(4);
        vendorTel.setHeight(vendorID.getHeight());
        //vendorTel.setValue("076266222");
        
        TextItem billNO = new TextItem(Constants.RR_BILL_NO, Constants.RR_BILL_NO_TITLE); 
        
        DateItem selectDate = new DateItem(Constants.RR_SELECT_DATE,Constants.RR_DATE_TITLE);
        selectDate.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
        selectDate.setUseMask(true);
        selectDate.setUseTextField(true);
        selectDate.setWidth(90);
        
        PickerIcon remarkPicker = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				remarkList.showRemarkList();
			}
		});
        TextItem remark = new TextItem(Constants.RR_REMARK_NAME,Constants.VENDOR_LIST_ADD_REMARK);
        remark.setIcons(remarkPicker);
        remark.setColSpan(4);
        remark.setWidth(300);
        
        formh1.setFields(vendorID,vendorName,vendorAddress1,vendorAddress2,customerPostalCode,vendorTel,billNO,selectDate,remark);
        
        /** Form Right **/ 
        formh2 = new DynamicForm();
		formh2.setTitleSuffix("");
		formh2.setBorder(Constants.BORDER_WHITE_THIN);
		formh2.setPadding(5);
		formh2.setWidth("48%");
		formh2.setHeight(180);
		//formh2.setDataSource(RRCreditTopRight.getInstance());
		//formh2.setUseAllDataSourceFields(false);

		formh2.setItemLayout(FormLayoutType.TABLE);
		//formh2.setCellBorder(1);
		formh2.setNumCols(4);
		//formh2.setAutoWidth();
		formh2.setColWidths(160,150,170,170);
	 	
		PickerIcon departmentNameSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				departmentList.showDepartmentList();
			}
		});
		
        TextItem departmentID = new TextItem(Constants.DEPARTMENTID, Constants.DEPARTMENT_TITLE_ID);
        departmentID.setIcons(departmentNameSearch);  
        departmentID.setWidth(80);
        departmentID.setColSpan(4);
        /*
        StaticTextItem departmentName = new StaticTextItem();  
        departmentName.setName("departmentDetailsTH");
        departmentName.setShowTitle(false);
        departmentName.setColSpan(2);
        //departmentName.setValue("Department Stores");
        */
        TextItem receiptNo = new TextItem(Constants.RR_RECEIPT_NO, Constants.RR_RECEIPT_NO_TITLE);       
        receiptNo.setWrapTitle(false);
        
        receiptDate = new DateItem(Constants.RR_RECEIPT_DATE,Constants.RR_RECEIPT_TITLE_DATE);        
        receiptDate.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
        receiptDate.setUseMask(true);
        receiptDate.setUseTextField(true);
        receiptDate.setWidth(90);      
        
        PickerIcon referPicker = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				//salesOrderList.showSalesOrderList();
			}
		});
        
        TextItem referPO = new TextItem(Constants.RR_PRODUCT_ORDER, Constants.RR_TITLE_PRODUCT_ORDER);               
        referPO.setIcons(referPicker);
        referPO.setRowSpan(2);
        referPO.setColSpan(2);
        
        ButtonItem button = new ButtonItem(Constants.RR_REMARK_PRODUCT_ORDER, Constants.RR_TITLE_REMARK_PRODUCT_ORDER);
        
        StaticTextItem empty = new StaticTextItem("empty","");
        empty.setColSpan(4);
        empty.setHeight(referPO.getHeight());
        
        IntegerItem vCredit = new IntegerItem(Constants.VENDOR_LIST_ADD_CREDIT, Constants.VENDOR_LIST_ADD_TITLE_CREDIT);
        vCredit.setTextAlign(Alignment.RIGHT);
        vCredit.setWidth(40);
        vCredit.setHint(Constants.VENDOR_LIST_ADD_CREDIT_DAYS);        
        
        DateItem dueDate = new DateItem(Constants.RR_DUE_DATE, Constants.RR_TITLE_DUE_DATE);
        dueDate.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
        dueDate.setUseMask(true);
        dueDate.setUseTextField(true);
        dueDate.setWidth(90);
        dueDate.setWrapTitle(false);
        
        PickerIcon saleTransportBySearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event){
				transportMethodList.showTransportMethodList();
			}
		});
        
        TextItem transferBy = new TextItem(Constants.TRANSPORTID, Constants.VENDOR_LIST_ADD_TRANSPORT);
        transferBy.setWidth(60);
        transferBy.setIcons(saleTransportBySearch);
        //transferBy.setColSpan(4);
        /*
        StaticTextItem transferName = new StaticTextItem();
        transferName.setName("transferName");
        transferName.setShowTitle(false);
        transferName.setColSpan(2);
        */
        //transferName.setValue("JDK Transporters.");
        
        SelectItem priceType = new SelectItem(Constants.VENDOR_LIST_ADD_VAT_TYPES, Constants.VENDOR_LIST_ADD_TITLE_VAT_TYPES);
        //priceType.setName("priceType");
        priceType.setColSpan(4);            
        LinkedHashMap<String, String> priceTypeValueMap = new LinkedHashMap<String, String>();
        priceTypeValueMap.put (Constants.VAT_TYPES_VALUE_0, Constants.VAT_TYPES_0);
		priceTypeValueMap.put (Constants.VAT_TYPES_VALUE_1, Constants.VAT_TYPES_1);
		priceTypeValueMap.put (Constants.VAT_TYPES_VALUE_2, Constants.VAT_TYPES_2);
		priceType.setValueMap(priceTypeValueMap);
		priceType.setValue(Constants.VAT_TYPES_VALUE_2);
                
        formh2.setFields(departmentID,receiptNo,receiptDate,referPO,button,empty,vCredit,dueDate,transferBy,priceType);
            
        priceForm = new DynamicForm();
		priceForm.setTitleSuffix("");
		//priceForm.setTitleWidth(100);
		priceForm.setItemLayout(FormLayoutType.TABLE);
		priceForm.setNumCols(6);
		priceForm.setAutoWidth();
		//priceForm.setCellBorder(1);

		//priceForm.setWidth("50%");
		FloatItem sumAllGoodsPrice = new FloatItem();
		sumAllGoodsPrice.setName(Constants.RR_SUM_ALL_GOODS_PRICE);
		sumAllGoodsPrice.setTitle(Constants.RR_SUM_ALL_GOODS_PRICE_TITLE);
		sumAllGoodsPrice.setTitleColSpan(5);
		sumAllGoodsPrice.setTextAlign(Alignment.RIGHT);
		sumAllGoodsPrice.setFormat(Constants.NUMBER_2_DECIMAL);
		sumAllGoodsPrice.setDefaultValue(0.00);
		sumAllGoodsPrice.setCanEdit(false);
		sumAllGoodsPrice.setCanFocus(false);
		
		FloatItem discountPrice = new FloatItem();
		discountPrice.setTitleColSpan(3);
		discountPrice.setName(Constants.RR_DISCOUNT_PRICE1);
		discountPrice.setTitle(Constants.RR_DISCOUNT_PRICE1_TITLE);
		discountPrice.setWrapTitle(false);
		discountPrice.setTextAlign(Alignment.RIGHT);
		discountPrice.setFormat(Constants.NUMBER_2_DECIMAL);
		discountPrice.setDefaultValue(0.00);		
		discountPrice.addEditorExitHandler(new EditorExitHandler(){			
			@Override
			public void onEditorExit(EditorExitEvent event) {
				goodsList.refreshFields();
				calculateTotalPrice();
			}
		});
		
		FloatItem discountPrice2 = new FloatItem();
		discountPrice2.setShowTitle(false);
		discountPrice2.setName(Constants.RR_DISCOUNT_PRICE2);
		discountPrice2.setTextAlign(Alignment.RIGHT);
		discountPrice2.setFormat(Constants.NUMBER_2_DECIMAL);
		discountPrice2.setDefaultValue(0.00);
		discountPrice2.setCanEdit(false);
		discountPrice2.setCanFocus(false);
		
		FloatItem sumDiscountPrice = new FloatItem();
		sumDiscountPrice.setName(Constants.RR_SUM_DISCOUNT_PRICE);
		sumDiscountPrice.setShowTitle(false);
		sumDiscountPrice.setTextAlign(Alignment.RIGHT);
		sumDiscountPrice.setDefaultValue(0.00);
		sumDiscountPrice.setFormat(Constants.NUMBER_2_DECIMAL);
		sumDiscountPrice.setCanEdit(false);
		sumDiscountPrice.setCanFocus(false);		

		PickerIcon pledgePriceSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				pledgeList.showPledgeList();
			}
		});

		TextItem pledgePrice = new TextItem();
		pledgePrice.setName(Constants.RR_PLEDGE_PRICE1);
		pledgePrice.setTitle(Constants.RR_PLEDGE_PRICE1_TITLE);
		pledgePrice.setWrapTitle(false);
		pledgePrice.setIcons(pledgePriceSearch);
		pledgePrice.setTitleColSpan(3);
		
		FloatItem pledgePrice2 = new FloatItem("");
		pledgePrice2.setName(Constants.RR_PLEDGE_PRICE2);
		pledgePrice2.setShowTitle(false);
		pledgePrice2.setTextAlign(Alignment.RIGHT);
		pledgePrice2.setFormat(Constants.NUMBER_2_DECIMAL);
		pledgePrice2.setDefaultValue(0.00);
		pledgePrice2.setCanEdit(false);
		pledgePrice2.setCanFocus(false);		
		
		FloatItem sumPledgePrice = new FloatItem("");
		sumPledgePrice.setShowTitle(false);
		sumPledgePrice.setName(Constants.RR_SUM_PLEDGE_PRICE);
		sumPledgePrice.setTextAlign(Alignment.RIGHT);
		sumPledgePrice.setFormat(Constants.NUMBER_2_DECIMAL);
		sumPledgePrice.setDefaultValue(0.00);
		sumPledgePrice.setCanEdit(false);
		sumPledgePrice.setCanFocus(false);
		
		FloatItem taxPercent = new FloatItem();
		taxPercent.setName(Constants.RR_TAX_PERCENT);
		taxPercent.setTitle(Constants.RR_TAX_PERCENT_TITLE);
		taxPercent.setTitleColSpan(4);
		taxPercent.setHint("%");
		taxPercent.setTextAlign(Alignment.RIGHT);
		taxPercent.setFormat(Constants.NUMBER_2_DECIMAL);
		taxPercent.setDefaultValue(0.00);
		taxPercent.addEditorExitHandler(new EditorExitHandler(){			
			@Override
			public void onEditorExit(EditorExitEvent event) {
				goodsList.refreshFields();
				calculateTotalPrice();
			}
		});
		
		FloatItem sumTax = new FloatItem();
		sumTax.setShowTitle(false);
		sumTax.setName(Constants.RR_SUM_TAX_PERCENT);
		sumTax.setCanEdit(false);
		sumTax.setTextAlign(Alignment.RIGHT);
		sumTax.setFormat(Constants.NUMBER_2_DECIMAL);
		sumTax.setDefaultValue(0.00);		
		sumTax.setCanFocus(false);
	
		FloatItem sumPrice = new FloatItem();
		sumPrice.setTitle(Constants.RR_SUM_PRICE_TITLE);
		sumPrice.setName(Constants.RR_SUM_PRICE);
		sumPrice.setTitleColSpan(5);
		sumPrice.setCanEdit(false);
		sumPrice.setTextAlign(Alignment.RIGHT);
		sumPrice.setFormat(Constants.NUMBER_2_DECIMAL);
		sumPrice.setDefaultValue(0.00);
		sumPrice.setCanFocus(false);
		
		priceForm.setFields(sumAllGoodsPrice, discountPrice, discountPrice2, sumDiscountPrice, pledgePrice, pledgePrice2, sumPledgePrice, taxPercent, sumTax, sumPrice);
		
		h1.addMembers(formh1,formh2);
		h3.addMember(priceForm);
        addHeaderSection(h1);
        addFooterSection(h3);                           
	}
	
private static void calculateTotalPrice(){
		
		double totalPrice = 0.0;
		for(Record goodRecordFromList:goodsList.getRecords()){
			totalPrice += Double.parseDouble(goodRecordFromList.getAttribute(Constants.RR_TAB1_GOODS_TOTAL_AMOUNT).toString());
		}
		priceForm.getField(Constants.RR_SUM_ALL_GOODS_PRICE).setValue(totalPrice);
	
		double discountPrice = 0.0;
		if(priceForm.getField(Constants.RR_DISCOUNT_PRICE1).getValue().toString().trim().charAt(priceForm.getField(Constants.RR_DISCOUNT_PRICE1).getValue().toString().trim().length()-1)=='%') {
			discountPrice = Double.parseDouble(priceForm.getField(Constants.RR_DISCOUNT_PRICE1).getValue().toString().trim().substring(0,priceForm.getField(Constants.RR_DISCOUNT_PRICE1).getValue().toString().trim().length()-1));
			discountPrice = (totalPrice * discountPrice)/100;
			totalPrice = totalPrice - discountPrice;
		} else {
			discountPrice = Double.parseDouble(priceForm.getField(Constants.RR_DISCOUNT_PRICE1).getValue().toString().trim());
			totalPrice = totalPrice - discountPrice;
		}
		priceForm.getField(Constants.RR_DISCOUNT_PRICE2).setValue(discountPrice);
		priceForm.getField(Constants.RR_SUM_DISCOUNT_PRICE).setValue(totalPrice);
		
		if(Integer.parseInt(formh2.getField(Constants.VENDOR_LIST_ADD_VAT_TYPES).getValue().toString())==0) {
			priceForm.getField(Constants.RR_SUM_TAX_PERCENT).setValue(0);
		} else if(Integer.parseInt(formh2.getField(Constants.VENDOR_LIST_ADD_VAT_TYPES).getValue().toString())==1) {
			//double taxPrice = (Double.parseDouble(priceForm.getField("taxPercent").getValue().toString())*totalPrice)/(100+Double.parseDouble(priceForm.getField("taxPercent").getValue().toString()));
			double taxPrice = (Double.parseDouble(priceForm.getField(Constants.RR_TAX_PERCENT).getValue().toString())*totalPrice)/(100+Double.parseDouble(priceForm.getField(Constants.RR_TAX_PERCENT).getValue().toString()));
			priceForm.getField(Constants.RR_SUM_TAX_PERCENT).setValue(taxPrice);
		} else if(Integer.parseInt(formh2.getField(Constants.VENDOR_LIST_ADD_VAT_TYPES).getValue().toString())==2) {
			double taxPrice = (Double.parseDouble(priceForm.getField(Constants.RR_TAX_PERCENT).getValue().toString())*totalPrice)/100;
			priceForm.getField(Constants.RR_SUM_TAX_PERCENT).setValue(taxPrice);
			totalPrice = totalPrice + taxPrice;
		}
		
		priceForm.getField(Constants.RR_SUM_PRICE).setValue(totalPrice);
		formF7.getField(Constants.RR_SUM_PRICE).setValue(totalPrice);
		addRecordToTaxList();
	}

	private void initCustomDataTabSet(){
		initGoodListGrid();
		initMoreDetailsList();
		paymentGridStack();
		initOutputTaxListGrid();
		initTaxListGrid();
			
		/*
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
		*/
		
		getCustomDataTabSet().newCustomTab(Constants.RR_TAB1_NAME, Constants.RR_TAB1_TITLE);
		getCustomDataTabSet().getCustomTab(Constants.RR_TAB1_NAME).setPane(goodsList);
		getCustomDataTabSet().newCustomTab(Constants.RR_TAB2_NAME, Constants.RR_TAB2_TITLE);
		getCustomDataTabSet().getCustomTab(Constants.RR_TAB2_NAME).setPane(moreDetailsList);
		getCustomDataTabSet().newCustomTab(Constants.RR_TAB3_NAME, Constants.RR_TAB3_TITLE);
		getCustomDataTabSet().getCustomTab(Constants.RR_TAB3_NAME).setPane(paymentGridStack);
		getCustomDataTabSet().newCustomTab(Constants.RR_TAB4_NAME, Constants.RR_TAB4_TITLE);
		getCustomDataTabSet().getCustomTab(Constants.RR_TAB4_NAME).setPane(outputTaxList);
		getCustomDataTabSet().newCustomTab(Constants.RR_TAB5_NAME, Constants.RR_TAB5_TITLE);
		getCustomDataTabSet().getCustomTab(Constants.RR_TAB5_NAME).setPane(taxList);
		
	}
	
	private void initControlMenu(){
		getCustomControlMenu().newButton(Constants.CONTROL_MENU_NEW+className, Constants.CONTROL_MENU_NEW, new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_EDIT+className).disable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_VOID+className).disable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_TRASH+className).disable();
				
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_SAVE+className).enable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_GOTOFIRST+className).disable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_GOTOPREVIOUS+className).disable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_GOTONEXT+className).disable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_GOTOLAST+className).disable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_SEARCH+className).disable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_PRINT+className).disable();
				
			}
		});

		getCustomControlMenu().newButton(Constants.CONTROL_MENU_EDIT+className, Constants.CONTROL_MENU_EDIT, new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_NEW+className).disable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_VOID+className).disable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_TRASH+className).disable();
				
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_SAVE+className).enable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_GOTOFIRST+className).disable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_GOTOPREVIOUS+className).disable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_GOTONEXT+className).disable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_GOTOLAST+className).disable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_SEARCH+className).disable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_PRINT+className).disable();
			}
		});

		getCustomControlMenu().newButton(Constants.CONTROL_MENU_VOID+className, Constants.CONTROL_MENU_VOID, new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				SC.say(Constants.CONTROL_MENU_VOID);
			}
		});

		getCustomControlMenu().newButton(Constants.CONTROL_MENU_TRASH+className, Constants.CONTROL_MENU_TRASH, new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_NEW+className).disable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_EDIT+className).disable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_VOID+className).disable();

				getCustomControlMenu().getButton(Constants.CONTROL_MENU_GOTOFIRST+className).disable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_GOTOPREVIOUS+className).disable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_GOTONEXT+className).disable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_GOTOLAST+className).disable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_SEARCH+className).disable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_PRINT+className).disable();
			}
		});

		getCustomControlMenu().newButton(Constants.CONTROL_MENU_CANCEL+className, Constants.CONTROL_MENU_CANCEL, new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_NEW+className).enable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_EDIT+className).enable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_VOID+className).enable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_TRASH+className).enable();
				
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_SAVE+className).disable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_GOTOFIRST+className).enable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_GOTOPREVIOUS+className).enable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_GOTONEXT+className).enable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_GOTOLAST+className).enable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_SEARCH+className).enable();
				getCustomControlMenu().getButton(Constants.CONTROL_MENU_PRINT+className).enable();
			}
		});

		getCustomControlMenu().newButton(Constants.CONTROL_MENU_SAVE+className, Constants.CONTROL_MENU_SAVE, new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				SC.say(Constants.CONTROL_MENU_SAVE);
			}
		});

		getCustomControlMenu().newButton(Constants.CONTROL_MENU_GOTOFIRST+className, Constants.CONTROL_MENU_GOTOFIRST, new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				SC.say(Constants.CONTROL_MENU_GOTOFIRST);
			}
		});

		getCustomControlMenu().newButton(Constants.CONTROL_MENU_GOTOPREVIOUS+className, Constants.CONTROL_MENU_GOTOPREVIOUS, new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event){
				SC.say(Constants.CONTROL_MENU_GOTOPREVIOUS);
			}
		});

		getCustomControlMenu().newButton(Constants.CONTROL_MENU_GOTONEXT+className, Constants.CONTROL_MENU_GOTONEXT, new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event){
				SC.say(Constants.CONTROL_MENU_GOTONEXT);
			}
		});

		getCustomControlMenu().newButton(Constants.CONTROL_MENU_GOTOLAST+className, Constants.CONTROL_MENU_GOTOLAST, new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event){
				SC.say(Constants.CONTROL_MENU_GOTOLAST);
			}
		});

		getCustomControlMenu().newButton(Constants.CONTROL_MENU_SEARCH+className, Constants.CONTROL_MENU_SEARCH, new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event){
				SC.say(Constants.CONTROL_MENU_SEARCH);
			}
		});

		getCustomControlMenu().newButton(Constants.CONTROL_MENU_PRINT+className, Constants.CONTROL_MENU_PRINT, new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event){
				SC.say(Constants.CONTROL_MENU_PRINT);
			}
		});
		
		getCustomControlMenu().getButton(Constants.CONTROL_MENU_SAVE+className).disable();
	}
	
	public static void setRemarkAsRecord(Record record) {
		remarkList.hide();
		formh1.getField(Constants.RR_REMARK_NAME).setValue(record.getAttributeAsRecord(Constants.RR_REMARK_RECORD).getAttribute(Constants.RR_REMARK_NAME));
	}
	
	protected Menu getGoodListContextMenu(){
		final Menu menu = new Menu();
		MenuItem add = new MenuItem(Constants.BUTTON_TITLE_ADD);
		add.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler(){
			@Override
			public void onClick(MenuItemClickEvent event){
				GoodsList.getInstance().showGoodsList();
				//GoodsList.getInstance().moveTo(EventHandler.getX(), EventHandler.getY());
			}
		});
		MenuItem edit = new MenuItem(Constants.BUTTON_TITLE_EDIT);
		edit.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler(){
			@Override
			public void onClick(MenuItemClickEvent event){
				if(goodsList.getSelectedRecord()!=null){
				goodsList.startEditing(goodsList.getRecordIndex(goodsList.getSelectedRecord()));
				}
			}
		});
		MenuItem delete = new MenuItem(Constants.BUTTON_TITLE_DELETE);
		delete.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler(){
			@Override
			public void onClick(MenuItemClickEvent event){
				if(goodsList.getSelectedRecord()!=null){
				goodsList.removeData(goodsList.getSelectedRecord());
				calculateTotalPrice();
				}
			}
		});
		menu.addItem(add);
		menu.addItem(edit);
		menu.addItem(delete);
		return menu;
	}
	
	protected Menu tab2ListContextMenu(){
		final Menu menu = new Menu();
		MenuItem add = new MenuItem(Constants.BUTTON_TITLE_ADD);
		add.setEnabled(false);
		MenuItem edit = new MenuItem(Constants.BUTTON_TITLE_EDIT);
		edit.setEnabled(false);
		MenuItem delete = new MenuItem(Constants.BUTTON_TITLE_DELETE);
		delete.setEnabled(false);
		menu.addItem(add);
		menu.addItem(edit);
		menu.addItem(delete);
		return menu;
	}
	
	protected Menu tab3ListContextMenu(){
		final Menu menu = new Menu();
		MenuItem add = new MenuItem(Constants.BUTTON_TITLE_ADD);
		add.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler(){
			@Override
			public void onClick(MenuItemClickEvent event){
				listtab3.startEditingNew();			
			}
		});
		MenuItem edit = new MenuItem(Constants.BUTTON_TITLE_EDIT);
		edit.setEnabled(false);
		MenuItem delete = new MenuItem(Constants.BUTTON_TITLE_DELETE);
		delete.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler(){
			@Override
			public void onClick(MenuItemClickEvent event){
				listtab3.removeData(listtab3.getSelectedRecord());
			}
		});
		menu.addItem(add);
		menu.addItem(edit);
		menu.addItem(delete);
		return menu;
	}
	
	private void initGoodListGrid(){
		goodsList = new ListGrid();
		//goodsList.setDataSource(RRCreditTab1.getInstance());
		//goodsList.setAutoFetchData(true);
		goodsList.setSaveLocally(true);
		goodsList.setCanEdit(true);		
		//goodsList.setShowCellContextMenus(true);
		goodsList.setShowComplexFields(true);
		//goodsList.setCanSelectCells(true);
		goodsList.setShowRowNumbers(true);
		//goodsList.setShowAllRecords(true);
		goodsList.setCanFocusInEmptyGrid(true);
		goodsList.setContextMenu(getGoodListContextMenu());				
		
		PickerIcon goodsIdSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {  
            public void onFormItemClick(FormItemIconClickEvent event) {              	
            	GoodsList.getInstance().showGoodsList();       
            		}   
        });	
		ListGridField goodsId = new ListGridField();
		goodsId.setTitle(Constants.RR_TAB1_CODE_TITLE);
		goodsId.setWidth("20%");
		goodsId.setName(Constants.GOODS_LIST_CODE);
		//goodsId.setCanEdit(false);
		goodsId.setIcons(goodsIdSearch);
		ListGridField goodsNameTH = new ListGridField();
		goodsNameTH.setTitle(Constants.RR_TAB1_NAME_TITLE);
		goodsNameTH.setWidth("40%");
		goodsNameTH.setName(Constants.GOODS_LIST_FULLNAME_TH);
		
		PickerIcon goodsStorageSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {  
            public void onFormItemClick(FormItemIconClickEvent event) { 
            	//SC.say(event.getForm().getValues().get("gsTypesId").toString());
            	//SC.say(goodsList.getSelectedRecord().getAttribute("gsTypesId").toString());
            	goodsStorageTypesList.showgsTypesList();
            		}   
        });			
		goodsStorage = new ListGridField();
		goodsStorage.setTitle(Constants.RR_TAB1_STORE_TITLE);
		goodsStorage.setWidth(45);
		goodsStorage.setName(Constants.GOOD_STORAGE_TYPESID);
		//goodsStorage.setCanEdit(false);
		goodsStorage.setIcons(goodsStorageSearch);

		ListGridField goodsAmount = new ListGridField();
		goodsAmount.setTitle(Constants.RR_TAB1_AMOUNT_TITLE);
		goodsAmount.setAlign(Alignment.RIGHT);
		goodsAmount.setDefaultValue(1);
		goodsAmount.setName(Constants.RR_TAB1_GOODS_AMOUNT);
		goodsAmount.setFormat(Constants.NUMBER_1_DECIMAL);
		ListGridField goodsUnit = new ListGridField();
		goodsUnit.setTitle("");
		goodsUnit.setWidth(25);
		goodsUnit.setName(Constants.RR_TAB1_UNITS_SMALL);
		goodsUnit.setCanEdit(false);
		ListGridField goodsPrice = new ListGridField();
		goodsPrice.setAlign(Alignment.RIGHT);
		goodsPrice.setTitle(Constants.RR_TAB1_AMOUNT_PER_UNIT_TITLE);
		goodsPrice.setName(Constants.GOODS_LIST_PRICE1);
		goodsPrice.setFormat(Constants.NUMBER_3_DECIMAL);
		ListGridField goodsDiscount = new ListGridField();
		goodsDiscount.setTitle(Constants.RR_TAB1_DISCOUNT_TITLE);
		goodsDiscount.setAlign(Alignment.RIGHT);
		goodsDiscount.setName(Constants.RR_TAB1_GOODS_DISCOUNT);
		goodsDiscount.setFormat(Constants.NUMBER_2_DECIMAL);
		ListGridField goodsTotalAmount = new ListGridField();
		goodsTotalAmount.setTitle(Constants.RR_TAB1_PRICE_TITLE);
		goodsTotalAmount.setAlign(Alignment.RIGHT);
		goodsTotalAmount.setName(Constants.RR_TAB1_GOODS_TOTAL_AMOUNT);
		goodsTotalAmount.setFormat(Constants.NUMBER_2_DECIMAL);
		goodsList.setFields(goodsId, goodsNameTH, goodsStorage, goodsAmount, goodsUnit, goodsPrice, goodsDiscount, goodsTotalAmount);

		goodsList.addShowContextMenuHandler(new ShowContextMenuHandler() {
			@Override
			public void onShowContextMenu(ShowContextMenuEvent event) {
				event.cancel();
				getGoodListContextMenu().showContextMenu();
			}
		});
		goodsList.addCellContextClickHandler(new CellContextClickHandler() {
			@Override
			public void onCellContextClick(final CellContextClickEvent rightClickEvent) {
				getGoodListContextMenu().showContextMenu();
			}
		});
		goodsList.addEditCompleteHandler(new EditCompleteHandler(){
			@Override
			public void onEditComplete(EditCompleteEvent event) {
				goodsList.saveAllEdits();
				goodsList.refreshFields();	
			}
		});
		goodsList.addCellSavedHandler(new CellSavedHandler(){
			@Override
			public void onCellSaved(CellSavedEvent event) {
				goodsList.saveAllEdits();
				goodsList.refreshFields();	
				int amount = Integer.parseInt(event.getRecord().getAttribute(Constants.RR_TAB1_GOODS_AMOUNT).toString());
				double sellPrice1 = Double.parseDouble(event.getRecord().getAttribute(Constants.GOODS_LIST_PRICE1).toString());				
				double price = (amount*sellPrice1);
				event.getRecord().setAttribute(Constants.RR_TAB1_GOODS_TOTAL_AMOUNT, price);
				goodsList.saveAllEdits();
			
				calculateTotalPrice();
			}
		});
	}
	
	private void initMoreDetailsList(){
        moreDetailsList = new ListGrid();  
        moreDetailsList.setWidth100();  
        moreDetailsList.setHeight100();
        //moreDetailsList.setDataSource(RRCreditTab2.getInstance());
        moreDetailsList.setShowRowNumbers(true);
        //moreDetailsList.setUseAllDataSourceFields(false);
        //moreDetailsList.setShowAllRecords(true);  
        
        //moreDetailsList.setShowCellContextMenus(true);
        moreDetailsList.setContextMenu(tab2ListContextMenu());
        /*
        ListGridField productNo2 = new ListGridField("productNo2", "No.");  
        productNo2.setWidth(34);
        productNo2.setAlign(Alignment.CENTER);
        */
        ListGridField productID2 = new ListGridField(Constants.GOODS_LIST_CODE, Constants.RR_TAB1_CODE_TITLE);  
        ListGridField productDetail2 = new ListGridField(Constants.GOODS_LIST_FULLNAME_TH, Constants.RR_TAB1_NAME_TITLE);
        productDetail2.setWidth("50%");
        ListGridField productdept2 = new ListGridField(Constants.GOOD_STORAGE_TYPESID, Constants.RR_TAB1_STORE_TITLE);  
        productdept2.setWidth(60);
        ListGridField productAmount2 = new ListGridField(Constants.RR_TAB1_GOODS_AMOUNT, Constants.RR_TAB1_AMOUNT_TITLE); 
        productAmount2.setAlign(Alignment.RIGHT);
        ListGridField productUnit2 = new ListGridField(Constants.RR_TAB1_UNITS_SMALL);
        productUnit2.setWidth(45);
        productUnit2.setShowTitle(false);
        ListGridField rate2 = new ListGridField(Constants.GOODS_LIST_UNIT_BUY_RATE, Constants.RR_TAB2_RATE_TITLE); 
        rate2.setAlign(Alignment.RIGHT);
        ListGridField v2 = new ListGridField(Constants.RR_TAB2_v2, Constants.RR_TAB2_v2_TITLE); 
        v2.setWidth(24);
        ListGridField f2 = new ListGridField(Constants.RR_TAB2_f2, Constants.RR_TAB2_f2_TITLE);
        f2.setWidth(24);
        ListGridField order2 = new ListGridField(Constants.RR_TAB2_PO_ORDER, Constants.RR_TAB2_PO_ORDER_TITLE); 
        moreDetailsList.setCanResizeFields(true);          
        moreDetailsList.setFields(productID2, productDetail2,productdept2,productAmount2,productUnit2,rate2,v2,f2,order2); 
        moreDetailsList.addCellContextClickHandler(new CellContextClickHandler() {
			@Override
			public void onCellContextClick(final CellContextClickEvent rightClickEvent) {
				tab2ListContextMenu().showContextMenu();
			}
		});
	
	}
	
	private void paymentGridStack(){
		paymentGridStack = new VLayout();		
		formF7 = new DynamicForm();	
		formF7.setNumCols(7);
		formF7.setAutoWidth();
		formF7.setItemLayout(FormLayoutType.TABLE);
		formF7.setAlign(Alignment.RIGHT);		
		formF7.setTitleSuffix("");
		formF7.setTitleOrientation(TitleOrientation.TOP);
		//formF7.setDataSource(RRCreditTab3Top.getInstance());
		//formF7.setUseAllDataSourceFields(false);
		
		ButtonItem editdata = new ButtonItem(Constants.RR_TAB3_EDIT_DATA, Constants.RR_TAB3_EDIT_DATA_TITLE);
		editdata.setAlign(Alignment.RIGHT);
		editdata.setVAlign(VerticalAlignment.BOTTOM);
		editdata.setRowSpan(2);           
        SelectItem selectTax = new SelectItem(Constants.RR_TAB3_SELECT_TAX, Constants.RR_TAB3_SELECT_TAX_TITLE); 
        LinkedHashMap<String, String> taxValueMap = new LinkedHashMap<String, String>();
        taxValueMap.put (Constants.RR_TAB3_TAX_TYPES0, Constants.RR_TAB3_TAX_TYPES0_TITLE);
        taxValueMap.put (Constants.RR_TAB3_TAX_TYPES1, Constants.RR_TAB3_TAX_TYPES1_TITLE);		
		selectTax.setValueMap(taxValueMap);
		
        TextItem billID = new TextItem(Constants.RR_TAB3_BILL_RECEIPT, Constants.RR_TAB3_BILL_RECEIPT_TITLE);   
        FloatItem total = new FloatItem(Constants.RR_SUM_PRICE, Constants.RR_SUM_PRICE_TITLE);
        total.setFormat(Constants.NUMBER_2_DECIMAL);
        total.setTextAlign(Alignment.RIGHT);
        FloatItem netTotal = new FloatItem(Constants.RR_TAB3_NET_TOTAL, Constants.RR_TAB3_NET_TOTAL_TITLE); 
        netTotal.setFormat(Constants.NUMBER_2_DECIMAL);
        netTotal.setTextAlign(Alignment.RIGHT);        
        FloatItem debt = new FloatItem(Constants.RR_TAB3_DEPT, Constants.RR_TAB3_DEPT_TITLE); 
        debt.setFormat(Constants.NUMBER_2_DECIMAL);
        debt.setTextAlign(Alignment.RIGHT);    
        FloatItem vat = new FloatItem(Constants.RR_TAB3_VAT, Constants.RR_TAB3_VAT_TITLE);    
        vat.setFormat(Constants.NUMBER_2_DECIMAL);
        vat.setTextAlign(Alignment.RIGHT);
        
        formF7.setFields(editdata,selectTax,billID,total,netTotal,debt,vat);
		        
		HLayout hTab3 = new HLayout();
		hTab3.setWidth100();
		hTab3.setHeight100();
		hTab3.setAlign(Alignment.CENTER);
		
		formLeftF7 = new DynamicForm();
		formLeftF7.setTitleSuffix("");
		formLeftF7.setWidth("17%");
		//formLeftF7.setDataSource(RRCreditTab3Left.getInstance());
		//formLeftF7.setUseAllDataSourceFields(false);
		
		TextItem completePayL = new TextItem(Constants.RR_TAB3_COMPLETE_PAY, Constants.RR_TAB3_COMPLETE_PAY_TITLE);
		completePayL.setWidth(24);
		completePayL.setWrapTitle(false);
		completeDateL = new DateItem(Constants.RR_TAB3_COMPLETE_DATE_L, Constants.RR_TAB3_COMPLETE_DATE_TITLE);
		completeDateL.setWidth(70);
		completeDateL.setDisplayFormat(DateDisplayFormat.TOEUROPEANSHORTDATE);
		completeDateL.setUseMask(true);
		completeDateL.setUseTextField(true);
		completeDateL.setWrapTitle(false);
		
		formLeftF7.setFields(completePayL,completeDateL);
		hTab3.addMember(formLeftF7);
		
		listtab3 = new ListGrid();  
		listtab3.setWidth100();  
		listtab3.setHeight100();  
		//listtab3.setShowCellContextMenus(true);
		listtab3.setContextMenu(tab3ListContextMenu());
		//listtab3.setShowAllRecords(true); 
		//listtab3.setDataSource(RRCreditTab3Grid.getInstance());
		//listtab3.setUseAllDataSourceFields(false);
        ListGridField billNo3 = new ListGridField(Constants.RR_TAB3_BILL_PAID, Constants.RR_TAB3_BILL_PAID_TITLE);
        billNo3.setAlign(Alignment.LEFT);
        ListGridField date3 = new ListGridField(Constants.RR_TAB3_BILL_DATE, Constants.RR_TAB3_BILL_DATE_TITLE);  
        ListGridField payDate3 = new ListGridField(Constants.RR_TAB3_BILL_PAY_DATE, Constants.RR_TAB3_BILL_PAY_DATE_TITLE);  
        ListGridField total3 = new ListGridField(Constants.RR_TAB3_BILL_PAY_TOTAL, Constants.RR_TAB3_BILL_PAY_TOTAL_TITLE);
        total3.setAlign(Alignment.RIGHT);
        ListGridField completeDateR = new ListGridField(Constants.RR_TAB3_COMPLETE_DATE_R, Constants.RR_TAB3_COMPLETE_DATE_TITLE);
        listtab3.setFields(billNo3,date3,payDate3,total3,completeDateR);
        
        listtab3.addCellContextClickHandler(new CellContextClickHandler() {
			@Override
			public void onCellContextClick(final CellContextClickEvent rightClickEvent) {
				tab3ListContextMenu().showContextMenu();
			}
		});
        
        hTab3.addMember(listtab3);
        
        paymentGridStack.addMember(formF7);
        paymentGridStack.addMember(hTab3);
	}
	
	private static void addRecordToTaxList() {
		
		if(Integer.parseInt(formh2.getField(Constants.VENDOR_LIST_ADD_VAT_TYPES).getValue().toString())==0) {
			outputTaxList.selectAllRecords();
			outputTaxList.removeSelectedData();
		}else{
			Record record = new Record();
			record.setAttribute(Constants.RR_TAB4_TAX_PERIOD, DateTimeFormat.getFormat(Constants.MM_YY).format((Date)formh2.getField(Constants.RR_RECEIPT_DATE).getValue()));
			record.setAttribute(Constants.RR_TAB4_TAX_DATE, DateTimeFormat.getFormat(Constants.DD_MM_YY).format((Date)formh2.getField(Constants.RR_RECEIPT_DATE).getValue()));
			record.setAttribute(Constants.RR_TAB4_TAX_ID, Constants.RR_ID+((int)(Math.random() * 99999) + 1));
			//record.setAttribute("taxDepartment", formh2.getField("departmentId").getValue().toString());
			record.setAttribute(Constants.RR_TAB4_TAX_SPEC, Constants.RR_TAB4_INIT_TAX_SPEC+vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.VENDOR_LIST_NAME));
			record.setAttribute(Constants.RR_TAB4_VALUE, (Double.parseDouble(priceForm.getField(Constants.RR_SUM_PRICE).getValue().toString())-Double.parseDouble(priceForm.getField(Constants.RR_SUM_TAX_PERCENT).getValue().toString())));
			record.setAttribute(Constants.RR_TAB4_TAX, priceForm.getField(Constants.RR_SUM_TAX_PERCENT).getValue());
			//record.setAttribute("value4", 0);
			//record.setAttribute("tax4", 0);
			record.setAttribute(Constants.RR_TAB4_UNVALUE, 0);
			record.setAttribute(Constants.RR_TAB4_UNTAX, 0);			
			record.setAttribute(Constants.RR_TAB4_TAX_SUM_PRICE, 0);
			//record.setAttribute("newNo", 0);
			//record.setAttribute("remark4", 0);
			outputTaxList.selectAllRecords();
			outputTaxList.removeSelectedData();
			outputTaxList.addData(record);
		}	
	}
	
	private void initOutputTaxListGrid(){
		outputTaxList = new ListGrid();  
		outputTaxList.setWidth100();  
		outputTaxList.setHeight100();  
        outputTaxList.setHeaderHeight(40);
        //outputTaxList.setDataSource(RRCreditTab4.getInstance()); 
        outputTaxList.setAutoFetchData(true);
        outputTaxList.setCanEdit(true);
        outputTaxList.setUseAllDataSourceFields(false);
        outputTaxList.setShowAllRecords(true);  
        
        ListGridField taxPeriod = new ListGridField(Constants.RR_TAB4_TAX_PERIOD, Constants.RR_TAB4_TAX_PERIOD_TITLE);
        taxPeriod.setAlign(Alignment.CENTER); 
        taxPeriod.setFormat(Constants.DD_MM_YY);
        //taxPeriod.toString();        
        ListGridField taxPeriodDate = new ListGridField(Constants.RR_TAB4_TAX_DATE, Constants.RR_TAB4_TAX_DATE_TITLE);
        taxPeriodDate.setFormat(Constants.DD_MM_YYYY);
        taxPeriodDate.setAlign(Alignment.CENTER);
        taxPeriodDate.setWidth(60);
        ListGridField taxId = new ListGridField(Constants.RR_TAB4_TAX_ID, Constants.RR_TAB4_TAX_ID_TITLE);       
        taxId.setAlign(Alignment.CENTER);
        ListGridField taxDepartment = new ListGridField(Constants.RR_TAB4_TAX_DEPARTMENT, Constants.RR_TAB4_TAX_DEPARTMENT_TITLE);  
        taxDepartment.setAlign(Alignment.CENTER);
        taxDepartment.setWidth(60);
        
        PickerIcon taxNameSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {  
            public void onFormItemClick(FormItemIconClickEvent event) {              	
            	//goodsStorageTypesList.showgsTypesList();
            		}   
        });	
        ListGridField taxSpec = new ListGridField(Constants.RR_TAB4_TAX_SPEC, Constants.RR_TAB4_TAX_SPEC_TITLE);  
        taxSpec.setAlign(Alignment.CENTER);
        taxSpec.setIcons(taxNameSearch);
        
        taxSpec.setCellAlign(Alignment.LEFT);
        ListGridField value4 = new ListGridField(Constants.RR_TAB4_VALUE, Constants.RR_TAB4_VALUE_TITLE); 
        value4.setFormat(Constants.NUMBER_2_DECIMAL);
        value4.setAlign(Alignment.CENTER);
        value4.setCellAlign(Alignment.RIGHT);
        ListGridField tax4 = new ListGridField(Constants.RR_TAB4_TAX, Constants.RR_TAB4_TAX_TITLE);  
        tax4.setFormat(Constants.NUMBER_2_DECIMAL);
        tax4.setAlign(Alignment.CENTER);
        tax4.setCellAlign(Alignment.RIGHT);
        ListGridField unvalue4 = new ListGridField(Constants.RR_TAB4_UNVALUE, Constants.RR_TAB4_VALUE_TITLE);
        unvalue4.setFormat(Constants.NUMBER_2_DECIMAL);
        unvalue4.setAlign(Alignment.CENTER);
        unvalue4.setCellAlign(Alignment.RIGHT);
        ListGridField untax4 = new ListGridField(Constants.RR_TAB4_UNTAX, Constants.RR_TAB4_TAX_TITLE);
        untax4.setFormat(Constants.NUMBER_2_DECIMAL);
        untax4.setAlign(Alignment.CENTER);
        untax4.setCellAlign(Alignment.RIGHT);
        ListGridField taxSumPrice = new ListGridField(Constants.RR_TAB4_TAX_SUM_PRICE, Constants.RR_TAB4_TAX_SUM_PRICE_TITLE);
        taxSumPrice.setFormat(Constants.NUMBER_2_DECIMAL);
        taxSumPrice.setAlign(Alignment.CENTER); 
        taxSumPrice.setCellAlign(Alignment.RIGHT);
        ListGridField newNo4 = new ListGridField(Constants.RR_TAB4_NEW_NO, Constants.RR_TAB4_NEW_NO_TITLE);  
        newNo4.setAlign(Alignment.CENTER);
        ListGridField remark4 = new ListGridField(Constants.RR_TAB4_REMARK, Constants.RR_TAB4_REMARK_TITLE);
        remark4.setAlign(Alignment.CENTER);
        outputTaxList.setCanResizeFields(true);  
        
        outputTaxList.setFields( taxPeriod,taxPeriodDate,taxId,taxDepartment,taxSpec,value4,tax4,unvalue4,untax4,taxSumPrice,newNo4,remark4); 
        
        outputTaxList.setHeaderSpans(  
                new HeaderSpan(Constants.RR_TAB4_HEAD_SPAN1, new String[]{Constants.RR_TAB4_TAX_DATE, Constants.RR_TAB4_TAX_ID}),
                new HeaderSpan(Constants.RR_TAB4_HEAD_SPAN2, new String[]{Constants.RR_TAB4_VALUE, Constants.RR_TAB4_TAX}),
                new HeaderSpan(Constants.RR_TAB4_HEAD_SPAN3, new String[]{Constants.RR_TAB4_UNVALUE, Constants.RR_TAB4_UNTAX})
                );
		
	}
	
	private void initTaxListGrid(){
	taxList = new ListGrid();  
	taxList.setWidth100();  
	taxList.setHeight100();  
	taxList.setHeaderHeight(40);
	//taxList.setDataSource(RRCreditTab5.getInstance());
	//taxList.setUseAllDataSourceFields(false);
	//taxList.setShowAllRecords(true); 
    
    ListGridField no5= new ListGridField(Constants.RR_TAB5_NO, Constants.RR_TAB5_NO_TITLE);  
    no5.setAlign(Alignment.CENTER);
    ListGridField date5 = new ListGridField(Constants.RR_TAB5_DATE, Constants.RR_TAB5_DATE_TITLE); 
    date5.setAlign(Alignment.CENTER);
    ListGridField department5 = new ListGridField(Constants.RR_TAB5_DEPARTMENT, Constants.RR_TAB5_DEPARTMENT_TITLE);
    department5.setAlign(Alignment.CENTER);
    ListGridField incomeType5 = new ListGridField(Constants.RR_TAB5_PAYMENT_TYPE, Constants.RR_TAB5_PAYMENT_TYPE_TITLE); 
    incomeType5.setAlign(Alignment.CENTER);
    ListGridField percent5 = new ListGridField(Constants.RR_TAB5_PERCENT, Constants.RR_TAB5_PERCENT_TITLE);  
    percent5.setAlign(Alignment.CENTER);
    ListGridField amount5 = new ListGridField(Constants.RR_TAB5_NET, Constants.RR_TAB5_NET_TITLE); 
    amount5.setAlign(Alignment.CENTER);
    ListGridField taxIncome5 = new ListGridField(Constants.RR_TAB5_TAX, Constants.RR_TAB5_TAX_TITLE); 
    taxIncome5.setAlign(Alignment.CENTER);
    taxList.setCanResizeFields(true);  
    
    taxList.setFields( no5,date5,department5,incomeType5,percent5,amount5,taxIncome5); 
    
    taxList.setHeaderSpans(  
            new HeaderSpan(Constants.RR_TAB5_HEAD_SPAN1, new String[]{Constants.RR_TAB5_NO, Constants.RR_TAB5_DATE}),
            new HeaderSpan(Constants.RR_TAB5_HEAD_SPAN2, new String[]{Constants.RR_TAB5_NET, Constants.RR_TAB5_TAX})              
            );
}
	public static void addGoodsToListAsRecord(Record record){
		record.getAttributeAsRecord(Constants.RR_GOODS_RECORD).setAttribute(Constants.RR_TAB1_GOODS_AMOUNT, 1);
		int amount = Integer.parseInt(record.getAttributeAsRecord(Constants.RR_GOODS_RECORD).getAttribute(Constants.RR_TAB1_GOODS_AMOUNT).toString());
		//SC.say(record.getAttributeAsRecord("goods").getAttribute("sellPrice1").toString());
		double sellPrice1 = Double.parseDouble(record.getAttributeAsRecord(Constants.RR_GOODS_RECORD).getAttribute(Constants.GOODS_LIST_PRICE1).toString());
		
		double price = (amount*sellPrice1);
		record.getAttributeAsRecord(Constants.RR_GOODS_RECORD).setAttribute(Constants.RR_TAB1_GOODS_TOTAL_AMOUNT, price);
		goodsList.addData(record.getAttributeAsRecord(Constants.RR_GOODS_RECORD));
		//goodsList.startEditingNew();
		//SC.say(""+(goodsList.getRecords().length-1));
		//goodsList.startEditing((goodsList.getRecords().length-1));
		//goodsList.getEditRow();
		//goodsList.getEditedRecord(0);
		//goodsList.startEditingNew(record.getAttributeAsRecord("goods"));
		goodsList.refreshFields();
		
		double totalPrice = 0.0;
		for(Record goodRecordFromList:goodsList.getRecords()) {
			double goodTotalPrice = Double.parseDouble(goodRecordFromList.getAttribute(Constants.RR_TAB1_GOODS_TOTAL_AMOUNT).toString());
			totalPrice += goodTotalPrice;
		}
		priceForm.getField(Constants.RR_SUM_ALL_GOODS_PRICE).setValue(totalPrice);		
		//calculateTotalPrice();
		//moreDetailsList.addData(record.getAttributeAsRecord("goods"));
	}
	/*
	public void setRRCreditAsRecord(Record record) {
		formh1.getField("vID").setValue(record.getAttribute("vID"));
		formh1.getField("vendorName").setValue(record.getAttribute("vendorName"));
		formh1.getField("vendorAddress").setValue(record.getAttribute("vendorAddress"));
		formh1.getField("tel").setValue(record.getAttribute("tel"));
		show();
	}
	 */

	public static void setVendorAsRecord(Record record) {
		vendorList.hide();
		vRecord = record;
		//formh1.setValues(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).toMap());
		formh1.getField(Constants.VENDOR_ADD_CODE).setValue(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.VENDOR_ADD_CODE));
		formh1.getField(Constants.VENDOR_LIST_NAME).setValue(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.VENDOR_LIST_NAME));
		formh1.getField(Constants.VENDOR_LIST_ADDRESS_1).setValue(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.VENDOR_LIST_ADDRESS_1));
		formh1.getField(Constants.VENDOR_LIST_ADDRESS_2).setValue(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.VENDOR_LIST_ADDRESS_2));
		formh1.getField(Constants.VENDOR_LIST_ADD_POST).setValue(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.VENDOR_LIST_ADD_POST));
		formh1.getField(Constants.VENDOR_LIST_ADD_TEL).setValue(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.VENDOR_LIST_ADD_TEL));
		formh1.getField(Constants.RR_REMARK_NAME).setValue(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.RR_REMARK_NAME));
		
		formh2.getField(Constants.VENDOR_LIST_ADD_CREDIT).setValue(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.VENDOR_LIST_ADD_CREDIT));
		formh2.getField(Constants.VENDOR_LIST_ADD_VAT_TYPES).setValue(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.VENDOR_LIST_ADD_VAT_TYPES));
		formh2.getField(Constants.TRANSPORTID).setValue(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.TRANSPORTID));
	}
	/*
	public static void setVendorToRecord(Record record){
		vRecord = record;
		formh1.getField("vID").setValue(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute("vID"));
		formh1.getField("vName").setValue(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute("vName"));
		formh1.getField("vAddress1").setValue(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute("vAddress1"));
		formh1.getField("vAddress2").setValue(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute("vAddress2"));
		formh1.getField("vPost").setValue(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute("vPost"));
		formh1.getField("vTel").setValue(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute("vTel"));
		formh1.getField("vRemarkName").setValue(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute("vRemarkName"));
	
		formh2.getField("creditDate").setValue(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute("vCredit"));
		formh2.getField("priceType").setValue(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute("vPriceType"));
		formh2.getField("transportMethodId").setValue(record.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute("vTransferByID"));
		
		saleForm.getField("salesAreaId").setValue(record.getAttributeAsRecord("customer").getAttributeAsRecord("sales").getAttributeAsRecord("salesArea").getAttribute("salesAreaShortNameTH"));
		saleForm.getField("salesAreaId").setHint(record.getAttributeAsRecord("customer").getAttributeAsRecord("sales").getAttributeAsRecord("salesArea").getAttribute("salesAreaDetailsTH"));
		saleForm.getField("salesId").setValue(record.getAttributeAsRecord("customer").getAttributeAsRecord("sales").getAttribute("salesId"));
		saleForm.getField("salesName").setValue(record.getAttributeAsRecord("customer").getAttributeAsRecord("sales").getAttribute("salesName"));
		saleForm.getField("transportMethodId").setValue(record.getAttributeAsRecord("customer").getAttributeAsRecord("transportMethod").getAttribute("transportMethodId"));
		saleForm.getField("transportMethodId").setHint(record.getAttributeAsRecord("customer").getAttributeAsRecord("transportMethod").getAttribute("transportMethodShortNameTH"));		
	}
	 */
	public static void setTransportMethodAsRecord(Record record){	
		transportMethodList.hide();
		formh2.getField(Constants.TRANSPORTID).setValue(record.getAttributeAsRecord(Constants.RR_TRANSPORT_RECORD).getAttribute(Constants.TRANSPORTID));
		formh2.getField(Constants.TRANSPORTID).setHint(record.getAttributeAsRecord(Constants.RR_TRANSPORT_RECORD).getAttribute(Constants.TRANSPORT_SHORTNAME_TH));
	}		
	
	public static void setDepartmentAsRecord(Record record){
		departmentList.hide();
		formh2.getField(Constants.DEPARTMENTID).setValue(record.getAttributeAsRecord(Constants.RR_DEPARTMENT_RECORD).getAttribute(Constants.DEPARTMENTID));
		formh2.getField(Constants.DEPARTMENTID).setHint(record.getAttributeAsRecord(Constants.RR_DEPARTMENT_RECORD).getAttribute(Constants.DEPARTMENT_SHORTNAME_TH));
	}

	public static void setGoodsStorageTypesAsRecord(Record record){
		//TODO:MessageService;
		//goodsList.getSelectedRecord().setAttribute(Constants.RR_GOODS_STORAGE_TYPE_RECORD,record.getAttributeAsRecord("gsTypes").getAttribute("gsTypesId"));		
		
		/*
		String msg = "";
		Record[] r = goodsList.getRecords();
		for(Record s:r){
			for(String a:s.getAttributes()) {
				msg += a + ":"+s.getAttribute(a)+"<br/>";
			}
		}
		*/
		//SC.say(msg);
		
		//Record goodStorageRecord = GoodsStorageTypesList.getGsTypesList().getSelectedRecord();
		//SC.say(goodStorageRecord.getAttribute(Constants.GOOD_STORAGE_TYPESID));
		//goodsStorageTypesList.hide();	
		
		
		//goodsList.getRecord(0).getAttribute(Constants.GOOD_STORAGE_TYPESID);
		//(Constants.GOOD_STORAGE_TYPESID).setSingleCellValue(record.getAttributeAsRecord(Constants.RR_GOODS_STORAGE_TYPE_RECORD).getAttribute(Constants.GOOD_STORAGE_TYPESID));
		//goodsList.getField("").setValueField(record.getAttributeAsRecord(Constants.RR_GOODS_STORAGE_TYPE_RECORD).getAttribute(Constants.GOOD_STORAGE_TYPESID));;
		//goodsList.getField(2).setValueField(record.getAttributeAsRecord(Constants.RR_GOODS_STORAGE_TYPE_RECORD).getAttribute(Constants.GOOD_STORAGE_TYPESID));
		//goodsList.getRecordIndex(goodsList.getEditedRecord(0));	
		//goodsList.getEditedRecord(0).setAttribute("gsTypesId",record.getAttributeAsRecord(Constants.RR_GOODS_STORAGE_TYPE_RECORD).getAttribute(Constants.GOOD_STORAGE_TYPESID));//.setValue(record.getAttributeAsRecord(Constants.RR_GOODS_STORAGE_TYPE_RECORD).getAttribute(Constants.GOOD_STORAGE_TYPESID));
		//goodsList.setSingleCellValueProperty(Constants.GOOD_STORAGE_TYPESID);//.setValue(record.getAttributeAsRecord(Constants.RR_GOODS_STORAGE_TYPE_RECORD).getAttribute(Constants.GOOD_STORAGE_TYPESID));
	}
}
