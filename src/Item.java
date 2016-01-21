import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Item {

    public Promotion promotion;
    public String itemText;
    public Double price;
    public Double originalPrice;
    public LocalDate lastPriceChangeDate = null;
    public PromotionCreator promotionCreator;

    public Item(String itemText, double price) {
        this.itemText = itemText;
        this.price = price;
        promotionCreator = new PromotionCreator(this);
    }

    public Double getPrice() {
        return price;
    }


    public boolean attemptPriceReduction(double priceToReduce) {

        if (priceToReduce >= this.price) {
            return false;
        }
        promotionCreator.attemptToBeginPromotion(priceToReduce);
        subtractPrice(priceToReduce);
        return true;
    }


    public void subtractPrice(double priceToReduce) {

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


    public void increasePrice(Double price) {
        this.price += price;
        lastPriceChangeDate = LocalDate.now();
        if (promotion != null) {
            promotion.expirePromotion();
        }
        originalPrice = null;
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
