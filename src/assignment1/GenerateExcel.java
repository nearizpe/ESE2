package assignment1;

import java.io.File;
import java.util.ArrayList;

import Book.Book;
import Model.AuthorBook;
import jxl.*;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class GenerateExcel {
	
	//GenerateExcel y = new GenerateExcel();
	//y.generateExcel("/Users/kevingonzales/Desktop/School/EnterpriseSoftware","Publisehr","Date",LIST);
	public void generateExcel(String path, String pubName, String date, ArrayList<Book> bookList){
		try{
			
			String filename = path+"/RoyaltyReport.xls";
			
			WritableWorkbook workbook = Workbook.createWorkbook(new File(filename));
			WritableSheet sheet = workbook.createSheet("Sheet1", 0);
			
			//bold stuff
			WritableFont arial15ptBold = new WritableFont(WritableFont.ARIAL, 15, WritableFont.BOLD);
			WritableCellFormat arial15BoldFormatNoBorder = new WritableCellFormat(arial15ptBold);
			
			WritableFont arial10ptBold = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
			WritableCellFormat arial10BoldFormatNoBorder = new WritableCellFormat(arial10ptBold);
			
			//Adding Labels
			Label TitleLabel = new Label(0,0,"RoyaltyReport",arial15BoldFormatNoBorder); //position of cell and content of cell
			Label PublisherNameLabel = new Label(0,1,"Publisher: "+pubName,arial15BoldFormatNoBorder);
			Label dateLabel = new Label(0,2,"Report	generated on : "+date,arial10BoldFormatNoBorder);
			Label BookTittleLabel = new Label(0,4,"Book Titles dsdsdsd",arial10BoldFormatNoBorder);
			Label BookISNLabel = new Label(1,4,"ISBN",arial10BoldFormatNoBorder);
			Label BookAuthorLabel = new Label(2,4,"Author",arial10BoldFormatNoBorder);
			Label BookRoyaltyLabel = new Label(3,4,"Royalty",arial10BoldFormatNoBorder);
		

			
			sheet.addCell(TitleLabel);
			sheet.addCell(PublisherNameLabel);
			sheet.addCell(dateLabel);
			sheet.addCell(BookTittleLabel);
			sheet.addCell(BookISNLabel);
			sheet.addCell(BookAuthorLabel);
			sheet.addCell(BookRoyaltyLabel);

			int i = 5; //starts at 5
			for(Book temp: bookList){
				Label bookTittle = new Label(0,i,temp.getTitle().getValue());
				Label bookISBN = new Label(1,i,temp.getIsbn().getValue());

				sheet.addCell(bookTittle);
				sheet.addCell(bookISBN);

				//now loop through the authors and their royalty
				ArrayList<AuthorBook> authors = temp.getAuthors(temp);
				for(AuthorBook authorsTemp: authors){
					Label authorTittle = new Label(2,i,authorsTemp.getAuthor().getFirstName().getValue());
					Number number = new Number (0,i,authorsTemp.getRoyalty().doubleValue());
					
					sheet.addCell(authorTittle);
					sheet.addCell(number);
					i++;
				}
				i++;				
			}
			//number stuff
			//Number number = new Number (0,3,3.334);
			//sheet.addCell(number);
			
			//to auto fit 
			for(int x=0;x<4;x++)
			{
				CellView cell=sheet.getColumnView(x);
			    cell.setAutosize(true);
			    sheet.setColumnView(x, cell);
			}

			
			workbook.write();
			workbook.close();
		}
		catch(Exception e){
			
		}
	}
}
