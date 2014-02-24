package SC13Project.Milestone1.BookStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import SC13Project.Milestone1.BookStore.JaxbComponent.CategoryInfo;
import SC13Project.Milestone1.BookStore.JaxbComponent.ObjectFactory;

//Please do not change the name of the package or this interface
//Please add here your implementation
public class BookStoreImpl implements BookCategoryWS {
	private JAXBContext jaxbContext;
	private Unmarshaller u;
	private CategoryInfo bs;
	private JAXBElement<?> bsCategoryElement;
	private List<SC13Project.Milestone1.BookStore.JaxbComponent.BookInfo> bookInfo;
	private static List<BookInfo> bookInfoList;

	public BookStoreImpl() {
		try {
			this.jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			// create an Unmarshaller
			this.u = jaxbContext.createUnmarshaller();

			// unmarshal a bs instance document into a tree of Java content
			// objects composed of classes from the
			// SC13Project.Milestone1.BookStore.JaxbComponent package
			String filePath="../datasource/ds_45_1.xml";
			File dbFile = new File(filePath);
			if (dbFile.exists()) {
				this.bsCategoryElement = (JAXBElement<?>) u
						.unmarshal(new FileInputStream(dbFile));

			} else {
				System.out
						.println("Database File could not be found in"+filePath+" path."
								+ "\nPlease Check if the Input XML is in right folder");
				throw new FileNotFoundException();
			}
			ProcessXMLDb();
		} catch (JAXBException e) {

			e.printStackTrace();
		} catch (FileNotFoundException e) {

			System.out.println("DataBaseXML not found");
			e.printStackTrace();
		}

	}

	private void ProcessXMLDb() {
		// Initialize Unmarshaler and get Values
		bs = (CategoryInfo) bsCategoryElement.getValue();
		bookInfo = bs.getBook();

		// Variable to hold and set BookInfo
		String title;
		String resourceID;
		String isbn13;
		String isbn10;
		AuthorsInfo authors;
		int pageNum;
		String publisher;
		String publishdate;
		String version;
		double price;
		// Initialis BookInfo Array to put the objects
		bookInfoList = new ArrayList<BookInfo>();

		for (SC13Project.Milestone1.BookStore.JaxbComponent.BookInfo bInfo : bookInfo) {
			// Get the data from XML
			title = bInfo.getTitle();
			resourceID = bInfo.getResourceID();
			isbn13 = bInfo.getISBN13();
			isbn10 = bInfo.getISBN10();
			authors = new AuthorsInfo();
			authors.setAuthorlist((ArrayList<String>) bInfo.getAuthors()
					.getAuthor());
			pageNum = bInfo.getPageNum();
			publisher = bInfo.getPublisher();
			publishdate = bInfo.getPublishdate();
			version = bInfo.getVersion();
			price = bInfo.getPrice();
			System.out.println("***Success***");
			System.out.println(title + ":" + isbn13 + ":" + price);
			// Set it into BookInfoList
			BookInfo book = new BookInfo();
			book.setTitle(title);
			book.setResourceID(resourceID);
			book.setISBN13(isbn13);
			book.setISBN10(isbn10);
			book.setAuthors(authors);
			book.setPageNum(pageNum);
			book.setPublisher(publisher);
			book.setPublishdate(publishdate);
			book.setVersion(version);
			book.setPrice(price);

			bookInfoList.add(book);
		}

	}

	@Override
	public List<String> getAllBooKNames() {
		List<String> result = new ArrayList<String>();
		for (BookInfo book : bookInfoList) {
			result.add(book.getTitle());
		}
		if (result.isEmpty()) {
			new UnAvailableException("Not a single Book found on Database");
		}
		return result;
	}

	@Override
	public List<String> getAllBookISBN10() {
		// iterate over List
		List<String> result = new ArrayList<String>();
		for (BookInfo book : bookInfoList) {
			result.add(book.getISBN10());
		}
		if (result.isEmpty()) {
			new UnAvailableException("Not a single ISBN10 found on Database");
		}
		return result;

	}

	@Override
	public List<String> getAllBookISBN13() {
		// iterate over List
		List<String> result = new ArrayList<String>();
		for (BookInfo book : bookInfoList) {
			result.add(book.getISBN13());
		}
		if (result.isEmpty()) {
			new UnAvailableException("Not a single ISBN13 found on Database");

		}
		return result;
	}

	@Override
	public List<BookInfo> getBooksByTitle(String title) {
		// iterate over List
		List<BookInfo> result = new ArrayList<BookInfo>();
		for (BookInfo book : bookInfoList) {
			if (title.equalsIgnoreCase(book.getTitle())) {
				result.add(book);
			}
		}
		if (result.isEmpty()) {
			new UnAvailableException("Book with the title: " + title
					+ " not found on Database");
		}
		return result;
	}

	@Override
	public List<BookInfo> getBooksByAuthor(String author) {
		List<BookInfo> result = new ArrayList<BookInfo>();
		for (BookInfo book : bookInfoList) {
			if (book.getAuthors().getAuthorlist().contains(author)) {
				result.add(book);
			}
		}
		if (result.isEmpty()) {
			new UnAvailableException("Book by the Author: " + author
					+ " not found on Database");
		}
		return result;
	}

	@Override
	public BookInfo getBookInfobyISBN10(String isbn10) {
		for (BookInfo book : bookInfoList) {
			if (isbn10.equalsIgnoreCase(book.getISBN10())) {
				return book;
			}
		}
		new UnAvailableException("Book with the ISBN10: " + isbn10
				+ " not found on Database");
		return null;
	}

	@Override
	public BookInfo getBookInfobyISBN13(String isbn13) {
		for (BookInfo book : bookInfoList) {
			if (isbn13.equalsIgnoreCase(book.getISBN13())) {
				return book;
			}
		}
		new UnAvailableException("Book with the ISBN13: " + isbn13
				+ " not found on Database");
		return null;
	}

}
