package com.qust.factory.bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Material {
	private IntegerProperty _id;
	private StringProperty material;
	private IntegerProperty number;
	private StringProperty user;

	public Material(int _id, String material, int number, String user) {
		super();
		this._id = new SimpleIntegerProperty(_id);
		this.material = new SimpleStringProperty(material);
		this.number = new SimpleIntegerProperty(number);
		this.user = new SimpleStringProperty(user);
	}

	public int getId() {
		return _id.get();
	}

	public void setId(int _id) {
		this._id.set(_id);
	}

	public IntegerProperty idProperty() {
		return this._id;
	}

	public String getMaterial() {
		return material.get();
	}

	public void setMaterial(String material) {
		this.material.set(material);
	}

	public StringProperty materialProperty() {
		return this.material;
	}

	public int getNumber() {
		return number.get();
	}

	public void setNumber(int number) {
		this.number.set(number);
	}

	public IntegerProperty numberProperty() {
		return this.number;
	}

	public String getUser() {
		return user.get();
	}

	public void setUser(String user) {
		this.user.set(user);
	}

	public StringProperty userProperty() {
		return this.user;
	}

}
