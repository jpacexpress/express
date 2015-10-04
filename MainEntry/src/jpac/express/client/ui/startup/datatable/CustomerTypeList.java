package jpac.express.client.ui.startup.datatable;

import jpac.express.client.Application;
import jpac.express.client.MainEntry;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.KeyPressEvent;
import com.smartgwt.client.widgets.events.KeyPressHandler;
import com.smartgwt.client.widgets.events.ShowContextMenuEvent;
import com.smartgwt.client.widgets.events.ShowContextMenuHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellContextClickHandler;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.EditorEnterEvent;
import com.smartgwt.client.widgets.grid.events.EditorEnterHandler;
import com.smartgwt.client.widgets.grid.events.EditorExitEvent;
import com.smartgwt.client.widgets.grid.events.EditorExitHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

public class CustomerTypeList extends CustomWindow {

	private String className = this.getClass().getSimpleName();
	
	private static CustomerTypeList instance = null;

	private HLayout dataLayout;
	public final ListGrid customerTypeList;
	
	public CustomerTypeList() {
		super(Constants.STARTUP_DATATABLE_CUSTOMERTYPELIST_ID,Constants.STARTUP_DATATABLE_CUSTOMERTYPELIST_TITLE, 1120, 600, false);
		customerTypeList = new ListGrid();
		customerTypeList.setDataSource(DataSource.get("customerType"));
		customerTypeList.setCanEdit(true);
		//customerTypeList.setCanSelectCells(true);
		customerTypeList.setAutoSaveEdits(true);
		customerTypeList.setAutoFetchData(true);
		customerTypeList.setContextMenu(getCustomerTypeListContextMenu());
		
		enableCustomControlMenu();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		initControlMenu();
		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAltKeyDown() && event.getKeyName().equals("A")) {
					customerTypeList.startEditingNew();
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("E")) {
					customerTypeList.startEditing(customerTypeList.getRecordIndex(customerTypeList.getSelectedRecord()));
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("D")) {
					customerTypeList.removeData(customerTypeList.getSelectedRecord());
					sendUpdateToAllClient(Constants.STARTUP_DATATABLE_CUSTOMERTYPELIST_ID);
				}
			}
		});
		addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler(){

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				instance.bringToFront();
			}
			
		});
		customerTypeList.focus();
	}
	
	public static CustomerTypeList getInstance() {
		if (instance == null) {
			instance = new CustomerTypeList();
		}
		return instance;
	}
	
	public void showCustomerTypeList() {
		show();
		Application.setOpeningWindow(instance.menuItem);
	}

	private void initWidgets() {
		dataLayout = new HLayout();
		dataLayout.setWidth100();
		dataLayout.setHeight100();
		
		customerTypeList.addShowContextMenuHandler(new ShowContextMenuHandler() {
			@Override
			public void onShowContextMenu(ShowContextMenuEvent event) {
				event.cancel();
				getCustomerTypeListContextMenu().showContextMenu();
			}
		});
		customerTypeList.addCellContextClickHandler(new CellContextClickHandler() {
			@Override
			public void onCellContextClick(final CellContextClickEvent rightClickEvent) {
				getCustomerTypeListContextMenu().showContextMenu();
			}
		});
		
		final ListGridField customerTypeId = new ListGridField();
		customerTypeId.setTitle(Constants.CUSTOMERTYPE_CUSTOMERTYPEID_TITLE);
		customerTypeId.setName(Constants.CUSTOMERTYPE_CUSTOMERTYPEID);
		customerTypeId.setWidth("10%");

		final ListGridField customerTypeShortNameTH = new ListGridField();
		customerTypeShortNameTH.setTitle(Constants.CUSTOMERTYPE_CUSTOMERTYPESHORTNAME_TH_TITLE);
		customerTypeShortNameTH.setName(Constants.CUSTOMERTYPE_CUSTOMERTYPESHORTNAME_TH);
		customerTypeShortNameTH.setWidth("10%");
		
		final ListGridField customerTypeShortNameEN = new ListGridField();
		customerTypeShortNameEN.setTitle(Constants.CUSTOMERTYPE_CUSTOMERTYPESHORTNAME_EN_TITLE);
		customerTypeShortNameEN.setName(Constants.CUSTOMERTYPE_CUSTOMERTYPESHORTNAME_EN);
		customerTypeShortNameEN.setWidth("10%");

		final ListGridField customerTypeDetailsTH = new ListGridField();
		customerTypeDetailsTH.setTitle(Constants.CUSTOMERTYPE_CUSTOMERTYPEDETAILS_TH_TITLE);
		customerTypeDetailsTH.setName(Constants.CUSTOMERTYPE_CUSTOMERTYPEDETAILS_TH);
		customerTypeDetailsTH.setWidth("50%");
		
		final ListGridField customerTypeDetailsEN = new ListGridField();
		customerTypeDetailsEN.setTitle(Constants.CUSTOMERTYPE_CUSTOMERTYPEDETAILS_EN_TITLE);
		customerTypeDetailsEN.setName(Constants.CUSTOMERTYPE_CUSTOMERTYPEDETAILS_EN);
		customerTypeDetailsEN.setWidth("20%");

		customerTypeList.addCellSavedHandler(new CellSavedHandler(){
			@Override
			public void onCellSaved(CellSavedEvent event) {
				getCustomControlMenu().getButton("new"+className).enable();
				getCustomControlMenu().getButton("edit"+className).enable();
				getCustomControlMenu().getButton("trash"+className).enable();
				
				getCustomControlMenu().getButton("save"+className).enable();
				getCustomControlMenu().getButton("gotofirst"+className).enable();
				getCustomControlMenu().getButton("gotoprevious"+className).enable();
				getCustomControlMenu().getButton("gotonext"+className).enable();
				getCustomControlMenu().getButton("gotolast"+className).enable();
				getCustomControlMenu().getButton("print"+className).enable();
				
				sendUpdateToAllClient(Constants.STARTUP_DATATABLE_CUSTOMERTYPELIST_ID);
			}
		});
		customerTypeList.addEditorEnterHandler(new EditorEnterHandler(){
			@Override
			public void onEditorEnter(EditorEnterEvent event) {
				getCustomControlMenu().getButton("new"+className).disable();
				getCustomControlMenu().getButton("trash"+className).disable();
				getCustomControlMenu().getButton("save"+className).disable();
				getCustomControlMenu().getButton("gotofirst"+className).disable();
				getCustomControlMenu().getButton("gotoprevious"+className).disable();
				getCustomControlMenu().getButton("gotonext"+className).disable();
				getCustomControlMenu().getButton("gotolast"+className).disable();
				getCustomControlMenu().getButton("print"+className).disable();
			}
		});
		customerTypeList.addEditorExitHandler(new EditorExitHandler(){
			@Override
			public void onEditorExit(EditorExitEvent event) {
				getCustomControlMenu().getButton("new"+className).enable();
				getCustomControlMenu().getButton("edit"+className).enable();
				getCustomControlMenu().getButton("trash"+className).enable();
				
				getCustomControlMenu().getButton("save"+className).enable();
				getCustomControlMenu().getButton("gotofirst"+className).enable();
				getCustomControlMenu().getButton("gotoprevious"+className).enable();
				getCustomControlMenu().getButton("gotonext"+className).enable();
				getCustomControlMenu().getButton("gotolast"+className).enable();
				getCustomControlMenu().getButton("print"+className).enable();
			}
		});
	
		customerTypeList.setFields(customerTypeId,customerTypeShortNameTH,customerTypeShortNameEN,customerTypeDetailsTH,customerTypeDetailsEN);
		dataLayout.addMember(customerTypeList);
		addDataSection(dataLayout);
	}

	protected Menu getCustomerTypeListContextMenu() {
		final Menu menu = new Menu();
		MenuItem add = new MenuItem(Constants.BUTTON_TITLE_ADD);
		add.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				customerTypeList.startEditingNew();
			}
		});
		MenuItem edit = new MenuItem(Constants.BUTTON_TITLE_EDIT);
		edit.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				customerTypeList.startEditing(customerTypeList.getRecordIndex(customerTypeList.getSelectedRecord()));
			}
		});
		MenuItem delete = new MenuItem(Constants.BUTTON_TITLE_DELETE);
		delete.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				customerTypeList.removeData(customerTypeList.getSelectedRecord());
				sendUpdateToAllClient(Constants.STARTUP_DATATABLE_CUSTOMERTYPELIST_ID);
			}
		});
		menu.addItem(add);
		menu.addItem(edit);
		menu.addItem(delete);
		return menu;
	}

	private void initControlMenu() {

		getCustomControlMenu().newButton("new"+className, "new", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				getCustomControlMenu().getButton("edit"+className).disable();
				getCustomControlMenu().getButton("trash"+className).disable();
				
				getCustomControlMenu().getButton("save"+className).enable();
				getCustomControlMenu().getButton("gotofirst"+className).disable();
				getCustomControlMenu().getButton("gotoprevious"+className).disable();
				getCustomControlMenu().getButton("gotonext"+className).disable();
				getCustomControlMenu().getButton("gotolast"+className).disable();
				getCustomControlMenu().getButton("print"+className).disable();
				
				customerTypeList.startEditingNew();
				
			}
		});

		getCustomControlMenu().newButton("edit"+className, "edit", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(customerTypeList.getSelectedRecord()!=null) {
					getCustomControlMenu().getButton("new"+className).disable();
					getCustomControlMenu().getButton("trash"+className).disable();
					getCustomControlMenu().getButton("save"+className).enable();
					getCustomControlMenu().getButton("gotofirst"+className).disable();
					getCustomControlMenu().getButton("gotoprevious"+className).disable();
					getCustomControlMenu().getButton("gotonext"+className).disable();
					getCustomControlMenu().getButton("gotolast"+className).disable();
					getCustomControlMenu().getButton("print"+className).disable();
					customerTypeList.startEditing(customerTypeList.getRecordIndex(customerTypeList.getSelectedRecord()));
				}
			}
		});

		getCustomControlMenu().newButton("trash"+className, "trash", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				getCustomControlMenu().getButton("new"+className).disable();
				getCustomControlMenu().getButton("edit"+className).disable();

				getCustomControlMenu().getButton("gotofirst"+className).disable();
				getCustomControlMenu().getButton("gotoprevious"+className).disable();
				getCustomControlMenu().getButton("gotonext"+className).disable();
				getCustomControlMenu().getButton("gotolast"+className).disable();
				getCustomControlMenu().getButton("print"+className).disable();
				
				SC.confirm(Constants.CONFIRM_ASK_DELETE, new BooleanCallback(){
					@Override
					public void execute(Boolean value) {
						getCustomControlMenu().getButton("new"+className).enable();
						getCustomControlMenu().getButton("edit"+className).enable();
						getCustomControlMenu().getButton("trash"+className).enable();
						
						getCustomControlMenu().getButton("save"+className).disable();
						getCustomControlMenu().getButton("gotofirst"+className).enable();
						getCustomControlMenu().getButton("gotoprevious"+className).enable();
						getCustomControlMenu().getButton("gotonext"+className).enable();
						getCustomControlMenu().getButton("gotolast"+className).enable();
						getCustomControlMenu().getButton("print"+className).enable();
						if(value) {
							customerTypeList.removeData(customerTypeList.getSelectedRecord());
							sendUpdateToAllClient(Constants.STARTUP_DATATABLE_CUSTOMERTYPELIST_ID);
						}
					}
					
				});
				
			}
		});

		getCustomControlMenu().newButton("cancel"+className, "cancel", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				getCustomControlMenu().getButton("new"+className).enable();
				getCustomControlMenu().getButton("edit"+className).enable();
				getCustomControlMenu().getButton("trash"+className).enable();
				
				getCustomControlMenu().getButton("save"+className).enable();
				getCustomControlMenu().getButton("gotofirst"+className).enable();
				getCustomControlMenu().getButton("gotoprevious"+className).enable();
				getCustomControlMenu().getButton("gotonext"+className).enable();
				getCustomControlMenu().getButton("gotolast"+className).enable();
				getCustomControlMenu().getButton("print"+className).enable();
			}
		});

		getCustomControlMenu().newButton("save"+className, "save", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				getCustomControlMenu().getButton("new"+className).enable();
				getCustomControlMenu().getButton("edit"+className).enable();
				getCustomControlMenu().getButton("trash"+className).enable();
				
				getCustomControlMenu().getButton("save"+className).enable();
				getCustomControlMenu().getButton("gotofirst"+className).enable();
				getCustomControlMenu().getButton("gotoprevious"+className).enable();
				getCustomControlMenu().getButton("gotonext"+className).enable();
				getCustomControlMenu().getButton("gotolast"+className).enable();
				getCustomControlMenu().getButton("print"+className).enable();
				customerTypeList.saveAllEdits();
			}
		});

		getCustomControlMenu().newButton("gotofirst"+className, "gotofirst", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(customerTypeList.getRecords().length>=0) {
					customerTypeList.selectSingleRecord(0);
				}
			}
		});

		getCustomControlMenu().newButton("gotoprevious"+className, "gotoprevious", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(customerTypeList.getRecords().length>=0) {
					if((customerTypeList.getRecordIndex(customerTypeList.getSelectedRecord())-1)>=0){
						customerTypeList.selectSingleRecord((customerTypeList.getRecordIndex(customerTypeList.getSelectedRecord())-1));
					}
				}
			}
		});

		getCustomControlMenu().newButton("gotonext"+className, "gotonext", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(customerTypeList.getRecords().length>=0) {
					if((customerTypeList.getRecordIndex(customerTypeList.getSelectedRecord())+1)<=(customerTypeList.getRecords().length-1)){
						customerTypeList.selectSingleRecord((customerTypeList.getRecordIndex(customerTypeList.getSelectedRecord())+1));
					}
				}
			}
		});

		getCustomControlMenu().newButton("gotolast"+className, "gotolast", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(customerTypeList.getRecords().length>=0) {
					customerTypeList.selectSingleRecord((customerTypeList.getRecords().length-1));
				}
			}
		});

		getCustomControlMenu().newButton("print"+className, "print", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				VLayout printContainer = new VLayout();

				Label companyName = new Label();
				companyName.setContents("<span style='font-size: 16px !important;'>บริษัท</span>");
				Label title = new Label();
				title.setContents("<span style='font-size: 22px !important;'>รายการตารางข้อมูล</span>");
				Label dataTitle = new Label();
				dataTitle.setContents("<span style='font-size: 16px !important;'>ตารางข้อมูล : "+Constants.STARTUP_DATATABLE_CUSTOMERTYPELIST_TITLE+"</span>");
				Label separator = new Label();
				separator.setContents("<span style='font-size: 16px !important;'>&nbsp;</span>");
				ListGrid printGrid = new ListGrid() {
				    @Override
				    protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
				        return "font-size: 16px !important;";
				    }
				};
				printGrid.setFields(customerTypeList.getFields());
				printGrid.setData(customerTypeList.getRecords());
				printGrid.setPrintHeaderStyle("");

				printContainer.addMembers(companyName,title,dataTitle,separator,printGrid);

				Canvas.showPrintPreview(printContainer);
			}
		});
		
	}

}
