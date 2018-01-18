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

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Branch;;

public class BranchDAO extends BaseDAO<Branch>  implements ResultSetExtractor<List<Branch>> {

	public void addBranch(Branch branch)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("INSERT INTO tbl_library_branch (branchName) VALUES (?)", new Object[] { branch.getBranchName() });
		
	}
	
	//
	public Integer addBranchWithID(Branch branch)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		KeyHolder holder = new GeneratedKeyHolder();
		final String sql = "INSERT INTO tbl_library_branch (branchName,branchAddress) VALUES (?,?)";
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, branch.getBranchName());
				ps.setString(2, branch.getBranchAddress());
				return ps;
			}
		}, holder);
		return holder.getKey().intValue();
	}

	public void updateBranch(Branch branch)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_library_branch SET branchName =?, branchAddress = ? WHERE branchId = ?",
				new Object[] { branch.getBranchName(),branch.getBranchAddress(), branch.getBranchId() });
	}
	
	public void updateBranchAddress(Branch branch)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_library_branch SET branchAddress =? WHERE branchId = ?",
				new Object[] { branch.getBranchAddress(), branch.getBranchId() });
	}
	public void updateBranchBookCopies(Integer bookId,Integer branchId, Integer newCopies)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("UPDATE tbl_book_copies SET noOfCopies=? WHERE bookId=? and branchId=?",
				new Object[] { newCopies,bookId,branchId });
	}
	//pagination
		public Integer getBranchCount()
				throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			return jdbcTemplate.queryForObject("SELECT COUNT(*) AS COUNT FROM tbl_library_branch",Integer.class);
		}

	public void deleteBranch(Branch branch)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		jdbcTemplate.update("DELETE FROM tbl_library_branch WHERE branchId = ?", new Object[] { branch.getBranchId() });
	}

	public List<Branch> readAllBranches(Integer pageNo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		if(pageNo!=null) {setPageNo(pageNo);}
		return jdbcTemplate.query("SELECT * FROM tbl_library_branch", this);
	}
	//checkout
	/*public List<Branch> readBranchesCheckOut()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return readCheckOut("SELECT * FROM tbl_library_branch", null);
	}*/
	

	public List<Branch> readBranchsByName(String branchName)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		branchName = "%" + branchName + "%";
		return jdbcTemplate.query("SELECT * FROM tbl_library_branch WHERE branchName LIKE ?", new Object[] { branchName },this);
	}


	@Override
	public List<Branch> extractData(ResultSet rs) throws SQLException{
		List<Branch> branches = new ArrayList<>();
		while (rs.next()) {
			Branch bn = new Branch();
			bn.setBranchId(rs.getInt("branchId"));
			bn.setBranchName(rs.getString("branchName"));
			bn.setBranchAddress(rs.getString("branchAddress"));
			branches.add(bn);
		}
		return branches;
	}
	/*public List<Branch> extractDataCheckOut(ResultSet rs) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		BookDAO bDao = new BookDAO(conn);
		List<Branch> branches = new ArrayList<>();
		while (rs.next()) {
			Branch bn = new Branch();
			bn.setBranchId(rs.getInt("branchId"));
			bn.setBranchName(rs.getString("branchName"));
			bn.setBranchAddress(rs.getString("branchAddress"));
			bn.setBooks(bDao.readFirstLevel("SELECT * FROM tbl_book WHERE bookId IN (SELECT bookId FROM tbl_book_copies WHERE branchId = ?)", new Object[]{bn.getBranchId()}));
			branches.add(bn);
		}
		return branches;
	}*/


}
