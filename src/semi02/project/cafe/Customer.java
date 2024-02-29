package semi02.project.cafe;

public class Customer {
    private int money;
    private String nickname;

    public Customer(int money, String nickname) {
        this.money = money;
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void spendMoney(int price) {
        money -= price;
    }
}
