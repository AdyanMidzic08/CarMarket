import java.util.ArrayList;
import java.util.List;

public class Player {
    private int money;
    private List<InventoryItem> inventory;

    public Player(int startMoney) {
        this.money = startMoney;
        this.inventory = new ArrayList<>();
    }

    public int getMoney() { return money; }

    public List<InventoryItem> getInventory() { return inventory; }

    public int getItemCount(CarType type) {
        for (InventoryItem item : inventory) {
            if (item.getCarType() == type) return item.getAmount();
        }
        return 0;
    }

    /** Kauft Autos vom Shop. Gibt true zurück wenn erfolgreich. */
    public boolean buy(CarShop shop, CarType type, int amount) {
        CarInventoryItem shopItem = shop.findItem(type);
        if (shopItem == null) {
            System.out.println("Dieses Auto ist in " + shop.getName() + " nicht verfügbar.");
            return false;
        }
        int totalCost = (int)(shopItem.getCurrentPrice() * amount);
        if (totalCost > money) {
            System.out.printf("Nicht genug Geld! Benötigt: %d€, Verfügbar: %d€%n", totalCost, money);
            return false;
        }
        int charged = shop.buyItem(type, amount);
        if (charged < 0) return false;

        money -= charged;

        // Zum Inventar hinzufügen
        boolean found = false;
        for (InventoryItem inv : inventory) {
            if (inv.getCarType() == type) {
                inv.setAmount(inv.getAmount() + amount);
                found = true;
                break;
            }
        }
        if (!found) {
            inventory.add(new InventoryItem(type, amount, shopItem.getCurrentPrice()));
        }
        System.out.printf("Kauf erfolgreich! Restgeld: %d€%n", money);
        return true;
    }

    /** Verkauft Autos an einen Shop. Gibt true zurück wenn erfolgreich. */
    public boolean sell(CarShop shop, CarType type, int amount) {
        int owned = getItemCount(type);
        if (owned < amount) {
            System.out.printf("Nicht genug %s im Inventar! Besitze: %d%n", type, owned);
            return false;
        }
        int received = shop.sellItem(type, amount);
        if (received < 0) return false;

        money += received;

        // Aus Inventar entfernen
        inventory.removeIf(inv -> {
            if (inv.getCarType() == type) {
                inv.setAmount(inv.getAmount() - amount);
                return inv.getAmount() <= 0;
            }
            return false;
        });
        System.out.printf("Verkauf erfolgreich! Neues Guthaben: %d€%n", money);
        return true;
    }

    public void printStatus() {
        System.out.println("\n========== SPIELER STATUS ==========");
        System.out.printf("Geld: %d€%n", money);
        if (inventory.isEmpty()) {
            System.out.println("Inventar: leer");
        } else {
            System.out.println("Inventar:");
            for (InventoryItem item : inventory) {
                System.out.println("  - " + item);
            }
        }
        System.out.println("=====================================");
    }
}
