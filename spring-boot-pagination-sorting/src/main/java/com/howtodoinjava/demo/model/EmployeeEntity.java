package com.howtodoinjava.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bank_transactions")
public class EmployeeEntity {

   

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @Column(name="account_no")
    private Long accountNo;
    
    public Long getAccountNo() {
		return accountNo;
	}



	public void setAccountNo(Long accountNo) {
		this.accountNo = accountNo;
	}



	@Column(name="date")
    private String date;
    
    @Column(name="transaction_details")
    private String transactionDetails;
    
    @Column(name="value_date")
    private String valueDate;
    
    @Column(name="withdrawal_amt")
    private Double withdrwalAmt;
    
    @Column(name="deposit_amt")
    private Double depositAmt;
    
    @Column(name="balance_amt")
    private Double balanceAmt;

	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}






	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public String getTransactionDetails() {
		return transactionDetails;
	}



	public void setTransactionDetails(String transactionDetails) {
		this.transactionDetails = transactionDetails;
	}



	public String getValueDate() {
		return valueDate;
	}



	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}



	public Double getWithdrwalAmt() {
		return withdrwalAmt;
	}



	public void setWithdrwalAmt(Double withdrwalAmt) {
		this.withdrwalAmt = withdrwalAmt;
	}



	public Double getDepositAmt() {
		return depositAmt;
	}



	public void setDepositAmt(Double depositAmt) {
		this.depositAmt = depositAmt;
	}



	public Double getBalanceAmt() {
		return balanceAmt;
	}



	public void setBalanceAmt(Double balanceAmt) {
		this.balanceAmt = balanceAmt;
	}



	@Override
	public String toString() {
		return "EmployeeEntity [id=" + id + ", AccountNo=" + accountNo + ", date=" + date + ", transactionDetails="
				+ transactionDetails + ", valueDate=" + valueDate + ", withdrwalAmt=" + withdrwalAmt + ", depositAmt="
				+ depositAmt + ", balanceAmt=" + balanceAmt + "]";
	}

	
    
    
    
}