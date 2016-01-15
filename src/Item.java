import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Item {

    public Promotion promotion;
    public String itemText;
    public Double price;
    public LocalDate lastPriceChangeDate = null;

    public Item(String itemText, double price) {
        this.itemText = itemText;
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void reducePrice(double price) {

        if (price < this.price) {
            if (price >= (this.price * 0.05) && (price <= this.price * 0.3)) {
                if (!priceIsChangedLessThan30DaysAgo()) {
                    promotion = new Promotion();
                        promotion.beginPromotion();
                }
            }
            this.price -= price;
            LocalDate now = LocalDate.now();
            lastPriceChangeDate = now;
        }
    }

    public void increasePrice(Double price) {
        this.price += price;
        LocalDate now = LocalDate.now();
        lastPriceChangeDate = now;

        if (promotion != null) {
            promotion.endPromotion();
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
        if (promotion != null) {
                promotion.expirePromotionIfAged();
        }
        if (promotion != null) {
            return itemText + promotion.promotionText;
        } else {
            return itemText;
        }
    }

    //Test Methods
    public void setLastPriceChangeDate(LocalDate testLastPriceChangeDate) {
        this.lastPriceChangeDate = testLastPriceChangeDate;
    }
}
