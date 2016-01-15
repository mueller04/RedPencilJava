import java.time.LocalDate;
import java.util.Calendar;
import java.time.temporal.ChronoUnit;

public class Item {

    public String itemText;
    public String promotionText = "";
    public Double price;
    public LocalDate date;

    public Item(String itemText, double price) {
        this.itemText = itemText;
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void beginPromotion() {
        promotionText = " (promotion)";
        date = LocalDate.now();
    }

    public void reducePrice(double price) {

        if (price < this.price) {
            if (price >= (this.price * 0.05) && (price <= this.price * 0.3)) {
                this.beginPromotion();
            }
            this.price -= price;
        }

    }

    @Override
    public String toString(){
        LocalDate now = LocalDate.now();

        if (date != null) {
            if (ChronoUnit.DAYS.between(date, now) > 30) {
                promotionText = "";
            }

        }
        return itemText + promotionText;
    }

    //Test Methods
    public void setupTestDates(LocalDate testBeginDate) {
        this.date = testBeginDate;
    }
}
