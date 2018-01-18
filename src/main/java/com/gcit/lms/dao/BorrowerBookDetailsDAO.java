package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.BorrowerBookDetails;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.BranchBooksDetails;

public class BorrowerBookDetailsDAO extends BaseDAO<BorrowerBookDetails> implements ResultSetExtractor<List<BorrowerBookDetails>>{
	
	public List<BorrowerBookDetails> readBorrowerBooks(Borrower borrower)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return jdbcTemplate.query("SELECT tbl_book.bookId ,tbl_book.title, tbl_book_loans.dateOut, tbl_book_loans.dueDate, tbl_book_loans.branchId FROM library.tbl_book_loans left join tbl_book on tbl_book.bookId= tbl_book_loans.bookId where cardNo=? and dateIn is null", new Object[] { borrower.getBorrowerCardNo() },this);
		
	}
	public void updateloanBookDueDate(BorrowerBookDetails brbd) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_book_loans SET dueDate = ? WHERE bookId = ? and branchId = ? and cardNo= ? and dateOut=?", new Object[] { brbd.getDueDate(), brbd.getBookId(), brbd.getBranchId(), brbd.getBorrower().getBorrowerCardNo(), brbd.getCheckOutDate()});
	}
	//
	//jdbcTemplate.update("INSERT INTO tbl_author (authorName) VALUES (?)", new Object[] { author.getAuthorName()});
		public void insertCheckOutEntry(Integer borrowerCardNo, Integer branchId, Integer bookId)
				throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");Date date = new Date();
			jdbcTemplate.update("INSERT INTO tbl_book_loans  (bookId, branchId, cardNo, dateOut, dueDate) VALUES (?,?,?,?,?)",
					new Object[] { bookId,branchId,borrowerCardNo,dateFormat.format(date),"2018-11-11 00:00:00"  });
		}
	//
	public void updateLoanCheckInDate(BorrowerBookDetails brbd,Borrower borrower )
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		//UPDATE `library`.`tbl_book_loans` SET `dateIn`='asd' WHERE `bookId`='1' and`branchId`='1' and`cardNo`='11230';
		jdbcTemplate.update("UPDATE tbl_book_loans SET dateIn =? WHERE bookId=? and branchId=? and cardNo= ? and dateOut= ?",
				new Object[] { dateFormat.format(date),brbd.getBookId(), brbd.getBranchId(),borrower.getBorrowerCardNo(), brbd.getCheckOutDate() });
	}
	

	@Override
	public List<BorrowerBookDetails> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<BorrowerBookDetails> brbds = new ArrayList<>();
		while(rs.next()){
			BorrowerBookDetails b = new BorrowerBookDetails();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			b.setCheckOutDate(rs.getString("dateOut"));
			b.setDueDate(rs.getString("dueDate"));
			b.setBranchId(rs.getInt("branchId"));
			brbds.add(b);	
		}
		return brbds;
	}
	
}
