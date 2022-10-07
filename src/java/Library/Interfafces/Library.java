package Library.Interfafces;

import Library.enums.Category;

import java.sql.*;
import java.util.Scanner;

public class Library {
    public  static void main(String[] args) throws SQLException{
        Connection connection =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "sadomazo911");

//        initializeScanner(connection);

//        addBook(connection, "Queen", "Dana White", 12, Category.HISTORICAL_FICTION);

//        findBook(connection, "Queen", "Dana White");

//        removeBook(connection, "Queen", "Dana White");

//        sortBooksByTitle(connection);

//        sortBooksByAuthor(connection);

    }

//    public static void borrowBook(Connection connection, String title, String author) throws SQLException{
//        ResultSet rs = searchOfBook(connection, title, author);
//    }

    public static void addBook(Connection connection, String title, String author, int ageLimit, Category category) throws SQLException{
        if(isBookPresent(connection, title, author)) {System.out.println("This book already exist");  return;}

        Book book = new Book(title, author, ageLimit, category);
        int category_id = getCategoryId(connection, category);

        addToDatabase(connection, book, category_id);
    }

    public static void findBook(Connection connection, String title, String author) throws SQLException{
        if(isBookPresent(connection, title, author)) {
            ResultSet rs = searchOfBook(connection, title, author);
            outputOnDisplay(rs);
            rs.close();
        }
    }

    public static void removeBook(Connection connection, String title, String author) throws SQLException{
        if(!isBookPresent(connection, title, author)) {System.out.println("This book isn't exist"); return;}

        int bookIndex = 0;
        ResultSet rs = searchOfBook(connection, title, author);
        while (rs.next()){
            bookIndex = rs.getInt("id");
            System.out.println(bookIndex);
        }

        String removeBook = "DELETE FROM library.book WHERE id=?";
        PreparedStatement pStatement = connection.prepareStatement(removeBook);
        pStatement.setInt(1, bookIndex);
        pStatement.executeUpdate();
        pStatement.close();
    }

    public static void sortBooksByTitle(Connection connection) throws SQLException{
        String sortByTitle = "SELECT * FROM library.book ORDER BY Title ASC;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sortByTitle);

        outputOnDisplay(resultSet);
        resultSet.close();
    }

    public static void sortBooksByAuthor(Connection connection) throws SQLException{
        String sortByTitle = "SELECT * FROM library.book ORDER BY Author ASC;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sortByTitle);

        outputOnDisplay(resultSet);
        resultSet.close();
    }

    public static boolean isBookPresent(Connection connection, String title, String author) throws SQLException{
        boolean result = true;
        while(!searchOfBook(connection, title, author).next()) {

            return result = false;
        }

        return result;
    }

    public static ResultSet searchOfBook(Connection connection, String title, String author) throws SQLException {
        String searchOfBook = "SELECT * FROM library.Book WHERE Title=\""+title+"\" AND Author=\""+author+"\"";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(searchOfBook);

        return resultSet;
    }
    public static void addToDatabase(Connection connection, Book book, int category_id) throws SQLException {
        String addNewBook = "INSERT INTO library.Book(Title, Author, Borrowed, AgeLimit, Category_id) VALUES (?, ?, ?, ?, ?);";
        PreparedStatement pStatement = connection.prepareStatement(addNewBook);
        pStatement.setString(1, book.getTitle());
        pStatement.setString(2, book.getAuthor());
        pStatement.setBoolean(3, book.getBorrowed());
        pStatement.setInt(4, book.getAgeLimit());
        pStatement.setInt(5, (category_id));
        pStatement.executeUpdate();
        pStatement.close();
    }
    public static int getCategoryId(Connection connection, Category category) throws SQLException {
        String getCategoryIndex = "SELECT id FROM library.Category WHERE Category='"+category.getReadableName()+"';";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getCategoryIndex);
        resultSet.next();

        return resultSet.getInt(1);
    }

    public static void outputOnDisplay(ResultSet rs) throws SQLException{
        while (rs.next()){
            System.out.println("Title: "+rs.getString("title")+
                    "\nAuthor: "+rs.getString("author")+"\n");
        }
    }

    public static void initializeScanner(Connection connection) throws SQLException {
        Category category = Category.OTHER;
        Scanner helloWorld = new Scanner(System.in);
        System.out.println("Enter title of the book: ");
        String title = helloWorld.nextLine();

        System.out.println("Enter book author: ");
        String author = helloWorld.nextLine();

        System.out.println("Age limit: ");
        int ageLimit = helloWorld.nextInt();
        helloWorld.nextLine();
        System.out.println("Enter number of category:\n 1. ADVENTURE_STORIES" +
                " \n 2. CLASSICS \n 3. FAIRY_TALES" +
                " \n 4. FANTASY \n 5. HISTORICAL_FICTION");
        String strCategory = helloWorld.nextLine();

        switch (strCategory) {
            case "1":
                category = Category.ADVENTURE_STORIES;
                break;
            case "2":
                category = Category.CLASSICS;
                break;
            case "3":
                category = Category.FAIRY_TALES;
                System.out.println(category.getReadableName());
                break;
            case "4":
                category = Category.FANTASY;
                break;
            case "5":
                category = Category.HISTORICAL_FICTION;
                break;
        }

        System.out.println("Book is: " + title + " written by " + author + " \nCategory is: "+category.getReadableName());

        addBook(connection, title, author, ageLimit, category);
    }
}
