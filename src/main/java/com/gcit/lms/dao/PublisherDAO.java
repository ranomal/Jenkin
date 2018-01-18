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
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Publisher;

public class PublisherDAO extends BaseDAO<Publisher>   implements ResultSetExtractor<List<Publisher>>{

	public void addPublisher(Publisher p) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("INSERT INTO tbl_publisher (publisherName, publisherAddress,publisherPhone) VALUES (?,?,?)", new Object[] {p.getPublisherName(),p.getPublisherAddress(),p.getPublisherPhone()});
	}
	public Publisher readPublisherByPK(Integer pk)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Publisher> publishers= jdbcTemplate.query("SELECT * FROM tbl_publisher WHERE publisherId  = ?", new Object[] { pk },this);
		if (publishers != null) {
			return publishers.get(0);
		} else {
			return null;
		}
	}
	public Publisher readPublisherByBookID(Integer bookId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Publisher> publisher= jdbcTemplate.query("SELECT * FROM tbl_publisher WHERE publisherId IN (SELECT pubId as publisherId FROM tbl_book WHERE bookId = ?)", new Object[]{bookId},this);
		if(publisher.size()>0) {
			return publisher.get(0);
		}
		else {
			return null;
		}
	}
	public void updatePublisher(Publisher publisher)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_publisher SET publisherName =? WHERE publisherId = ?",
				new Object[] { publisher.getPublisherName(), publisher.getPublisherId() });
	}
	
	public Integer addPublisherWithID(Publisher publisher)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		KeyHolder holder = new GeneratedKeyHolder();
		final String sql = "INSERT INTO tbl_publisher (publisherName,publisherAddress,publisherPhone) VALUES (?,?,?)";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, publisher.getPublisherName());
				ps.setString(2, publisher.getPublisherAddress());
				ps.setString(3, publisher.getPublisherPhone());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();
	}
	
	public List<Publisher> readAllPublisher(Integer pageNo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		if(pageNo!=null) {setPageNo(pageNo);}
		return jdbcTemplate.query("SELECT * FROM tbl_publisher", this);
	}
	//By Name
			public List<Publisher> readPublishersByName(String publisherName)
					throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
				publisherName = "%"+publisherName+"%";
				return jdbcTemplate.query("SELECT * FROM tbl_publisher WHERE publisherName LIKE ?", new Object[] { publisherName },this);
			}
	
	//pagination
	public Integer getPublisherCount()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return jdbcTemplate.queryForObject("SELECT COUNT(*) AS COUNT FROM tbl_publisher",Integer.class);
	}
	
	public void deletePublisher(Publisher publisher) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("DELETE FROM tbl_publisher WHERE publisherId = ?", new Object[]{publisher.getPublisherId()});
	}
	
	@Override
	public List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<>();
		while(rs.next()){
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("publisherId"));
			p.setPublisherName(rs.getString("publisherName"));
			p.setPublisherAddress("publisherAddress");
			p.setPublisherPhone("publisherPhone");
			//add to populate books
			//p.setBooks(bDao.readFirstLevel("SELECT * FROM tbl_book WHERE pubId =?", new Object[]{rs.getInt("publisherId")}));
			publishers.add(p);
		}
		return publishers;
	}

}
