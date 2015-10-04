package jpac.express.client.ui.sell.hs;

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
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VStack;

import jpac.express.client.ui.custom.CenteredLayout;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

public class AddSales extends CustomWindow {

	
	private static final String name = "เพิ่มรายละเอียดพนักงานขาย";
	private VStack mainStack;
	private HLayout controlLayout;
	private CenteredLayout layout;

	private HLayout saleIdLayout;
	private HLayout saleDetailsLayout;
	private HLayout saleAddressLayout;
	private HLayout saleTaxIdLayout;

	private static DynamicForm saleIdForm;
	private static DynamicForm saleDetailsForm;
	private static DynamicForm saleAddressForm;
	private static DynamicForm saleTaxIdForm;

	private IButton submit;
	private IButton cancel;

	private static Record salesRecord;

	public AddSales() {
		super("AddSales",name, 550, 455, true);
		salesRecord = new Record();
		salesRecord.setAttribute("sales", new Record());
		disableMaximizeButton();
		disableMinimizeButton();
		disableCanResize();
		disableControlSection();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		hide();
	}

	public void showAddSales(Object object) {
		centerInPage();
		show();
		saleIdForm.focus();
	}

	private void initWidgets() {
		layout = new CenteredLayout(500);
		mainStack = new VStack();
		mainStack.setHeight100();

		controlLayout = new HLayout();
		controlLayout.setHeight(25);
		controlLayout.setMargin(5);
		controlLayout.setAlign(Alignment.CENTER);

		submit = new IButton(Constants.BUTTON_TITLE_SUBMIT);
		submit.setHeight(30);
		submit.setMargin(5);
		cancel = new IButton(Constants.BUTTON_TITLE_CANCEL);
		cancel.setHeight(30);
		cancel.setMargin(5);
		cancel.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		submit.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				int salesAreaId = -1;
				Record[] salesAreas = SalesAreaList.getInstance().getSalesAreaList().getRecords();
				for(Record record:salesAreas) {
					if(record.getAttributeAsString("salesAreaIdentify").equals(salesRecord.getAttributeAsRecord("sales").getAttributeAsRecord("salesArea").getAttributeAsString("salesAreaIdentify"))) {
						salesAreaId = record.getAttributeAsInt("salesAreaId");
						break;
					}
				}
				
				int salesTypeId = -1;
				Record[] salesTypes = SalesTypeList.getInstance().getSalesTypeList().getRecords();
				for(Record record:salesTypes) {
					if(record.getAttributeAsString("salesTypeIdentify").equals(salesRecord.getAttributeAsRecord("sales").getAttributeAsRecord("salesType").getAttributeAsString("salesTypeIdentify"))) {
						salesTypeId = record.getAttributeAsInt("salesTypeId");
						break;
					}
				}
				Record salesArea = salesRecord.getAttributeAsRecord("sales").getAttributeAsRecord("salesArea");
				salesArea.setAttribute("salesAreaId", salesAreaId);
				Record salesType = salesRecord.getAttributeAsRecord("sales").getAttributeAsRecord("salesType");
				salesType.setAttribute("salesTypeId", salesTypeId);
				
				Record.copyAttributesInto(salesRecord.getAttributeAsRecord("sales"), saleIdForm.getValuesAsRecord(), saleIdForm.getValuesAsRecord().getAttributes());
				Record.copyAttributesInto(salesRecord.getAttributeAsRecord("sales"), saleDetailsForm.getValuesAsRecord(), saleDetailsForm.getValuesAsRecord().getAttributes());
				Record.copyAttributesInto(salesRecord.getAttributeAsRecord("sales"), saleAddressForm.getValuesAsRecord(), saleAddressForm.getValuesAsRecord().getAttributes());
				Record.copyAttributesInto(salesRecord.getAttributeAsRecord("sales"), saleTaxIdForm.getValuesAsRecord(), saleTaxIdForm.getValuesAsRecord().getAttributes());
				
	
				if(salesAreaId>-1) {
					salesRecord.getAttributeAsRecord("sales").setAttribute("salesArea",salesAreaId);
				}
				if(salesTypeId>-1) {
					salesRecord.getAttributeAsRecord("sales").setAttribute("salesType",salesTypeId);
				}
			
				SalesList.addSalesToListAsRecord(salesRecord,salesArea,salesType);
				salesRecord = new Record();
				salesRecord.setAttribute("sales", new Record());
				saleIdForm.clearValues();
				saleDetailsForm.clearValues();
				saleAddressForm.clearValues();
				saleTaxIdForm.clearValues();
				hide();
			}
		});
		controlLayout.setHeight(40);
		controlLayout.setMargin(3);
		controlLayout.addMembers(submit, cancel);

		saleIdLayout = new HLayout();
		saleDetailsLayout = new HLayout();
		saleAddressLayout = new HLayout();
		saleTaxIdLayout = new HLayout();

		saleIdForm = new DynamicForm();
		saleIdForm.setWidth100();
		saleIdForm.setTitleWidth(100);
		saleIdForm.setTitleSuffix("");

		TextItem saleID = new TextItem();
		saleID.setName("salesIdentify");
		saleID.setTitle("รหัสพนักงานขาย");

		TextItem saleName = new TextItem();
		saleName.setName("salesName");
		saleName.setTitle("ชื่อพนักงานขาย");
		saleName.setWidth("*");

		saleIdForm.setFields(saleID, saleName);

		saleDetailsForm = new DynamicForm();
		saleDetailsForm.setWidth100();
		saleDetailsForm.setTitleWidth(100);
		saleDetailsForm.setTitleSuffix("");

		TextItem salePosition = new TextItem("ตำแหน่ง");
		salePosition.setName("salesPosition");
		salePosition.setTitle("ตำแหน่ง");
		salePosition.setWidth("*");

		TextItem saleType = new TextItem();
		saleType.setTitle("ประเภท");
		saleType.setName("salesType");
		saleType.setWidth(150);
		PickerIcon saleTypeSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				SalesTypeList.getInstance().showSalesTypeList();
			}
		});
		saleType.setIcons(saleTypeSearch);

		PickerIcon saleAreaSearch = new PickerIcon(PickerIcon.SEARCH, new FormItemClickHandler() {
			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				SalesAreaList.getInstance().showSalesAreaList();
			}
		});
		TextItem saleArea = new TextItem();
		saleArea.setTitle("เขตการขาย");
		saleArea.setName("salesArea");
		saleArea.setIcons(saleAreaSearch);
		saleArea.setWidth(150);

		TextItem saleComission = new TextItem("อัตราค่าคอม&nbsp;ฯ");
		saleComission.setName("salesComission");
		saleComission.setTitle("อัตราค่าคอม&nbsp;ฯ");
		saleComission.setWidth(160);
		saleDetailsForm.setFields(salePosition, saleType, saleArea, saleComission);

		saleAddressForm = new DynamicForm();
		saleAddressForm.setTitleWidth(50);
		saleAddressForm.setItemLayout(FormLayoutType.TABLE);
		saleAddressForm.setNumCols(4);
		saleAddressForm.setWidth100();
		saleAddressForm.setTitleWidth(70);
		saleAddressForm.setTitleSuffix("");

		TextItem saleAddressLine1 = new TextItem("ที่อยู่");
		saleAddressLine1.setName("salesAddressLine1");
		saleAddressLine1.setTitle("ที่อยู่");
		saleAddressLine1.setColSpan(4);
		saleAddressLine1.setWidth("*");

		TextItem saleAddressLine2 = new TextItem();
		saleAddressLine2.setName("salesAddressLine2");
		saleAddressLine2.setTitle("");
		saleAddressLine2.setColSpan(4);
		saleAddressLine2.setWidth("*");

		TextItem saleAddressLine3 = new TextItem();
		saleAddressLine3.setName("salesAddressLine3");
		saleAddressLine3.setTitle("");
		saleAddressLine3.setWidth("*");
		TextItem salePostalCode = new TextItem("รหัสไปรษณีย์");
		// salePostalCode.setWidth(60);
		salePostalCode.setWidth("*");

		TextItem salePhoneNumber = new TextItem("โทร");
		salePhoneNumber.setName("salesPhoneNumber");
		salePhoneNumber.setTitle("โทร");
		salePhoneNumber.setColSpan(4);
		saleAddressForm.setFields(saleAddressLine1, saleAddressLine2, saleAddressLine3, salePostalCode, salePhoneNumber);

		saleTaxIdForm = new DynamicForm();
		saleTaxIdForm.setTitleWidth(120);
		saleTaxIdForm.setWidth100();
		saleTaxIdForm.setTitleSuffix("");

		TextItem saleTaxId = new TextItem("เลขประจำตัวผู้เสียภาษี");
		saleTaxId.setName("salesTaxId");
		saleTaxId.setTitle("เลขประจำตัวผู้เสียภาษี");

		TextItem saleInsuranceId = new TextItem("เลขที่บัตรประกันสังคม");
		saleInsuranceId.setName("salesInsuranceId");
		saleInsuranceId.setTitle("เลขที่บัตรประกันสังคม");
		saleTaxIdForm.setFields(saleTaxId, saleInsuranceId);

		saleIdForm.setHeight(50);
		saleIdLayout.addMember(saleIdForm);
		saleIdLayout.setHeight(50);
		saleIdLayout.setMargin(3);
		saleDetailsForm.setHeight(100);
		saleDetailsLayout.setMargin(3);
		saleDetailsLayout.addMember(saleDetailsForm);
		saleDetailsLayout.setBorder("1px solid white");
		saleAddressForm.setHeight(100);
		saleAddressLayout.setMargin(3);
		saleAddressLayout.addMember(saleAddressForm);
		saleAddressLayout.setBorder("1px solid white");
		saleTaxIdLayout.setMargin(3);
		saleTaxIdLayout.setHeight(50);
		saleTaxIdLayout.addMember(saleTaxIdForm);

		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals(KeyNames.ENTER)) {
					int salesAreaId = -1;
					Record[] salesAreas = SalesAreaList.getInstance().getSalesAreaList().getRecords();
					for(Record record:salesAreas) {
						if(record.getAttributeAsString("salesAreaIdentify").equals(salesRecord.getAttributeAsRecord("sales").getAttributeAsRecord("salesArea").getAttributeAsString("salesAreaIdentify"))) {
							salesAreaId = record.getAttributeAsInt("salesAreaId");
							break;
						}
					}
					
					int salesTypeId = -1;
					Record[] salesTypes = SalesTypeList.getInstance().getSalesTypeList().getRecords();
					for(Record record:salesTypes) {
						if(record.getAttributeAsString("salesTypeIdentify").equals(salesRecord.getAttributeAsRecord("sales").getAttributeAsRecord("salesType").getAttributeAsString("salesTypeIdentify"))) {
							salesTypeId = record.getAttributeAsInt("salesTypeId");
							break;
						}
					}
					Record salesArea = salesRecord.getAttributeAsRecord("sales").getAttributeAsRecord("salesArea");
					salesArea.setAttribute("salesAreaId", salesAreaId);
					Record salesType = salesRecord.getAttributeAsRecord("sales").getAttributeAsRecord("salesType");
					salesType.setAttribute("salesTypeId", salesTypeId);
					
					Record.copyAttributesInto(salesRecord.getAttributeAsRecord("sales"), saleIdForm.getValuesAsRecord(), saleIdForm.getValuesAsRecord().getAttributes());
					Record.copyAttributesInto(salesRecord.getAttributeAsRecord("sales"), saleDetailsForm.getValuesAsRecord(), saleDetailsForm.getValuesAsRecord().getAttributes());
					Record.copyAttributesInto(salesRecord.getAttributeAsRecord("sales"), saleAddressForm.getValuesAsRecord(), saleAddressForm.getValuesAsRecord().getAttributes());
					Record.copyAttributesInto(salesRecord.getAttributeAsRecord("sales"), saleTaxIdForm.getValuesAsRecord(), saleTaxIdForm.getValuesAsRecord().getAttributes());
					
		
					if(salesAreaId>-1) {
						salesRecord.getAttributeAsRecord("sales").setAttribute("salesArea",salesAreaId);
					}
					if(salesTypeId>-1) {
						salesRecord.getAttributeAsRecord("sales").setAttribute("salesType",salesTypeId);
					}
				
					SalesList.addSalesToListAsRecord(salesRecord,salesArea,salesType);
					salesRecord = new Record();
					salesRecord.setAttribute("sales", new Record());
					saleIdForm.clearValues();
					saleDetailsForm.clearValues();
					saleAddressForm.clearValues();
					saleTaxIdForm.clearValues();
					hide();
				}
			}
		});

		mainStack.addMembers(saleIdLayout, saleDetailsLayout, saleAddressLayout, saleTaxIdLayout, controlLayout);
		layout.add(mainStack);
		addDataSection(layout);
	}

	public static void setSalesAreaAsRecord(Record record) {
		Record.copyAttributesInto(salesRecord.getAttributeAsRecord("sales"), record, record.getAttributes());
		saleDetailsForm.getField("salesArea").setValue(salesRecord.getAttributeAsRecord("sales").getAttributeAsRecord("salesArea").getAttribute("salesAreaIdentify"));
		saleDetailsForm.getField("salesArea").setHint(salesRecord.getAttributeAsRecord("sales").getAttributeAsRecord("salesArea").getAttribute("salesAreaDetailsTH"));
	}

	public static void setSalesTypeAsRecord(Record record) {
		SalesTypeList.getInstance().hide();
		Record.copyAttributesInto(salesRecord.getAttributeAsRecord("sales"), record, record.getAttributes());
		saleDetailsForm.getField("salesType").setValue(salesRecord.getAttributeAsRecord("sales").getAttributeAsRecord("salesType").getAttribute("salesTypeIdentify"));
		saleDetailsForm.getField("salesType").setHint(salesRecord.getAttributeAsRecord("sales").getAttributeAsRecord("salesType").getAttribute("salesTypeDetailsTH"));
	}
}
