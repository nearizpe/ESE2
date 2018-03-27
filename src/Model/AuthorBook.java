package Model;

import Book.Book;

public class AuthorBook {
	Author author ;
	Book book;
	int royalty; //which is the royalty amount multiplied by 100000. why???)
	
	public AuthorBook(){
		
	}
	
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getRoyalty() {
		return royalty;
	}
	public void setRoyalty(int royalty) {
		this.royalty = royalty;
	}
	public boolean isNewRecord() {
		return newRecord;
	}
	public void setNewRecord(boolean newRecord) {
		this.newRecord = newRecord;
	}
	boolean newRecord = true; //defaults to True, set to False when instantiated using data from a database record
}
