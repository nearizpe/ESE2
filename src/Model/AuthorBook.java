package Model;

import Book.Book;

import java.math.BigDecimal;

public class AuthorBook {
	Author author ;
	Book book;
	BigDecimal royalty; //which is the royalty amount multiplied by 100000. why???)
	
	public AuthorBook(){
		
	}

	@Override
	public String toString(){
		return this.getAuthor().getFirstName().getValue() + " " + this.getAuthor().getLastName().getValue() + "		" + this.getRoyalty() + "%";
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
	public BigDecimal getRoyalty() {
		return royalty;
	}
	public void setRoyalty(BigDecimal royalty) {
		this.royalty = royalty;
	}
	public boolean isNewRecord() {
		return newRecord;
	}
	public void setNewRecord(boolean newRecord) {
		this.newRecord = newRecord;
	}
	boolean newRecord = true; //defaults to True, set to False when instantiated using data from a database record
	public boolean isValidRoyalty(){if(this.royalty.compareTo(new BigDecimal(0)) != -1 && this.royalty.compareTo(new BigDecimal(1)) != 1){ return true;}else return false;}
}
