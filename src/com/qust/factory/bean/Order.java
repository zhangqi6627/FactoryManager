package com.qust.factory.bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Order {
	private IntegerProperty _id;
	private IntegerProperty orderId;
	private StringProperty productName;
	private StringProperty productType;
	private StringProperty productColor;
	private IntegerProperty productNumber;
	// 0:创建订单，未派单，
	// 1:已派单，注塑未接单，
	// 2:注塑接单，开始注塑
	// 3:注塑完成，等待印刷接单
	// 4:印刷接单，开始印刷
	// 5:印刷完成，等待包装接单
	// 6:包装开始接单，开始包装
	// 7:包装完成，等待验货入库
	private IntegerProperty progress;
	private StringProperty createTime;
	private StringProperty finishTime;
	private StringProperty modelStartTime;
	private StringProperty modelFinishTime;
	private IntegerProperty modelCost;
	private StringProperty printStartTime;
	private StringProperty printFinishTime;
	private IntegerProperty printCost;
	private StringProperty packStartTime;
	private StringProperty packFinishTime;
	private IntegerProperty packCost;

	public Order(int orderId, String productName, String productType, String productColor, int productNumber) {
		this.orderId = new SimpleIntegerProperty(orderId);
		this.productName = new SimpleStringProperty(productName);
		this.productType = new SimpleStringProperty(productType);
		this.productColor = new SimpleStringProperty(productColor);
		this.productNumber = new SimpleIntegerProperty(productNumber);
	}

	public Order(int _id, int orderId, String productName, String productType, String productColor, int productNumber) {
		this._id = new SimpleIntegerProperty(_id);
		this.orderId = new SimpleIntegerProperty(orderId);
		this.productName = new SimpleStringProperty(productName);
		this.productType = new SimpleStringProperty(productType);
		this.productColor = new SimpleStringProperty(productColor);
		this.productNumber = new SimpleIntegerProperty(productNumber);
		this.progress = new SimpleIntegerProperty(0);
		this.createTime = new SimpleStringProperty();
		this.finishTime = new SimpleStringProperty();
		this.modelStartTime = new SimpleStringProperty();
		this.modelFinishTime = new SimpleStringProperty();
		this.modelCost = new SimpleIntegerProperty();
		this.printStartTime = new SimpleStringProperty();
		this.printFinishTime = new SimpleStringProperty();
		this.printCost = new SimpleIntegerProperty();
		this.packStartTime = new SimpleStringProperty();
		this.packFinishTime = new SimpleStringProperty();
		this.packCost = new SimpleIntegerProperty();
	}

	public int getId() {
		return _id.get();
	}

	public void setId(int _id) {
		if (this._id == null) {
			this._id = new SimpleIntegerProperty(_id);
		} else {
			this._id.set(_id);
		}
	}

	public IntegerProperty idProperty() {
		return _id;
	}

	public int getOrderId() {
		return orderId.get();
	}

	public void setOrderId(int orderId) {
		this.orderId.set(orderId);
	}

	public IntegerProperty orderIdProperty() {
		return orderId;
	}

	public String getProductName() {
		return productName.get();
	}

	public void setProductName(String productName) {
		this.productName.set(productName);
	}

	public StringProperty productNameProperty() {
		return productName;
	}

	public String getProductType() {
		return productType.get();
	}

	public void setProductType(String productType) {
		this.productType.set(productType);
	}

	public StringProperty productTypeProperty() {
		return productType;
	}

	public String getProductColor() {
		return productColor.get();
	}

	public void setProductColor(StringProperty productColor) {
		this.productColor = productColor;
	}

	public StringProperty productColorProperty() {
		return productColor;
	}

	public int getProductNumber() {
		return productNumber.get();
	}

	public void setProductNumber(int productNumber) {
		this.productNumber.set(productNumber);
	}

	public IntegerProperty productNumberProperty() {
		return productNumber;
	}

	public int getProgress() {
		return progress.get();
	}

	public void setProgress(int progress) {
		this.progress.set(progress);
	}

	public IntegerProperty progressProperty() {
		return progress;
	}

	public String getCreateTime() {
		return createTime.get();
	}

	public void setCreateTime(String createTime) {
		this.createTime.set(createTime);
	}

	public StringProperty createTimeProperty() {
		return createTime;
	}

	public String getFinishTime() {
		return finishTime.get();
	}

	public void setFinishTime(String finishTime) {
		this.finishTime.set(finishTime);
	}

	public StringProperty finishTimeProperty() {
		return this.finishTime;
	}

	public String getModelStartTime() {
		return modelStartTime.get();
	}

	public void setModelStartTime(String modelStartTime) {
		this.modelStartTime.set(modelStartTime);
	}

	public StringProperty modelStartTimeProperty() {
		return this.modelStartTime;
	}

	public String getModelFinishTime() {
		return modelFinishTime.get();
	}

	public void setModelFinishTime(String modelFinishTime) {
		this.modelFinishTime.set(modelFinishTime);
	}

	public StringProperty modelFinishTimeProperty() {
		return this.modelFinishTime;
	}

	public int getModelCost() {
		return modelCost.get();
	}

	public void setModelCost(int modelCost) {
		this.modelCost.set(modelCost);
	}

	public IntegerProperty modelCostProperty() {
		return this.modelCost;
	}

	public String getPrintStartTime() {
		return printStartTime.get();
	}

	public void setPrintStartTime(String printStartTime) {
		this.printStartTime.set(printStartTime);
	}

	public StringProperty printStartTimeProperty() {
		return this.printStartTime;
	}

	public String getPrintFinishTime() {
		return printFinishTime.get();
	}

	public void setPrintFinishTime(String printFinishTime) {
		this.printFinishTime.set(printFinishTime);
	}

	public StringProperty printFinishTimeProperty() {
		return this.printFinishTime;
	}

	public int getPrintCost() {
		return printCost.get();
	}

	public void setPrintCost(int printCost) {
		this.printCost.set(printCost);
	}

	public IntegerProperty printCostProperty() {
		return this.printCost;
	}

	public String getPackStartTime() {
		return packStartTime.get();
	}

	public void setPackStartTime(String packStartTime) {
		this.packStartTime.set(packStartTime);
	}

	public StringProperty packStartTimeProperty() {
		return this.packStartTime;
	}

	public String getPackFinishTime() {
		return packFinishTime.get();
	}

	public void setPackFinishTime(String packFinishTime) {
		this.packFinishTime.set(packFinishTime);
	}

	public StringProperty packFinishTimeProperty() {
		return this.packFinishTime;
	}

	public int getPackCost() {
		return packCost.get();
	}

	public void setPackCost(int packCost) {
		this.packCost.set(packCost);
	}

	public IntegerProperty packCostProperty() {
		return this.packCost;
	}

	@Override
	public String toString() {
		return "Order [_id=" + _id + ", orderId=" + orderId + ", productName=" + productName + ", productType=" + productType + ", productColor=" + productColor + ", productNumber=" + productNumber + ", progress="
		        + progress + ", createTime=" + createTime + ", finishTime=" + finishTime + ", modelStartTime=" + modelStartTime + ", modelFinishTime=" + modelFinishTime + ", modelCost=" + modelCost + ", printStartTime="
		        + printStartTime + ", printFinishTime=" + printFinishTime + ", printCost=" + printCost + ", packStartTime=" + packStartTime + ", packFinishTime=" + packFinishTime + ", packCost=" + packCost + "]";
	}
}
