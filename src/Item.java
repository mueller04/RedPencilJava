public class Item {

    public String displayText;
    public Double price;

    public Item(String displayText, double price){
        this.displayText = displayText;
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void beginPromotion(){
        this.displayText = displayText + " " + "(promotion)";
    }

    public void reducePrice(double price) {

        if (price >= (this.price * 0.05) && (price <= this.price * 0.3)){
            this.beginPromotion();
        }

        this.price -= price;
    }

    @Override
    public String toString(){
        return displayText;
    }
}
