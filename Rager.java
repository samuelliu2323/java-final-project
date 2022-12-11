import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
/**
 * Creates rager subclass and methods
 * Final project
 * @author Sam Liu
 * ITP 265, Tea
 * Email: sqliu@usc.edu
 *
 */
public class Rager extends Party{
    private String club;
    private String theme;
    private int ageLimit;

    public Rager(String type, String partyName, String hostEmail, List<String> guestList, LocalDate date, double entryFee, String location, List<String> invited, List<String> declined, Map<String, String> giftList, String club, String theme, int ageLimit) {
        super(type, partyName, hostEmail, guestList, date, entryFee, location, invited, declined, giftList);
        this.club = club;
        this.theme = theme;
        this.ageLimit = ageLimit;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit) {
        this.ageLimit = ageLimit;
    }

    @Override
    public String toString() {
        return "Rager! " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Rager rager = (Rager) o;
        return ageLimit == rager.ageLimit && Objects.equals(club, rager.club) && Objects.equals(theme, rager.theme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), club, theme, ageLimit);
    }
}
