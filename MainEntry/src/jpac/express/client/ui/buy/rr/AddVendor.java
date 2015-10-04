package jpac.express.client.ui.buy.rr;

import java.util.LinkedHashMap;

//import jpac.express.client.datasource.Vendor;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.FormLayoutType;
import com.smartgwt.client.types.KeyNames;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FloatItem;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.layout.HStack;
import com.smartgwt.client.widgets.layout.VStack;

public class AddVendor extends CustomWindow{
	
	private static VStack mainLayout;
	private static HStack v1;
	private static HStack v2;
	private static HStack v3;
	private static HStack v4;
	
	private static DynamicForm formV1;
	private static DynamicForm formV2;
	private static DynamicForm formV3;
	private static TextItem taxType;
	private Button submit;
	private Button cancel;
	
	private static PaymentConditionsList paymentConditionsList;
	private static PaymentTypesList paymentTypesList;
	private static RemarkList remarkList;
	private static TaxTypesList taxTypesList;
	private static VendorAccountList vendorAccountList;
	private static VendorTitleList vendorTitleList;
	private static VendorTypeList vendorTypeList;
	private static TransportMethodList transportMethodList;
	private static Record vRecord;
	private static final String name = Constants.RR_ADDVENDORS_TITLE;
	private String action = Constants.RECORD_ACTION_NULL;
	
	public AddVendor(){ 
		super(Constants.RR_ADDVENDORSID, name, 750, 500, true);
		vRecord = new Record();
		vRecord.setAttribute(Constants.RR_VENDOR_RECORD, new Record());
		vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).setAttribute(Constants.RR_VENDOR_RECORD , new Record());
		
