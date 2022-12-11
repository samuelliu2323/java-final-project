import java.io.Serializable;
import java.util.Objects;
/**
 * Creates User abstract parent class
 * Final project
 * @author Sam Liu
 * ITP 265, Tea
 * Email: sqliu@usc.edu
 *
 */
public abstract class User implements Serializable {
    private String name;
    private String email;
    private String password;


    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkPassword(String attempt) {
        return attempt.equals(password);
    }
    public boolean updatePassword(String attempt, String newPword) {
        boolean updated = false;
        if(checkPassword(attempt)) {
            this.password = newPword;
            updated = true;

        }
        return updated;
    }


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String toFileString(){
        //return email,name,password
        return email+","+password+","+name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password);
    }
}
