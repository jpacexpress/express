package jpac.express.client.ui.startup;

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

public class DepartmentList extends CustomWindow {

	private String className = this.getClass().getSimpleName();
	
	private static DepartmentList instance = null;

	private HLayout dataLayout;
	public final ListGrid departmentList;
	
	public DepartmentList() {
		super(Constants.STARTUP_DEPARTMENTLIST_ID,Constants.STARTUP_DEPARTMENTLIST_TITLE, 1120, 600, false);
		departmentList = new ListGrid();
		departmentList.setDataSource(DataSource.get("department"));
		departmentList.setCanEdit(true);
		//departmentList.setCanSelectCells(true);
		departmentList.setAutoSaveEdits(true);
		departmentList.setAutoFetchData(true);
		departmentList.setContextMenu(getDepartmentListContextMenu());
		
		enableCustomControlMenu();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		initControlMenu();
		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAltKeyDown() && event.getKeyName().equals("A")) {
					departmentList.startEditingNew();
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("E")) {
					departmentList.startEditing(departmentList.getRecordIndex(departmentList.getSelectedRecord()));
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("D")) {
					departmentList.removeData(departmentList.getSelectedRecord());
					sendUpdateToAllClient(Constants.STARTUP_DEPARTMENTLIST_ID);
				}
			}
		});
		addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler(){

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				instance.bringToFront();
			}
			
		});
		departmentList.focus();
	}
	
	public static DepartmentList getInstance() {
		if (instance == null) {
			instance = new DepartmentList();
		}
		return instance;
	}
	
	public void showDepartmentList() {
		show();
		Application.setOpeningWindow(instance.menuItem);
	}

	private void initWidgets() {
		dataLayout = new HLayout();
		dataLayout.setWidth100();
		dataLayout.setHeight100();
		
		departmentList.addShowContextMenuHandler(new ShowContextMenuHandler() {
			@Override
			public void onShowContextMenu(ShowContextMenuEvent event) {
				event.cancel();
				getDepartmentListContextMenu().showContextMenu();
			}
		});
		departmentList.addCellContextClickHandler(new CellContextClickHandler() {
			@Override
			public void onCellContextClick(final CellContextClickEvent rightClickEvent) {
				getDepartmentListContextMenu().showContextMenu();
			}
		});
		
		final ListGridField departmentId = new ListGridField();
		departmentId.setTitle(Constants.DEPARTMENT_DEPARTMENTID_TITLE);
		departmentId.setName(Constants.DEPARTMENT_DEPARTMENTID);
		departmentId.setWidth("10%");

		final ListGridField departmentShortNameTH = new ListGridField();
		departmentShortNameTH.setTitle(Constants.DEPARTMENT_DEPARTMENTSHORTNAME_TH_TITLE);
		departmentShortNameTH.setName(Constants.DEPARTMENT_DEPARTMENTSHORTNAME_TH);
		departmentShortNameTH.setWidth("10%");
		
		final ListGridField departmentShortNameEN = new ListGridField();
		departmentShortNameEN.setTitle(Constants.DEPARTMENT_DEPARTMENTSHORTNAME_EN_TITLE);
		departmentShortNameEN.setName(Constants.DEPARTMENT_DEPARTMENTSHORTNAME_EN);
		departmentShortNameEN.setWidth("10%");

		final ListGridField departmentDetailsTH = new ListGridField();
		departmentDetailsTH.setTitle(Constants.DEPARTMENT_DEPARTMENTDETAILS_TH_TITLE);
		departmentDetailsTH.setName(Constants.DEPARTMENT_DEPARTMENTDETAILS_TH);
		departmentDetailsTH.setWidth("50%");
		
		final ListGridField departmentDetailsEN = new ListGridField();
		departmentDetailsEN.setTitle(Constants.DEPARTMENT_DEPARTMENTDETAILS_EN_TITLE);
		departmentDetailsEN.setName(Constants.DEPARTMENT_DEPARTMENTDETAILS_EN);
		departmentDetailsEN.setWidth("20%");

		departmentList.addCellSavedHandler(new CellSavedHandler(){
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
				
				sendUpdateToAllClient(Constants.STARTUP_DEPARTMENTLIST_ID);
			}
		});
		departmentList.addEditorEnterHandler(new EditorEnterHandler(){
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
		departmentList.addEditorExitHandler(new EditorExitHandler(){
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
	
		departmentList.setFields(departmentId,departmentShortNameTH,departmentShortNameEN,departmentDetailsTH,departmentDetailsEN);
		dataLayout.addMember(departmentList);
		addDataSection(dataLayout);
	}

	protected Menu getDepartmentListContextMenu() {
		final Menu menu = new Menu();
		MenuItem add = new MenuItem(Constants.BUTTON_TITLE_ADD);
		add.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				departmentList.startEditingNew();
			}
		});
		MenuItem edit = new MenuItem(Constants.BUTTON_TITLE_EDIT);
		edit.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				departmentList.startEditing(departmentList.getRecordIndex(departmentList.getSelectedRecord()));
			}
		});
		MenuItem delete = new MenuItem(Constants.BUTTON_TITLE_DELETE);
		delete.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				departmentList.removeData(departmentList.getSelectedRecord());
				sendUpdateToAllClient(Constants.STARTUP_DEPARTMENTLIST_ID);
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
				
				departmentList.startEditingNew();
				
			}
		});

		getCustomControlMenu().newButton("edit"+className, "edit", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(departmentList.getSelectedRecord()!=null) {
					getCustomControlMenu().getButton("new"+className).disable();
					getCustomControlMenu().getButton("trash"+className).disable();
					getCustomControlMenu().getButton("save"+className).enable();
					getCustomControlMenu().getButton("gotofirst"+className).disable();
					getCustomControlMenu().getButton("gotoprevious"+className).disable();
					getCustomControlMenu().getButton("gotonext"+className).disable();
					getCustomControlMenu().getButton("gotolast"+className).disable();
					getCustomControlMenu().getButton("print"+className).disable();
					departmentList.startEditing(departmentList.getRecordIndex(departmentList.getSelectedRecord()));
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
							departmentList.removeData(departmentList.getSelectedRecord());
							sendUpdateToAllClient(Constants.STARTUP_DEPARTMENTLIST_ID);
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
				departmentList.saveAllEdits();
			}
		});

		getCustomControlMenu().newButton("gotofirst"+className, "gotofirst", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(departmentList.getRecords().length>=0) {
					departmentList.selectSingleRecord(0);
				}
			}
		});

		getCustomControlMenu().newButton("gotoprevious"+className, "gotoprevious", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(departmentList.getRecords().length>=0) {
					if((departmentList.getRecordIndex(departmentList.getSelectedRecord())-1)>=0){
						departmentList.selectSingleRecord((departmentList.getRecordIndex(departmentList.getSelectedRecord())-1));
					}
				}
			}
		});

		getCustomControlMenu().newButton("gotonext"+className, "gotonext", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(departmentList.getRecords().length>=0) {
					if((departmentList.getRecordIndex(departmentList.getSelectedRecord())+1)<=(departmentList.getRecords().length-1)){
						departmentList.selectSingleRecord((departmentList.getRecordIndex(departmentList.getSelectedRecord())+1));
					}
				}
			}
		});

		getCustomControlMenu().newButton("gotolast"+className, "gotolast", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(departmentList.getRecords().length>=0) {
					departmentList.selectSingleRecord((departmentList.getRecords().length-1));
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
				dataTitle.setContents("<span style='font-size: 16px !important;'>ตารางข้อมูล : "+Constants.STARTUP_DEPARTMENTLIST_TITLE+"</span>");
				Label separator = new Label();
				separator.setContents("<span style='font-size: 16px !important;'>&nbsp;</span>");
				ListGrid printGrid = new ListGrid() {
				    @Override
				    protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
				        return "font-size: 16px !important;";
				    }
				};
				printGrid.setFields(departmentList.getFields());
				printGrid.setData(departmentList.getRecords());
				printGrid.setPrintHeaderStyle("");

				printContainer.addMembers(companyName,title,dataTitle,separator,printGrid);

				Canvas.showPrintPreview(printContainer);
			}
		});
		
	}

}
