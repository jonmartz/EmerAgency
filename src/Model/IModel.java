package Model;

import java.util.ArrayList;

public interface IModel {
    User getUser(String username);
    Event getEvent(int id);
    Category getCategory(int id);
    void addUser(User user);
    void addEvent(Event event);
    void addCategory(Category category);
    User getCurrentUser();
    void setCurrentUser(User user);
    ArrayList<Category> getAllCategories();
    ArrayList<Event> getAllEvents();
}
