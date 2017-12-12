package com.cellbiotech.model.sfa;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;

/**
 * 납품의뢰리스트 관련 jpa model
 */
@Entity
@Table(name = "E_DELIVERY_REQUEST_LIST")
public class DeliveryRequestList {

    @EmbeddedId
    private DeliveryRequestListId id;

    @Column(name = "REQUEST_DATE")
    @Temporal(TemporalType.DATE)
    private Date requestDate;

    @Column(name = "ACCOUNT_NAME")
    private String accountName;

    @Column(name = "ACCOUNT_CODE")
    private String accountCode;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "REQUEST_QTY")
    private BigDecimal requestQty;

    @Column(name = "SHIPPING_QTY")
    private BigDecimal shippingQty;

    @Column(name = "NOT_SHIPPING_QTY")
    private BigDecimal notShippingQty;

    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "SHIPPING_WAREHOUSE")
    private String shippingWarehouse;

    @Column(name = "GOODS_ISSUE")
    private String goodsIssue;

    @Column(name = "LINE_REMARKS")
    private String lineRemarks;

    public DeliveryRequestListId getId() {
        return id;
    }

    public void setId(DeliveryRequestListId id) {
        this.id = id;
    }

    public String getRequestDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(requestDate);
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getRequestQty() {
        return requestQty;
    }

    public void setRequestQty(BigDecimal requestQty) {
        this.requestQty = requestQty;
    }

    public BigDecimal getShippingQty() {
        return shippingQty;
    }

    public void setShippingQty(BigDecimal shippingQty) {
        this.shippingQty = shippingQty;
    }

    public BigDecimal getNotShippingQty() {
        return notShippingQty;
    }

    public void setNotShippingQty(BigDecimal notShippingQty) {
        this.notShippingQty = notShippingQty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getShippingWarehouse() {
        return shippingWarehouse;
    }

    public void setShippingWarehouse(String shippingWarehouse) {
        this.shippingWarehouse = shippingWarehouse;
    }

    public String getGoodsIssue() {
        return goodsIssue;
    }

    public void setGoodsIssue(String goodsIssue) {
        this.goodsIssue = goodsIssue;
    }

    public String getLineRemarks() {
        return lineRemarks;
    }

    public void setLineRemarks(String lineRemarks) {
        this.lineRemarks = lineRemarks;
    }
}
