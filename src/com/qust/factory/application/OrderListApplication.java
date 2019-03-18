package com.qust.factory.application;

import com.qust.factory.bean.User;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OrderListApplication extends Application {

	private User user;

	public OrderListApplication(User user) {
		this.user = user;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		if (user != null) {
			String role = user.getRole();
			System.out.println("role:" + role);
		}
		try {
			// Read file fxml and draw interface.
			Parent root = FXMLLoader.load(getClass().getResource("/com/qust/factory/scene/OrderListScene.fxml"));
			primaryStage.setTitle("生产管理界面");
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
