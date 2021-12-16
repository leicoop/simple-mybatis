package test.bean;

/**
 * @author lei
 * @create 2021-12-11-11:11 AM
 */
public class Account {

    private int id;
    private String username;
    private Double balance;

    public Account() {
    }

    public Account(int id, String username, Double balance) {
        this.id = id;
        this.username = username;
        this.balance = balance;
    }

    public Account(String username, Double balance) {
        this.username = username;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", balance=" + balance +
                '}';
    }
}
