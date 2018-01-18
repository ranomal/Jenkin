package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.gcit.lms.entity.Borrower;;

public class BorrowerDAO extends BaseDAO<Borrower> implements ResultSetExtractor<List<Borrower>> {
	public void addBorrower(Borrower borrower)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("INSERT INTO tbl_borrower (borrowerName) VALUES (?)", new Object[] { borrower.getBorrowerName() });
		
	}
	//
	public List<Borrower> readBorrowerByCardNo(Borrower borrower)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		return jdbcTemplate.query("SELECT * FROM tbl_borrower where cardNo=?", new Object[] { borrower.getBorrowerCardNo() },this);
	}
	
	//
	public Integer addBorrowerWithID(Borrower borrower)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		KeyHolder holder = new GeneratedKeyHolder();
		final String sql = "INSERT INTO tbl_borrower (name,address,phone) VALUES (?,?,?)";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, borrower.getBorrowerName());
				ps.setString(2, borrower.getBorrowerAddress());
				ps.setInt(3, borrower.getBorrowerPhone());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();
	}

	public void updateBorrower(Borrower borrower)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_borrower SET name = ?, address = ?, phone =?  WHERE cardNo = ?",
				new Object[] { borrower.getBorrowerName(), borrower.getBorrowerAddress(), borrower.getBorrowerPhone(), borrower.getBorrowerCardNo() });
	}
	
	public void updateBorrowerAddress(Borrower borrower)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_borrower SET address =? WHERE cardNo = ?",
				new Object[] { borrower.getBorrowerAddress(), borrower.getBorrowerCardNo() });
	}
	//
	public void updateLoanDuaDate(Borrower borrower)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		//UPDATE `library`.`tbl_book_loans` SET `dateIn`='asd' WHERE `bookId`='1' and`branchId`='1' and`cardNo`='11230';
		jdbcTemplate.update("UPDATE tbl_book_loans SET dateIn =? WHERE bookId=? and branchId=? and cardNo= ?",
				new Object[] { dateFormat.format(date),borrower.getBookId(), borrower.getBranchId(),borrower.getBorrowerCardNo() });
	}
	//checkout
	public void insertCheckOutEntry(Borrower borrower)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		//INSERT INTO tbl_book_loans  (bookId, branchId, cardNo, dateOut, dueDate) VALUES (?,?,?,?,?);
		jdbcTemplate.update("INSERT INTO tbl_book_loans  (bookId, branchId, cardNo, dateOut, dueDate) VALUES (?,?,?,?,?)",
				new Object[] { borrower.getBookId(),borrower.getBranchId(),borrower.getBorrowerCardNo(),dateFormat.format(date),"2018-11-11 00:00:00"  });
	}
	public void updateBorrowerPhone(Borrower borrower)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_borrower SET phone =? WHERE cardNo = ?",
				new Object[] { borrower.getBorrowerPhone(), borrower.getBorrowerCardNo() });
	}
	public void updateBorrowerBookCopies(Integer bookId,Integer borrowerId, Integer newCopies)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
//		jdbcTemplate.update("UPDATE tbl_book_copies SET noOfCopies=? WHERE bookId=? and borrowerId=?",
//				new Object[] { newCopies,bookId,borrowerId });
	}

	public void deleteBorrower(Borrower borrower)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("DELETE FROM tbl_borrower WHERE cardNo = ?", new Object[] { borrower.getBorrowerCardNo() });
	}

	public List<Borrower> readAllBorrowers()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return jdbcTemplate.query("SELECT * FROM tbl_borrower", this);
	}

	public List<Borrower> readBorrowersByName(String borrowerName)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		borrowerName = "%" + borrowerName + "%";
		return jdbcTemplate.query("SELECT * FROM tbl_borrower WHERE borrowerName LIKE ?", new Object[] { borrowerName },this);
	}
	//
	/*public Borrower readBorrowersByBookBorrower(Borrower borrower)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Book> books = readbbb("SELECT tbl_book.bookId ,tbl_book.title, tbl_book_loans.dateOut, tbl_book_loans.dueDate, tbl_book_loans.branchId FROM library.tbl_book_loans left join tbl_book on tbl_book.bookId= tbl_book_loans.bookId where cardNo=? and dateIn is null", new Object[] { borrower.getBorrowerCardNo() });
		borrower.setBooks(books);
		if (books != null) {
			return borrower;
		} else {
			return null;
		}
	}*/
	public List<Borrower> readBorrowersByBook(Integer bookId, Integer branchId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return jdbcTemplate.query("SELECT tbl_borrower.cardNo,tbl_borrower.name,tbl_borrower.address,tbl_borrower.phone FROM library.tbl_book_loans left join tbl_borrower on tbl_borrower.cardNo= tbl_book_loans.cardNo where bookId=? and branchId=?;", new Object[] { bookId,branchId },this);
		
	}
	
	//
	/*@Override
	public List<Book> extractDatabbb(ResultSet rs) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<Book> books = new ArrayList<>();
		while(rs.next()){
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			book.setCheckOutDate(rs.getString("dateOut"));
			book.setDueDate(rs.getString("dueDate"));
			book.setBranchId(rs.getInt("branchId"));
			books.add(book);
		}
		return books;
	}*/


	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException{
		List<Borrower> borrowers = new ArrayList<>();
		while (rs.next()) {
			Borrower br = new Borrower();
			br.setBorrowerCardNo(rs.getInt("cardNo"));
			br.setBorrowerName(rs.getString("name"));
			br.setBorrowerAddress(rs.getString("address"));
			br.setBorrowerPhone(rs.getInt("phone"));
			borrowers.add(br);
		}
		return borrowers;
	}


}
