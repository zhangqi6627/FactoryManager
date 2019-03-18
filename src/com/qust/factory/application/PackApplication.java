package com.qust.factory.application;

import com.qust.factory.bean.User;
import com.qust.factory.controller.PackController;
import com.qust.factory.controller.PrintController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PackApplication extends Application{
	private User mUser;
	public PackApplication(User user) {
		this.mUser = user;
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/qust/factory/scene/PackScene.fxml"));
			Parent root = loader.load();
			primaryStage.setTitle("包装车间管理界面");
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			
			Object controller = loader.getController();
			if(controller instanceof PackController) {
				System.out.println("ModelApplication->start() mUser:"+mUser);
				((PackController)controller).setUser(mUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
