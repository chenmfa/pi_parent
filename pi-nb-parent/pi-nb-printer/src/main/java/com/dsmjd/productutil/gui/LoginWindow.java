package com.dsmjd.productutil.gui;

import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import com.dsmjd.productutil.dto.PuSysUser;
import com.dsmjd.productutil.utils.ConfigUtil;

public class LoginWindow extends ApplicationWindow {

	private Text text;
	private Text passwordText;
	Label resultLabel;

	/**
	 * Create the application window.
	 */
	public LoginWindow() {
		super(null);
		createActions();
		addToolBar(SWT.FLAT | SWT.WRAP);
		addMenuBar();
		addStatusLine();
	}

	/**
	 * Create contents of the application window.
	 * 
	 * @param parent
	 */
	@Override
	protected Control createContents(final Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		text = new Text(container, SWT.BORDER);
		text.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		text.setBounds(136, 49, 119, 35);
		text.setTextLimit(2);
		text.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent key) {
				if (key.keyCode == 13) {
					doLogin(parent);
				}
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
			}

		});

		Label lblNewLabel = new Label(container, SWT.NONE);
		lblNewLabel.setAlignment(SWT.RIGHT);
		lblNewLabel.setBounds(69, 52, 61, 35);
		lblNewLabel.setText("用户名：");

		passwordText = new Text(container, SWT.BORDER);
		passwordText.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		passwordText.setBounds(136, 99, 119, 35);
		passwordText.setEnabled(false);

		Label lblNewLabel_1 = new Label(container, SWT.NONE);
		lblNewLabel_1.setAlignment(SWT.RIGHT);
		lblNewLabel_1.setBounds(69, 102, 61, 35);
		lblNewLabel_1.setText("密码：");

		resultLabel = new Label(container, SWT.NONE);
		resultLabel.setAlignment(SWT.CENTER);
		resultLabel.setBounds(136, 140, 136, 21);

		Button button = new Button(container, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			// @SuppressWarnings("unchecked")
			@Override
			public void widgetSelected(SelectionEvent e) {
				doLogin(parent);
			}
		});
		button.setBounds(136, 167, 80, 35);
		button.setText("登录");

		return container;
	}

	public void doLogin(Composite parent) {
		try {
			if (!text.getText().trim().isEmpty()) {
				resultLabel.setText("登录成功，跳转中...");
				try {
					parent.setVisible(false);
					PuSysUser user = new PuSysUser();
					String operator = text.getText().trim();
					if(null == operator || "".equals(operator)){
						operator ="1";
					}
					user.setName(operator);
					MainWindow window = new MainWindow(user);
					window.setBlockOnOpen(true);
					window.open();
					Display.getCurrent().dispose();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			} else {
				System.out.println("用户名或密码错误。");
				resultLabel.setText("用户名或密码错误。");
			}
		} catch (Exception ee) {
			ee.printStackTrace();

		}
		// try {
		// PuSysUserDAO puSysUserDAO = BeanUtil.getBean(PuSysUserDAO.class);
		// PuSysUserExample example = new PuSysUserExample();
		// example.createCriteria().andUsernameEqualTo(text.getText().trim()).andPasswordEqualTo(text_1.getText());
		// List<PuSysUser> userList = puSysUserDAO.selectByExample(example);
		// if (!userList.isEmpty()) {
		// System.out.println("登录的：" + userList.get(0).getName());
		// resultLabel.setText("登录成功，跳转中...");
		// try {
		// parent.setVisible(false);
		// MainWindow window = new MainWindow(userList.get(0));
		// window.setBlockOnOpen(true);
		// window.open();
		// Display.getCurrent().dispose();
		// } catch (Exception e2) {
		// e2.printStackTrace();
		// }
		// } else {
		// System.out.println("用户名或密码错误。");
		// resultLabel.setText("用户名或密码错误");
		// }
		// } catch (Exception ee) {
		// ee.printStackTrace();
		// if (!text.getText().trim().isEmpty()) {
		// try {
		// parent.setVisible(false);
		// PuSysUser user = new PuSysUser();
		// user.setName(text.getText().trim());
		// MainWindow window = new MainWindow(user);
		// window.setBlockOnOpen(true);
		// window.open();
		// Display.getCurrent().dispose();
		// } catch (Exception e2) {
		// e2.printStackTrace();
		// }
		// }
		// }
	}

	/**
	 * Create the actions.
	 */
	private void createActions() {
		// Create the actions
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
    if(null != args && args.length >0){
      ConfigUtil.setConfigFile(args[0]);
    }
		LoginWindow window = new LoginWindow();
		window.setBlockOnOpen(true);
		window.open();
		Display.getCurrent().dispose();
	}

	/**
	 * Configure the shell.
	 * 
	 * @param newShell
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setImage(SWTResourceManager.getImage(MainWindow.class, "/" + ConfigUtil.utilLogoImage));
		newShell.setText("生产串口二维码工具 " + ConfigUtil.versionStr);
	}

	/**
	 * Return the initial size of the window.
	 */
	@Override
	protected Point getInitialSize() {
		return new Point(379, 298);
	}
}
