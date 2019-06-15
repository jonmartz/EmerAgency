package Model;

public class CategoryFactory {

    public static Category createCategory(String name){
        Model model = Model.getInstance();
        int nextID = model.getAllCategories().size();
        return new Category(nextID, name);
    }
}
