public class Asia extends CarShop {

    public Asia() {
        super("Asien");
    }

    @Override
    protected void initItems() {
        items.add(new CarInventoryItem(CarType.BYD,    30000, 10));
        items.add(new CarInventoryItem(CarType.TOYOTA, 25000, 10));
    }

    @Override
    public void rollPrices() {
        for (CarInventoryItem item : items) {
            // Asiatische Autos: ±8% Schwankung (stabiler Markt)
            double change = 0.92 + random.nextDouble() * 0.16;
            item.setCurrentPrice(item.getBasePrice() * change);
        }
        System.out.println("[Asien] Preise wurden aktualisiert.");
    }

    @Override
    public void rollEvents() {
        int event = random.nextInt(4);
        switch (event) {
            case 0:
                System.out.println("[Asien] ⚡ E-Auto-Boom! BYD +20% Nachfrage.");
                adjustPrice(CarType.BYD, 1.20);
                break;
            case 1:
                System.out.println("[Asien] Massenlieferung! Toyota +5 Stück.");
                addStock(CarType.TOYOTA, 5);
                break;
            case 2:
                System.out.println("[Asien] Überproduktion! Alle Preise -10%.");
                for (CarInventoryItem item : items) {
                    item.setCurrentPrice(item.getCurrentPrice() * 0.90);
                }
                break;
            default:
                System.out.println("[Asien] Keine besonderen Ereignisse.");
        }
    }

    private void adjustPrice(CarType type, double factor) {
        CarInventoryItem item = findItem(type);
        if (item != null) item.setCurrentPrice(item.getCurrentPrice() * factor);
    }

    private void addStock(CarType type, int amount) {
        CarInventoryItem item = findItem(type);
        if (item != null) item.setStock(item.getStock() + amount);
    }
}
