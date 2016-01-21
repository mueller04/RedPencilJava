import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PromotionCreator {

    Item item;

    public PromotionCreator(Item item) {
        this.item = item;
    }

    //the returned flag doesn't do anything, I use it to control the exiting of the method.
    public boolean attemptToBeginPromotion(double priceToReduce) {
        boolean returnFlag = true;

        if (!priceToReduceIsWithinAllowableRange(priceToReduce)) {
            return false;
        }

        if (!priceToReduceIsWithinAllowableRange(priceToReduce)) {
            return false;
        }

        returnFlag = promotionDateandPriceDatesWithinAllowableRanges();
        return returnFlag;
    }


    private boolean priceToReduceIsWithinAllowableRange (double priceToReduce) {
        return (priceToReduce >= (item.price * 0.05) && (priceToReduce <= item.price * 0.3));
    }


    private boolean promotionDateandPriceDatesWithinAllowableRanges() {
        if (item.promotion != null && item.lastPriceChangeDate != null) {
            if ((ChronoUnit.DAYS.between((item.promotion.getPromotionBeginDate()), item.lastPriceChangeDate) > 30)) {

                return beginPromotionIfPriceIsChangedMoreThan30DaysAgo();
            }
        } else {
            return beginPromotionIfPriceIsChangedMoreThan30DaysAgo();
        }
        return true;
    }

    private boolean beginPromotionIfPriceIsChangedMoreThan30DaysAgo() {
        if (item.lastPriceChangeDate != null) {
            LocalDate now = LocalDate.now();
            if (ChronoUnit.DAYS.between(item.lastPriceChangeDate, now) < 30) {
                return false;
            } else {
                beginNewPromotion();
                return true;
            }
        }
        beginNewPromotion();
        return true;
    }


    private void beginNewPromotion() {
        item.promotion = new Promotion();
        item.originalPrice = item.price;
    }


}
