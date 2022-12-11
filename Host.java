import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * Creates an host subclsas and methods
 * Final project
 * @author Sam Liu
 * ITP 265, Tea
 * Email: sqliu@usc.edu
 *
 */
public class Host extends Guest{
    private List<Party> partiesHosting;
    private int hostLimit;
    private double balance;

    public Host(String name, String email, String password, List<Party> partiesHosting, int hostLimit, double balance) {
        super(name, email, password);
        this.partiesHosting = partiesHosting;
        this.hostLimit = hostLimit;
        this.balance = balance;
    }


    public void setPartiesHosting(List<Party> partiesHosting) {
        this.partiesHosting = partiesHosting;
    }

    public void setHostLimit(int hostLimit) {
        this.hostLimit = hostLimit;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Party> getPartiesHosting() {
        return partiesHosting;
    }

    public int getHostLimit() {
        return hostLimit;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Host{" +
                "partiesHosting=" + partiesHosting +
                ", hostLimit=" + hostLimit +
                ", balance=" + balance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Host host = (Host) o;
        return hostLimit == host.hostLimit && Double.compare(host.balance, balance) == 0 && Objects.equals(partiesHosting, host.partiesHosting);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), partiesHosting, hostLimit, balance);
    }
}
