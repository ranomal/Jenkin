package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.BranchBooksDetails;

public class BranchBooksDetailsDAO extends BaseDAO<BranchBooksDetails> implements ResultSetExtractor<List<BranchBooksDetails>>{
	
	public List<BranchBooksDetails> readBooksDetails(Branch branch)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return jdbcTemplate.query("SELECT  tbl_book.bookId,title, noOfCopies, tbl_book_copies.branchId FROM tbl_book left join tbl_book_copies on tbl_book.bookId= tbl_book_copies.bookId WHERE tbl_book_copies.branchId=?", new Object[] { branch.getBranchId() },this);
	}
	
	
	
	public void updateBookCopies(BranchBooksDetails bbd) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_book_copies SET noofCopies = ? where bookId = ? and branchId = ?", new Object[] {bbd.getCopiesAvailable(),bbd.getBookId(), bbd.getBranchId()});
	}
	
	@Override
	public List<BranchBooksDetails> extractData(ResultSet rs) throws SQLException {
		List<BranchBooksDetails> branchBooksDetails = new ArrayList<>();
		while (rs.next()) {
			BranchBooksDetails a = new BranchBooksDetails();
			a.setBookId(rs.getInt("bookId"));
			a.setTitle(rs.getString("title"));
			a.setCopiesAvailable(rs.getInt("noOfCopies"));
			a.setBranchId(rs.getInt("branchId"));
			branchBooksDetails.add(a);
		}
		return branchBooksDetails;
	}

	
}
