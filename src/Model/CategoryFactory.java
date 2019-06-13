package Model;

public class CategoryFactory {
    private static int nextID = 0;

    public static Category createCategory(String name){
        return new Category(nextID++, name);
    }
}
