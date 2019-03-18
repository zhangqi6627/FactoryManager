package com.qust.factory.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.qust.factory.bean.Material;
import com.qust.factory.bean.User;
import com.qust.factory.utils.DBUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class StoreController implements Initializable {

	@FXML
	private TableView<Material> tableView;

	@FXML
	private TableColumn<Material, String> tc_id, tc_material, tc_number, tc_user;

	@FXML
	private Button btn_refresh;

	@FXML
	private Label tv_plastic, tv_box, tv_bag, tv_model, tv_print, tv_pack;

	private ObservableList<Material> materialList = FXCollections.observableArrayList();

	private ObservableList<Material> getMaterialList() {
		ObservableList<Material> materialList = FXCollections.observableArrayList();
		ResultSet resultSet = DBUtil.getDBUtil().executeQuery("select * from records;");
		try {
			while (resultSet.next()) {
				int _id = resultSet.getInt("_id");
				String materialStr = resultSet.getString("material");
				int number = resultSet.getInt("number");
				String user = resultSet.getString("username");
				materialList.add(new Material(_id, materialStr, number, user));
				System.out.println("_id:" + _id + " materialStr:" + materialStr + " number:" + number + " user:" + user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return materialList;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		refresh();

		tc_id.setCellValueFactory(new PropertyValueFactory<Material, String>("_id"));
		tc_material.setCellValueFactory(new PropertyValueFactory<Material, String>("material"));
		tc_number.setCellValueFactory(new PropertyValueFactory<Material, String>("number"));
		tc_user.setCellValueFactory(new PropertyValueFactory<Material, String>("user"));

		// 开启一个线程不停刷新界面
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(5000);
						refresh();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		// 刷新按钮
		btn_refresh.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				refresh();
			}
		});
	}

	private int getProductNumber(Label textView, String product) {
		int number = 0;
		try {
			ResultSet resultSet = DBUtil.getDBUtil().executeQuery("select number from products where product='" + product + "';");
			number = resultSet.getInt("number");
			System.out.println(product + "剩余:" + number);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		textView.setText(product + "剩余:" + number);
		return number;
	}

	/** 刷新页面 */
	private void refresh() {
		getProductNumber(tv_plastic, "塑料颗粒");
		getProductNumber(tv_box, "盒子");
		getProductNumber(tv_bag, "袋子");
		getProductNumber(tv_model, "注塑半成品");
		getProductNumber(tv_print, "印刷半成品");
		getProductNumber(tv_pack, "包装成品");

		materialList = getMaterialList();
		tableView.setItems(materialList);
	}

	private User mUser;

	public void setUser(User user) {
		this.mUser = user;
	}
}
