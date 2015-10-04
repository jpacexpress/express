package jpac.express.client.ui.startup.datamessage.general;

import jpac.express.client.Application;
import jpac.express.client.MainEntry;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.types.Alignment;
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

public class TitleNameList extends CustomWindow {

	private String className = this.getClass().getSimpleName();
	
	private static TitleNameList instance = null;

	private HLayout dataLayout;
	public final ListGrid titleNameList;
	
	public TitleNameList() {
		super(Constants.STARTUP_DATAMESSAGE_GENERAL_TITLENAMELIST_ID,Constants.STARTUP_DATAMESSAGE_GENERAL_TITLENAMELIST_TITLE, 1120, 600, false);
		titleNameList = new ListGrid();
		titleNameList.setDataSource(DataSource.get("titleName"));
		titleNameList.setCanEdit(true);
		//titleNameList.setCanSelectCells(true);
		titleNameList.setAutoSaveEdits(true);
		titleNameList.setAutoFetchData(true);
		titleNameList.setContextMenu(getTitleNameListContextMenu());
		
		enableCustomControlMenu();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		initControlMenu();
		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAltKeyDown() && event.getKeyName().equals("A")) {
					titleNameList.startEditingNew();
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("E")) {
					titleNameList.startEditing(titleNameList.getRecordIndex(titleNameList.getSelectedRecord()));
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("D")) {
					titleNameList.removeData(titleNameList.getSelectedRecord());
					sendUpdateToAllClient(Constants.STARTUP_DATAMESSAGE_GENERAL_TITLENAMELIST_ID);
				}
			}
		});
		addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler(){

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				instance.bringToFront();
			}
			
		});
		titleNameList.focus();
	}
	
	public static TitleNameList getInstance() {
		if (instance == null) {
			instance = new TitleNameList();
		}
		return instance;
	}
	
	public void showTitleNameList() {
		show();
		Application.setOpeningWindow(instance.menuItem);
	}

	private void initWidgets() {
		dataLayout = new HLayout();
		dataLayout.setWidth100();
		dataLayout.setHeight100();
		
		titleNameList.addShowContextMenuHandler(new ShowContextMenuHandler() {
			@Override
			public void onShowContextMenu(ShowContextMenuEvent event) {
				event.cancel();
				getTitleNameListContextMenu().showContextMenu();
			}
		});
		titleNameList.addCellContextClickHandler(new CellContextClickHandler() {
			@Override
			public void onCellContextClick(final CellContextClickEvent rightClickEvent) {
				getTitleNameListContextMenu().showContextMenu();
			}
		});
		
		final ListGridField titleNameId = new ListGridField();
		titleNameId.setTitle(Constants.TITLENAME_CUSTOMERTITLENAMEID_TITLE);
		titleNameId.setAlign(Alignment.LEFT);
		titleNameId.setWidth("5%");
		titleNameId.setName(Constants.TITLENAME_CUSTOMERTITLENAMEID);

		final ListGridField titleName = new ListGridField();
		titleName.setTitle(Constants.TITLENAME_CUSTOMERTITLENAME_TH_TITLE);
		titleName.setName(Constants.TITLENAME_CUSTOMERTITLENAME_TH);

		titleNameList.addCellSavedHandler(new CellSavedHandler(){
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
				
				sendUpdateToAllClient(Constants.STARTUP_DATAMESSAGE_GENERAL_TITLENAMELIST_ID);
			}
		});
		titleNameList.addEditorEnterHandler(new EditorEnterHandler(){
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
		titleNameList.addEditorExitHandler(new EditorExitHandler(){
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
	
		titleNameList.setFields(titleNameId,titleName);
		dataLayout.addMember(titleNameList);
		addDataSection(dataLayout);
	}

	protected Menu getTitleNameListContextMenu() {
		final Menu menu = new Menu();
		MenuItem add = new MenuItem(Constants.BUTTON_TITLE_ADD);
		add.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				titleNameList.startEditingNew();
			}
		});
		MenuItem edit = new MenuItem(Constants.BUTTON_TITLE_EDIT);
		edit.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				titleNameList.startEditing(titleNameList.getRecordIndex(titleNameList.getSelectedRecord()));
			}
		});
		MenuItem delete = new MenuItem(Constants.BUTTON_TITLE_DELETE);
		delete.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				titleNameList.removeData(titleNameList.getSelectedRecord());
				sendUpdateToAllClient(Constants.STARTUP_DATAMESSAGE_GENERAL_TITLENAMELIST_ID);
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
				
				titleNameList.startEditingNew();
				
			}
		});

		getCustomControlMenu().newButton("edit"+className, "edit", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(titleNameList.getSelectedRecord()!=null) {
					getCustomControlMenu().getButton("new"+className).disable();
					getCustomControlMenu().getButton("trash"+className).disable();
					getCustomControlMenu().getButton("save"+className).enable();
					getCustomControlMenu().getButton("gotofirst"+className).disable();
					getCustomControlMenu().getButton("gotoprevious"+className).disable();
					getCustomControlMenu().getButton("gotonext"+className).disable();
					getCustomControlMenu().getButton("gotolast"+className).disable();
					getCustomControlMenu().getButton("print"+className).disable();
					titleNameList.startEditing(titleNameList.getRecordIndex(titleNameList.getSelectedRecord()));
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
							titleNameList.removeData(titleNameList.getSelectedRecord());
							sendUpdateToAllClient(Constants.STARTUP_DATAMESSAGE_GENERAL_TITLENAMELIST_ID);
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
				titleNameList.saveAllEdits();
			}
		});

		getCustomControlMenu().newButton("gotofirst"+className, "gotofirst", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(titleNameList.getRecords().length>=0) {
					titleNameList.selectSingleRecord(0);
				}
			}
		});

		getCustomControlMenu().newButton("gotoprevious"+className, "gotoprevious", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(titleNameList.getRecords().length>=0) {
					if((titleNameList.getRecordIndex(titleNameList.getSelectedRecord())-1)>=0){
						titleNameList.selectSingleRecord((titleNameList.getRecordIndex(titleNameList.getSelectedRecord())-1));
					}
				}
			}
		});

		getCustomControlMenu().newButton("gotonext"+className, "gotonext", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(titleNameList.getRecords().length>=0) {
					if((titleNameList.getRecordIndex(titleNameList.getSelectedRecord())+1)<=(titleNameList.getRecords().length-1)){
						titleNameList.selectSingleRecord((titleNameList.getRecordIndex(titleNameList.getSelectedRecord())+1));
					}
				}
			}
		});

		getCustomControlMenu().newButton("gotolast"+className, "gotolast", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(titleNameList.getRecords().length>=0) {
					titleNameList.selectSingleRecord((titleNameList.getRecords().length-1));
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
				dataTitle.setContents("<span style='font-size: 16px !important;'>ตารางข้อมูล : "+Constants.TITLENAME_CUSTOMERTITLENAME_TH_TITLE+"</span>");
				Label separator = new Label();
				separator.setContents("<span style='font-size: 16px !important;'>&nbsp;</span>");
				ListGrid printGrid = new ListGrid() {
				    @Override
				    protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
				        return "font-size: 16px !important;";
				    }
				};
				printGrid.setFields(titleNameList.getFields());
				printGrid.setData(titleNameList.getRecords());
				printGrid.setPrintHeaderStyle("");

				printContainer.addMembers(companyName,title,dataTitle,separator,printGrid);

				Canvas.showPrintPreview(printContainer);
			}
		});
		
	}

}
