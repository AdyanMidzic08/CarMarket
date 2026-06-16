import java.util.LinkedList;

public abstract class CarShop {
    private String name;
    private LinkedList<CarInventoryItem> items;

    public CarShop(String name) {
        this.name = name;
        this.items = new LinkedList<>();
    }

    public LinkedList<CarInventoryItem> getItems() {
        return new LinkedList<>(items);
    }

    public void buyItem(CarType type, int amount) {

    }

    public void sellItem(CarType type, int amount) {

    }

    public abstract void rollPrices();
    public abstract void rollEvents();

}