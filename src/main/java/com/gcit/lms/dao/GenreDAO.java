package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Genre;;

public class GenreDAO extends BaseDAO<Genre>  implements ResultSetExtractor<List<Genre>> {

	public void addGenre(Genre genre)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("INSERT INTO tbl_genre (genre_name) VALUES (?)", new Object[] { genre.getGenreName() });
		
	}

	public Integer addGenreWithID(Genre genre)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		KeyHolder holder = new GeneratedKeyHolder();
		final String sql = "INSERT INTO tbl_genre (genre_name) VALUES (?)";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, genre.getGenreName());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();
	}

	public void updateGenre(Genre genre)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_genre SET genre_name =? WHERE genre_id = ?",
				new Object[] { genre.getGenreName(), genre.getGenreId() });
	}

	public void deleteGenre(Genre genre)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("DELETE FROM tbl_genre WHERE genre_id = ?", new Object[] { genre.getGenreId() });
	}

	public List<Genre> readAllGenres()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return jdbcTemplate.query("SELECT * FROM tbl_genre", this);
	}
	
	public List<Genre> readAuthorByBookID(Integer bookId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return jdbcTemplate.query("select * from tbl_genre where genre_id IN (select genre_id from tbl_book_genres where bookId IN (select bookId from tbl_book where bookId = ?))", new Object[]{bookId},this);
	}

	public List<Genre> readGenresByName(String genre_name)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		genre_name = "%" + genre_name + "%";
		return jdbcTemplate.query("SELECT * FROM tbl_genre WHERE genre_name LIKE ?", new Object[] { genre_name },this);
	}

	public Genre readGenreByPK(Integer genre_id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Genre> genres = jdbcTemplate.query("SELECT * FROM tbl_genre WHERE genre_id  = ?", new Object[] { genre_id },this);
		if (genres != null) {
			return genres.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException{
		List<Genre> genres = new ArrayList<>();
		while (rs.next()) {
			Genre a = new Genre();
			a.setGenreId(rs.getInt("genre_id"));
			a.setGenreName(rs.getString("genre_name"));
			genres.add(a);
		}
		return genres;
	}

}
