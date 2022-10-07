package Library.Interfafces;

import Library.enums.Category;

public interface IBook {
    public String title = "";
    public String author = "";
    public Category category = null;
    public boolean borrowed = true;
    public int ageLimit = 0;

  public void setCategory(Category category);

  public void borrow();
  public void putBack();

}
