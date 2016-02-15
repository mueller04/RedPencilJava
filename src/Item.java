import java.time.LocalDate;

public class Item {

    public Promotion promotion;
    public String itemText;
    public Double price;
    public Double originalPrice;
    public LocalDate lastPriceChangeDate = null;
    public PromotionManager promotionCreator;

    //should promotion creator be initialized in attemptPriceReduction?
    public Item(String itemText, double price) {
        this.itemText = itemText;
        this.price = price;
        promotionCreator = new PromotionManager(this);
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


    private void subtractPrice(double priceToReduce) {

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
