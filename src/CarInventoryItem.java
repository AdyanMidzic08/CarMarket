public class CarInventoryItem {
    private double basePrice;
    private double currentPrice;
    private int stock;
    private CarType carType;

    public CarInventoryItem(double basePrice, double currentPrice, int stock, CarType carType) {
        this.basePrice = basePrice;
        this.currentPrice = currentPrice;
        this.stock = stock;
        this.carType = carType;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public int getStock() {
        return stock;
    }

    public CarType getCarType() {
        return carType;
    }
}
