package ethos.runehub.skill.artisan.crafting;

public class TanningItem {

    public int getHideId() {
        return hideId;
    }

    public int getLeatherId() {
        return leatherId;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "TanningItem{" +
                "hideId=" + hideId +
                ", leatherId=" + leatherId +
                ", price=" + price +
                '}';
    }

    public TanningItem(int hideId, int leatherId, int price) {
        this.hideId = hideId;
        this.leatherId = leatherId;
        this.price = price;
    }

    private final int hideId;
    private final int leatherId;
    private final int price;
}
