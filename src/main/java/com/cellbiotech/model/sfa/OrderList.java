package com.cellbiotech.model.sfa;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;

/**
 * 수주리스트 관련 jpa model
 */
@Entity
@Table(name = "E_ORDER_LIST")
public class OrderList {

    @EmbeddedId
    private OrderListId id;

    @Column(name = "ACCOUNT_CODE")
    private String accountCode;

    @Column(name = "ACCOUNT_NAME")
    private String accountName;

    @Column(name = "SALES_GROUP_CODE")
    private String salesGroupCode;

    @Column(name = "SALES_GROUP_NAME")
    private String salesGroupName;

    @Column(name = "ITEM_CODE")
    private String itemCode;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "ORDER_DATE")
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @Column(name = "STANDARD_INFO")
    private String standardInfo;

    @Column(name = "ORDER_MANAGER")
    private String orderManager;

    @Column(name = "QTY")
    private BigDecimal qty;

    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "SUPER_TAX")
    private BigDecimal superTax;

    @Column(name = "SUM_AMOUNT")
    private BigDecimal sumAmount;

    @Column(name = "REMARKS")
    private String remarks;

    public OrderListId getId() {
        return id;
    }

    public void setId(OrderListId id) {
        this.id = id;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getSalesGroupCode() {
        return salesGroupCode;
    }

    public void setSalesGroupCode(String salesGroupCode) {
        this.salesGroupCode = salesGroupCode;
    }

    public String getSalesGroupName() {
        return salesGroupName;
    }

    public void setSalesGroupName(String salesGroupName) {
        this.salesGroupName = salesGroupName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getOrderDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(orderDate);
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStandardInfo() {
        return standardInfo;
    }

    public void setStandardInfo(String standardInfo) {
        this.standardInfo = standardInfo;
    }

    public String getOrderManager() {
        return orderManager;
    }

    public void setOrderManager(String orderManager) {
        this.orderManager = orderManager;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getSuperTax() {
        return superTax;
    }

    public void setSuperTax(BigDecimal superTax) {
        this.superTax = superTax;
    }

    public BigDecimal getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(BigDecimal sumAmount) {
        this.sumAmount = sumAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
