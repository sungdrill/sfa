package com.cellbiotech.model.sfa;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.*;

/**
 * 매출매감리스트 관련 jpa model
 */
@Entity
@Table(name = "E_SALES_DEADLINE_LIST")
public class SalesDeadlineList {

    @EmbeddedId
    private SalesDeadlineListId id;

    @Column(name = "SALES_DATE")
    @Temporal(TemporalType.DATE)
    private Date salesDate;

    @Column(name = "ITEM_NAME")
    private String itemName;

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

    @Column(name = "ACCOUNT_CODE")
    private String accountCode;

    @Column(name = "ACCOUNT_NAME")
    private String accountName;

    @Column(name = "RETURN_GB")
    private String returnGb;

    public SalesDeadlineListId getId() {
        return id;
    }

    public void setId(SalesDeadlineListId id) {
        this.id = id;
    }

    public String getSalesDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(salesDate);
    }

    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    public void setSum(BigDecimal sumAmount) {
        this.sumAmount = sumAmount;
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

    public String getReturnGb() {
        return returnGb;
    }

    public void setReturnGb(String returnGb) {
        this.returnGb = returnGb;
    }

}
