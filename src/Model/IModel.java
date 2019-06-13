package Model;

public interface IModel {
    User getUser(String userID);
    Event getEvent(int id);
    Category getCategory(int id);
}
