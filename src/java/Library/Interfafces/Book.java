package Library.Interfafces;

import Library.enums.Category;

public class Book implements IBook{
    private String title;
    private String author;
    private Category category;
    private boolean borrowed;
    private int ageLimit = 0;

    public Book(String title, String author, int ageLimit, Category category){
        this.title = title;
        this.author = author;
        this.ageLimit = ageLimit;
        this.borrowed = false;
        setCategory(category);
    }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public Category getCategory() { return category; }
    public Boolean getBorrowed() { return borrowed; }
    public int getAgeLimit() { return ageLimit; }

    public  void setCategory(Category category){
        this.category = category;
    }

    public void borrow(){
        this.borrowed = true;
    }

    public void putBack(){
        this.borrowed = false;
    }
}
