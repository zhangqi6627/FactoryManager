package com.qust.factory.controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.qust.factory.bean.Order;
import com.qust.factory.bean.User;
import com.qust.factory.utils.DBUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class PackController implements Initializable {
	@FXML
	private TableView<Order> tableView;

	@FXML
	private TableColumn<Order, String> tc_id, tc_orderId, tc_productName, tc_productType, tc_productColor, tc_productNumber, tc_progress, tc_receive, tc_start, tc_finish;

	@FXML
	private Button btn_refresh;

	private ObservableList<Order> orderList = FXCollections.observableArrayList();

	private ObservableList<Order> getOrderList() {
		ObservableList<Order> orderList = FXCollections.observableArrayList();
		ResultSet resultSet = DBUtil.getDBUtil().executeQuery("select * from orders where progress=5 or progress=6;");
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

		tc_orderId.setCellValueFactory(new PropertyValueFactory<Order, String>("orderId"));
		tc_productName.setCellValueFactory(new PropertyValueFactory<Order, String>("productName"));
		tc_productType.setCellValueFactory(new PropertyValueFactory<Order, String>("productType"));
		tc_productColor.setCellValueFactory(new PropertyValueFactory<Order, String>("productColor"));
		tc_productNumber.setCellValueFactory(new PropertyValueFactory<Order, String>("productNumber"));
		tc_progress.setCellValueFactory(new PropertyValueFactory<Order, String>("progress"));

		tc_receive.setCellFactory(new Callback<TableColumn<Order, String>, TableCell<Order, String>>() {
			@Override
			public TableCell<Order, String> call(final TableColumn<Order, String> param) {
				final TableCell<Order, String> cell = new TableCell<Order, String>() {
					final Button btn_receive = new Button("领取物料");

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn_receive.setOnAction(event -> {
								Order order = getTableView().getItems().get(getIndex());
								System.out.println("领取印刷半成品+袋子+盒子");
								orderList = getOrderList();
								tableView.setItems(orderList);

								int productNumber = order.getProductNumber();
								//领取印刷半成品
								{
									ResultSet resultSet = DBUtil.getDBUtil().executeQuery("select number from products where product='印刷半成品';");
									try {
										int number = resultSet.getInt("number");
										int leftNumber = number - productNumber;
										System.out.println("原有印刷半成品:" + number + " 剩余印刷半成品:" + leftNumber);
										// 领取预计的塑料颗粒，更新products表中的物料信息
										int updateId = DBUtil.getDBUtil().executeUpdate("update products set number=" + leftNumber + " where product='印刷半成品';");
										System.out.println("updateId:" + updateId);
										// 在records中添加一条记录
										if (mUser != null) {
											String username = mUser.getUsername();
											System.out.println("username:" + username);
											DBUtil.getDBUtil().execute("insert into records(material, number, username) values('印刷半成品', " + (-productNumber) + ",'" + username + "');");
										}
									} catch (SQLException e) {
										e.printStackTrace();
									}
								}
								
								//领取袋子
								{
									ResultSet resultSet = DBUtil.getDBUtil().executeQuery("select number from products where product='袋子';");
									try {
										int number = resultSet.getInt("number");
										int leftNumber = number - productNumber;
										System.out.println("原有袋子:" + number + " 剩余袋子:" + leftNumber);
										// 领取预计的塑料颗粒，更新products表中的物料信息
										int updateId = DBUtil.getDBUtil().executeUpdate("update products set number=" + leftNumber + " where product='袋子';");
										System.out.println("updateId:" + updateId);
										// 在records中添加一条记录
										if (mUser != null) {
											String username = mUser.getUsername();
											System.out.println("username:" + username);
											DBUtil.getDBUtil().execute("insert into records(material, number, username) values('袋子', " + (-productNumber) + ",'" + username + "');");
										}
									} catch (SQLException e) {
										e.printStackTrace();
									}
								}
								
								//领取盒子
								{
									ResultSet resultSet = DBUtil.getDBUtil().executeQuery("select number from products where product='盒子';");
									try {
										int number = resultSet.getInt("number");
										int leftNumber = number - productNumber;
										System.out.println("原有盒子:" + number + " 剩余盒子:" + leftNumber);
										// 领取预计的塑料颗粒，更新products表中的物料信息
										int updateId = DBUtil.getDBUtil().executeUpdate("update products set number=" + leftNumber + " where product='盒子';");
										System.out.println("updateId:" + updateId);
										// 在records中添加一条记录
										if (mUser != null) {
											String username = mUser.getUsername();
											System.out.println("username:" + username);
											DBUtil.getDBUtil().execute("insert into records(material, number, username) values('盒子', " + (-productNumber) + ",'" + username + "');");
										}
									} catch (SQLException e) {
										e.printStackTrace();
									}
								}
							});
							setGraphic(btn_receive);
							setText(null);
						}
					}
				};
				return cell;
			}
		});

		tc_start.setCellFactory(new Callback<TableColumn<Order, String>, TableCell<Order, String>>() {
			@Override
			public TableCell<Order, String> call(final TableColumn<Order, String> param) {
				final TableCell<Order, String> cell = new TableCell<Order, String>() {
					final Button btn_start = new Button("开始包装");

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn_start.setOnAction(event -> {
								Order order = getTableView().getItems().get(getIndex());
								// 更新进度为已派单，等待注塑车间接单
								boolean isSuc = DBUtil.getDBUtil().execute("update orders set progress=6 where _id=" + order.getId() + ";");
								if (isSuc) {
									System.out.println("开始包装");
									orderList = getOrderList();
									tableView.setItems(orderList);
								} else {
									System.out.println("更新数据库失败");
								}
							});
							setGraphic(btn_start);
							setText(null);
						}
					}
				};
				return cell;
			}
		});

		tc_finish.setCellFactory(new Callback<TableColumn<Order, String>, TableCell<Order, String>>() {
			@Override
			public TableCell<Order, String> call(final TableColumn<Order, String> param) {
				final TableCell<Order, String> cell = new TableCell<Order, String>() {
					final Button btn_finish = new Button("完成包装");

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
							setText(null);
						} else {
							btn_finish.setOnAction(event -> {
								Order order = getTableView().getItems().get(getIndex());
								boolean isSuc = DBUtil.getDBUtil().execute("update orders set progress=7 where _id=" + order.getId() + ";");
								if (isSuc) {
									System.out.println("完成包装");
									orderList = getOrderList();
									tableView.setItems(orderList);

									if (mUser != null) {
										int productNumber = order.getProductNumber();
										String username = mUser.getUsername();
										System.out.println("username:" + username);
										// 在records中添加一条包装成品的数据记录
										DBUtil.getDBUtil().execute("insert into records(material, number, username) values('包装成品', " + (productNumber) + ",'" + username + "');");
										try {
											ResultSet resultSet = DBUtil.getDBUtil().executeQuery("select number from products where product='包装成品';");
											int number = resultSet.getInt("number");
											System.out.println("number:" + number);
											// 在products中更新包装成品的数量
											DBUtil.getDBUtil().execute("update products set number=" + (number + productNumber) + " where product='包装成品';");
										} catch (SQLException e) {
											e.printStackTrace();
										}
									}
								} else {
									System.out.println("更新数据库失败");
								}
							});
							setGraphic(btn_finish);
							setText(null);
						}
					}
				};
				return cell;
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

		// 刷新按钮
		btn_refresh.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				orderList = getOrderList();
				tableView.setItems(orderList);
			}
		});
	}

	private User mUser;

	public void setUser(User user) {
		this.mUser = user;
	}
}
