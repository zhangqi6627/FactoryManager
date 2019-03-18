package com.qust.factory.application;

import com.qust.factory.bean.User;
import com.qust.factory.controller.PrintController;
import com.qust.factory.controller.StoreController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StoreApplication extends Application {
	private User mUser;

	public StoreApplication(User user) {
		this.mUser = user;
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			System.out.println("storeapplication");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/qust/factory/scene/StoreScene.fxml"));
			Parent root = loader.load();
			primaryStage.setTitle("仓库管理界面");
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			Object controller = loader.getController();
			if(controller instanceof StoreController) {
				System.out.println("StoreController->start() mUser:"+mUser);
				((StoreController)controller).setUser(mUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
