package collection.Library_book_management;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Book {
    private int bookId;
    private static int bookCounter = 100;
    private String title;
    private int pages;
    private String author;
    ArrayList<Book>booksList = new ArrayList<>();
    public Book(String title, int pages, String author){
        this.bookId = ++bookCounter;// autoIncrement
        this.title = title;
        this.pages = pages;
        this.author =  author;
    }


    // getters
    public void setBookId(int id){
        this.bookId = bookId;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setPages(int pages){
        this.pages = pages;
    }
    public void setAuthor(String author){
        this.author = author;
    }

    public int getBookId(){
        return this.bookId;
    }
    public String getTitle(){
        return this.title;
    }
    public int getPages(){
       return this.pages;
    }
    public String getAuthor(){
        return this.author;
    }

    /**
     * -1-add books
     * @param book
     */
    void AddBook(Book book){
        if(book == null){
            throw new IllegalArgumentException("the book is null");
        } else if (book.title == null || book.getTitle().isEmpty()) {
            throw new IllegalArgumentException("book must have title");
        }
        booksList.add(book);
    }


    /**
     * -2-display all books
     */
    void displayAllbooks(){
        if (!booksList.isEmpty()) {
            for (Book book : booksList){
                System.out.println(book.getBookId());
                System.out.println(book.getTitle());
                System.out.println(book.getPages());
                System.out.println(book.getAuthor());
            }
        }else{
            System.out.println("no books available");
        }
    }


    /**
     * -3- show total books
     * @return
     */
    int showTotalBooks(){
        return booksList.size();
    }


    /**
     * -4-filter books by number of pages
     */
    List<Book>BooksListWithFilter(int pagesNum){
        return booksList.stream().
                filter(book -> book.getPages() > pagesNum).collect(Collectors.toList()).forEach(book-> System.out.println(book.getTitle()));
    }


    /**
     * -5- update book by title
     */
    void updateBookByTitle(int id,  String newTitle){
        Book book = booksList.get(id);
        if( book== null){
            System.out.println("book not found");
        }else{
            book.setTitle(newTitle);
            System.out.println("title updated success");
        }

    }


    /**
     * remove the book by id
     */

    void removeBookById(int id){
        booksList.remove(id);
    }


    Book searchByAuthor(String author){
      return booksList.stream().filter(book -> book.getAuthor().equals(author)).findFirst().orElse(null);
    }


}
