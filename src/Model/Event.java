package Model;

import java.util.ArrayList;
import java.util.Date;

public class Event {
    public int id;
    public String title;
    public Date date;
    public Enum.EventState status;
    public ArrayList<Enum.Organization.SecurityForces> securityForces;
    public String publisherID;
    public ArrayList<Category> categories;

    Event(int id, String title, Date date, Enum.EventState status,
          ArrayList<Enum.Organization.SecurityForces> securityForces, String publisherID, ArrayList<Category> categories) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.status = status;
        this.securityForces = securityForces;
        this.publisherID = publisherID;
        this.categories = categories;
    }
}
