import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class CarShop {
    protected String name;
    protected List<CarInventoryItem> items;
    protected Random random = new Random();

    public CarShop(String name) {
        this.name = name;
        this.items = new ArrayList<>();
        initItems();
    }

    protected abstract void initItems();

    public List<CarInventoryItem> getItems() {
        return items;
    }

    public CarInventoryItem findItem(CarType type) {
        for (CarInventoryItem item : items) {
            if (item.getCarType() == type) return item;
        }
        return null;
    }

    public int buyItem(CarType type, int amount) {
        CarInventoryItem item = findItem(type);
        if (item == null) {
            System.out.println("[" + name + "] Dieses Auto ist hier nicht erhältlich.");
            return -1;
        }
        if (item.getStock() < amount) {
            System.out.println("[" + name + "] Nicht genug auf Lager! Verfügbar: " + item.getStock());
            return -1;
        }
        int totalCost = (int)(item.getCurrentPrice() * amount);
        item.setStock(item.getStock() - amount);
        System.out.printf("[%s] Verkauft: %d x %s für insgesamt %d€%n", name, amount, type, totalCost);
        return totalCost;
    }

    public int sellItem(CarType type, int amount) {
        CarInventoryItem item = findItem(type);
        if (item == null) {
            System.out.println("[" + name + "] Wir kaufen dieses Auto hier nicht an.");
            return -1;
        }
        int totalValue = (int)(item.getCurrentPrice() * amount * 0.9);
        item.setStock(item.getStock() + amount);
        System.out.printf("[%s] Angekauft: %d x %s für insgesamt %d€%n", name, amount, type, totalValue);
        return totalValue;
    }

    public abstract void rollPrices();
    public abstract void rollEvents();

    public String getName() { return name; }

    public void printInventory() {
        System.out.println("\n=== " + name + " ===");
        for (CarInventoryItem item : items) {
            System.out.println("  " + item);
        }
    }
}
