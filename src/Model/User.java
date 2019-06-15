package Model;

public class User {
    public String username;
    public String mail;
    public String password;
    public String status;
    public Enum.Organization organization;
    // type is deduced from organization

    public User(String username, String mail, String password, String status, Enum.Organization organization) {
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.status = status;
        this.organization = organization;
    }

    public User(String username, String mail, String password, String status, Enum.Organization.SecurityForces organization) {
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.status = status;
        this.organization = organization;
    }
}
