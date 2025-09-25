package collection.Library_book_management;

import java.util.ArrayList;


public class Book {
    private int bookId;
    private static int bookCounter = 100;
    private String title;
    private int pages;
    private String author;

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



}
