import java.time.LocalDate;
import java.util.Calendar;

public class Item {

    public String displayText;
    public Double price;
    public LocalDate date;

    public Item(String displayText, double price){
        this.displayText = displayText;
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public LocalDate getDate(){
        return date;
    }

    public void beginPromotion(){
        this.displayText = displayText + " " + "(promotion)";
        date = LocalDate.now();
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
