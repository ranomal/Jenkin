package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;


public class BookDAO extends BaseDAO<Book>  implements ResultSetExtractor<List<Book>>{
	

	public void addBook(Book book) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("INSERT INTO tbl_book (title) VALUES (?)", new Object[] {book.getTitle()});
	}
	
	public Integer addBookWithID(Book book) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		KeyHolder holder = new GeneratedKeyHolder();
		final String sql = "INSERT INTO tbl_book (title) VALUES (?)";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, book.getTitle());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();
	}
	
	//
	public void addBookBranchCopy(Integer bookId, Integer branchId) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		//INSERT INTO `library`.`tbl_book_copies` (`bookId`, `branchId`, `noOfCopies`) VALUES ('4', '2', '0');
		jdbcTemplate.update("INSERT INTO tbl_book_copies VALUES (?,?,?)", new Object[] {bookId,branchId,1});
	
}
	public Integer addBookWithIDPubId(Book book) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		KeyHolder holder = new GeneratedKeyHolder();
		final String sql = "INSERT INTO tbl_book (title,pubId) VALUES (?,?)";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, book.getTitle());
				ps.setInt(2, book.getPublisher().getPublisherId());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();
	}
	
	public void addBookAuthors(Book book) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		for(Author a: book.getAuthors()){
			jdbcTemplate.update("INSERT INTO tbl_book_authors VALUES (?, ?)", new Object[] {book.getBookId(), a.getAuthorId()});
		}
	}
	
	public void updateBook(Book book) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_book SET title =? WHERE bookId = ?", new Object[] {book.getTitle(), book.getBookId()});
	}
	//loan
	public void updateloanBookDueDate(Book book) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		//UPDATE `library`.`tbl_book_loans` SET `dueDate`='2018-10-11 00:00:00' WHERE `bookId`='6' and`branchId`='1' and`cardNo`='11231' and`dateOut`='2014-08-11 00:00:00';
		//System.out.println("bid1: "+book.getBookId()+" bnid1: "+book.getBookId()+" card1: "+book.getBorrowerCardNo()+" due1: "+book.getDueDate()+" out1:"+book.getCheckOutDate());
		//UPDATE tbl_book_loans SET dueDate= ? WHERE bookId= ? and branchId= ? and cardNo= ?
		//save("UPDATE tbl_book_loans SET dueDate = ? WHERE bookId = ? and branchId = ? and cardNo= ? and dateOut=?", new Object[] {book.getDueDate(), book.getBookId(), book.getBranchId(), book.getBorrowerCardNo(), book.getCheckOutDate()});
//		PreparedStatement pstmt = conn.prepareStatement("UPDATE tbl_book_loans SET dueDate= ? WHERE bookId= ? and branchId= ? and cardNo= ? and dateOut= ?");
//		pstmt.setString(1, book.getDueDate());
//		pstmt.setInt(2, book.getBookId());
//		pstmt.setInt(3, book.getBranchId());
//		pstmt.setInt(4, book.getBorrowerCardNo());
//		pstmt.setString(5, book.getCheckOutDate());
//		pstmt.executeUpdate();
//		conn.commit();
//		conn.close();
	}
	
	public void updateBookPubid(Book book) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_book SET pubId =? WHERE bookId = ?", new Object[] {book.getPublisher().getPublisherId(), book.getBookId()});
	}

	public void deleteBook(Book book) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("DELETE FROM tbl_book WHERE bookId = ?", new Object[]{book.getBookId()});
	}
	
	public List<Book> readAllBooks(Integer pageNo) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		if(pageNo!=null) {setPageNo(pageNo);}
		List<Book> books=jdbcTemplate.query("SELECT * FROM tbl_book", this);
		return books;
	}
	public List<Book> readAllBooksByAuthorID(Integer authorId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		List<Book> books=jdbcTemplate.query("SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_authors WHERE authorId = ?)", new Object[]{authorId},this);
		return books;
	}
	public List<Book> readAllBooksByPublisherID(Integer pubId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		List<Book> books=jdbcTemplate.query("SELECT * FROM tbl_book WHERE pubId = ?", new Object[]{pubId},this);
		return books;
	}
	
	public List<Book> readAllBooksPubNull() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		return jdbcTemplate.query("SELECT * FROM tbl_book where pubId is null", this);
	}
	//pagination
		public Integer getBooksCount()
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			return jdbcTemplate.queryForObject("SELECT COUNT(*) AS COUNT FROM tbl_book",Integer.class);
		}
	//By Name
		public List<Book> readBooksByName(String bookName)
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			bookName = "%"+bookName+"%";
			return jdbcTemplate.query("SELECT * FROM tbl_book WHERE title LIKE ?", new Object[] { bookName },this);
		}
	public Book readBookByPK(Integer bookId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Book> books = jdbcTemplate.query("SELECT * FROM tbl_book WHERE bookId  = ?", new Object[] { bookId },this);
		if (books != null) {
			return books.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while(rs.next()){
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			books.add(b);	
		}
		return books;
	}

}
