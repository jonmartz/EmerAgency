package Model;

import java.time.LocalDate;
import java.util.ArrayList;

public class EventFactory {
    public static Event createEvent(String title, Enum.Organization organization,
                                    String publisherID, Category category){

        ArrayList<Enum.Organization> organizations = new ArrayList<>();
        organizations.add(organization);
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(category);

        Model model = Model.getInstance();
        int nextID = model.getAllEvents().size();

        return new Event(nextID, title, LocalDate.now(), Enum.EventState.BEING_HANDLED,
                organizations, publisherID, categories);
    }
}
