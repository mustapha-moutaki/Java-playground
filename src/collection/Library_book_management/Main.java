package collection.Library_book_management;

public class Main {
    public static void main(String[] args) {
        Book book1 = new Book("never die", 120, "mustapha");
        Book book2 = new Book("let meet one day", 0, "noOne");
        Book book3 = new Book("c++ stories", 1200, "james goodman");
        Book book4 = new Book("the biginning of the end", 170, "kari sara mari");

        BookImpl bookMaker = new BookImpl();
        bookMaker.AddBook(book1);// add a book
        bookMaker.AddBook(book2);// add a book
        bookMaker.AddBook(book3);// add a book
        bookMaker.AddBook(book4);// add a book

        System.out.print("------total books : ");
        System.out.println(bookMaker.showTotalBooks());
        System.out.println("---------------end-----------");

        System.out.print("------filter books : ");
        System.out.println(bookMaker.booksListWithFilter(300));
        System.out.println("---------------end -------");
        System.out.print("------search by author : ");
        System.out.println(bookMaker.searchByAuthor("mustapha"));
        System.out.println("-----------end ---------");
        System.out.print("------all books : ");
        bookMaker.displayAllbooks();
        System.out.println("-------------end ---------");



    }
}
