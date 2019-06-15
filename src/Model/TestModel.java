//package Model;
//
//import java.util.ArrayList;
//
//public class TestModel implements IModel {
//    private User currentUser;
//    private ArrayList<Category> categories = new ArrayList<Category>();
//    private ArrayList<Event> events = new ArrayList<Event>();
//
//    @Override
//    public User getUser(String username) {
//        return new User(username, "mail", "1234", "OK", Enum.Organization.HOTLINE);
//    }
//
//    @Override
//    public Event getEvent(int id) {
//        return events.get(id);
//    }
//
//    @Override
//    public Category getCategory(int id) {
//        return categories.get(id);
//    }
//
//    @Override
//    public void addCategory(Category category) {
//        categories.add(category);
//    }
//
//    @Override
//    public User getCurrentUser() {
//        return currentUser;
//    }
//
//    @Override
//    public void setCurrentUser(User user) {
//        currentUser = user;
//    }
//
//    @Override
//    public ArrayList<Category> getAllCategories() {
//        return categories;
//    }
//
//    @Override
//    public ArrayList<Event> getAllEvents() {
//        return events;
//    }
//
//    @Override
//    public void addEvent(Event event) {
//        events.add(event);
//    }
//
//
//}
