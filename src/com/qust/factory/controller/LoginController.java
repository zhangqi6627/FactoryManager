package com.qust.factory.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.qust.factory.application.ModelApplication;
import com.qust.factory.application.OrderListApplication;
import com.qust.factory.application.PackApplication;
import com.qust.factory.application.PrintApplication;
import com.qust.factory.application.StoreApplication;
import com.qust.factory.bean.User;
import com.qust.factory.utils.DBUtil;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	@FXML
	private Button btn_login;

	@FXML
	private Button btn_cancel;

	@FXML
	private TextField tf_username;

	@FXML
	private PasswordField pf_password;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	public void onLogin(ActionEvent event) {
		System.out.println("login button clicked!");
		String username = tf_username.getText();
		String password = pf_password.getText();
		System.out.println("username:" + username + " password:" + password);

		ResultSet resultSet = DBUtil.getDBUtil().executeQuery("select * from user where username=? and password=?", new String[] { username, password });
		try {
			int rowCount = 0;
			String role = null;
			while (resultSet.next()) {
				role = resultSet.getString("role");
				rowCount++;
			}
			System.out.println("rowCount:" + rowCount + " role:" + role);
			if (rowCount == 1) {
				final User user = new User(username, password, role);
				/** 登录成功之后跳转到订单列表界面 */
				if ("admin".equalsIgnoreCase(role) || "manager".equalsIgnoreCase(role)) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							try {
								new OrderListApplication(user).start(new Stage());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} else if("modeler".equalsIgnoreCase(role)) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							try {
								System.out.println("1");
								new ModelApplication(user).start(new Stage());
								System.out.println("2");
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} else if("printer".equalsIgnoreCase(role)) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							try {
								new PrintApplication(user).start(new Stage());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} else if("packer".equalsIgnoreCase(role)) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							try {
								new PackApplication(user).start(new Stage());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				} else if("material".equalsIgnoreCase(role)) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							try {
								new StoreApplication(user).start(new Stage());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
				
				// 关闭登录界面
				Stage stage = (Stage) btn_login.getScene().getWindow();
				if (stage != null) {
					stage.close();
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	public void onCancel(ActionEvent event) {
		System.out.println("cancel button clicked!");
		Stage stage = (Stage) btn_cancel.getScene().getWindow();
		stage.close();
	}
}