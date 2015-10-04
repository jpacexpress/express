package jpac.express.client.ui.startup.datatable;

import jpac.express.client.Application;
import jpac.express.client.MainEntry;
import jpac.express.client.ui.custom.CustomWindow;
import jpac.express.shared.Constants;

import com.google.gwt.appengine.channel.client.ChannelError;
import com.google.gwt.appengine.channel.client.SocketListener;
import com.google.gwt.user.client.rpc.AsyncCallback;
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

public class SalesTypeList extends CustomWindow {

	private String className = this.getClass().getSimpleName();

	private static SalesTypeList instance = null;

	private HLayout dataLayout;
	public final ListGrid salesTypeList;

	public SalesTypeList() {
		super(Constants.STARTUP_DATATABLE_SALESTYPELIST_ID, Constants.STARTUP_DATATABLE_SALESTYPELIST_TITLE, 1120, 600, false);
		salesTypeList = new ListGrid();
		salesTypeList.setDataSource(DataSource.get("salesType"));
		salesTypeList.setCanEdit(true);
		salesTypeList.setAutoSaveEdits(true);
		salesTypeList.setAutoFetchData(true);
		salesTypeList.setContextMenu(getSalesTypeListContextMenu());

		enableCustomControlMenu();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		initControlMenu();
		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAltKeyDown() && event.getKeyName().equals("A")) {
					salesTypeList.startEditingNew();
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("E")) {
					salesTypeList.startEditing(salesTypeList.getRecordIndex(salesTypeList.getSelectedRecord()));
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("D")) {
					salesTypeList.removeData(salesTypeList.getSelectedRecord());
					sendUpdateToAllClient(Constants.STARTUP_DATATABLE_SALESTYPELIST_ID);
				}
			}
		});
		addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				instance.bringToFront();
			}

		});

		salesTypeList.focus();
	}

	public static SalesTypeList getInstance() {
		if (instance == null) {
			instance = new SalesTypeList();
		}
		return instance;
	}

	public void showSalesTypeList() {
		show();
		Application.setOpeningWindow(instance.menuItem);
	}

	private void initWidgets() {
		dataLayout = new HLayout();
		dataLayout.setWidth100();
		dataLayout.setHeight100();

		salesTypeList.addShowContextMenuHandler(new ShowContextMenuHandler() {
			@Override
			public void onShowContextMenu(ShowContextMenuEvent event) {
				event.cancel();
				getSalesTypeListContextMenu().showContextMenu();
			}
		});
		salesTypeList.addCellContextClickHandler(new CellContextClickHandler() {
			@Override
			public void onCellContextClick(final CellContextClickEvent rightClickEvent) {
				getSalesTypeListContextMenu().showContextMenu();
			}
		});

		final ListGridField salesTypeId = new ListGridField();
		salesTypeId.setTitle(Constants.SALESTYPE_SALESTYPEID_TITLE);
		salesTypeId.setName(Constants.SALESTYPE_SALESTYPEID);
		salesTypeId.setWidth("10%");

		final ListGridField salesTypeShortNameTH = new ListGridField();
		salesTypeShortNameTH.setTitle(Constants.SALESTYPE_SALESTYPESHORTNAME_TH_TITLE);
		salesTypeShortNameTH.setName(Constants.SALESTYPE_SALESTYPESHORTNAME_TH);
		salesTypeShortNameTH.setWidth("10%");

		final ListGridField salesTypeShortNameEN = new ListGridField();
		salesTypeShortNameEN.setTitle(Constants.SALESTYPE_SALESTYPESHORTNAME_EN_TITLE);
		salesTypeShortNameEN.setName(Constants.SALESTYPE_SALESTYPESHORTNAME_EN);
		salesTypeShortNameEN.setWidth("10%");

		final ListGridField salesTypeDetailsTH = new ListGridField();
		salesTypeDetailsTH.setTitle(Constants.SALESTYPE_SALESTYPEDETAILS_TH_TITLE);
		salesTypeDetailsTH.setName(Constants.SALESTYPE_SALESTYPEDETAILS_TH);
		salesTypeDetailsTH.setWidth("50%");

		final ListGridField salesTypeDetailsEN = new ListGridField();
		salesTypeDetailsEN.setTitle(Constants.SALESTYPE_SALESTYPEDETAILS_EN_TITLE);
		salesTypeDetailsEN.setName(Constants.SALESTYPE_SALESTYPEDETAILS_EN);
		salesTypeDetailsEN.setWidth("20%");

		salesTypeList.addCellSavedHandler(new CellSavedHandler() {
			@Override
			public void onCellSaved(CellSavedEvent event) {
				getCustomControlMenu().getButton("new" + className).enable();
				getCustomControlMenu().getButton("edit" + className).enable();
				getCustomControlMenu().getButton("trash" + className).enable();

				getCustomControlMenu().getButton("save" + className).enable();
				getCustomControlMenu().getButton("gotofirst" + className).enable();
				getCustomControlMenu().getButton("gotoprevious" + className).enable();
				getCustomControlMenu().getButton("gotonext" + className).enable();
				getCustomControlMenu().getButton("gotolast" + className).enable();
				getCustomControlMenu().getButton("print" + className).enable();

				sendUpdateToAllClient(Constants.STARTUP_DATATABLE_SALESTYPELIST_ID);
			}
		});
		salesTypeList.addEditorEnterHandler(new EditorEnterHandler() {
			@Override
			public void onEditorEnter(EditorEnterEvent event) {
				getCustomControlMenu().getButton("new" + className).disable();
				getCustomControlMenu().getButton("trash" + className).disable();
				getCustomControlMenu().getButton("save" + className).disable();
				getCustomControlMenu().getButton("gotofirst" + className).disable();
				getCustomControlMenu().getButton("gotoprevious" + className).disable();
				getCustomControlMenu().getButton("gotonext" + className).disable();
				getCustomControlMenu().getButton("gotolast" + className).disable();
				getCustomControlMenu().getButton("print" + className).disable();
			}
		});
		salesTypeList.addEditorExitHandler(new EditorExitHandler() {
			@Override
			public void onEditorExit(EditorExitEvent event) {
				getCustomControlMenu().getButton("new" + className).enable();
				getCustomControlMenu().getButton("edit" + className).enable();
				getCustomControlMenu().getButton("trash" + className).enable();

				getCustomControlMenu().getButton("save" + className).enable();
				getCustomControlMenu().getButton("gotofirst" + className).enable();
				getCustomControlMenu().getButton("gotoprevious" + className).enable();
				getCustomControlMenu().getButton("gotonext" + className).enable();
				getCustomControlMenu().getButton("gotolast" + className).enable();
				getCustomControlMenu().getButton("print" + className).enable();
			}
		});

		salesTypeList.setFields(salesTypeId, salesTypeShortNameTH, salesTypeShortNameEN, salesTypeDetailsTH, salesTypeDetailsEN);
		dataLayout.addMember(salesTypeList);
		addDataSection(dataLayout);
	}

	protected Menu getSalesTypeListContextMenu() {
		final Menu menu = new Menu();
		MenuItem add = new MenuItem(Constants.BUTTON_TITLE_ADD);
		add.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				salesTypeList.startEditingNew();
			}
		});
		MenuItem edit = new MenuItem(Constants.BUTTON_TITLE_EDIT);
		edit.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				salesTypeList.startEditing(salesTypeList.getRecordIndex(salesTypeList.getSelectedRecord()));
			}
		});
		MenuItem delete = new MenuItem(Constants.BUTTON_TITLE_DELETE);
		delete.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				salesTypeList.removeData(salesTypeList.getSelectedRecord());
				sendUpdateToAllClient(Constants.STARTUP_DATATABLE_SALESTYPELIST_ID);
			}
		});
		menu.addItem(add);
		menu.addItem(edit);
		menu.addItem(delete);
		return menu;
	}

	private void initControlMenu() {

		getCustomControlMenu().newButton("new" + className, "new", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				getCustomControlMenu().getButton("edit" + className).disable();
				getCustomControlMenu().getButton("trash" + className).disable();

				getCustomControlMenu().getButton("save" + className).enable();
				getCustomControlMenu().getButton("gotofirst" + className).disable();
				getCustomControlMenu().getButton("gotoprevious" + className).disable();
				getCustomControlMenu().getButton("gotonext" + className).disable();
				getCustomControlMenu().getButton("gotolast" + className).disable();
				getCustomControlMenu().getButton("print" + className).disable();

				salesTypeList.startEditingNew();

			}
		});

		getCustomControlMenu().newButton("edit" + className, "edit", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (salesTypeList.getSelectedRecord() != null) {
					getCustomControlMenu().getButton("new" + className).disable();
					getCustomControlMenu().getButton("trash" + className).disable();
					getCustomControlMenu().getButton("save" + className).enable();
					getCustomControlMenu().getButton("gotofirst" + className).disable();
					getCustomControlMenu().getButton("gotoprevious" + className).disable();
					getCustomControlMenu().getButton("gotonext" + className).disable();
					getCustomControlMenu().getButton("gotolast" + className).disable();
					getCustomControlMenu().getButton("print" + className).disable();
					salesTypeList.startEditing(salesTypeList.getRecordIndex(salesTypeList.getSelectedRecord()));
				}
			}
		});

		getCustomControlMenu().newButton("trash" + className, "trash", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				getCustomControlMenu().getButton("new" + className).disable();
				getCustomControlMenu().getButton("edit" + className).disable();

				getCustomControlMenu().getButton("gotofirst" + className).disable();
				getCustomControlMenu().getButton("gotoprevious" + className).disable();
				getCustomControlMenu().getButton("gotonext" + className).disable();
				getCustomControlMenu().getButton("gotolast" + className).disable();
				getCustomControlMenu().getButton("print" + className).disable();

				SC.confirm(Constants.CONFIRM_ASK_DELETE, new BooleanCallback() {
					@Override
					public void execute(Boolean value) {
						getCustomControlMenu().getButton("new" + className).enable();
						getCustomControlMenu().getButton("edit" + className).enable();
						getCustomControlMenu().getButton("trash" + className).enable();

						getCustomControlMenu().getButton("save" + className).disable();
						getCustomControlMenu().getButton("gotofirst" + className).enable();
						getCustomControlMenu().getButton("gotoprevious" + className).enable();
						getCustomControlMenu().getButton("gotonext" + className).enable();
						getCustomControlMenu().getButton("gotolast" + className).enable();
						getCustomControlMenu().getButton("print" + className).enable();
						if (value) {
							salesTypeList.removeData(salesTypeList.getSelectedRecord());
							sendUpdateToAllClient(Constants.STARTUP_DATATABLE_SALESTYPELIST_ID);
						}
					}

				});

			}
		});

		getCustomControlMenu().newButton("cancel" + className, "cancel", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				getCustomControlMenu().getButton("new" + className).enable();
				getCustomControlMenu().getButton("edit" + className).enable();
				getCustomControlMenu().getButton("trash" + className).enable();

				getCustomControlMenu().getButton("save" + className).enable();
				getCustomControlMenu().getButton("gotofirst" + className).enable();
				getCustomControlMenu().getButton("gotoprevious" + className).enable();
				getCustomControlMenu().getButton("gotonext" + className).enable();
				getCustomControlMenu().getButton("gotolast" + className).enable();
				getCustomControlMenu().getButton("print" + className).enable();
			}
		});

		getCustomControlMenu().newButton("save" + className, "save", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				getCustomControlMenu().getButton("new" + className).enable();
				getCustomControlMenu().getButton("edit" + className).enable();
				getCustomControlMenu().getButton("trash" + className).enable();

				getCustomControlMenu().getButton("save" + className).enable();
				getCustomControlMenu().getButton("gotofirst" + className).enable();
				getCustomControlMenu().getButton("gotoprevious" + className).enable();
				getCustomControlMenu().getButton("gotonext" + className).enable();
				getCustomControlMenu().getButton("gotolast" + className).enable();
				getCustomControlMenu().getButton("print" + className).enable();
				salesTypeList.saveAllEdits();
			}
		});

		getCustomControlMenu().newButton("gotofirst" + className, "gotofirst", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (salesTypeList.getRecords().length >= 0) {
					salesTypeList.selectSingleRecord(0);
				}
			}
		});

		getCustomControlMenu().newButton("gotoprevious" + className, "gotoprevious", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (salesTypeList.getRecords().length >= 0) {
					if ((salesTypeList.getRecordIndex(salesTypeList.getSelectedRecord()) - 1) >= 0) {
						salesTypeList.selectSingleRecord((salesTypeList.getRecordIndex(salesTypeList.getSelectedRecord()) - 1));
					}
				}
			}
		});

		getCustomControlMenu().newButton("gotonext" + className, "gotonext", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (salesTypeList.getRecords().length >= 0) {
					if ((salesTypeList.getRecordIndex(salesTypeList.getSelectedRecord()) + 1) <= (salesTypeList.getRecords().length - 1)) {
						salesTypeList.selectSingleRecord((salesTypeList.getRecordIndex(salesTypeList.getSelectedRecord()) + 1));
					}
				}
			}
		});

		getCustomControlMenu().newButton("gotolast" + className, "gotolast", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (salesTypeList.getRecords().length >= 0) {
					salesTypeList.selectSingleRecord((salesTypeList.getRecords().length - 1));
				}
			}
		});

		getCustomControlMenu().newButton("print" + className, "print", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				VLayout printContainer = new VLayout();

				Label companyName = new Label();
				companyName.setContents("<span style='font-size: 16px !important;'>บริษัท</span>");
				Label title = new Label();
				title.setContents("<span style='font-size: 22px !important;'>รายการตารางข้อมูล</span>");
				Label dataTitle = new Label();
				dataTitle.setContents("<span style='font-size: 16px !important;'>ตารางข้อมูล : " + Constants.STARTUP_DATATABLE_SALESTYPELIST_TITLE + "</span>");
				Label separator = new Label();
				separator.setContents("<span style='font-size: 16px !important;'>&nbsp;</span>");
				ListGrid printGrid = new ListGrid() {
					@Override
					protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
						return "font-size: 16px !important;";
					}
				};
				printGrid.setFields(salesTypeList.getFields());
				printGrid.setData(salesTypeList.getRecords());
				printGrid.setPrintHeaderStyle("");

				printContainer.addMembers(companyName, title, dataTitle, separator, printGrid);

				Canvas.showPrintPreview(printContainer);
			}
		});

	}

}
