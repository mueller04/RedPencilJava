import java.time.LocalDate;
import java.util.Calendar;
import java.time.temporal.ChronoUnit;

public class Item {

    public String itemText;
    public String promotionText = "";
    public Double price;
    public LocalDate date;
    public LocalDate lastPriceChangeDate = null;
    public boolean isPromotion = false;

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

    public boolean getPromotion() {
        return isPromotion;
    }

    public void beginPromotion(Double price) {
        promotionText = " (promotion)";
        isPromotion = true;
        this.price -= price;
        date = LocalDate.now();
        lastPriceChangeDate = date;
    }

    public void reducePrice(double price) {

        if (price < this.price) {
            if (price >= (this.price * 0.05) && (price <= this.price * 0.3)) {
                if (!priceIsChangedWithin30Days()) {
                        this.beginPromotion(price);
                }
            }
        }
    }

    public boolean priceIsChangedWithin30Days() {
        if (lastPriceChangeDate != null) {
            if (ChronoUnit.DAYS.between(lastPriceChangeDate, date) <= 30) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        LocalDate now = LocalDate.now();

        if (date != null) {
            if (ChronoUnit.DAYS.between(date, now) > 30) {
                isPromotion = false;
            }

        }
        return itemText + promotionText;
    }

    //Test Methods
    public void setupTestDates(LocalDate testBeginDate, LocalDate testLastPriceChangeDate) {
        this.date = testBeginDate;
        this.lastPriceChangeDate = testLastPriceChangeDate;
    }
}
