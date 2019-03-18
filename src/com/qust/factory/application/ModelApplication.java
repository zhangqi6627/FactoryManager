package com.qust.factory.application;

import com.qust.factory.bean.User;
import com.qust.factory.controller.ModelController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ModelApplication extends Application{
	private User mUser;
	public ModelApplication(User user) {
		this.mUser = user;
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/qust/factory/scene/ModelScene.fxml"));
			Parent root = loader.load();
			
			primaryStage.setTitle("注塑车间管理界面");
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			Object controller = loader.getController();
			if(controller instanceof ModelController) {
				System.out.println("ModelApplication->start() mUser:"+mUser);
				((ModelController)controller).setUser(mUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public User getUser() {
		return this.mUser;
	}
}
