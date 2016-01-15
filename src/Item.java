import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Item {

    public String itemText;
    public String promotionText = "";
    public Double price;
    public LocalDate promotionBeginDate;
    public LocalDate lastPriceChangeDate = null;
    public boolean isPromotion = false;

    public Item(String itemText, double price) {
        this.itemText = itemText;
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public LocalDate getPromotionBeginDate() {
        return promotionBeginDate;
    }

    public boolean getPromotion() {
        return isPromotion;
    }

    public void beginPromotion() {
        promotionText = " (promotion)";
        isPromotion = true;
        promotionBeginDate = LocalDate.now();
        lastPriceChangeDate = promotionBeginDate;
    }

    public void reducePrice(double price) {

        if (price < this.price) {
            if (price >= (this.price * 0.05) && (price <= this.price * 0.3)) {
                if (!priceIsChangedLessThan30DaysAgo()) {
                        this.beginPromotion();
                }

            }
            this.price -= price;
            lastPriceChangeDate = promotionBeginDate;
        }
    }

    public boolean priceIsChangedLessThan30DaysAgo() {
        if (lastPriceChangeDate != null) {
            LocalDate now = LocalDate.now();
            if (ChronoUnit.DAYS.between(lastPriceChangeDate, now) < 30) {
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

        if (promotionBeginDate != null) {
            if (ChronoUnit.DAYS.between(promotionBeginDate, now) > 30) {
                isPromotion = false;
                promotionText = "";
            }
        }
        return itemText + promotionText;
    }

    //Test Methods
    public void setBeginDateForTest(LocalDate testBeginDate) {
        this.promotionBeginDate = testBeginDate;
    }

    public void setLastPriceChangeDate(LocalDate testLastPriceChangeDate) {
        this.lastPriceChangeDate = testLastPriceChangeDate;
    }
}
