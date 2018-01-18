package com.gcit.lms.entity;

import java.util.List;

public class Borrower {
	private Integer borrowerCardNo;
	private String borrowerName;
	private String borrowerAddress;
	private Integer borrowerPhone;
	private List<Book> books;
	
	private Integer branchId;
	private Integer bookId;
	private boolean status;
	
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Integer getBorrowerCardNo() {
		return borrowerCardNo;
	}
	public void setBorrowerCardNo(Integer borrowerCardNo) {
		this.borrowerCardNo = borrowerCardNo;
	}
	public String getBorrowerName() {
		return borrowerName;
	}
	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}
	public String getBorrowerAddress() {
		return borrowerAddress;
	}
	public void setBorrowerAddress(String borrowerAddress) {
		this.borrowerAddress = borrowerAddress;
	}
	public Integer getBorrowerPhone() {
		return borrowerPhone;
	}
	public void setBorrowerPhone(Integer borrowerPhone) {
		this.borrowerPhone = borrowerPhone;
	}
	

}
