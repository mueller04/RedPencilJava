import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Item {

    public Promotion promotion;
    public String itemText;
    public Double price;
    public Double originalPrice;
    public LocalDate lastPriceChangeDate = null;



    public Item(String itemText, double price) {
        this.itemText = itemText;
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void reducePrice(double priceToReduce) {

        if (priceToReduce < this.price) {
            if (priceToReduce >= (this.price * 0.05) && (priceToReduce <= this.price * 0.3)) {
                if (promotion != null && lastPriceChangeDate != null) {
                    if ((ChronoUnit.DAYS.between((promotion.getPromotionBeginDate()), lastPriceChangeDate) > 30)) {
                        if (!priceIsChangedLessThan30DaysAgo()) {
                            promotion = new Promotion();
                            originalPrice = this.price;
                        } else {
                            //no new promotion
                        }
                    }
                } else {
                    if (!priceIsChangedLessThan30DaysAgo()) {
                        promotion = new Promotion();
                        originalPrice = this.price;
                    }
                }
            }

            if (originalPrice != null) {
                if (priceToReduce > originalPrice * 0.3) {
                    if (promotion != null) {
                        promotion.expirePromotion();
                    }
                    originalPrice = null;
                }
            }

            this.price -= priceToReduce;
            lastPriceChangeDate = LocalDate.now();
        }
    }



    //refactored methods




    //end refactored methods


    public void increasePrice(Double price) {
        this.price += price;
        lastPriceChangeDate = LocalDate.now();
        if (promotion != null) {
            promotion.expirePromotion();
        }
        originalPrice = null;
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
