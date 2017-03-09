package edu.rivier.ppms.budget;

import java.sql.Date;

import edu.rivier.ppms.client.Client;

public class Budget {
	private int budgetId;
	private Date incomeDate;
	private String revenueType;
	private String revenueAmount;
	private Date expenceDate;
	private String description;
	private int amount;
	private Client client;
	
	public int getBudgetId() {
		return budgetId;
	}
	public void setBudgetId(int budgetId) {
		this.budgetId = budgetId;
	}
	public Date getIncomeDate() {
		return incomeDate;
	}
	public void setIncomeDate(Date incomeDate) {
		this.incomeDate = incomeDate;
	}
	public String getRevenueType() {
		return revenueType;
	}
	public void setRevenueType(String revenueType) {
		this.revenueType = revenueType;
	}
	public String getRevenueAmount() {
		return revenueAmount;
	}
	public void setRevenueAmount(String revenueAmount) {
		this.revenueAmount = revenueAmount;
	}
	public Date getExpenceDate() {
		return expenceDate;
	}
	public void setExpenceDate(Date expenceDate) {
		this.expenceDate = expenceDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount2) {
		this.amount = amount2;
	}
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
}
