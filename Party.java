import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
/**
 * Creates party parent class and methods
 * Final project
 * @author Sam Liu
 * ITP 265, Tea
 * Email: sqliu@usc.edu
 *
 */
public abstract class Party implements Serializable {
    private String type;
    private String partyName;
    private String hostEmail;
    private List<String> guestList;
    private LocalDate date;
    private double entryFee;
    private String location;
    private List<String> invited;
    private List<String> declined;

    private Map<String, String> giftList;

    public Party(String type, String partyName, String hostEmail, List<String> guestList, LocalDate date, double entryFee, String location, List<String> invited, List<String> declined, Map<String, String> giftList) {
        this.type = type;
        this.partyName = partyName;
        this.hostEmail = hostEmail;
        this.guestList = guestList;
        this.date = date;
        this.entryFee = entryFee;
        this.location = location;
        this.invited = invited;
        this.declined = declined;
        this.giftList = giftList;
    }

    public Map<String, String> getGiftList() {
        return giftList;
    }

    public void setGiftList(Map<String, String> giftList) {
        this.giftList = giftList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }

    public List<String> getGuestList() {
        return guestList;
    }

    public void setGuestList(List<String> guestList) {
        this.guestList = guestList;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getEntryFee() {
        return entryFee;
    }

    public void setEntryFee(double entryFee) {
        this.entryFee = entryFee;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getInvited() {
        return invited;
    }

    public void setInvited(List<String> invited) {
        this.invited = invited;
    }

    public List<String> getDeclined() {
        return declined;
    }

    public void setDeclined(List<String> declined) {
        this.declined = declined;
    }

    public void addGift(String email, String gift){
        giftList.put(email, gift);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Party party = (Party) o;
        return Double.compare(party.entryFee, entryFee) == 0 && Objects.equals(type, party.type) && Objects.equals(partyName, party.partyName) && Objects.equals(hostEmail, party.hostEmail) && Objects.equals(guestList, party.guestList) && Objects.equals(date, party.date) && Objects.equals(location, party.location) && Objects.equals(invited, party.invited) && Objects.equals(declined, party.declined) && Objects.equals(giftList, party.giftList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, partyName, hostEmail, guestList, date, entryFee, location, invited, declined, giftList);
    }

    @Override
    public String toString() {
        return partyName + " taking place on "+date+" at "+location;
    }

    public String toFileString(){
        String guests = String.join(",", guestList);
        String invitedString = String.join(",", invited);
        String declinedString = String.join(",", declined);
        return type+"/"+partyName+"/"+hostEmail+"/"+guests+"/"+invitedString+"/"+declinedString+"/"+date.toString()+"/"+location+"/"+entryFee;
    }
}
