package Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Event {
    public int id;
    public String title;
    public LocalDate date;
    public Enum.EventState status;
    public ArrayList<Enum.Organization> securityForces;
    public String publisherID;
    public ArrayList<Category> categories;

    Event(int id, String title, LocalDate date, Enum.EventState status,
          ArrayList<Enum.Organization> securityForces, String publisherID, ArrayList<Category> categories) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.status = status;
        this.securityForces = securityForces;
        this.publisherID = publisherID;
        this.categories = categories;
    }
}
