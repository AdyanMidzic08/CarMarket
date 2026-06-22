public class InventoryItem {
    private CarType carType;
    private int amount;
    private double buyPrice;

    public InventoryItem(CarType carType, int amount, double buyPrice) {
        this.carType = carType;
        this.amount = amount;
        this.buyPrice = buyPrice;
    }

    public CarType getCarType() { return carType; }
    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
    public double getBuyPrice() { return buyPrice; }

    @Override
    public String toString() {
        return carType + " x" + amount + " (gekauft für " + String.format("%.2f", buyPrice) + ")";
    }
}
