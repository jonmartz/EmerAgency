package Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class EventFactory {
    private static int nextID = 0;

    public static Event createEvent(String title, Enum.Organization organization,
                                    String publisherID, Category category){

        ArrayList<Enum.Organization> organizations = new ArrayList<>();
        organizations.add(organization);
        ArrayList<Category> categories = new ArrayList<Category>();
        categories.add(category);

        return new Event(nextID++, title, LocalDate.now(), Enum.EventState.BEING_HANDLED,
                organizations, publisherID, categories);
    }
}