		initWidgets();
		hide();
		//DataSource conDS = DataSource.get("Vendor");
		/*
		ListGrid vGrid = new ListGrid();
		vGrid.setDataSource(conDS);
		*/
		/*VLayout 1  Part 1*/					
	}
	
	public void showAddVendor(Record record){	
		formV1.clearValues();		
		formV1.getField(Constants.VENDOR_ADD_CODE).setCanEdit(true);
		formV2.clearValues();
		formV2.getField(Constants.TAX_TYPES_LISTID).setHint("");
		formV3.clearValues();
		centerInPage();
		show();
		formV1.focusInItem(Constants.VENDOR_ADD_CODE);
	}
		public void showAddVendor(Record record, String action){
		
		if(action.equals(Constants.RECORD_ACTION_EDIT)){
			this.action = action;
			//SC.say(v1.getDynamicContents().toString());			
			formV1.setValues(record.toMap());
			formV2.setValues(record.toMap());
			formV3.setValues(record.toMap());
			
			formV1.getField(Constants.VENDOR_ADD_CODE).setCanEdit(false);			
			formV1.editSelectedData(VendorList.getInstance().getVendorList());
			formV2.editSelectedData(VendorList.getInstance().getVendorList());
			formV3.editSelectedData(VendorList.getInstance().getVendorList());
		}
		
		centerInPage();
		show();
	}

	private void initWidgets(){
		disableMaximizeButton();
		disableMinimizeButton();
		disableHeaderSection();
		disableControlSection();
		disableCanResize();
		disableFooterSection();
		
		paymentConditionsList = PaymentConditionsList.getInstance();
		paymentTypesList = PaymentTypesList.getInstance();
		taxTypesList = TaxTypesList.getInstance();
		remarkList = RemarkList.getInstance();
		vendorAccountList = VendorAccountList.getInstance();
		vendorTitleList = VendorTitleList.getInstance();
		vendorTypeList = VendorTypeList.getInstance();
		transportMethodList = TransportMethodList.getInstance();
		mainLayout = new VStack();
		//mainLayout.setHeight100();
		mainLayout.setWidth100();
		mainLayout.setLayoutAlign(Alignment.CENTER);
		
		v1 = new HStack();			
		v1.setWidth("95%");
		v1.setAutoHeight();
		v1.setMargin(2);
        v1.setPadding(2);
		v1.setLayoutAlign(Alignment.CENTER);
		//v1.setAlign(Alignment.CENTER);
		//v1.setAlign(Alignment.CENTER);
		//v1.setBackgroundColor("#cccccc");
		//v1.setAlign(VerticalAlignment.CENTER);
			
		formV1 = new DynamicForm();	
		formV1.setWidth100();
		//formV1.setDataSource(Vendor.getInstance());
		//formV1.setAlign(Alignment.CENTER);
		formV1.setItemLayout(FormLayoutType.TABLE);
		formV1.setTitleSuffix("");
		//formV1.setCellBorder(1);
		//formV1.setDataSource(conDS);
		//formV1.setBorder("solid thin #ffcc00");			
		formV1.setNumCols(6);
		formV1.setColWidths("19%","19%","19%","19%","12%","12%");
		//formV1.setAutoWidth();
		StaticTextItem autoId = new StaticTextItem();
		autoId.setName(Constants.IDS); 
	    TextItem vID = new TextItem(Constants.VENDOR_ADD_CODE, Constants.VENDOR_ADD_TITLE_CODE); 
	    vID.setWrapTitle(false);	
	           
	    PickerIcon searchsalutation = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {  
	           public void onFormItemClick(FormItemIconClickEvent event) {  
	        	   vendorTitleList.showVendorTitleList();
	            }  
	        });
	        TextItem salutation = new TextItem(Constants.VENDOR_TITLE_NAME, Constants.VENDOR_LIST_ADD_SALUTATION); 		
	        salutation.setWrapTitle(false);
	        salutation.setIcons(searchsalutation);	
	        salutation.setColSpan(3);
	        	    
	        TextItem vName = new TextItem();	       
	        vName.setTitleColSpan(3);
	        vName.setColSpan(3);
	        vName.setWidth(300);
	        vName.setName(Constants.VENDOR_LIST_NAME); 
	        vName.setTitle(Constants.VENDOR_LIST_TITLE_NAME); 
	        	       
	        formV1.setFields(autoId,vID,salutation,vName); 	    
	        formV1.hideItem(Constants.IDS);
	        v1.addMember(formV1);
	        
	        /*VLayout 1 Part 2*/    
	        v2 = new HStack();
	        v2.setLayoutAlign(Alignment.CENTER);
	        //v2.setHeight(210);
	        v2.setWidth("95%");
	        //v2.setBackgroundColor("#cccccc");
	        //v2.setAlign(Alignment.CENTER);
	        v2.setMargin(2);
	        v2.setPadding(2);	
	        
	        formV2 = new DynamicForm();	 
	        //formV2.setDataSource(Vendor.getInstance());
	        formV2.setBorder(Constants.BORDER_WHITE_THIN);
	        formV2.setTitleSuffix("");
	        formV2.setWidth100();
	        //formV2.setCellBorder(1);	        
			formV2.setItemLayout(FormLayoutType.TABLE);
			//formV2.setDataSource(conDS);			
		 	formV2.setNumCols(6);
		 	formV2.setColWidths("19%","19%","19%","19%","12%","12%");
	        
		 	TextItem vAddress1 = new TextItem(); 
		 	vAddress1.setColSpan(6);
		 	vAddress1.setWidth(500);
	        vAddress1.setName(Constants.VENDOR_LIST_ADDRESS_1);
	        vAddress1.setTitle(Constants.VENDOR_LIST_ADD_ADDRESS); 
	        
	        TextItem vAddress2 = new TextItem(); 
	        vAddress2.setColSpan(6);
	        vAddress2.setWidth(500);
	        vAddress2.setName(Constants.VENDOR_LIST_ADDRESS_2);
	        vAddress2.setTitle(""); 
	        
	        TextItem vPost = new TextItem();
	        vPost.setColSpan(6);	       
	        vPost.setName(Constants.VENDOR_LIST_ADD_POST);
	        vPost.setTitle(Constants.VENDOR_LIST_ADD_TITLE_POST);
	        //vPost.setMask("#####");
	        
	        TextItem vTel = new TextItem(); 
	        vTel.setColSpan(6);
	        vTel.setWidth(500);
	        vTel.setName(Constants.VENDOR_LIST_ADD_TEL);
	        vTel.setTitle(Constants.VENDOR_LIST_ADD_TITLE_TEL); 
	        //vTel.setBrowserInputType("digits");
	        
	        TextItem pName = new TextItem(); 
	        pName.setColSpan(6);
	        pName.setWidth(500);
	        pName.setName(Constants.VENDOR_LIST_ADD_CO_PERSON);
	        pName.setTitle(Constants.VENDOR_LIST_ADD_TITLE_CO_PERSON); 
	        
	        PickerIcon searchRemark = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {  
	            public void onFormItemClick(FormItemIconClickEvent event) {  
	            	remarkList.showRemarkList();  
	            }  
	        }); 
	        TextItem remark = new TextItem(Constants.RR_REMARK_NAME, Constants.VENDOR_LIST_ADD_REMARK); 
	        remark.setColSpan(6);
	        remark.setWidth(470);
	        remark.setIcons(searchRemark);  
	        
	        TextItem taxID = new TextItem(); 
	        //taxID.setColSpan(6);
	        taxID.setName(Constants.VENDOR_LIST_ADD_TAXID);
	        taxID.setTitle(Constants.VENDOR_LIST_ADD_TITLE_TAXID);	       
	        //taxID.setMask("#############");
	        
	        PickerIcon searchInType = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {  
	            public void onFormItemClick(FormItemIconClickEvent event) {  
	            	paymentTypesList.showPaymentTypesList();
	            }  
	        });
	        TextItem inType = new TextItem(Constants.RR_PAYMENT_TYPE_NAME, Constants.VENDOR_LIST_ADD_PAYMENT_TYPE);
	        inType.setWidth(130);
	        inType.setIcons(searchInType);  
	        
	        FloatItem taxRate = new FloatItem(); 
	        taxRate.setWidth(60);
	        taxRate.setName(Constants.VENDOR_LIST_ADD_TAXRATE);
	        taxRate.setTitle(Constants.VENDOR_LIST_ADD_TITLE_TAXRATE);
	        taxRate.setTextAlign(Alignment.RIGHT);
	        taxRate.setFormat(Constants.NUMBER_2_DECIMAL);
	        /*
	        TextItem taxType = new TextItem();
	        taxType.setWidth(42);
	        taxType.setTitleColSpan(3);
	        taxType.setName("taxTypeID");
	        taxType.setTitle("หมวดภาษีหัก ณ ที่จ่าย");
	        */
	        
	        PickerIcon searchTaxType = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {  
	            public void onFormItemClick(FormItemIconClickEvent event) {  
	            	taxTypesList.showTaxTypesList();
	            }  
	        });
	        taxType = new TextItem(Constants.TAX_TYPES_LISTID, Constants.VENDOR_LIST_ADD_TAX_CATEGORY); 
	        taxType.setWidth(60);
	        taxType.setTitleColSpan(3);
	        taxType.setIcons(searchTaxType);  
	        
	        TextItem taxCondition = new TextItem(); 
	        taxCondition.setWidth(24);
	        taxCondition.setName(Constants.VENDOR_LIST_ADD_TAX_CONDITION);
	        taxCondition.setTitle(Constants.VENDOR_LIST_ADD_TITLE_TAX_CONDITION);
	        taxCondition.setWrapTitle(false);
	        
	        formV2.setFields(vAddress1,vAddress2,vPost,vTel,pName,remark,taxID,inType,taxRate,taxType,taxCondition);
	        v2.addMember(formV2);	        
	        
	        /*VLayout 1 Part 3*/	        
	        v3 = new HStack();
	        //v3.setHeight(180);
	        v3.setWidth("95%");
	        v3.setLayoutAlign(Alignment.CENTER);
	        //v3.setAlign(Alignment.CENTER);
	        v3.setMargin(2);
	        v3.setPadding(2);	
	        //v3.setBorder("solid medium #dddddd");
	        
	        formV3 = new DynamicForm();
	        //formV3.setDataSource(Vendor.getInstance());
	        formV3.setBorder(Constants.BORDER_WHITE_THIN);
	        formV3.setTitleSuffix("");
	        formV3.setWidth100();
	        //formV3.setCellBorder(2);	        
			formV3.setItemLayout(FormLayoutType.TABLE);
			//formV3.setDataSource(conDS);	
		 	formV3.setNumCols(6);
		 	formV3.setColWidths("19%","19%","19%","19%","12%","12%");
	        		 			 
		 	PickerIcon searchVType = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {  
	            public void onFormItemClick(FormItemIconClickEvent event) {  
	            	vendorTypeList.showVendorTypeList();
	            }  
	        });
	        TextItem vType = new TextItem(Constants.VENDOR_TYPEID, Constants.VENDOR_LIST_ADD_VENDOR_TYPES); 
	        vType.setWidth(60);	
	        //vType.setColSpan(3);		 		
	        vType.setIcons(searchVType);
		 	
	        StaticTextItem vTypeName = new StaticTextItem(Constants.VENDOR_TYPE_FULLNAME_TH); 
	        vTypeName.setShowTitle(false);
	        vTypeName.setColSpan(2);
	        
	        TextItem credit = new TextItem(); 
	        credit.setWidth(50);
	        credit.setName(Constants.VENDOR_LIST_ADD_CREDIT);
	        credit.setTitle(Constants.VENDOR_LIST_ADD_TITLE_CREDIT);	 
	        credit.setHint(Constants.VENDOR_LIST_ADD_CREDIT_DAYS); 
	        credit.setTextAlign(Alignment.RIGHT);
	 	        
	        PickerIcon searchAcc = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {  
	            public void onFormItemClick(FormItemIconClickEvent event) {  
	            	vendorAccountList.showVendorAccountList();
	            }  
	        });
	        TextItem accID = new TextItem(Constants.VENDOR_ACCID, Constants.VENDOR_LIST_ADD_ACCOUNTID); 
	        //accID.setColSpan(3);		        		 	
	        accID.setIcons(searchAcc);
	        
	        StaticTextItem vAccName = new StaticTextItem(Constants.VENDOR_ACC_NAME_TH); 
	        vAccName.setShowTitle(false);
	        vAccName.setColSpan(2);
	        
	        PickerIcon searchCon = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {  
	            public void onFormItemClick(FormItemIconClickEvent event) {  
	            	paymentConditionsList.showPaymentConditionsList(); 
	            }  
	        });
	        TextItem payCondition = new TextItem(Constants.RR_PAYMENT_CON_NAME, Constants.VENDOR_LIST_ADD_TITLE_PAY_CONDITION); 
	        payCondition.setWidth(120);
	        payCondition.setWrapTitle(false);
	        payCondition.setIcons(searchCon);
	        
	        SelectItem priceType = new SelectItem(); 
	        priceType.setColSpan(6);	        
	        priceType.setName(Constants.VENDOR_LIST_ADD_VAT_TYPES);
	        priceType.setTitle(Constants.VENDOR_LIST_ADD_TITLE_VAT_TYPES);
	        LinkedHashMap<String, String> priceMap = new LinkedHashMap<String, String> ();
	        priceMap.put (Constants.VAT_TYPES_VALUE_0, Constants.VAT_TYPES_0);
	        priceMap.put (Constants.VAT_TYPES_VALUE_1, Constants.VAT_TYPES_1);
	        priceMap.put (Constants.VAT_TYPES_VALUE_2, Constants.VAT_TYPES_2);
			priceType.setValueMap(priceMap);
			//priceType.setValue("2");
	        
			FloatItem vat = new FloatItem();
	        vat.setColSpan(3);
	        vat.setWidth(100);
	        vat.setName(Constants.VENDOR_LIST_ADD_VAT);
	        vat.setTitle(Constants.VENDOR_LIST_ADD_TITLE_VAT);	 
	        vat.setTextAlign(Alignment.RIGHT);
	        vat.setFormat(Constants.NUMBER_2_DECIMAL);
	        	              	        
	        FloatItem discount = new FloatItem(); 
	        discount.setName(Constants.VENDOR_LIST_ADD_DISCOUNT);
	        discount.setTitle(Constants.VENDOR_LIST_ADD_TITLE_DISCOUNT);
	        discount.setTextAlign(Alignment.RIGHT);
	        discount.setFormat(Constants.NUMBER_2_DECIMAL);
	        
	        PickerIcon searchTrans = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {  
	            public void onFormItemClick(FormItemIconClickEvent event) {  
	            	transportMethodList.showTransportMethodList();  
	            }  
	        });
	        TextItem transferByID = new TextItem(Constants.TRANSPORTID, Constants.VENDOR_LIST_ADD_TRANSPORT); 
	        //transferBy.setColSpan(3);
	        transferByID.setWidth(60);        		 	
	        transferByID.setIcons(searchTrans);
	        
	        StaticTextItem vTransferBy = new StaticTextItem(Constants.TRANSPORT_SHORTNAME_TH); 
	        vTransferBy.setShowTitle(false);
	        vTransferBy.setColSpan(2);
	        	              	        
	        FloatItem AppvLimit = new FloatItem(); 
	        //AppvLimit.setWidth(24);
	        AppvLimit.setName(Constants.VENDOR_LIST_ADD_APPROVED_LIMIT);
	        AppvLimit.setTitle(Constants.VENDOR_LIST_ADD_TITLE_APPROVED_LIMIT);	
	        AppvLimit.setTextAlign(Alignment.RIGHT);
	        AppvLimit.setFormat(Constants.NUMBER_2_DECIMAL);
	        
	        formV3.setFields(vType,vTypeName,credit,accID,vAccName,payCondition,priceType,vat,discount,transferByID,vTransferBy,AppvLimit);
	        v3.addMembers(formV3);
	        
	        v4 = new HStack();
	        v4.setWidth100();	
	        v4.setMargin(5);
	        v4.setPadding(5);
	        v4.setAutoHeight();
	        //v4.setHeight100();
	        v4.setAlign(Alignment.CENTER);
	        
	        /*
	        addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals(KeyNames.ENTER)) {
						departmentDetailsForm.getField("departmentId").setValue(departmentIdForm.getField("departmentId").getValue());
						Record record = new Record();
						record.setAttribute("department", new Record());
						Record.copyAttributesInto(record.getAttributeAsRecord("department"), departmentDetailsForm.getValuesAsRecord(), departmentDetailsForm.getValuesAsRecord().getAttributes());

						
						if(action.equals("edit")) {
							departmentDetailsForm.saveData();
							action = "";
						} else {
							DepartmentList.addDepartmentToListAsRecord(record,action);
						}
						hide();
					}
				}
			});
	        
	        */
	        
	        addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					if (event.getKeyName().equals(KeyNames.ENTER)) {
						
						vRecord.setAttribute(Constants.RR_VENDOR_RECORD, new Record());
						//vRecord.getAttributeAsRecord("vendor").setAttribute("vendor", new Record());
													
						Record.copyAttributesInto(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD), formV1.getValuesAsRecord(), formV1.getValuesAsRecord().getAttributes());
						Record.copyAttributesInto(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD), formV2.getValuesAsRecord(), formV2.getValuesAsRecord().getAttributes());					
						Record.copyAttributesInto(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD), formV3.getValuesAsRecord(), formV3.getValuesAsRecord().getAttributes());
						//VendorList.addVendorToListAsRecord(vRecord);							
						
						if(action.equals(Constants.BUTTON_TITLE_SUBMIT)){
							formV1.saveData();
							formV2.saveData();
							formV3.saveData();
							action = Constants.RECORD_ACTION_NULL;
						}else{
							VendorList.addVendorToListAsRecord(vRecord,action);
						}
						
						formV1.clearValues();
						formV2.clearValues();
						formV3.clearValues();
						
						hide();												
					}
				}
	        });
	        submit = new Button();
	        submit.setTitle(Constants.BUTTON_TITLE_SUBMIT);
	        
	        submit.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					vRecord = new Record();
					vRecord.setAttribute(Constants.RR_VENDOR_RECORD, new Record());
					//vRecord.getAttributeAsRecord("vendor").setAttribute("vendor", new Record());
					
					Record.copyAttributesInto(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD), formV1.getValuesAsRecord(), formV1.getValuesAsRecord().getAttributes());
					Record.copyAttributesInto(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD), formV2.getValuesAsRecord(), formV2.getValuesAsRecord().getAttributes());
					Record.copyAttributesInto(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD), formV3.getValuesAsRecord(), formV3.getValuesAsRecord().getAttributes());
					
					if(action.equals(Constants.RECORD_ACTION_EDIT)){
						formV1.saveData();
						formV2.saveData();
						formV3.saveData();						
						action = Constants.RECORD_ACTION_NULL;
					}else{										
					VendorList.addVendorToListAsRecord(vRecord,action);
					}								
					formV1.clearValues();
					formV2.clearValues();
					formV3.clearValues();
					hide();
				}
			});
	        
	        cancel = new Button();
	        cancel.setTitle(Constants.BUTTON_TITLE_CANCEL);
	        cancel.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					hide();
					//System.out.println("22");			
				}				
	        });
	        
	        v4.addMember(submit);
	        v4.addMember(cancel);
	        
	        
	        mainLayout.addMember(v1);
	        mainLayout.addMember(v2);	      
	        mainLayout.addMember(v3);
	        mainLayout.addMember(v4);			
			addDataSection(mainLayout);
	}

	public static void setTransportMethodAsRecord(Record record){
		transportMethodList.hide();
		Record.copyAttributesInto(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD), record.getAttributeAsRecord(Constants.RR_TRANSPORT_RECORD), record.getAttributeAsRecord(Constants.RR_TRANSPORT_RECORD).getAttributes());
		formV3.getField(Constants.TRANSPORTID).setValue(record.getAttributeAsRecord(Constants.RR_TRANSPORT_RECORD).getAttribute(Constants.TRANSPORTID));
		formV3.getField(Constants.TRANSPORT_SHORTNAME_TH).setValue(record.getAttributeAsRecord(Constants.RR_TRANSPORT_RECORD).getAttribute(Constants.TRANSPORT_SHORTNAME_TH));
	}

	public static void setVendorTitleNameAsRecord(Record record){
		vendorTitleList.hide();
		Record.copyAttributesInto(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD), record.getAttributeAsRecord(Constants.RR_VENDOR_TITLE_RECORD), record.getAttributeAsRecord(Constants.RR_VENDOR_TITLE_RECORD).getAttributes());
		formV1.getField(Constants.VENDOR_TITLE_NAME).setValue(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.VENDOR_TITLE_NAME));
	}

	

	public static void setRemarkAsRecord(Record record){
		remarkList.hide();
		Record.copyAttributesInto(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD), record.getAttributeAsRecord(Constants.RR_REMARK_RECORD), record.getAttributeAsRecord(Constants.RR_REMARK_RECORD).getAttributes());
		formV2.getField(Constants.RR_REMARK_NAME).setValue(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.RR_REMARK_NAME));
	}

	public static void setPaymentTypesAsRecord(Record record) {
		paymentTypesList.hide();
		Record.copyAttributesInto(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD), record.getAttributeAsRecord(Constants.RR_PAYMENT_RECORD), record.getAttributeAsRecord(Constants.RR_PAYMENT_RECORD).getAttributes());
		formV2.getField(Constants.RR_PAYMENT_TYPE_NAME).setValue(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.RR_PAYMENT_TYPE_NAME));
		
	}

	public static void setTaxTypesAsRecord(Record record) {
		taxTypesList.hide();
		Record.copyAttributesInto(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD), record.getAttributeAsRecord(Constants.RR_TAX_TYPE_RECORD), record.getAttributeAsRecord(Constants.RR_TAX_TYPE_RECORD).getAttributes());
		formV2.getField(Constants.TAX_TYPES_LISTID).setValue(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.TAX_TYPES_LISTID));
		formV2.getField(Constants.TAX_TYPES_LISTID).setHint(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.TAX_TYPES_LIST_SHORTNAME_TH));
	}


	public static void setVendorAccountAsRecord(Record record) {
		vendorAccountList.hide();
		Record.copyAttributesInto(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD), record.getAttributeAsRecord(Constants.RR_VENDOR_ACC_RECORD), record.getAttributeAsRecord(Constants.RR_VENDOR_ACC_RECORD).getAttributes());
		formV3.getField(Constants.VENDOR_ACCID).setValue(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.VENDOR_ACCID));
		formV3.getField(Constants.VENDOR_ACC_NAME_TH).setValue(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.VENDOR_ACC_NAME_TH));
	}
	
	public static void setPaymentConditionsAsRecord(Record record) {
		paymentConditionsList.hide();
		Record.copyAttributesInto(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD), record.getAttributeAsRecord(Constants.RR_PAYMENT_CON_RECORD), record.getAttributeAsRecord(Constants.RR_PAYMENT_CON_RECORD).getAttributes());
		formV3.getField(Constants.RR_PAYMENT_CON_NAME).setValue(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.RR_PAYMENT_CON_NAME));
	}
	
	public static void setVendorTypeAsRecord(Record record){
		vendorTypeList.hide();
		Record.copyAttributesInto(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD), record.getAttributeAsRecord(Constants.RR_VENDOR_TYPE_RECORD), record.getAttributeAsRecord(Constants.RR_VENDOR_TYPE_RECORD).getAttributes());
		formV3.getField(Constants.VENDOR_TYPEID).setValue(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.VENDOR_TYPEID));
		formV3.getField(Constants.VENDOR_TYPE_FULLNAME_TH).setValue(vRecord.getAttributeAsRecord(Constants.RR_VENDOR_RECORD).getAttribute(Constants.VENDOR_TYPE_FULLNAME_TH));
	}
	
}
