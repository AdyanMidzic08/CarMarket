import java.util.Scanner;

public class Game {
    private Player player;
    private CarShop shop;
    private CarShop[] shops;
    private int round;
    private static final int MAX_ROUNDS = 10;
    private static final int START_MONEY = 100_000;

    public void init() {
        player = new Player(START_MONEY);
        shops = new CarShop[]{new Germany(), new France(), new Italy(), new Asia()};
        shop = shops[0];
        round = 1;
        System.out.println("===========================================");
        System.out.println("      Willkommen beim Auto-Handelsspiel!  ");
        System.out.println("===========================================");
        System.out.printf("Du startest mit %d€. Ziel: Möglichst viel Gewinn in %d Runden!%n%n",
                START_MONEY, MAX_ROUNDS);
    }

    public void startRound() {
        System.out.println("\n================================");
        System.out.printf( "=        RUNDE %2d / %2d          =%n", round, MAX_ROUNDS);
        System.out.println("================================");

        // Alle Shops aktualisieren
        System.out.println("\n--- Marktbewegungen ---");
        for (CarShop s : shops) {
            s.rollPrices();
            s.rollEvents();
        }
    }

    public void play() {
        Scanner sc = new Scanner(System.in);

        while (round <= MAX_ROUNDS) {
            startRound();
            player.printStatus();

            boolean roundDone = false;
            while (!roundDone) {
                printMenu();
                String input = sc.nextLine().trim();

                switch (input) {
                    case "1":
                        selectShop(sc);
                        break;
                    case "2":
                        shop.printInventory();
                        break;
                    case "3":
                        doBuy(sc);
                        break;
                    case "4":
                        doSell(sc);
                        break;
                    case "5":
                        player.printStatus();
                        break;
                    case "6":
                        roundDone = true;
                        round++;
                        break;
                    case "0":
                        System.out.println("Spiel beendet.");
                        printFinalScore();
                        return;
                    default:
                        System.out.println("Ungültige Eingabe.");
                }
            }
        }
        printFinalScore();
    }

    private void printMenu() {
        System.out.println("\n--- Aktueller Markt: " + shop.getName() + " ---");
        System.out.println("[1] Markt wechseln");
        System.out.println("[2] Angebote ansehen");
        System.out.println("[3] Kaufen");
        System.out.println("[4] Verkaufen");
        System.out.println("[5] Spielerstatus");
        System.out.println("[6] Nächste Runde");
        System.out.println("[0] Beenden");
        System.out.print("Auswahl: ");
    }

    private void selectShop(Scanner sc) {
        System.out.println("Verfügbare Märkte:");
        for (int i = 0; i < shops.length; i++) {
            System.out.printf("[%d] %s%n", i + 1, shops[i].getName());
        }
        System.out.print("Wähle einen Markt (1-" + shops.length + "): ");
        try {
            int idx = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (idx >= 0 && idx < shops.length) {
                shop = shops[idx];
                System.out.println("Gewechselt zu: " + shop.getName());
            } else {
                System.out.println("Ungültige Auswahl.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ungültige Eingabe.");
        }
    }

    private void doBuy(Scanner sc) {
        shop.printInventory();
        System.out.print("Welches Auto kaufen (z.B. BMW): ");
        String typeName = sc.nextLine().trim().toUpperCase();
        try {
            CarType type = CarType.valueOf(typeName);
            System.out.print("Anzahl: ");
            int amount = Integer.parseInt(sc.nextLine().trim());
            if (amount <= 0) { System.out.println("Ungültige Menge."); return; }
            player.buy(shop, type, amount);
        } catch (IllegalArgumentException e) {
            System.out.println("Unbekanntes Auto: " + typeName);
        }
    }

    private void doSell(Scanner sc) {
        if (player.getInventory().isEmpty()) {
            System.out.println("Dein Inventar ist leer.");
            return;
        }
        player.printStatus();
        System.out.print("Welches Auto verkaufen (z.B. BMW): ");
        String typeName = sc.nextLine().trim().toUpperCase();
        try {
            CarType type = CarType.valueOf(typeName);
            System.out.print("Anzahl: ");
            int amount = Integer.parseInt(sc.nextLine().trim());
            if (amount <= 0) { System.out.println("Ungültige Menge."); return; }
            player.sell(shop, type, amount);
        } catch (IllegalArgumentException e) {
            System.out.println("Unbekanntes Auto: " + typeName);
        }
    }

    private void printFinalScore() {
        // Restliche Autos zum Basispreis bewerten
        int inventoryValue = 0;
        for (InventoryItem inv : player.getInventory()) {
            for (CarShop s : shops) {
                CarInventoryItem ci = s.findItem(inv.getCarType());
                if (ci != null) {
                    inventoryValue += (int)(ci.getCurrentPrice() * inv.getAmount() * 0.9);
                    break;
                }
            }
        }
        int totalWealth = player.getMoney() + inventoryValue;
        int profit = totalWealth - START_MONEY;

        System.out.println("\n================================");
        System.out.println("=         SPIELENDE - ERGEBNIS     =");
        System.out.println("================================");
        System.out.printf("Bargeld:           %10d€%n", player.getMoney());
        System.out.printf("Inventarwert:      %10d€%n", inventoryValue);
        System.out.printf("Gesamtvermögen:    %10d€%n", totalWealth);
        System.out.printf("Gewinn/Verlust:    %10d€%n", profit);
        System.out.println();
        if (profit > 500_000)       System.out.println("Legendärer Autohändler!");
        else if (profit > 100_000)  System.out.println("Ausgezeichnet!");
        else if (profit > 0)        System.out.println("Gut gemacht!");
        else                        System.out.println("Besser Glück beim nächsten Mal...");
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.init();
        game.play();
    }
}
