public class Italy extends CarShop {

    public Italy() {
        super("Italien");
    }

    @Override
    protected void initItems() {
        items.add(new CarInventoryItem(CarType.FERRARI,     250000, 2));
        items.add(new CarInventoryItem(CarType.LAMBORGHINI, 300000, 2));
    }

    @Override
    public void rollPrices() {
        for (CarInventoryItem item : items) {
            double change = 0.80 + random.nextDouble() * 0.4;
            item.setCurrentPrice(item.getBasePrice() * change);
        }
        System.out.println("[Italien] Preise wurden aktualisiert.");
    }

    @Override
    public void rollEvents() {
        int event = random.nextInt(4);
        switch (event) {
            case 0:
                System.out.println("[Italien] Formel-1-Sieg! Ferrari Preis +25%.");
                adjustPrice(CarType.FERRARI, 1.25);
                break;
            case 1:
                System.out.println("[Italien] Produktionsstopp! Lamborghini -1 Stück.");
                removeStock(CarType.LAMBORGHINI, 1);
                break;
            case 2:
                System.out.println("[Italien] Neue Sonderedition! Lamborghini +30%.");
                adjustPrice(CarType.LAMBORGHINI, 1.30);
                break;
            default:
                System.out.println("[Italien] Keine besonderen Ereignisse.");
        }
    }

    private void adjustPrice(CarType type, double factor) {
        CarInventoryItem item = findItem(type);
        if (item != null) item.setCurrentPrice(item.getCurrentPrice() * factor);
    }

    private void removeStock(CarType type, int amount) {
        CarInventoryItem item = findItem(type);
        if (item != null) item.setStock(Math.max(0, item.getStock() - amount));
    }
}
