import java.util.List;
import java.util.Map;
import java.util.Objects;
/**
 * Creates guest subclass and methods
 * Final project
 * @author Sam Liu
 * ITP 265, Tea
 * Email: sqliu@usc.edu
 *
 */
public class Guest extends User{
    private Map<String, List<Party>> rsvpStatus;

    public Guest(String name, String email, String password) {
        super(name, email, password);
    }

    public Map<String, List<Party>> getRsvpStatus() {
        return rsvpStatus;
    }

    public void setRsvpStatus(Map<String, List<Party>> rsvpStatus) {
        this.rsvpStatus = rsvpStatus;
    }

    @Override
    public boolean checkPassword(String attempt) {
        return attempt.equals(super.getPassword());
    }
    @Override
    public String toString() {
        return "Guest{" +
                "rsvpStatus=" + rsvpStatus +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Guest guest = (Guest) o;
        return Objects.equals(rsvpStatus, guest.rsvpStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rsvpStatus);
    }
}
