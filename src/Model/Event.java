package Model;

import java.util.ArrayList;

public class Event {
    public int id;
    public String title;
    public String date;
    public Enum.EventState status;
    public ArrayList<Enum.Organization> organizations;
    public String publisherID;
    public ArrayList<Category> categories;

    public Event(int id, String title, String date, Enum.EventState status,
                 ArrayList<Enum.Organization> organizations, String publisherID, ArrayList<Category> categories) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.status = status;
        this.organizations = organizations;
        this.publisherID = publisherID;
        this.categories = categories;
    }
}
