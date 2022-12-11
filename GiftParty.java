import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
/**
 * Creates gift party subclass and methods
 * Final project
 * @author Sam Liu
 * ITP 265, Tea
 * Email: sqliu@usc.edu
 *
 */
public class GiftParty extends Party {
    private String guestOfHonor;
    private String favor;


    public GiftParty(String type, String partyName, String hostEmail, List<String> guestList, LocalDate date, double entryFee, String location, List<String> invited, List<String> declined, Map<String, String> giftList, String guestOfHonor, String favor) {
        super(type, partyName, hostEmail, guestList, date, entryFee, location, invited, declined, giftList);
        this.guestOfHonor = guestOfHonor;
        this.favor = favor;
    }

    public String getGuestOfHonor() {
        return guestOfHonor;
    }

    public void setGuestOfHonor(String guestOfHonor) {
        this.guestOfHonor = guestOfHonor;
    }

    public String getFavor() {
        return favor;
    }

    public void setFavor(String favor) {
        this.favor = favor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GiftParty giftParty = (GiftParty) o;
        return Objects.equals(guestOfHonor, giftParty.guestOfHonor) && Objects.equals(favor, giftParty.favor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), guestOfHonor, favor);
    }

    @Override
    public String toString() {
        return "Gift Party! " + super.toString();
    }
}