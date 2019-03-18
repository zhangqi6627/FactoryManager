package com.qust.factory.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.qust.factory.bean.Order;
import com.qust.factory.utils.DBUtil;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.Pair;

public class OrderListController implements Initializable {
	@FXML
	private AnchorPane displayAnchorPane;

	@FXML
	private TableView<Order> tableView;

	@FXML
	private Button btn_add;

	@FXML
	private TableColumn<Order, String> tc_id, tc_orderId, tc_productName, tc_productType, tc_productColor, tc_productNumber, tc_send, tc_delete, tc_progress;

	private ObservableList<Order> orderList = FXCollections.observableArrayList();

	private ObservableList<Order> getOrderList() {
		ObservableList<Order> orderList = FXCollections.observableArrayList();
		ResultSet resultSet = DBUtil.getDBUtil().executeQuery("select * from orders;");
		try {
			while (resultSet.next()) {
				int _id = resultSet.getInt("_id");
				int orderId = resultSet.getInt("orderId");
				String productName = resultSet.getString("productName");
				String productType = resultSet.getString("productType");
				String productColor = resultSet.getString("productColor");
				int productNumber = resultSet.getInt("productNumber");
				int progress = resultSet.getInt("progress");
				Order mOrder = new Order(_id, orderId, productName, productType, productColor, productNumber);
				mOrder.setProgress(progress);
				orderList.add(mOrder);
				System.out.println("_id:" + _id + " orderId:" + orderId + " productName:" + productName + " productType:" + productType + " productColor:" + productColor + " number:" + productNumber);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderList;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		orderList = getOrderList();
		tableView.setItems(orderList);

		// 绑定单元格数据
		tc_orderId.setCellValueFactory(new PropertyValueFactory<Order, String>("orderId"));
		tc_productName.setCellValueFactory(new PropertyValueFactory<Order, String>("productName"));
		tc_productType.setCellValueFactory(new PropertyValueFactory<Order, String>("productType"));
		tc_productColor.setCellValueFactory(new PropertyValueFactory<Order, String>("productColor"));
		tc_productNumber.setCellValueFactory(new PropertyValueFactory<Order, String>("productNumber"));
		tc_progress.setCellValueFactory(new PropertyValueFactory<Order, String>("progress"));

		tc_send.setCellFactory(new Callback<TableColumn<Order, String>, TableCell<Order, String>>() {
			@Override
			public TableCell<Order, String> call(final TableColumn<Order, String> param) {
				final TableCell<Order, String> cell = new TableCell<Order, String>() {
					final Button btn_send = new Button("派单");

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn_send.setOnAction(event -> {
								Order order = getTableView().getItems().get(getIndex());
								// 更新进度为已派单，等待注塑车间接单
								boolean isSuc = DBUtil.getDBUtil().execute("update orders set progress=1 where _id=" + order.getId() + ";");
								if (isSuc) {
									System.out.println("已派单，等待注塑接单");
									orderList = getOrderList();
									tableView.setItems(orderList);
								} else {
									System.out.println("更新数据库失败");
								}
							});
							setGraphic(btn_send);
							setText(null);
						}
					}
				};
				return cell;
			}
		});

		tc_delete.setCellFactory(new Callback<TableColumn<Order, String>, TableCell<Order, String>>() {
			@Override
			public TableCell<Order, String> call(final TableColumn<Order, String> param) {
				final TableCell<Order, String> cell = new TableCell<Order, String>() {
					final Button btn_delete = new Button("删除");

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn_delete.setOnAction(event -> {
								Order order = getTableView().getItems().get(getIndex());
								System.out.println("orderId:" + order.getId());
								// TODO: 弹出确认对话框???
								boolean delSuc = DBUtil.getDBUtil().execute("delete from orders where _id=" + order.getId() + ";");
								if (delSuc) {
									orderList = getOrderList();
									tableView.setItems(orderList);
								} else {
									System.out.println("删除订单失败");
								}
							});
							setGraphic(btn_delete);
							setText(null);
						}
					}
				};
				return cell;
			}
		});
		// add order button
		btn_add.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				showAddDialog();
			}
		});

		// 开启一个线程不停刷新界面
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(5000);
						orderList = getOrderList();
						tableView.setItems(orderList);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void showAddDialog() {
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("添加订单");
		// dialog.setHeaderText("Look, a Custom Login Dialog");

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(30, 30, 30, 30));

		//
		TextField tf_orderId = new TextField();
		grid.add(new Label("订单编号:"), 0, 0);
		grid.add(tf_orderId, 1, 0);
		tf_orderId.setText("2");
		//
		TextField tf_productName = new TextField();
		grid.add(new Label("产品名称:"), 0, 1);
		grid.add(tf_productName, 1, 1);
		tf_productName.setText("圆尺");
		//
		TextField tf_productType = new TextField();
		grid.add(new Label("产品型号:"), 0, 2);
		grid.add(tf_productType, 1, 2);
		tf_productType.setText("30cm");
		//
		TextField tf_productColor = new TextField();
		grid.add(new Label("产品颜色:"), 0, 3);
		grid.add(tf_productColor, 1, 3);
		tf_productColor.setText("红色");
		//
		TextField tf_productNumber = new TextField();
		grid.add(new Label("产品数量:"), 0, 4);
		grid.add(tf_productNumber, 1, 4);
		tf_productNumber.setText("200");

		// Set the button types.
		ButtonType doneButtonType = new ButtonType("确定", ButtonData.OK_DONE);
		ButtonType cancelButtonType = new ButtonType("取消", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(doneButtonType, cancelButtonType);
		// Enable/Disable login button depending on whether a username was entered.
		// Node doneButton = dialog.getDialogPane().lookupButton(doneButtonType);
		// doneButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		tf_orderId.textProperty().addListener((observable, oldValue, newValue) -> {
			// doneButton.setDisable(newValue.trim().isEmpty());
			/**
			 * 
			 * 
			 * 添加表单数据校验？？？
			 * 
			 * 
			 */
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> tf_orderId.requestFocus());

		// Convert the result to a username-password-pair when the login button is
		// clicked.
		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == doneButtonType) {
				System.out.println("loginButtonPresse");
				return new Pair<>(tf_orderId.getText(), tf_productName.getText());
			}
			return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent(usernamePassword -> {
			int orderId = Integer.parseInt(tf_orderId.getText());
			String productName = tf_productName.getText();
			String productType = tf_productType.getText();
			String productColor = tf_productColor.getText();
			int productNumber = Integer.parseInt(tf_productNumber.getText());

			boolean addOderSuc = DBUtil.getDBUtil().execute(
			        "insert into orders(orderId, productName, productType, productColor, productNumber) values(" + orderId + ",'" + productName + "','" + productType + "','" + productColor + "'," + productNumber + ");");
			System.out.println("insertSuccess:" + addOderSuc);
			if (addOderSuc) {
				orderList = getOrderList();
				tableView.setItems(orderList);
				System.out.println("添加订单成功");
			} else {
				System.out.println("添加订单失败");
			}
		});
	}

	public void onTableClicked() {
		Order mPerson = tableView.getSelectionModel().getSelectedItem();
		if (mPerson != null) {
			System.out.println("getProductName:" + mPerson.getProductName());
		}
	}
}
