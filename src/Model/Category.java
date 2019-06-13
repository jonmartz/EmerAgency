package Model;

public class Category {
    public int id;
    public String name;

    Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
