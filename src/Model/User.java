package Model;

public class User {
    public String id;
    public String mail;
    public String password;
    public String status;
    public Enum.Organization organization;
    // type is deduced from organization

    public User(String id, String mail, String password, String status, Enum.Organization organization) {
        this.id = id;
        this.mail = mail;
        this.password = password;
        this.status = status;
        this.organization = organization;
    }
}
