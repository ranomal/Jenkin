package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.BorrowerBookDetails;
import com.gcit.lms.entity.Branch;
import com.gcit.lms.entity.BranchBooksDetails;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.Publisher;

@RestController
public class AdminService {

	@Autowired
	AuthorDAO adao;
	
	@Autowired
	BookDAO bdao;
	
	@Autowired
	BookAuthorDAO badao;
	
	@Autowired
	PublisherDAO pdao;
	
	@Autowired
	GenreDAO gdao;
	
	@Autowired
	BookGenreDAO bgdao;
	@Autowired
	BranchBooksDetailsDAO bbddao;
	@Autowired
	BranchDAO bndao;
	@Autowired
	BorrowerDAO brdao;
	@Autowired
	BorrowerBookDetailsDAO brbddao;
	
	@RequestMapping (value ="/rsaveAuthor", method= RequestMethod.POST, consumes= "application/json")
	public String saveAuthor(@RequestBody Author author) {
		try {
			if (author.getAuthorId() != null) {
				adao.updateAuthor(author);
			} else {
				adao.addAuthor(author);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return "Author add Fail";
		}
		return "Author added Sucessfully";
	}
	@RequestMapping (value ="/rsaveBorrower", method= RequestMethod.POST, consumes= "application/json")
	public void saveBorrower(@RequestBody Borrower borrower) {
		try {
			if (borrower.getBorrowerCardNo() != null) {
				brdao.updateBorrower(borrower);
			} else {
				brdao.addBorrowerWithID(borrower);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping (value ="/rsaveBook", method= RequestMethod.POST, consumes= "application/json")
	public void saveBook(@RequestBody Book book) {
		try {
			if (book.getBookId() != null) {
				bdao.updateBook(book);
			} else {
				bdao.addBook(book);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	//
	@RequestMapping (value ="/rupdateBranch", method= RequestMethod.POST, consumes= "application/json")
	public void saveBranchName(@RequestBody Branch branch) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		try {
			if(branch.getBranchId()!=null){
				bndao.updateBranch(branch);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
	}
	@RequestMapping (value ="/rupdateBorrower", method= RequestMethod.POST, consumes= "application/json")
	public void saveBorrowerName(@RequestBody Borrower borrower) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		try {
			if(borrower.getBorrowerCardNo()!=null){
				brdao.updateBorrower(borrower);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
	}
	//
	@RequestMapping (value ="/rupdateBookCopies", method= RequestMethod.POST, consumes= "application/json")
	public void updateBookCopies(@RequestBody BranchBooksDetails bbd) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		try {
				bbddao.updateBookCopies(bbd);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
	}
	//
	
	public void returnBook(BorrowerBookDetails brbd,Borrower borrower ) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
				brbddao.updateLoanCheckInDate(brbd,borrower);
	}
	@RequestMapping (value ="/rsavePublisher", method= RequestMethod.POST, consumes= "application/json")
	public void savePublisher(@RequestBody Publisher publisher) {
		try {
			if (publisher.getPublisherId() != null) {
				pdao.updatePublisher(publisher);
			} else {
				pdao.addPublisher(publisher);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	//
	@RequestMapping (value ="/rupdateBookPubid", method= RequestMethod.POST, consumes= "application/json")
	public void saveBookPubId(@RequestBody Book book) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		try {
			if(book.getBookId()!=null){
				bdao.updateBookPubid(book);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
	}
	//
	@RequestMapping (value ="/rsaveBookWithIDPubId", method= RequestMethod.POST, consumes= "application/json")
	public Integer saveBookWithIDPubId(@RequestBody Book book) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Integer id=null;
		try {
			if(book.getBookId()!=null){
				bdao.updateBook(book);
			}else{
				id= bdao.addBookWithIDPubId(book);
			}
			return id;
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return id;
		} 
	}
	//
	@RequestMapping (value ="/rupdateloanBookDueDate", method= RequestMethod.POST, consumes= "application/json")
	public void updateloanBookDueDate(@RequestBody BorrowerBookDetails brbd) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		brbddao.updateloanBookDueDate(brbd);
	}
	//
	@RequestMapping (value ="/raddBookBranchCopy/{bookId}/{branchId}", method= RequestMethod.POST, consumes= "application/json")
	public void saveBookBranchCopies(@PathVariable Integer bookId,@PathVariable Integer branchId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		try {
				bdao.addBookBranchCopy(bookId,branchId);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
		}
	}
	//
	@RequestMapping (value ="/rsaveBranchWithID", method= RequestMethod.POST, consumes= "application/json")
	public Integer saveBranchWithID(@RequestBody Branch branch) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Integer id=null;
		try {
			if(branch.getBranchId()!=null){
				bndao.updateBranch(branch);
			}else{
				id= bndao.addBranchWithID(branch);
			}
			return id;
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return id;
		} 
	}
	@RequestMapping (value ="/rsaveAuthorWithID", method= RequestMethod.POST, consumes= "application/json")
	public Integer saveAuthorWithID(@RequestBody Author author) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Integer id=null;
		try {
			if(author.getAuthorId()!=null){
				adao.updateAuthor(author);
			}else{
				id= adao.addAuthorWithID(author);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			return id;
		}
	}
	@RequestMapping (value ="/rsavePublisherWithID", method= RequestMethod.POST, consumes= "application/json")
	public Integer savePublisherWithID(@RequestBody Publisher publisher) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Integer id=null;
		try {
			if(publisher.getPublisherId()!=null){
				pdao.updatePublisher(publisher);
			}else{
				id= pdao.addPublisherWithID(publisher);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally{
			return id;
		}
	}
	@RequestMapping (value ="/rsaveBookAuthor/{authorId}/{bookId}", method= RequestMethod.POST, consumes= "application/json")
	public void saveBookAuthor(@PathVariable Integer authorId,@PathVariable Integer bookId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		try {
			if(bookId!=null&& authorId!=null){
				badao.addBookAuthor(authorId, bookId);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	//
	@RequestMapping (value ="/rsaveBookGenre/{genreId}/{bookId}", method= RequestMethod.POST, consumes= "application/json")
	public void saveBookGenre(@PathVariable Integer genreId,@PathVariable Integer bookId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		try {
			if(bookId!=null&& genreId!=null){
				bgdao.addBookGenre(genreId, bookId);
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping (value ="/deleteAuthor", method= RequestMethod.POST, consumes= "application/json")
	public void deleteAuthor(@RequestBody Author author)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		adao.deleteAuthor(author);
	}
	@RequestMapping (value ="/rdeleteBorrower", method= RequestMethod.POST, consumes= "application/json")
	public void deleteBorrower(@RequestBody Borrower br)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		brdao.deleteBorrower(br);
	}
	@RequestMapping (value ="/rdeleteBranch", method= RequestMethod.POST, consumes= "application/json")
	public void deleteBranch(@RequestBody Branch branch)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		bndao.deleteBranch(branch);
	}
	@RequestMapping (value ="/rdeletePublisher", method= RequestMethod.POST, consumes= "application/json")
	public void deletePublisher(@RequestBody Publisher publisher)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		pdao.deletePublisher(publisher);
	}
	@RequestMapping (value ="/rdeleteBook", method= RequestMethod.POST, consumes= "application/json")
	public void deleteBook(@RequestBody Book book)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		bdao.deleteBook(book);
	}
	
	public void addBook(Book book)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		book.setBookId(bdao.addBookWithID(book));
		bdao.addBookAuthors(book);
		// do for genre
		// publisher

	}
	
	@RequestMapping (value ="/rreadAuthors/{pageNo}/{searchString}")
	//@PathVariable Integer pageNo,@PathVariable  String searchString
	public List<Author> readAuthors(@PathVariable Integer pageNo,@PathVariable  String searchString)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Author> authors = new ArrayList<>();
		if (searchString != null) {
			authors = adao.readAuthorsByName(searchString);
		}else{
			authors = adao.readAllAuthors(pageNo);
		}
		for(Author a: authors){
			a.setBooks(bdao.readAllBooksByAuthorID(a.getAuthorId()));
		}
		return authors;
	}
	
	@RequestMapping (value ="/rreadGenres", produces= "application/json")
	public List<Genre> readGenres()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Genre> genres = new ArrayList<>();
			genres = gdao.readAllGenres();
		for(Genre g: genres){
//			g.setBooks(bdao.readAllBooksByAuthorID(a.getAuthorId()));
		}
		return genres;
	}
	//
	@RequestMapping (value ="/rreadBranches/{pageNo}/{searchString}", method= RequestMethod.POST, produces= "application/json")
	public List<Branch> readBranches(@PathVariable Integer pageNo,@PathVariable String searchString) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		if(searchString!=null) {
			return bndao.readBranchsByName(searchString);
		}
		else {
			return bndao.readAllBranches(pageNo);}
	}
	//viewBranchBooks
	public Branch viewBranchBooks(Branch branch) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		branch.setBranchBooksDetails(bbddao.readBooksDetails(branch));
		List<BranchBooksDetails> bbds=branch.getBranchBooksDetails();
		if(bbds!=null) {
		for(BranchBooksDetails bbd: bbds){
			bbd.setBorrowers(brdao.readBorrowersByBook(bbd.getBookId(),bbd.getBranchId()));
			bbd.setCopiesBorrowed(bbd.getBorrowers().size());
		}}
		//bndao.viewBranchBooks(branch);
		return branch;
	}
	//viewBorrowerBooks
	public List<BorrowerBookDetails> viewBorrowerBooks(Borrower borrower) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		return brbddao.readBorrowerBooks(borrower);
	}
	
	//
	public List<BranchBooksDetails> availableCheckoutBooks(Integer branchId) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		Branch bn= new Branch();
		bn.setBranchId(branchId);
		return bbddao.readBooksDetails(bn);
	}
	//
	public void checkOutBook(Integer borrowerCardNo, Integer branchId, Integer bookId) {
		try {
			brbddao.insertCheckOutEntry(borrowerCardNo,branchId,bookId);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping (value ="/rreadBooks/{pageNo}/{searchString}", method= RequestMethod.POST, produces= "application/json")
	public List<Book> readBooks(@PathVariable Integer pageNo,@PathVariable  String searchString) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		List<Book> books = new ArrayList<>();
		if(searchString!=null) {
			books=bdao.readBooksByName(searchString);
		}
		else {
		books=bdao.readAllBooks(pageNo);}
		for(Book b: books){
			b.setPublisher(pdao.readPublisherByBookID(b.getBookId()));
			b.setAuthors(adao.readAuthorByBookID(b.getBookId()));
			b.setGenres(gdao.readAuthorByBookID(b.getBookId()));
		}
		return books;
	}
	//
	@RequestMapping (value ="/rreadBorrower", produces= "application/json")
	public List<Borrower> readBorrower() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		return brdao.readAllBorrowers();
	}
	
	public List<Borrower> readBorrowerByCardNo(Borrower br) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		return brdao.readBorrowerByCardNo(br);
	}
	@RequestMapping (value ="/rreadPublishers/{pageNo}/{searchString}",  produces= "application/json")
	public List<Publisher> readPublishers(@PathVariable Integer pageNo,@PathVariable  String searchString) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		List<Publisher> publishers = new ArrayList<>();
		if(searchString!=null) {
			publishers=pdao.readPublishersByName(searchString);
		}
		else {
			publishers=pdao.readAllPublisher(pageNo);}
		for(Publisher p: publishers){
			p.setBooks(bdao.readAllBooksByPublisherID(p.getPublisherId()));
			//b.setAuthors(adao.readAuthorByBookID(b.getBookId()));
			//b.setGenres(gdao.readAuthorByBookID(b.getBookId()));
		}
		return publishers;
	}
	public List<Book> readBooksPubNull() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		return bdao.readAllBooksPubNull();
	}

	public Author readAuthorByPk(Integer authorId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return adao.readAuthorByPK(authorId);
	}

	public Integer getAuthorsCount()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return adao.getAuthorsCount();
	}
	public Integer getBranchesCount()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return bndao.getBranchCount();
	}
	public Integer getBooksCount() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		return bdao.getBooksCount();
	}
	public Integer getPublishersCount() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		return pdao.getPublisherCount();
	}

}
