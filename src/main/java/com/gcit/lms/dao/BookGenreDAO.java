package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Genre;

public class BookGenreDAO extends BaseDAO<Genre>   implements ResultSetExtractor<List<Genre>>  {
	
	public void addBookGenre(Integer genreId, Integer bookId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("INSERT INTO tbl_book_genres (genre_id, bookId) VALUES (?,?)", new Object[] {genreId,bookId});
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException, DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


}
