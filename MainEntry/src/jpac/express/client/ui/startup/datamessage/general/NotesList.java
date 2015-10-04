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

public class NotesList extends CustomWindow {

	private String className = this.getClass().getSimpleName();
	
	private static NotesList instance = null;

	private HLayout dataLayout;
	public final ListGrid notesList;
	
	public NotesList() {
		super(Constants.STARTUP_DATAMESSAGE_GENERAL_NOTESLIST_ID,Constants.STARTUP_DATAMESSAGE_GENERAL_NOTESLIST_TITLE, 1120, 600, false);
		notesList = new ListGrid();
		notesList.setDataSource(DataSource.get("notes"));
		notesList.setCanEdit(true);
		//notesList.setCanSelectCells(true);
		notesList.setAutoSaveEdits(true);
		notesList.setAutoFetchData(true);
		notesList.setContextMenu(getNotesListContextMenu());
		
		enableCustomControlMenu();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		initControlMenu();
		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAltKeyDown() && event.getKeyName().equals("A")) {
					notesList.startEditingNew();
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("E")) {
					notesList.startEditing(notesList.getRecordIndex(notesList.getSelectedRecord()));
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("D")) {
					notesList.removeData(notesList.getSelectedRecord());
					sendUpdateToAllClient(Constants.STARTUP_DATAMESSAGE_GENERAL_NOTESLIST_ID);
				}
			}
		});
		addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler(){

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				instance.bringToFront();
			}
			
		});
		notesList.focus();
	}
	
	public static NotesList getInstance() {
		if (instance == null) {
			instance = new NotesList();
		}
		return instance;
	}
	
	public void showNotesList() {
		show();
		Application.setOpeningWindow(instance.menuItem);
	}

	private void initWidgets() {
		dataLayout = new HLayout();
		dataLayout.setWidth100();
		dataLayout.setHeight100();
		
		notesList.addShowContextMenuHandler(new ShowContextMenuHandler() {
			@Override
			public void onShowContextMenu(ShowContextMenuEvent event) {
				event.cancel();
				getNotesListContextMenu().showContextMenu();
			}
		});
		notesList.addCellContextClickHandler(new CellContextClickHandler() {
			@Override
			public void onCellContextClick(final CellContextClickEvent rightClickEvent) {
				getNotesListContextMenu().showContextMenu();
			}
		});
		
		final ListGridField notesId = new ListGridField();
		notesId.setTitle(Constants.NOTES_NOTESID_TITLE);
		notesId.setAlign(Alignment.LEFT);
		notesId.setWidth("5%");
		notesId.setName(Constants.NOTES_NOTESID);

		final ListGridField notesDetails = new ListGridField();
		notesDetails.setTitle(Constants.NOTES_NOTESDETAILS_TITLE);
		notesDetails.setName(Constants.NOTES_NOTESDETAILS);

		notesList.addCellSavedHandler(new CellSavedHandler(){
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
				
				sendUpdateToAllClient(Constants.STARTUP_DATAMESSAGE_GENERAL_NOTESLIST_ID);
			}
		});
		notesList.addEditorEnterHandler(new EditorEnterHandler(){
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
		notesList.addEditorExitHandler(new EditorExitHandler(){
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
	
		notesList.setFields(notesId,notesDetails);
		dataLayout.addMember(notesList);
		addDataSection(dataLayout);
	}

	protected Menu getNotesListContextMenu() {
		final Menu menu = new Menu();
		MenuItem add = new MenuItem(Constants.BUTTON_TITLE_ADD);
		add.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				notesList.startEditingNew();
			}
		});
		MenuItem edit = new MenuItem(Constants.BUTTON_TITLE_EDIT);
		edit.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				notesList.startEditing(notesList.getRecordIndex(notesList.getSelectedRecord()));
			}
		});
		MenuItem delete = new MenuItem(Constants.BUTTON_TITLE_DELETE);
		delete.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				notesList.removeData(notesList.getSelectedRecord());
				sendUpdateToAllClient(Constants.STARTUP_DATAMESSAGE_GENERAL_NOTESLIST_ID);
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
				
				notesList.startEditingNew();
				
			}
		});

		getCustomControlMenu().newButton("edit"+className, "edit", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(notesList.getSelectedRecord()!=null) {
					getCustomControlMenu().getButton("new"+className).disable();
					getCustomControlMenu().getButton("trash"+className).disable();
					getCustomControlMenu().getButton("save"+className).enable();
					getCustomControlMenu().getButton("gotofirst"+className).disable();
					getCustomControlMenu().getButton("gotoprevious"+className).disable();
					getCustomControlMenu().getButton("gotonext"+className).disable();
					getCustomControlMenu().getButton("gotolast"+className).disable();
					getCustomControlMenu().getButton("print"+className).disable();
					notesList.startEditing(notesList.getRecordIndex(notesList.getSelectedRecord()));
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
							notesList.removeData(notesList.getSelectedRecord());
							sendUpdateToAllClient(Constants.STARTUP_DATAMESSAGE_GENERAL_NOTESLIST_ID);
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
				notesList.saveAllEdits();
			}
		});

		getCustomControlMenu().newButton("gotofirst"+className, "gotofirst", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(notesList.getRecords().length>=0) {
					notesList.selectSingleRecord(0);
				}
			}
		});

		getCustomControlMenu().newButton("gotoprevious"+className, "gotoprevious", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(notesList.getRecords().length>=0) {
					if((notesList.getRecordIndex(notesList.getSelectedRecord())-1)>=0){
						notesList.selectSingleRecord((notesList.getRecordIndex(notesList.getSelectedRecord())-1));
					}
				}
			}
		});

		getCustomControlMenu().newButton("gotonext"+className, "gotonext", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(notesList.getRecords().length>=0) {
					if((notesList.getRecordIndex(notesList.getSelectedRecord())+1)<=(notesList.getRecords().length-1)){
						notesList.selectSingleRecord((notesList.getRecordIndex(notesList.getSelectedRecord())+1));
					}
				}
			}
		});

		getCustomControlMenu().newButton("gotolast"+className, "gotolast", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if(notesList.getRecords().length>=0) {
					notesList.selectSingleRecord((notesList.getRecords().length-1));
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
				dataTitle.setContents("<span style='font-size: 16px !important;'>ตารางข้อมูล : "+Constants.STARTUP_DATAMESSAGE_GENERAL_NOTESLIST_TITLE+"</span>");
				Label separator = new Label();
				separator.setContents("<span style='font-size: 16px !important;'>&nbsp;</span>");
				ListGrid printGrid = new ListGrid() {
				    @Override
				    protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
				        return "font-size: 16px !important;";
				    }
				};
				printGrid.setFields(notesList.getFields());
				printGrid.setData(notesList.getRecords());
				printGrid.setPrintHeaderStyle("");

				printContainer.addMembers(companyName,title,dataTitle,separator,printGrid);

				Canvas.showPrintPreview(printContainer);
			}
		});
		
	}

}
