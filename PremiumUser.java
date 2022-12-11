import java.io.Serializable;
import java.util.List;
import java.util.Objects;
/**
 * Creates an premium user subclass to increase host lijit
 * Final project
 * @author Sam Liu
 * ITP 265, Tea
 * Email: sqliu@usc.edu
 *
 */
public class PremiumUser extends Host {
    private final double SUBSCRIPTION_FEE;

    public PremiumUser(String name, String email, String password, List<Party> partiesHosting, int hostLimit, double balance, double SUBSCRIPTION_FEE) {
        super(name, email, password, partiesHosting, hostLimit, balance);
        this.SUBSCRIPTION_FEE = SUBSCRIPTION_FEE;
    }

    public double getSUBSCRIPTION_FEE() {
        return SUBSCRIPTION_FEE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PremiumUser that = (PremiumUser) o;
        return Double.compare(that.SUBSCRIPTION_FEE, SUBSCRIPTION_FEE) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), SUBSCRIPTION_FEE);
    }

    @Override
    public String toString() {
        return "PremiumUser{" +
                "SUBSCRIPTION_FEE=" + SUBSCRIPTION_FEE +
                '}';
    }

    public String toFileString(){
        //return email,name,password
        return getEmail()+","+getName()+","+getPassword();
    }
}
