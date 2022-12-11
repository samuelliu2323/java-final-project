import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
/**
 * Creates potluck subclass and methods
 * Final project
 * @author Sam Liu
 * ITP 265, Tea
 * Email: sqliu@usc.edu
 *
 */
public class PotluckParty extends Party{
    private List<String> dishList;
    private Map<String, String> signup;

    public PotluckParty(String type, String partyName, String hostEmail, List<String> guestList, LocalDate date, double entryFee, String location, List<String> invited, List<String> declined, Map<String, String> giftList, List<String> dishList, Map<String, String> signup) {
        super(type, partyName, hostEmail, guestList, date, entryFee, location, invited, declined, giftList);
        this.dishList = dishList;
        this.signup = signup;
    }

    public List<String> getDishList() {
        return dishList;
    }

    public void setDishList(List<String> dishList) {
        this.dishList = dishList;
    }

    public Map<String, String> getSignup() {
        return signup;
    }
    public void setSignup(Map<String, String> signup) {
        this.signup = signup;
    }

    public void removeDish(String dish){
        for(String s: dishList){
            if(s.equalsIgnoreCase(dish)){
                dishList.remove(dish);
            }
        }
    }

    public void addSignup(String email, String dish){
        signup.put(email, dish);
    }

    public String toString() {
        return "Potluck Party! " + super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PotluckParty that = (PotluckParty) o;
        return Objects.equals(dishList, that.dishList) && Objects.equals(signup, that.signup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dishList, signup);
    }
}
