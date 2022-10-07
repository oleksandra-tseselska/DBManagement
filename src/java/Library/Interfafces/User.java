package Library.Interfafces;

public class User implements IUser{
    private String name = "";
    private int age = 0;
    private boolean active = true;

    public User (String name, int age){
        this.name = name;
        this.age = age;

    }

    public void suspend(){
        this.active = false;
    }
}
