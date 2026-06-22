public class CarInventoryItem {
    private CarType carType;
    private double basePrice;
    private double currentPrice;
    private int stock;

    public CarInventoryItem(CarType carType, double basePrice, int stock) {
        this.carType = carType;
        this.basePrice = basePrice;
        this.currentPrice = basePrice;
        this.stock = stock;
    }

    public CarType getCarType() { return carType; }
    public double getBasePrice() { return basePrice; }
    public double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(double currentPrice) { this.currentPrice = currentPrice; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    @Override
    public String toString() {
        return String.format("%-12s | Preis: %8.2f€ | Bestand: %d", carType, currentPrice, stock);
    }
}
