package jpac.express.client.ui.startup;

import jpac.express.client.Application;
import jpac.express.client.MainEntry;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
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

public class DocumentsNumberList extends CustomWindow {

	private String className = this.getClass().getSimpleName();
	
	private static DocumentsNumberList instance = null;
	public static final String name = "กำหนดเลขที่เอกสาร";

	private HLayout dataLayout;
	private static ListGrid documentsNumberList;
	
	public DocumentsNumberList() {
		super("startupDocumentsNumberList",name, 1120, 600, false);
		documentsNumberList = new ListGrid();
		documentsNumberList.setDataSource(DataSource.get("documentNumber"));
		documentsNumberList.setCanEdit(true);
		//documentsNumberList.setCanSelectCells(true);
		documentsNumberList.setAutoSaveEdits(true);
		documentsNumberList.setAutoFetchData(true);
		documentsNumberList.setContextMenu(getDocumentsNumberListContextMenu());
		
		enableCustomControlMenu();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		initControlMenu();
		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAltKeyDown() && event.getKeyName().equals("A")) {
					documentsNumberList.startEditingNew();
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("E")) {
					documentsNumberList.startEditing(documentsNumberList.getRecordIndex(documentsNumberList.getSelectedRecord()));
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("D")) {
					documentsNumberList.removeData(documentsNumberList.getSelectedRecord());
				}
			}
		});
		addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler(){

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				instance.bringToFront();
			}
			
		});
		documentsNumberList.fetchData();
	}
	
	public Record getDocumentNextNumber(String documentName) {
		
		//DataSource datasource = DataSource.get("documentsNumber");
		Record[] documents = documentsNumberList.getRecords();
		for(Record record:documents) {
			if(record.getAttribute("documentNumberSupId").equals(documentName)) {
				Record document = new Record();
				document.setAttribute("documentNumber", record);
				return document;
			}
		}
		return null;
	}
	
	public static DocumentsNumberList getInstance() {
		if (instance == null) {
			instance = new DocumentsNumberList();
		}
		return instance;
	}
	
	public void showDocumentsNumberList() {
		show();
		Application.setOpeningWindow(instance.menuItem);
	}
	
	private void initWidgets() {
		dataLayout = new HLayout();
		dataLayout.setWidth100();
		dataLayout.setHeight100();
		
		documentsNumberList.addShowContextMenuHandler(new ShowContextMenuHandler() {
			@Override
			public void onShowContextMenu(ShowContextMenuEvent event) {
				event.cancel();
				getDocumentsNumberListContextMenu().showContextMenu();
			}
		});
		documentsNumberList.addCellContextClickHandler(new CellContextClickHandler() {
			@Override
			public void onCellContextClick(final CellContextClickEvent rightClickEvent) {
				getDocumentsNumberListContextMenu().showContextMenu();
			}
		});
		
		final ListGridField documentsNumberId = new ListGridField();
		documentsNumberId.setTitle("หมวด");
		documentsNumberId.setName("documentNumberId");
		documentsNumberId.setWidth("10%");

		final ListGridField documentsNumberNameTH = new ListGridField();
		documentsNumberNameTH.setTitle("ชื่อเอกสาร");
		documentsNumberNameTH.setName("documentNumberNameTH");
		documentsNumberNameTH.setWidth("50%");
		
		final ListGridField documentsNumberNameEN = new ListGridField();
		documentsNumberNameEN.setTitle("ชื่อภาษาอังกฤษ");
		documentsNumberNameEN.setName("documentNumberNameEN");
		documentsNumberNameEN.setWidth("20%");

		final ListGridField documentsNumberSupId = new ListGridField();
		documentsNumberSupId.setTitle("");
		documentsNumberSupId.setName("documentNumberSupId");
		documentsNumberSupId.setWidth("5%");
		
		final ListGridField documentsNumberNext = new ListGridField();
		documentsNumberNext.setTitle("เลขที่ถัดไป");
		documentsNumberNext.setName("documentNextNumber");
		documentsNumberNext.setWidth("10%");
		
		final ListGridField documentsNumberDepartment = new ListGridField();
		documentsNumberDepartment.setTitle("แผนก");
		documentsNumberDepartment.setName("documentNumberDepartment");
		documentsNumberDepartment.setWidth("5%");

		documentsNumberList.addCellSavedHandler(new CellSavedHandler(){
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
			}
		});
		documentsNumberList.addEditorEnterHandler(new EditorEnterHandler(){
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
		documentsNumberList.addEditorExitHandler(new EditorExitHandler(){
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
	
		documentsNumberList.setFields(documentsNumberId, documentsNumberNameTH, documentsNumberNameEN, documentsNumberSupId, documentsNumberNext, documentsNumberDepartment);
		dataLayout.addMember(documentsNumberList);
		addDataSection(dataLayout);
	}

	protected Menu getDocumentsNumberListContextMenu() {
		final Menu menu = new Menu();
		MenuItem add = new MenuItem(Constants.BUTTON_TITLE_ADD);
		add.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				documentsNumberList.startEditingNew();
			}
		});
		MenuItem edit = new MenuItem(Constants.BUTTON_TITLE_EDIT);
		edit.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				documentsNumberList.startEditing(documentsNumberList.getRecordIndex(documentsNumberList.getSelectedRecord()));
			}
		});
		MenuItem delete = new MenuItem(Constants.BUTTON_TITLE_DELETE);
		delete.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				documentsNumberList.removeData(documentsNumberList.getSelectedRecord());
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
				
				documentsNumberList.startEditingNew();
				
			}
		});

		getCustomControlMenu().newButton("edit"+className, "edit", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(documentsNumberList.getSelectedRecord()!=null) {
					getCustomControlMenu().getButton("new"+className).disable();
					getCustomControlMenu().getButton("trash"+className).disable();
					getCustomControlMenu().getButton("save"+className).enable();
					getCustomControlMenu().getButton("gotofirst"+className).disable();
					getCustomControlMenu().getButton("gotoprevious"+className).disable();
					getCustomControlMenu().getButton("gotonext"+className).disable();
					getCustomControlMenu().getButton("gotolast"+className).disable();
					getCustomControlMenu().getButton("print"+className).disable();
					documentsNumberList.startEditing(documentsNumberList.getRecordIndex(documentsNumberList.getSelectedRecord()));
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
							documentsNumberList.removeData(documentsNumberList.getSelectedRecord());
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
				documentsNumberList.saveAllEdits();
			}
		});

		getCustomControlMenu().newButton("gotofirst"+className, "gotofirst", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(documentsNumberList.getRecords().length>=0) {
					documentsNumberList.selectSingleRecord(0);
				}
			}
		});

		getCustomControlMenu().newButton("gotoprevious"+className, "gotoprevious", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(documentsNumberList.getRecords().length>=0) {
					if((documentsNumberList.getRecordIndex(documentsNumberList.getSelectedRecord())-1)>=0){
						documentsNumberList.selectSingleRecord((documentsNumberList.getRecordIndex(documentsNumberList.getSelectedRecord())-1));
					}
				}
			}
		});

		getCustomControlMenu().newButton("gotonext"+className, "gotonext", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(documentsNumberList.getRecords().length>=0) {
					if((documentsNumberList.getRecordIndex(documentsNumberList.getSelectedRecord())+1)<=(documentsNumberList.getRecords().length-1)){
						documentsNumberList.selectSingleRecord((documentsNumberList.getRecordIndex(documentsNumberList.getSelectedRecord())+1));
					}
				}
			}
		});

		getCustomControlMenu().newButton("gotolast"+className, "gotolast", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(documentsNumberList.getRecords().length>=0) {
					documentsNumberList.selectSingleRecord((documentsNumberList.getRecords().length-1));
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
				dataTitle.setContents("<span style='font-size: 16px !important;'>ตารางข้อมูล : "+name+"</span>");
				Label separator = new Label();
				separator.setContents("<span style='font-size: 16px !important;'>&nbsp;</span>");
				ListGrid printGrid = new ListGrid() {
				    @Override
				    protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
				        return "font-size: 16px !important;";
				    }
				};
				printGrid.setFields(documentsNumberList.getFields());
				printGrid.setData(documentsNumberList.getRecords());
				printGrid.setPrintHeaderStyle("");

				printContainer.addMembers(companyName,title,dataTitle,separator,printGrid);

				Canvas.showPrintPreview(printContainer);
			}
		});
		
	}

}
