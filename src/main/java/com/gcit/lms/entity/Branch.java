package com.gcit.lms.entity;

import java.util.List;

public class Branch {
	private Integer branchId;
	private String branchName;
	private String branchAddress;
	private List<Book> books;
	private List<BranchBooksDetails> branchBooksDetails;
	
	public List<BranchBooksDetails> getBranchBooksDetails() {
		return branchBooksDetails;
	}
	public void setBranchBooksDetails(List<BranchBooksDetails> branchBooksDetails) {
		this.branchBooksDetails = branchBooksDetails;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchAddress() {
		return branchAddress;
	}
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}
	

}
