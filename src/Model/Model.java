package Model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Model implements IModel {
    private Connection connection; // to database
    private User currentUser; // user that is currently signed in
    private int connectionStack = 0; // used to not open more than one connection

    public Model() {
        try {
            openConnection();
            Statement statement = connection.createStatement();

            // Create user table
            statement.executeUpdate("create table if not exists users (" +
                    "ID string, " +
                    "mail string, " +
                    "password string, " +
                    "status string, " +
                    "orgID string)");

            //Create messages table
            statement.executeUpdate("create table if not exists events (" +
                    "ID string, " +
                    "title string, " +
                    "date string, " +
                    "status string, " +
                    "OrgID string, " +
                    "publisherID string)");

            //Create course-student table
            statement.executeUpdate("create table if not exists categories (" +
                    "ID string, " +
                    "name string) ");

            //Create courses table
            statement.executeUpdate("create table if not exists eventsCategories (" +
                    "eventID string, " +
                    "CategoryID string) " );

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
    private void openConnection() {
        try {
            if (connectionStack == 0) { // if there's no connection. Else, don't open more connections
                connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            }
            connectionStack++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the connection with the database.
     */
    private void closeConnection() {
        try {
            if (connection != null) {
                if (connectionStack == 1) {
                    // if this function wasn't called from a parent function that opened a connection, and didn't close it yet.
                    connection.close();
                }
                connectionStack--;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public User getUser(String username) {
        User user = null;
        try {
            openConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from users where ID='" + username + "'");
            if (rs.next()) {
                String ID = rs.getString("ID");
                String mail = rs.getString("mail");
                String password = rs.getString("password");
                String status = rs.getString("status");
                String orgID = rs.getString("orgID");
                Enum.Organization orgEnum=null;
                switch (orgID) {
                    case "POLICE":
                        orgEnum = Enum.Organization.POLICE;
                        break;
                    case "REDCROSS":
                        orgEnum = Enum.Organization.REDCROSS;
                        break;
                    case "FIREMEN":
                        orgEnum=Enum.Organization.FIREMEN;

                }
                user = new User(ID,mail,password,status,orgEnum);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return user;
    }

    @Override
    public Event getEvent(int id) {
        Event event = null;
        try {
            openConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from events where ID='" + id + "'");
            if (rs.next()) {
                String ID = rs.getString("ID");
                String title = rs.getString("title");
                String date = rs.getString("date");
                String status = rs.getString("status");
                String orgID = rs.getString("orgID");
                String publisherID = rs.getString("publisherID");
                Enum.EventState o;
                if (status == Enum.EventState.BEING_HANDLED.toString()){
                    o = Enum.EventState.BEING_HANDLED;
                }
                else{
                    o = Enum.EventState.DONE;
                }
                ArrayList<Enum.Organization> orgs = new ArrayList<>();
                for (String s : orgID.split(",")) {
                    switch (s) {
                        case "POLICE":
                            orgs.add(Enum.Organization.POLICE);
                            break;
                        case "REDCROSS":
                            orgs.add(Enum.Organization.REDCROSS);
                            break;
                        case "FIREMEN":
                            orgs.add(Enum.Organization.FIREMEN);

                    }
                }
                ArrayList<Category> cate = new ArrayList<>();
                ResultSet rs2 = statement.executeQuery("select CategoryID from eventsCategories where eventID='" + id + "'");
                while (rs2.next()){
                    cate.add(getCategory(Integer.parseInt(rs2.getString("CategoryID"))));
                }
                event = new Event(Integer.parseInt(ID),title,LocalDate.parse(date),o,orgs,publisherID,cate);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return event;    }

    @Override
    public Category getCategory(int id) {
        Category c = null;
        try {
            openConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from categories where ID='" + id + "'");
            if (rs.next()) {
                String ID = rs.getString("ID");
                String name = rs.getString("name");
                c = new Category(Integer.parseInt(ID),name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return c;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> c = new ArrayList<>();
        try {
            openConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select ID from categories");
            while (rs.next()) {
                int ID = Integer.parseInt(rs.getString("ID"));
                c.add(getCategory(ID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return c;
    }

    @Override
    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> ev = new ArrayList<>();
        try {
            openConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select ID from events");
            while (rs.next()) {
                int ID = Integer.parseInt(rs.getString("ID"));
                ev.add(getEvent(ID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return ev;
    }

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Override
    public void addCategory(Category category) {
        try {
            openConnection();
            Statement statement = connection.createStatement();
            String command = "insert into categories values(" +
                    "'" + category.id + "', " +
                    "'" + category.name + "'" + ")";
            statement.execute(command);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void addEvent(Event event) {
        String sf = "";
        for (Enum.Organization enu: event.securityForces){
            sf += ',' + enu.toString();
        }
        try {
            openConnection();
            Statement statement = connection.createStatement();
            String command = "insert into events values(" +
                    "'" + event.id + "', " +
                    "'" + event.title + "', " +
                    "'" + event.date + "', " +
                    "'" + event.status + "', " +
                    "'" + sf + "', " +
                    "'" + event.publisherID + "'" + ")";
            statement.executeUpdate(command);

            for (Category c : event.categories ) {
                String addctgr = "insert into eventsCategories values(" +
                        "'" + event.id + "', " +
                        "'" + c.id + "'" + ")";
                statement.executeUpdate(command);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

//    @Override
//    public void addUser(User user) {
//        try {
//            openConnection();
//            Statement statement = connection.createStatement();
//            String command = "insert into users values(" +
//                    "'" + user.username + "', " +
//                    "'" + user.mail + "', " +
//                    "'" + user.password + "', " +
//                    "'" + user.status + "', " +
//                    "'" + user.organization + "', " +
//                    "'" + user.type + "'" + ")";
//            statement.executeUpdate(command);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            closeConnection();
//        }
//    }
}
