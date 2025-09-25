package collection.Library_book_management;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.List;
import java.util.stream.Collectors;
public class BookImpl {
    ArrayList<Book> booksList = new ArrayList<>();
    /**
     * -1-add books
     * @param book
     */
    void AddBook(Book book){
        if(book == null){
            throw new IllegalArgumentException("the book is null");
        } else if (book.getTitle() == null || book.getTitle().isEmpty()) {
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
    List<Book> booksListWithFilter(int pagesNum) {
        List<Book> listFiltered = booksList.stream()
                .filter(book -> book.getPages() > pagesNum)
                .collect(Collectors.toList());

        listFiltered.forEach(book -> System.out.println(book.getTitle()));

        return listFiltered;
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
