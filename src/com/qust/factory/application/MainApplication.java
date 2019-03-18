package com.qust.factory.application;


import com.qust.factory.utils.DBUtil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class MainApplication extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			// Read file fxml and draw interface.
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/qust/factory/scene/LoginScene.fxml"));
			Parent root = fxmlLoader.load();
			primaryStage.setTitle("登录界面");
			primaryStage.setScene(new Scene(root));
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		/**
		 * TODO: 初始化数据库
		 */
		DBUtil dbUtil = DBUtil.getDBUtil();
		System.out.println("3");
		// 检查数据库是否初始化
		if (dbUtil.execute("select 1 from user;")) {
			System.out.println("4");
		}else {
			// user表
			boolean createUserSuc = dbUtil.execute("create table if not exists user(_id integer primary key autoincrement, username varchar(32), password varchar(32), role varchar(32))");
			if (createUserSuc) {
				System.out.println("create user table success!");
				dbUtil.execute("insert into user(role, username, password) values('admin', 'admin', '123');");
				dbUtil.execute("insert into user(role, username, password) values('manager', 'manager', '123');");
				dbUtil.execute("insert into user(role, username, password) values('modeler', 'modeler', '123');");
				dbUtil.execute("insert into user(role, username, password) values('printer', 'printer', '123');");
				dbUtil.execute("insert into user(role, username, password) values('packer', 'packer', '123');");
				dbUtil.execute("insert into user(role, username, password) values('material', 'material', '123');");
			} else {
				System.out.println("create user table failure!");
			}
			
			// order表
			boolean createOrderSuc = dbUtil.execute("create table if not exists orders(" +
					"_id integer primary key autoincrement," +
					"orderId varchar(16)," +
					"productName varchar(32)," +
					"productType varchar(8)," +
					"productColor varchar(8)," +
					"productNumber varchar(32)," +
					"progress integer," +
					"createTime varchar(32)," +
					"finishTime varchar(32)," +
					"sendTime varchar(32)," +
					"modelStartTime varchar(32)," +
					"modelFinishTime varchar(32)," +
					"modelCost integer," +
					"printStartTime varchar(32)," +
					"printFinishTime varchar(32)," +
					"printCost integer," +
					"packStartTime varchar(32)," +
					"packFinishTime varchar(32)," +
					"packCost integer," +
					"priority integer);");
			if (createOrderSuc) {
				System.out.println("create order table success");
			} else {
				System.out.println("create order table failure");
			}
			
			// records(物料登记表)
			boolean createRecords = dbUtil.execute("create table if not exists records(_id integer primary key autoincrement, material varchar(32), number integer, username varchar(32));");
			if (createRecords) {
				System.out.println("records table create success");
				dbUtil.execute("insert into records(material, number) values('塑料颗粒', 1000)");
				dbUtil.execute("insert into records(material, number) values('袋子', 1000)");
				dbUtil.execute("insert into records(material, number) values('盒子', 1000)");
				dbUtil.execute("insert into records(material, number) values('注塑半成品', 1000)");
				dbUtil.execute("insert into records(material, number) values('印刷半成品', 1000)");
				dbUtil.execute("insert into records(material, number) values('包装成品', 1000)");
			} else {
				System.out.println("records table create failure");
			}
			
			// products(产品表)
			boolean createProducts = dbUtil.execute("create table if not exists products(_id integer primary key autoincrement, product varchar(32), number integer);");
			if(createProducts) {
				System.out.println("products table create success");
				dbUtil.execute("insert into products(product, number) values('塑料颗粒', 1000)");
				dbUtil.execute("insert into products(product, number) values('袋子', 1000)");
				dbUtil.execute("insert into products(product, number) values('盒子', 1000)");
				dbUtil.execute("insert into products(product, number) values('注塑半成品', 1000)");
				dbUtil.execute("insert into products(product, number) values('印刷半成品', 1000)");
				dbUtil.execute("insert into products(product, number) values('包装成品', 1000)");
			}else {
				System.out.println("products table create failure");
			}
		}
		launch(args);
	}
}
