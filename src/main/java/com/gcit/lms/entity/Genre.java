package com.gcit.lms.entity;

import java.util.ArrayList;

public class Genre {
	
	private Integer genreId;
	private String genreName;
	private ArrayList<Book> books;
	/**
	 * @return the genreId
	 */
	public Integer getGenreId() {
		return genreId;
	}
	/**
	 * @param genreId the genreId to set
	 */
	public void setGenreId(Integer genreId) {
		this.genreId = genreId;
	}
	/**
	 * @return the genreName
	 */
	public String getGenreName() {
		return genreName;
	}
	/**
	 * @param genreName the genreName to set
	 */
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
	/**
	 * @return the books
	 */
	public ArrayList<Book> getBooks() {
		return books;
	}
	/**
	 * @param books the books to set
	 */
	public void setBooks(ArrayList<Book> books) {
		this.books = books;
	}

}
