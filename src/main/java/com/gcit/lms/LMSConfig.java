package com.gcit.lms;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookAuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookGenreDAO;
import com.gcit.lms.dao.BorrowerBookDetailsDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.BranchBooksDetailsDAO;
import com.gcit.lms.dao.BranchDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.BorrowerBookDetails;
import com.gcit.lms.service.AdminService;

@Configuration
public class LMSConfig {
	
	@Bean
	public AuthorDAO adao(){
		return new AuthorDAO();
	}
	
	
	@Bean
	public BookDAO bdao(){
		return new BookDAO();
	}
	@Bean
	public BookAuthorDAO badao() {
		return new BookAuthorDAO();
	}
	@Bean
	public PublisherDAO pdao() {
		return new PublisherDAO();
	}
	@Bean
	public GenreDAO gdao() {
		return new GenreDAO();
	}
	@Bean
	public BookGenreDAO bgdao() {
		return new BookGenreDAO();
	}
	@Bean
	public BranchDAO bndao() {
		return new BranchDAO();
	}
	@Bean
	public BranchBooksDetailsDAO bbddao() {
		return new BranchBooksDetailsDAO();
	}
	@Bean
	public BorrowerDAO brdao() {
		return new BorrowerDAO();
	}
	@Bean
	public BorrowerBookDetailsDAO brbddao() {
		return new BorrowerBookDetailsDAO();
	}
	
	@Bean
	public AdminService adminService(){
		return new AdminService();
	}
}
