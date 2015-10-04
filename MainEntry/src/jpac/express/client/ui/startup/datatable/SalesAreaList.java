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

public class SalesAreaList extends CustomWindow {

	private String className = this.getClass().getSimpleName();

	private static SalesAreaList instance = null;

	private HLayout dataLayout;
	public final ListGrid salesAreaList;

	public SalesAreaList() {
		super(Constants.STARTUP_DATATABLE_SALESAREALIST_ID, Constants.STARTUP_DATATABLE_SALESAREALIST_TITLE, 1120, 600, false);
		salesAreaList = new ListGrid();
		salesAreaList.setDataSource(DataSource.get("salesArea"));
		salesAreaList.setCanEdit(true);
		salesAreaList.setAutoSaveEdits(true);
		salesAreaList.setAutoFetchData(true);
		salesAreaList.setContextMenu(getSalesAreaListContextMenu());

		enableCustomControlMenu();
		disableHeaderSection();
		disableFooterSection();
		initWidgets();
		initControlMenu();
		addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.isAltKeyDown() && event.getKeyName().equals("A")) {
					salesAreaList.startEditingNew();
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("E")) {
					salesAreaList.startEditing(salesAreaList.getRecordIndex(salesAreaList.getSelectedRecord()));
				}
				if (event.isAltKeyDown() && event.getKeyName().equals("D")) {
					salesAreaList.removeData(salesAreaList.getSelectedRecord());
					sendUpdateToAllClient(Constants.STARTUP_DATATABLE_SALESAREALIST_ID);
				}
			}
		});
		addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				instance.bringToFront();
			}

		});
		salesAreaList.focus();
	}

	public static SalesAreaList getInstance() {
		if (instance == null) {
			instance = new SalesAreaList();
		}
		return instance;
	}

	public void showSalesAreaList() {
		show();
		Application.setOpeningWindow(instance.menuItem);
	}

	private void initWidgets() {
		dataLayout = new HLayout();
		dataLayout.setWidth100();
		dataLayout.setHeight100();

		salesAreaList.addShowContextMenuHandler(new ShowContextMenuHandler() {
			@Override
			public void onShowContextMenu(ShowContextMenuEvent event) {
				event.cancel();
				getSalesAreaListContextMenu().showContextMenu();
			}
		});
		salesAreaList.addCellContextClickHandler(new CellContextClickHandler() {
			@Override
			public void onCellContextClick(final CellContextClickEvent rightClickEvent) {
				getSalesAreaListContextMenu().showContextMenu();
			}
		});

		final ListGridField salesAreaId = new ListGridField();
		salesAreaId.setTitle(Constants.SALESAREA_SALESAREAID_TITLE);
		salesAreaId.setName(Constants.SALESAREA_SALESAREAIDENTIFY);
		salesAreaId.setWidth("10%");

		final ListGridField salesAreaShortNameTH = new ListGridField();
		salesAreaShortNameTH.setTitle(Constants.SALESAREA_SALESAREASHORTNAME_TH_TITLE);
		salesAreaShortNameTH.setName(Constants.SALESAREA_SALESAREASHORTNAME_TH);
		salesAreaShortNameTH.setWidth("10%");

		final ListGridField salesAreaShortNameEN = new ListGridField();
		salesAreaShortNameEN.setTitle(Constants.SALESAREA_SALESAREASHORTNAME_EN_TITLE);
		salesAreaShortNameEN.setName(Constants.SALESAREA_SALESAREASHORTNAME_EN);
		salesAreaShortNameEN.setWidth("10%");

		final ListGridField salesAreaDetailsTH = new ListGridField();
		salesAreaDetailsTH.setTitle(Constants.SALESAREA_SALESAREADETAILS_TH_TITLE);
		salesAreaDetailsTH.setName(Constants.SALESAREA_SALESAREADETAILS_TH);
		salesAreaDetailsTH.setWidth("50%");

		final ListGridField salesAreaDetailsEN = new ListGridField();
		salesAreaDetailsEN.setTitle(Constants.SALESAREA_SALESAREADETAILS_EN_TITLE);
		salesAreaDetailsEN.setName(Constants.SALESAREA_SALESAREADETAILS_EN);
		salesAreaDetailsEN.setWidth("20%");

		salesAreaList.addCellSavedHandler(new CellSavedHandler() {
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

				sendUpdateToAllClient(Constants.STARTUP_DATATABLE_SALESAREALIST_ID);
			}
		});
		salesAreaList.addEditorEnterHandler(new EditorEnterHandler() {
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
		salesAreaList.addEditorExitHandler(new EditorExitHandler() {
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

		salesAreaList.setFields(salesAreaId, salesAreaShortNameTH, salesAreaShortNameEN, salesAreaDetailsTH, salesAreaDetailsEN);
		dataLayout.addMember(salesAreaList);
		addDataSection(dataLayout);
	}

	protected Menu getSalesAreaListContextMenu() {
		final Menu menu = new Menu();
		MenuItem add = new MenuItem(Constants.BUTTON_TITLE_ADD);
		add.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				salesAreaList.startEditingNew();
			}
		});
		MenuItem edit = new MenuItem(Constants.BUTTON_TITLE_EDIT);
		edit.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				salesAreaList.startEditing(salesAreaList.getRecordIndex(salesAreaList.getSelectedRecord()));
			}
		});
		MenuItem delete = new MenuItem(Constants.BUTTON_TITLE_DELETE);
		delete.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
			@Override
			public void onClick(MenuItemClickEvent event) {
				salesAreaList.removeData(salesAreaList.getSelectedRecord());
				sendUpdateToAllClient(Constants.STARTUP_DATATABLE_SALESAREALIST_ID);
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

				salesAreaList.startEditingNew();

			}
		});

		getCustomControlMenu().newButton("edit" + className, "edit", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (salesAreaList.getSelectedRecord() != null) {
					getCustomControlMenu().getButton("new" + className).disable();
					getCustomControlMenu().getButton("trash" + className).disable();
					getCustomControlMenu().getButton("save" + className).enable();
					getCustomControlMenu().getButton("gotofirst" + className).disable();
					getCustomControlMenu().getButton("gotoprevious" + className).disable();
					getCustomControlMenu().getButton("gotonext" + className).disable();
					getCustomControlMenu().getButton("gotolast" + className).disable();
					getCustomControlMenu().getButton("print" + className).disable();
					salesAreaList.startEditing(salesAreaList.getRecordIndex(salesAreaList.getSelectedRecord()));
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
							salesAreaList.removeData(salesAreaList.getSelectedRecord());
							sendUpdateToAllClient(Constants.STARTUP_DATATABLE_SALESAREALIST_ID);
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
				salesAreaList.saveAllEdits();
			}
		});

		getCustomControlMenu().newButton("gotofirst" + className, "gotofirst", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (salesAreaList.getRecords().length >= 0) {
					salesAreaList.selectSingleRecord(0);
				}
			}
		});

		getCustomControlMenu().newButton("gotoprevious" + className, "gotoprevious", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (salesAreaList.getRecords().length >= 0) {
					if ((salesAreaList.getRecordIndex(salesAreaList.getSelectedRecord()) - 1) >= 0) {
						salesAreaList.selectSingleRecord((salesAreaList.getRecordIndex(salesAreaList.getSelectedRecord()) - 1));
					}
				}
			}
		});

		getCustomControlMenu().newButton("gotonext" + className, "gotonext", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (salesAreaList.getRecords().length >= 0) {
					if ((salesAreaList.getRecordIndex(salesAreaList.getSelectedRecord()) + 1) <= (salesAreaList.getRecords().length - 1)) {
						salesAreaList.selectSingleRecord((salesAreaList.getRecordIndex(salesAreaList.getSelectedRecord()) + 1));
					}
				}
			}
		});

		getCustomControlMenu().newButton("gotolast" + className, "gotolast", new com.smartgwt.client.widgets.events.ClickHandler() {
			@Override
			public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
				if (salesAreaList.getRecords().length >= 0) {
					salesAreaList.selectSingleRecord((salesAreaList.getRecords().length - 1));
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
				dataTitle.setContents("<span style='font-size: 16px !important;'>ตารางข้อมูล : " + Constants.STARTUP_DATATABLE_SALESAREALIST_TITLE + "</span>");
				Label separator = new Label();
				separator.setContents("<span style='font-size: 16px !important;'>&nbsp;</span>");
				ListGrid printGrid = new ListGrid() {
					@Override
					protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
						return "font-size: 16px !important;";
					}
				};
				printGrid.setFields(salesAreaList.getFields());
				printGrid.setData(salesAreaList.getRecords());
				printGrid.setPrintHeaderStyle("");

				printContainer.addMembers(companyName, title, dataTitle, separator, printGrid);

				Canvas.showPrintPreview(printContainer);
			}
		});

	}

}
