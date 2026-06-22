public class France extends CarShop {

    public France() {
        super("Frankreich");
    }

    @Override
    protected void initItems() {
        items.add(new CarInventoryItem(CarType.PEUGEOT, 22000, 8));
        items.add(new CarInventoryItem(CarType.BMW,     48000, 3));
    }

    @Override
    public void rollPrices() {
        for (CarInventoryItem item : items) {
            // Französische Autos: ±15% Schwankung
            double change = 0.85 + random.nextDouble() * 0.3;
            item.setCurrentPrice(item.getBasePrice() * change);
        }
        System.out.println("[Frankreich] Preise wurden aktualisiert.");
    }

    @Override
    public void rollEvents() {
        int event = random.nextInt(4);
        switch (event) {
            case 0:
                System.out.println("[Frankreich] 🥖 Streik in Paris! Alle Preise -5%.");
                for (CarInventoryItem item : items) {
                    item.setCurrentPrice(item.getCurrentPrice() * 0.95);
                }
                break;
            case 1:
                System.out.println("[Frankreich] 🏎️ Motorsport-Event! Peugeot Nachfrage steigt, +20%.");
                adjustPrice(CarType.PEUGEOT, 1.20);
                break;
            case 2:
                System.out.println("[Frankreich] 📦 Neue Lieferung! Peugeot +4 Stück.");
                addStock(CarType.PEUGEOT, 4);
                break;
            default:
                System.out.println("[Frankreich] Keine besonderen Ereignisse.");
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
