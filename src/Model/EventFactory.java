package Model;

import java.util.ArrayList;
import java.util.Date;

public class EventFactory {
    private static int nextID = 0;

    public static Event createEvent(String title, Enum.Organization.SecurityForces organization,
                                    String publisherID, Category category){

        ArrayList<Enum.Organization.SecurityForces> organizations = new ArrayList<>();
        organizations.add(organization);
        ArrayList<Category> categories = new ArrayList<Category>();
        categories.add(category);

        return new Event(nextID++, title, new Date(), Enum.EventState.BEING_HANDLED,
                organizations, publisherID, categories);
    }
}
