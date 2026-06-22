public class Germany extends CarShop {

    public Germany() {
        super("Deutschland");
    }

    @Override
    protected void initItems() {
        items.add(new CarInventoryItem(CarType.BMW,        45000, 5));
        items.add(new CarInventoryItem(CarType.AUDI,       40000, 5));
        items.add(new CarInventoryItem(CarType.BUGATTI,   2500000, 1));
    }

    @Override
    public void rollPrices() {
        for (CarInventoryItem item : items) {
            // Deutsche Autos: ±10% Schwankung
            double change = 0.9 + random.nextDouble() * 0.2;
            item.setCurrentPrice(item.getBasePrice() * change);
        }
        System.out.println("[Deutschland] Preise wurden aktualisiert.");
    }

    @Override
    public void rollEvents() {
        int event = random.nextInt(4);
        switch (event) {
            case 0:
                System.out.println("[Deutschland] 🏭 Exportboom! BMW und Audi-Preise steigen um 15%.");
                adjustPrice(CarType.BMW, 1.15);
                adjustPrice(CarType.AUDI, 1.15);
                break;
            case 1:
                System.out.println("[Deutschland] 🔧 Streik in Wolfsburg! Audi Bestand halbiert.");
                halveStock(CarType.AUDI);
                break;
            case 2:
                System.out.println("[Deutschland] 💰 Luxusnachfrage! Bugatti Preis +20%.");
                adjustPrice(CarType.BUGATTI, 1.20);
                break;
            default:
                System.out.println("[Deutschland] Keine besonderen Ereignisse.");
        }
    }

    private void adjustPrice(CarType type, double factor) {
        CarInventoryItem item = findItem(type);
        if (item != null) item.setCurrentPrice(item.getCurrentPrice() * factor);
    }

    private void halveStock(CarType type) {
        CarInventoryItem item = findItem(type);
        if (item != null) item.setStock(Math.max(1, item.getStock() / 2));
    }
}
