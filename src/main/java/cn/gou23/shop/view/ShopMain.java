package cn.gou23.shop.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressAdapter;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.springframework.beans.BeanUtils;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;

import cn.gou23.cgodo.page.Page;
import cn.gou23.cgodo.page.PageContext;
import cn.gou23.cgodo.util.UtilBean;
import cn.gou23.cgodo.util.UtilDateTime;
import cn.gou23.cgodo.util.UtilLog;
import cn.gou23.shop.constant.SaleStatus;
import cn.gou23.shop.constant.SourceType;
import cn.gou23.shop.handler.ItemSourceHandler;
import cn.gou23.shop.handler.ItemSourceHandler.ProcessHandler;
import cn.gou23.shop.model.ItemSourceModel;
import cn.gou23.shop.model.ShopModel;
import cn.gou23.shop.model.SourceOwnerModel;
import cn.gou23.shop.service.ItemSourceService;
import cn.gou23.shop.service.ShopService;
import cn.gou23.shop.service.SourceOwnerService;
import cn.gou23.shop.taobaoapi.ItemApi;
import cn.gou23.shop.util.UtilApplicationContext;
import cn.gou23.shop.util.UtilImage;

public class ShopMain {

	protected Shell shell;
	private Text text;
	private static ItemSourceHandler itemSourceHandler;
	private static ItemSourceService itemSourceService;
	private static ShopService shopService;
	private static Map<String, ShopModel> shopModelByName = new HashMap<String, ShopModel>();
	private Table table;
	private Text text_1;
	private Label lblNewLabel;
	private TabFolder tabFolder;
	private Browser browser;
	private TabItem tabItem;
	private// 从淘宝店铺更新过来的数据
	List<ItemSourceModel> itemSourceModelsOfShopAll = new ArrayList<ItemSourceModel>();
	private Combo combo;
	// 当前正在处理的货源，用于比如更新淘宝客数据，等情况需要处理
	private ItemSourceModel currentItemSourceModel;
	private static ItemApi itemApi;
	private static SourceOwnerService sourceOwnerService;
	private static TaobaoClient taobaoClient;
	/**
	 * 淘宝分页
	 */
	private Page<ItemSourceModel> page = new Page<ItemSourceModel>();
	private Text text_2;
	private Text text_3;
	private Label lblNewLabel_1;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("org.eclipse.swt.browser.XULRunnerPath",
				System.getProperty("user.dir") + "/xulrunner");
		try {
			// 读取spring
			UtilApplicationContext.load();
			itemSourceHandler = UtilApplicationContext
					.get(ItemSourceHandler.class);
			itemSourceService = UtilApplicationContext
					.get(ItemSourceService.class);
			shopService = UtilApplicationContext.get(ShopService.class);
			itemApi = UtilApplicationContext.get(ItemApi.class);
			sourceOwnerService = UtilApplicationContext
					.get(SourceOwnerService.class);
			taobaoClient = UtilApplicationContext.get(TaobaoClient.class);
			ShopMain window = new ShopMain();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(1360, 768);
		shell.setText("\u7F51\u5E97\u8D27\u6E90\u7BA1\u7406\u5E73\u53F0");

		tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setBounds(10, 60, 1324, 660);

		tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("\u6D4F\u89C8\u5668");

		browser = new Browser(tabFolder, SWT.NONE | SWT.MOZILLA);
		browser.setUrl("https://login.taobao.com/member/login.jhtml?style=mini&from=alimama&redirectURL=http%3A%2F%2Flogin.taobao.com%2Fmember%2Ftaobaoke%2Flogin.htm%3Fis_login%3d1&full_redirect=true&disableQuickLogin=true");
		tabItem.setControl(browser);

		browser.addProgressListener(new MyProgressListener() {
			@Override
			public void realCompleted(ProgressEvent event, Browser browser) {
				if (browser.getUrl().indexOf("login.taobao.com/member/login") >= 0) {
					// 登陆，自动设置密码
					browser.evaluate("document.getElementById('TPL_username_1').value='rjwgshuai'");
					browser.evaluate("document.getElementById('TPL_password_1').value='rjwtxdyshuai~!@#2'");
				}
			}

			public boolean isCompleted(ProgressEvent event, Browser browser) {
				if (browser.getUrl().indexOf("login.taobao.com/member/login") >= 0) {
					return browser
							.execute("document.getElementById('TPL_username_1').toString()")
							&& browser
									.execute("document.getElementById('TPL_password_1').toString()");
				} else {
					return true;
				}
			}

			@Override
			public void changed(ProgressEvent event) {

			}
		});

		TabItem tabItem_1 = new TabItem(tabFolder, SWT.NONE);
		tabItem_1.setText("\u64CD\u4F5C");

		Group group = new Group(tabFolder, SWT.NONE);
		group.setText("");
		tabItem_1.setControl(group);

		text = new Text(group, SWT.BORDER);
		text.setBounds(87, 20, 310, 23);

		Label label = new Label(group, SWT.NONE);
		label.setBounds(10, 23, 61, 17);
		label.setText("\u8D27\u6E90\u5730\u5740");

		Button button_1 = new Button(group, SWT.NONE);

		button_1.setBounds(404, 20, 80, 27);
		button_1.setText("\u6DFB\u52A0\u8D27\u6E90");

		table = new Table(group, SWT.BORDER | SWT.FULL_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				addTableMenu();
			}
		});
		table.setBounds(10, 111, 1296, 476);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		initTable(group);

		text_1 = new Text(group, SWT.BORDER);
		text_1.setBounds(87, 49, 310, 23);

		Label label_2 = new Label(group, SWT.NONE);
		label_2.setText("\u6807\u9898");
		label_2.setBounds(10, 55, 61, 17);

		Button button_2 = new Button(group, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ItemSourceModel itemSourceModel = new ItemSourceModel();
				itemSourceModel.setTitle(text_1.getText());
				itemSourceModel.setItemId(text_1.getText().trim());
				PageContext.set(new Page<ItemSourceModel>());
				List<ItemSourceModel> itemSourceModels = itemSourceService
						.find(itemSourceModel);
				reviewTable(itemSourceModels);
			}
		});
		button_2.setBounds(10, 78, 80, 27);
		button_2.setText("查询");

		Button button_3 = new Button(group, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				List<ItemSourceModel> itemSourceModels;
				ItemSourceModel itemSourceModel = new ItemSourceModel();
				itemSourceModel.setShopId(getShop().getId());
				itemSourceModel.setType(SourceType.淘宝联盟);
				itemSourceModels = itemSourceService.getCreate(itemSourceModel);

				// 导出到文件
				try {
					FileDialog fileselect = new FileDialog(shell, SWT.SINGLE);
					fileselect.setFilterNames(new String[] { "请选择txt文件" });
					fileselect.setFilterExtensions(new String[] { "*.txt" });
					String url = "";
					url = fileselect.open();

					if (StringUtils.isBlank(url)) {
						errorMessage("请选择导出的文件");
						return;
					}

					if (!url.endsWith(".txt")) {
						url = url + ".txt";
					}

					// 初始化文件
					File file = new File(url);
					File parent = new File(file.getParent());

					if (!parent.exists()) {
						parent.mkdirs();
					}

					if (!file.exists()) {
						file.createNewFile();
					}

					BufferedWriter writer = new BufferedWriter(new FileWriter(
							file, false));

					for (ItemSourceModel itemSourceModel2 : itemSourceModels) {
						writer.write(itemSourceModel2.getSourceDetailUrl()
								+ "\r\n");
					}

					writer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		button_3.setBounds(490, 20, 80, 27);
		button_3.setText("导出");

		text_2 = new Text(group, SWT.BORDER);
		text_2.setBounds(404, 49, 73, 23);

		Button button_4 = new Button(group, SWT.NONE);
		button_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				List<ItemSourceModel> itemSourceModels = itemSourceService
						.findBlacklist();
				reviewTable(itemSourceModels);
			}
		});
		button_4.setBounds(91, 78, 80, 27);
		button_4.setText("黑名单查询");

		Button button_5 = new Button(group, SWT.NONE);
		button_5.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				List<ItemSourceModel> itemSourceModels = itemSourceService
						.findSourceBlacklist();
				reviewTable(itemSourceModels);
			}
		});
		button_5.setText("货源黑名单");
		button_5.setBounds(170, 78, 80, 27);

		text_3 = new Text(group, SWT.WRAP);
		text_3.setBounds(765, 10, 200, 130);

		Button button_6 = new Button(group, SWT.NONE);
		button_6.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// 显示店铺描述
				ItemSourceModel itemSourceModel = getSelect();

				if (itemSourceModel != null) {
					SourceOwnerModel sourceOwnerModel = new SourceOwnerModel();

					sourceOwnerModel.setType(itemSourceModel.getType());
					sourceOwnerModel.setSourceOwner(itemSourceModel
							.getSourceOwner());
					sourceOwnerModel = sourceOwnerService
							.getByOwnerAndType(sourceOwnerModel);

					if (sourceOwnerModel != null) {
						text_3.setText(sourceOwnerModel.getRemark());
					} else {
						text_3.setText("无");
					}
				}
			}
		});
		button_6.setBounds(571, 20, 80, 27);
		button_6.setText("\u8BFB\u53D6\u5907\u6CE8");

		Button button_7 = new Button(group, SWT.NONE);
		button_7.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					browser.setUrl("https://oauth.taobao.com/authorize?response_type=token&client_id="
							// taobaoClient.getClass().getDeclaredField("appKey").get(obj)
							+ (FieldUtils.getDeclaredField(
									DefaultTaobaoClient.class, "appKey", true))
									.get(taobaoClient)

					);
				} catch (Exception e1) {
					throw new RuntimeException(e1);
				}
			}
		});
		button_7.setBounds(658, 20, 80, 27);
		button_7.setText("\u6DD8\u5B9D\u6388\u6743");

		Button button_8 = new Button(group, SWT.NONE);
		button_8.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PageContext.get().setPageNo(PageContext.get().getPageNo() - 1);
				ItemSourceModel itemSourceModel = new ItemSourceModel();
				// 第一页数据
				List<ItemSourceModel> itemSourceModels = itemSourceService
						.find(itemSourceModel);
				reviewTable(itemSourceModels);
			}
		});
		button_8.setBounds(10, 593, 80, 27);
		button_8.setText("上一页");

		Button btnNewButton = new Button(group, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PageContext.get().setPageNo(PageContext.get().getPageNo() + 1);
				ItemSourceModel itemSourceModel = new ItemSourceModel();
				// 第一页数据
				List<ItemSourceModel> itemSourceModels = itemSourceService
						.find(itemSourceModel);
				reviewTable(itemSourceModels);
			}
		});
		btnNewButton.setBounds(91, 593, 80, 27);
		btnNewButton.setText("\u4E0B\u4E00\u9875");

		lblNewLabel_1 = new Label(group, SWT.NONE);
		lblNewLabel_1.setBounds(177, 603, 180, 17);
		lblNewLabel_1
				.setText("\u5171\u9875/\u5F53\u524D\u9875/\u5171\u6761\u6570\u636E");

		text_3.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				ItemSourceModel itemSourceModel = getSelect();
				SourceOwnerModel sourceOwnerModel = new SourceOwnerModel();

				sourceOwnerModel.setType(itemSourceModel.getType());
				sourceOwnerModel.setSourceOwner(itemSourceModel
						.getSourceOwner());
				sourceOwnerModel.setRemark(text_3.getText());
				sourceOwnerService.save(sourceOwnerModel);
			}
		});

		/**
		 * 错误消息提示框
		 */
		lblNewLabel = new Label(shell, SWT.BORDER);
		lblNewLabel.setBounds(10, 10, 404, 44);
		errorMessage("\u9519\u8BEF\u6D88\u606F");
		lblNewLabel.setForeground(lblNewLabel.getDisplay().getSystemColor(
				SWT.COLOR_RED));

		combo = new Combo(shell, SWT.NONE);
		combo.setBounds(729, 7, 88, 25);

		initShop(combo);

		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setBounds(641, 10, 61, 17);
		label_1.setText("\u5E97\u94FA");

		Button button = new Button(shell, SWT.NONE);
		button.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		button.setFont(SWTResourceManager.getFont("微软雅黑", 10, SWT.NORMAL));
		button.setBounds(826, 5, 95, 27);
		button.setText("\u767B\u5F55\u963F\u91CC\u5988\u5988");

		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				browser.setUrl("https://login.taobao.com/member/login.jhtml?style=mini&from=alimama&redirectURL=http%3A%2F%2Flogin.taobao.com%2Fmember%2Ftaobaoke%2Flogin.htm%3Fis_login%3d1&full_redirect=true&disableQuickLogin=true");
				tabFolder.setSelection(tabItem);
			}
		});

		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String url = text.getText();
				String sourceId = itemSourceHandler.parseSourceId(url);

				if (StringUtils.isBlank(sourceId)) {
					errorMessage("无效的货源地址");
					return;
				}

				if (StringUtils.isBlank(combo.getText())) {
					errorMessage("店铺无效");
					return;
				}

				final ItemSourceModel newItemSourceModel = new ItemSourceModel();

				// 货源ID和店铺ID解析
				newItemSourceModel.setSourceId(sourceId);
				newItemSourceModel.setShopId(shopModelByName.get(
						combo.getText()).getId());
				newItemSourceModel.setSourceDetailUrl(url);
				newItemSourceModel.setType(SourceType.淘宝联盟);

				ItemSourceModel itemSourceModel = itemSourceService
						.getBySourceIdAndShopId(newItemSourceModel);

				if (itemSourceModel != null) {
					BeanUtils.copyProperties(itemSourceModel,
							newItemSourceModel);
					errorMessage("货源已经添加过");

					return;
				}

				// 点击添加货源时，处理淘宝客的信息
				ProgressAdapter addSourceListener = new ProgressAdapter() {
					public void completed(ProgressEvent event) {
						try {
							if (!itemSourceHandler.parseInfo(
									newItemSourceModel, browser.getText())) {
								errorMessage("无效的货源，没有加入淘宝客？");
								return;
							}
						} catch (Exception e) {
							errorMessage("淘宝解析失败，是否已经登录淘宝客？");
							tabFolder.setSelection(tabItem);
							return;
						} finally {
							browser.removeProgressListener(this);
						}

						// 默认定价
						newItemSourceModel.setPrice(newItemSourceModel
								.countDefaultPrice());
						// 保存
						itemSourceService.add(newItemSourceModel);
						showItemSource(newItemSourceModel);
					}
				};

				browser.addProgressListener(addSourceListener);

				// 读取淘客信息
				try {
					browser.setUrl("http://pub.alimama.com/pubauc/searchAuctionList.json?spm=a219t.7473494.1998155389.3.7ywcjG&q="
							+ URLEncoder.encode(url, "utf-8")
							+ "&toPage=1&perPagesize=40&t=1439885919272&_tb_token_=xDd4yfa9Mho&_input_charset=utf-8");
				} catch (UnsupportedEncodingException e1) {
					throw new RuntimeException(e1);
				}
			}
		});

		ItemSourceModel itemSourceModel = new ItemSourceModel();
		// 第一页数据
		PageContext.set(new Page<ItemSourceModel>());
		List<ItemSourceModel> itemSourceModels = itemSourceService
				.find(itemSourceModel);
		reviewTable(itemSourceModels);
	}

	/**
	 * 
	 * 描述:初始化表格
	 * 
	 * @param group
	 * @author liyixing 2015年8月18日 下午8:01:08
	 */
	private void initTable(Group group) {
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(60);
		tblclmnNewColumn.setText("图片");

		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(375);
		tblclmnNewColumn_1.setText("标题");

		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(60);
		tableColumn.setText("销售价");

		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(60);
		tableColumn_1.setText("进货价");

		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(80);
		tableColumn_2.setText("进货折扣额");

		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(60);
		tableColumn_3.setText("利润");
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnDefaultPrice = new TableColumn(table, SWT.NONE);
		tblclmnDefaultPrice.setWidth(60);
		tblclmnDefaultPrice.setText("默认价");

		TableColumn tblclmnStatus = new TableColumn(table, SWT.NONE);
		tblclmnStatus.setWidth(60);
		tblclmnStatus.setText("状态");

		TableColumn tblclmnSourceTotalSoldQuantity = new TableColumn(table,
				SWT.NONE);
		tblclmnSourceTotalSoldQuantity.setWidth(60);
		tblclmnSourceTotalSoldQuantity.setText("货源销量");

		TableColumn tblclmnMyTotalSoldQuantity = new TableColumn(table,
				SWT.NONE);
		tblclmnMyTotalSoldQuantity.setWidth(60);
		tblclmnMyTotalSoldQuantity.setText("本地销量");

		TableColumn tblclmnLastNoticeDay = new TableColumn(table, SWT.NONE);
		tblclmnLastNoticeDay.setWidth(80);
		tblclmnLastNoticeDay.setText("最后交易日");

		TableColumn tblclmnUrl = new TableColumn(table, SWT.NONE);
		tblclmnUrl.setWidth(10);
		tblclmnUrl.setText("URL");

		// ID列只能放在最后一列
		TableColumn tblclmnId = new TableColumn(table, SWT.NONE);
		tblclmnId.setText("ID");
		tblclmnId.setWidth(10);

		// Display display = group.getDisplay();
		// final Color foreground = display.getSystemColor(SWT.COLOR_RED);
		// final Color background = display.getSystemColor(SWT.COLOR_YELLOW);

		/*
		 * NOTE: EraseItem is called repeatedly. Therefore it is critical for
		 * performance that this method be as efficient as possible.
		 */
		// table.addSelectionListener(new SelectionListener() {
		//
		// @Override
		// public void widgetSelected(SelectionEvent event) {
		// event.detail &= ~SWT.HOT;
		// if ((event.detail & SWT.SELECTED) == 0)
		// return; /* item not selected */
		// int clientWidth = table.getClientArea().width;
		// GC gc = event.gc;
		// Color oldForeground = gc.getForeground();
		// Color oldBackground = gc.getBackground();
		// gc.setForeground(foreground);
		// gc.setBackground(background);
		// gc.fillGradientRectangle(0, event.y, clientWidth, event.height,
		// false);
		// gc.setForeground(oldForeground);
		// gc.setBackground(oldBackground);
		// event.detail &= ~SWT.SELECTED;
		//
		// if (table.getSelectionIndex() >= 0) {
		// text.setText(table.getItem(table.getSelectionIndex())
		// .getText(table.getColumnCount() - 2));
		// text_1.setText(table.getItem(table.getSelectionIndex())
		// .getText(1));
		// text_2.setText(table.getItem(table.getSelectionIndex())
		// .getText(2));
		// }
		// }
		//
		// @Override
		// public void widgetDefaultSelected(SelectionEvent e) {
		// }
		// });
		// SWT.EraseItem，选择某个行
		table.addListener(SWT.EraseItem, new Listener() {
			public void handleEvent(Event event) {
				// event.detail &= ~SWT.HOT;
				// if ((event.detail & SWT.SELECTED) == 0)
				// return; /* item not selected */
				// int clientWidth = table.getClientArea().width;
				// GC gc = event.gc;
				// Color oldForeground = gc.getForeground();
				// Color oldBackground = gc.getBackground();
				// gc.setForeground(foreground);
				// gc.setBackground(background);
				// gc.fillGradientRectangle(0, event.y, clientWidth,
				// event.height,
				// false);
				// gc.setForeground(oldForeground);
				// gc.setBackground(oldBackground);
				// event.detail &= ~SWT.SELECTED;

				if (table.getSelectionIndex() >= 0) {
					text.setText(table.getItem(table.getSelectionIndex())
							.getText(table.getColumnCount() - 2));
					text_1.setText(table.getItem(table.getSelectionIndex())
							.getText(1));
					text_2.setText(table.getItem(table.getSelectionIndex())
							.getText(2));
				}

			}
		});
	}

	/**
	 * 描述:初始化店铺信息
	 * 
	 * @param combo
	 * @author liyixing 2015年8月18日 下午2:50:59
	 */
	private void initShop(Combo combo) {
		List<ShopModel> shopModels = shopService.getAll();

		if (shopModels.size() > 0) {
			for (ShopModel shopModel : shopModels) {
				combo.add(shopModel.getName());
				shopModelByName.put(shopModel.getName(), shopModel);
			}
			combo.select(0);
		}
	}

	public void fullContents(Object... values) {
		// Button btn = new Button(getTable(), SWT.NONE);
		// btn.setText(column.getButtonText());
		// btn.addListener(SWT.Selection, new Listener() {
		// public void handleEvent(Event arg0) {
		// column.getListener().handleEvent(arg0);
		// }
		// });
		// btn.setData(value);
		// TableEditor editor = new TableEditor(getTable());
		// editor.horizontalAlignment = SWT.CENTER;
		// editor.minimumWidth = column.getWidth();
		// editor.setEditor(btn, ti, i);
		// editors.add(btn);
		// editors.add(editor);
	}

	/**
	 * 
	 * 描述:显示一行数据
	 * 
	 * @param itemSourceModel
	 * @author liyixing 2015年8月18日 下午10:33:42
	 */
	private void showItemSource(final ItemSourceModel itemSourceModel) {
		// 数据显示在表格中
		TableItem item = new TableItem(table, SWT.NONE);

		try {
			Image image = new Image(null, UtilImage.resizeImage(
					itemSourceModel.getImg(), 60, 60));
			item.setImage(0, image);
			item.setText(1, itemSourceModel.getTitle());
			item.setText(2, itemSourceModel.getPrice().toString());
			item.setText(3, itemSourceModel.getPurchasePrice().toString());
			item.setText(4, itemSourceModel.getPurchaseDiscountPrice()
					.toString());
			item.setText(5, itemSourceModel.getProfit().toString());
			item.setText(6, itemSourceModel.countDefaultPrice().toString());
			item.setText(7, itemSourceModel.getSaleStatus().toString());

			item.setText(8, itemSourceModel.getSourceTotalSoldQuantity()
					.toString());
			item.setText(9, itemSourceModel.getMyTotalSoldQuantity().toString());
			item.setText(10,
					UtilDateTime.format(itemSourceModel.getLastNoticeDay(),
							UtilDateTime.YYYY_MM_DD));
			item.setText(table.getColumnCount() - 2, itemSourceModel
					.getSourceDetailUrl().toString());
			// ID列只能放在最后一列
			item.setText(table.getColumnCount() - 1, itemSourceModel.getId());

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * 描述:重新显示表格数据
	 * 
	 * @author liyixing 2015年8月18日 下午10:33:50
	 */
	private void reviewTable(List<ItemSourceModel> itemSourceModels) {
		table.removeAll();
		for (ItemSourceModel itemSourceModel : itemSourceModels) {
			showItemSource(itemSourceModel);
		}
		lblNewLabel_1.setText("共" + PageContext.get().getTotalPage() + "页/当前第"
				+ PageContext.get().getPageNo() + "页/共"
				+ +PageContext.get().getTotalCount() + "条数据");
	}

	/**
	 * 
	 * 描述:表格右击菜单
	 * 
	 * @author liyixing 2015年8月18日 下午10:56:46
	 */
	private void addTableMenu() {
		Menu menu = new Menu(table);
		table.setMenu(menu);
		MenuItem item = new MenuItem(menu, SWT.PUSH);
		item.setText("删除");
		item.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (table.getSelection().length > 0) {
					// 删除数据库
					TableItem item1 = (TableItem) table.getSelection()[0];
					// ID列只能放在最后一列
					String id = item1.getText(table.getColumnCount() - 1);
					itemSourceService.delete(id);
					table.remove(table.getSelectionIndices());
				}
			}
		});

		// 添加黑名单
		MenuItem addBlacklist = new MenuItem(menu, SWT.PUSH);
		addBlacklist.setText("添加黑名单");
		addBlacklist.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (table.getSelectionIndex() >= 0) {
					ItemSourceModel itemSourceModel = itemSourceService
							.get(table.getItem(table.getSelectionIndex())
									.getText(table.getColumnCount() - 1));
					itemSourceService.addSourceBlacklist(itemSourceModel);
				}
			}
		});

		// 关联淘宝客货源与店铺商品关系按钮
		MenuItem itemRef = new MenuItem(menu, SWT.PUSH);
		itemRef.setText("关联店铺商品信息");
		itemRef.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				page.setPageNo(1);
				page.setPageSize(20);
				PageContext.set(page);
				itemSourceModelsOfShopAll.clear();

				// 关联信息，处理淘宝的出售中的数据的信息
				ProgressAdapter taobaoAuctionListener = new ProgressAdapter() {
					public void changed(ProgressEvent event) {
						UtilLog.debug("changed");
						// String text = browser.getText();
						// UtilLog.debug("浏览器获取的数据是：{}", text);
					}

					public void completed(ProgressEvent event) {
						UtilLog.debug("completed");
						try {
							// 从淘宝店铺更新过来的数据
							List<ItemSourceModel> itemSourceModelsOfShop = itemSourceService
									.getByTaobaoAuction(browser.getText());

							// /淘宝店铺更新过来的数据，存入总的list中
							itemSourceModelsOfShopAll
									.addAll(itemSourceModelsOfShop);

							if (page.next()) {
								browser.setUrl("https://sell.taobao.com/auction/merchandise/auction_list.htm?page="
										+ page.getPageNo());
							} else {
								PageContext.clear();
								browser.removeProgressListener(this);
								// 关联店铺销售信息
								relationShop(SaleStatus.创建);
							}
						} catch (Exception e) {
							errorMessage("淘宝出售中数据解析失败，是否已经登录淘宝客？\r\n"
									+ e.getMessage());
							tabFolder.setSelection(tabItem);
							PageContext.clear();
							browser.removeProgressListener(this);
							return;
						}
					}
				};

				browser.addProgressListener(taobaoAuctionListener);
				browser.setUrl("https://sell.taobao.com/auction/merchandise/auction_list.htm?page="
						+ page.getPageNo());
			}
		});

		// 关联淘宝客货源与店铺商品关系按钮，并更新店铺的淘宝商品信息
		MenuItem itemRefAndUpdate = new MenuItem(menu, SWT.PUSH);
		itemRefAndUpdate.setText("更新店铺商品信息");
		itemRefAndUpdate.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				page.setPageNo(1);
				page.setPageSize(20);
				PageContext.set(page);
				itemSourceModelsOfShopAll.clear();

				// 关联信息，处理淘宝的出售中的数据的信息
				ProgressAdapter taobaoAuctionListener = new ProgressAdapter() {
					public void completed(ProgressEvent event) {
						try {
							// 从淘宝店铺更新过来的数据
							List<ItemSourceModel> itemSourceModelsOfShop = itemSourceService
									.getByTaobaoAuction(browser.getText());

							// /淘宝店铺更新过来的数据，存入总的list中
							itemSourceModelsOfShopAll
									.addAll(itemSourceModelsOfShop);

							if (page.next()) {
								browser.setUrl("https://sell.taobao.com/auction/merchandise/auction_list.htm?page="
										+ page.getPageNo());
							} else {
								PageContext.clear();
								browser.removeProgressListener(this);
								// 关联店铺销售信息
								relationShop();
							}
						} catch (Exception e) {
							errorMessage("淘宝出售中数据解析失败，是否已经登录淘宝客？\r\n"
									+ e.getMessage());
							tabFolder.setSelection(tabItem);
							PageContext.clear();
							browser.removeProgressListener(this);
							return;
						}
					}
				};

				browser.addProgressListener(taobaoAuctionListener);
				browser.setUrl("https://sell.taobao.com/auction/merchandise/auction_list.htm?page="
						+ page.getPageNo());
			}
		});

		// 更新淘宝客数据
		MenuItem itemAiTaobao = new MenuItem(menu, SWT.PUSH);
		itemAiTaobao.setText("更新淘宝客价格数据");
		itemAiTaobao.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				try {

					List<ItemSourceModel> itemSourceModels;

					if (table.getSelectionIndex() >= 0) {
						ItemSourceModel itemSourceModel = itemSourceService
								.get(table.getItem(table.getSelectionIndex())
										.getText(table.getColumnCount() - 1));
						itemSourceModels = new ArrayList<ItemSourceModel>();
						itemSourceModels.add(itemSourceModel);
					} else {

						ItemSourceModel itemSourceModel = new ItemSourceModel();
						itemSourceModel.setShopId(getShop().getId());
						itemSourceModel.setType(SourceType.淘宝联盟);

						itemSourceModels = itemSourceService
								.getOn(itemSourceModel);
					}

					if (itemSourceModels.size() < 1) {
						errorMessage("没有需要更新的商品！");
						return;
					}
					final Iterator<ItemSourceModel> iterator = itemSourceModels
							.iterator();

					// 循环更新每个商品
					// 点击添加货源时，处理淘宝客的信息
					ProgressAdapter addSourceListener = new ProgressAdapter() {
						public void completed(ProgressEvent event) {
							try {
								if (!itemSourceHandler.parseInfo(
										currentItemSourceModel,
										browser.getText())) {
									UtilLog.debug("由于商品{}在淘宝客中不存在，因此自动下架",
											currentItemSourceModel.getItemId());
									// 淘宝客无效，下架
									itemApi.delisting(Long
											.valueOf(currentItemSourceModel
													.getItemId()), getShop()
											.getSessionKey());
									currentItemSourceModel
											.setSaleStatus(SaleStatus.下架);
									itemSourceService
											.saveSource(currentItemSourceModel);
								} else {
									// 不需要下架，自动修改标题
									itemSourceService
											.saveSource(currentItemSourceModel);
									if (StringUtils
											.isNotBlank(currentItemSourceModel
													.getItemId())) {
										UtilLog.debug(
												"宝贝：{}，已经关联店铺信息，需要自动同步标题",
												currentItemSourceModel
														.getTitle());
										// 更新标题
										itemApi.updateTitle(Long
												.valueOf(currentItemSourceModel
														.getItemId()),
												currentItemSourceModel
														.getTitle(), getShop()
														.getSessionKey());
									}
								}
							} catch (Exception e) {
								errorMessage("淘宝解析失败，是否已经登录淘宝客？\r\n"
										+ e.getMessage());
								tabFolder.setSelection(tabItem);
								browser.removeProgressListener(this);
								return;
							} finally {
							}

							if (iterator.hasNext()) {
								currentItemSourceModel = iterator.next();
								try {
									browser.setUrl("http://pub.alimama.com/pubauc/searchAuctionList.json?spm=a219t.7473494.1998155389.3.7ywcjG&q="
											+ URLEncoder.encode(
													currentItemSourceModel
															.getSourceDetailUrl(),
													"utf-8")
											+ "&toPage=1&perPagesize=40&t=1439885919272&_tb_token_=xDd4yfa9Mho&_input_charset=utf-8");
								} catch (UnsupportedEncodingException e) {
									throw new RuntimeException(e);
								}
							} else {
								browser.removeProgressListener(this);
								errorMessage("更新淘宝客价格数据完成！");
							}
						}
					};

					browser.addProgressListener(addSourceListener);
					currentItemSourceModel = iterator.next();
					// 读取淘客信息
					browser.setUrl("http://pub.alimama.com/pubauc/searchAuctionList.json?spm=a219t.7473494.1998155389.3.7ywcjG&q="
							+ URLEncoder.encode(
									currentItemSourceModel.getSourceDetailUrl(),
									"utf-8")
							+ "&toPage=1&perPagesize=40&t=1439885919272&_tb_token_=xDd4yfa9Mho&_input_charset=utf-8");
				} catch (UnsupportedEncodingException e1) {
					throw new RuntimeException(e1);
				}
			}
		});

		// 更新淘宝客数据
		MenuItem itemAiTaobaoSku = new MenuItem(menu, SWT.PUSH);
		itemAiTaobaoSku.setText("更新淘宝客SKU");
		itemAiTaobaoSku.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				List<ItemSourceModel> itemSourceModels;

				if (table.getSelectionIndex() >= 0) {
					ItemSourceModel itemSourceModel = itemSourceService
							.get(table.getItem(table.getSelectionIndex())
									.getText(table.getColumnCount() - 1));
					itemSourceModels = new ArrayList<ItemSourceModel>();
					itemSourceModels.add(itemSourceModel);

					errorMessage(itemSourceHandler.syncItemSourceSku(
							itemSourceModels, getShop().getSessionKey()));
				} else {
					ItemSourceModel itemSourceModel = new ItemSourceModel();
					itemSourceModel.setShopId(getShop().getId());
					itemSourceModel.setType(SourceType.淘宝联盟);

					itemSourceModels = itemSourceService.getOn(itemSourceModel);

					errorMessage(itemSourceHandler.syncItemSourceSku(
							itemSourceModels, getShop().getSessionKey()));
				}
			}
		});

		// 更新淘宝销售数量
		MenuItem itemAiTaobaoSoldQun = new MenuItem(menu, SWT.PUSH);
		itemAiTaobaoSoldQun.setText("更新淘宝销售数量");
		itemAiTaobaoSoldQun.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				List<ItemSourceModel> itemSourceModels;

				if (table.getSelectionIndex() >= 0) {
					ItemSourceModel itemSourceModel = itemSourceService
							.get(table.getItem(table.getSelectionIndex())
									.getText(table.getColumnCount() - 1));
					itemSourceModels = new ArrayList<ItemSourceModel>();
					itemSourceModels.add(itemSourceModel);

					itemSourceHandler.syncTotalSoldQuantity(itemSourceModels);
					errorMessage("同步销量成功！");
				} else {
					ItemSourceModel itemSourceModel = new ItemSourceModel();
					itemSourceModel.setShopId(getShop().getId());
					itemSourceModel.setType(SourceType.淘宝联盟);

					itemSourceModels = itemSourceService.getOn(itemSourceModel);
					itemSourceHandler.syncTotalSoldQuantity(itemSourceModels);
					errorMessage("同步销量成功！");
				}
			}
		});

		// 更新淘宝最后交易日
		MenuItem itemAiTaobaoLastNoticeDay = new MenuItem(menu, SWT.PUSH);
		itemAiTaobaoLastNoticeDay.setText("更新淘宝最后交易日");
		itemAiTaobaoLastNoticeDay.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				List<ItemSourceModel> itemSourceModels;

				if (table.getSelectionIndex() >= 0) {
					ItemSourceModel itemSourceModel = itemSourceService
							.get(table.getItem(table.getSelectionIndex())
									.getText(table.getColumnCount() - 1));
					itemSourceModels = new ArrayList<ItemSourceModel>();
					itemSourceModels.add(itemSourceModel);

					itemSourceHandler.syncLastNoticeDay(itemSourceModels,
							browser, new ProcessHandler() {

								@Override
								public void doSuccess() {
									errorMessage("同步淘宝最后交易日成功！");
								}

								@Override
								public void doError(Exception exception) {
									errorMessage(exception.getMessage());
								}
							});

				} else {
					ItemSourceModel itemSourceModel = new ItemSourceModel();
					itemSourceModel.setShopId(getShop().getId());
					itemSourceModel.setType(SourceType.淘宝联盟);

					itemSourceModels = itemSourceService.getOn(itemSourceModel);

					itemSourceHandler.syncLastNoticeDay(itemSourceModels,
							browser, new ProcessHandler() {

								@Override
								public void doSuccess() {
									errorMessage("同步淘宝最后交易日成功！");
								}

								@Override
								public void doError(Exception exception) {
									errorMessage(exception.getMessage());
								}
							});
				}
			}
		});

		// 自动下架
		MenuItem autoDelisting = new MenuItem(menu, SWT.PUSH);
		autoDelisting.setText("自动下架");
		autoDelisting.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				ItemSourceModel itemSourceModel1 = new ItemSourceModel();
				itemSourceModel1.setShopId(getShop().getId());
				itemSourceModel1.setType(SourceType.淘宝联盟);
				List<ItemSourceModel> itemSourceModels = itemSourceService
						.getOn(itemSourceModel1);

				for (ItemSourceModel itemSourceModel : itemSourceModels) {
					if (itemSourceModel.needOff()) {
						// 淘宝客无效，下架
						try {
							itemApi.delisting(
									Long.valueOf(itemSourceModel.getItemId()),
									getShop().getSessionKey());
						} catch (Exception e) {
							UtilLog.warn("商品{}，{}自动下架失败，请手动下架", e,
									itemSourceModel.getItemId(),
									itemSourceModel.getTitle());
							errorMessage("商品" + itemSourceModel.getTitle()
									+ "自动下架失败，请手动下架");
						}
						itemSourceModel.setSaleStatus(SaleStatus.下架);
						itemSourceService.saveSource(itemSourceModel);
					}
				}

				errorMessage("自动下架检查完成!");
			}
		});
	}

	/**
	 * 
	 * 描述:错误消息
	 * 
	 * @param text
	 * @author liyixing 2015年8月18日 下午11:21:11
	 */
	private void errorMessage(String text) {
		lblNewLabel.setText("【"
				+ UtilDateTime.format(new Date(),
						UtilDateTime.YYYY_MM_DD_HH_MM_SS) + "】：【" + text + "】");
	}

	/**
	 * 描述:关联店铺销售
	 * 
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @author liyixing 2015年8月26日 下午1:55:48
	 */
	private void relationShop() throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		relationShop(null);
	}

	/**
	 * 描述:关联店铺销售
	 * 
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @author liyixing 2015年8月26日 下午1:55:48
	 */
	private void relationShop(SaleStatus saleStatus)
			throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		List<ItemSourceModel> notOffItemSourceModels;

		if (table.getSelectionIndex() >= 0) {
			ItemSourceModel itemSourceModel = itemSourceService.get(table
					.getItem(table.getSelectionIndex()).getText(
							table.getColumnCount() - 1));
			notOffItemSourceModels = new ArrayList<ItemSourceModel>();
			notOffItemSourceModels.add(itemSourceModel);
		} else {
			// 自动关联ID
			// 查询所有ID没有被关联的
			ItemSourceModel itemSourceModel = new ItemSourceModel();
			itemSourceModel.setShopId(shopModelByName.get(combo.getText())
					.getId());
			itemSourceModel.setType(SourceType.淘宝联盟);
			itemSourceModel.setSaleStatus(saleStatus);
			// 标题
			notOffItemSourceModels = itemSourceService
					.getNotOff(itemSourceModel);

			List<String> titles = UtilBean.beansFieldTolist(
					notOffItemSourceModels, "title");

			// 检查不存在的
			for (ItemSourceModel itemSourceModelOfShop : itemSourceModelsOfShopAll) {
				if (!titles.contains(itemSourceModelOfShop.getTitle())) {
					UtilLog.debug("商品名称：{}，在货源中不存在",
							itemSourceModelOfShop.getTitle());
				}
			}
		}
		Map<String, ItemSourceModel> itemSourceModelsOfShopAllByItemId = UtilBean
				.beansGroupByPkField(itemSourceModelsOfShopAll, "itemId");
		Map<String, ItemSourceModel> itemSourceModelsOfShopAllByItemTitle = UtilBean
				.beansGroupByPkField(itemSourceModelsOfShopAll, "title");

		for (ItemSourceModel itemSourceModelNotOff : notOffItemSourceModels) {
			if (itemSourceModelNotOff.getSaleStatus().equals(SaleStatus.上架)) {
				// 位于店铺中的属性
				ItemSourceModel itemSourceModelOfShop = itemSourceModelsOfShopAllByItemId
						.get(itemSourceModelNotOff.getItemId());
				// 下架那些已经上架，但是淘宝店铺已经不存在的
				if (itemSourceModelOfShop == null) {
					// 下架
					itemSourceModelNotOff.setSaleStatus(SaleStatus.下架);
					itemSourceService.saveSource(itemSourceModelNotOff);
				} else {
					// 更新价格
					itemSourceModelNotOff.setPrice(itemSourceModelOfShop
							.getPrice());
					itemSourceService.saveSource(itemSourceModelNotOff);
				}
			} else {
				// 创建的情况
				// 从标题中取一个
				// 位于店铺中的属性
				ItemSourceModel itemSourceModelOfShop = itemSourceModelsOfShopAllByItemTitle
						.get(itemSourceModelNotOff.getTitle());

				if (itemSourceModelOfShop == null) {
					// 还未上架
					continue;
				}

				itemSourceModelNotOff
						.setPrice(itemSourceModelOfShop.getPrice());
				itemSourceModelNotOff.setItemId(itemSourceModelOfShop
						.getItemId());
				itemSourceModelNotOff.setSaleStatus(SaleStatus.上架);
				itemSourceService.saveSource(itemSourceModelNotOff);
			}
		}

		errorMessage("更新店铺商品信息完成");
	}

	/**
	 * 描述:获取选中的店铺
	 * 
	 * @return
	 * @author liyixing 2015年8月26日 下午2:52:30
	 */

	private ShopModel getShop() {
		return shopModelByName.get(combo.getText());
	}

	/**
	 * 描述:获取选中的货源
	 * 
	 * @return
	 * @author liyixing 2015年9月7日 上午10:23:31
	 */
	private ItemSourceModel getSelect() {
		if (table.getSelectionIndex() >= 0) {
			return itemSourceService.get(table.getItem(
					table.getSelectionIndex()).getText(
					table.getColumnCount() - 1));
		} else {
			return null;
		}
	}

	public class MyBrowser extends Browser {

		protected void checkSubclass() {

		}

		public MyBrowser(Composite parent, int style) {
			super(parent, style);
			final MyBrowser my = this;

			this.addProgressListener(new ProgressListener() {
				@Override
				public void completed(ProgressEvent event) {
				}

				@Override
				public void changed(ProgressEvent event) {
					String text = my.getSuperText();
					if (StringUtils.isNotBlank(text)) {
						my.setTextNotEmpty(text);
					}
				}
			});
		}

		private String text;

		public String getSuperText() {
			return super.getText();
		}

		public String getText() {
			if (text == null) {
				return super.getText();
			}

			return text;
		}

		public void setTextNotEmpty(String text) {
			this.text = text;
		}
	}
}
