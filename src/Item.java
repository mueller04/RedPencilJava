public class Item {

    public String displayText;
    public Double price;

    public Item(String displayText, double price){
        this.displayText = displayText;
        this.price = price;
    }

    public void beginPromotion(){
        this.displayText = displayText + " " + "(promotion)";
    }

    public void reducePrice(double price) {
        this.price -= price;
    }

    @Override
    public String toString(){
        return displayText;
    }
}
