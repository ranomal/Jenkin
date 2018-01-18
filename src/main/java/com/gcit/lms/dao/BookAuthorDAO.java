package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;

public class BookAuthorDAO extends BaseDAO<Author>   implements ResultSetExtractor<List<Author>> {

	public void addBookAuthor(Integer authorId, Integer bookId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		//INSERT INTO `library`.`tbl_book_authors` (`bookId`, `authorId`) VALUES ('12', '4');
		jdbcTemplate.update("INSERT INTO tbl_book_authors (bookId, authorId) VALUES (?,?)", new Object[] {bookId,authorId});
	}
	
	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException, DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


}
