import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PromotionEvaluator {

    Item item;

    public String[] businessRuleMethods = {"isPriceToReduceGreaterThanCurrentPrice",
            "isPriceReductionPercentageWithinRange",
            "doesPreviousPromotion30DaysNotIntersectWithLastPriceChangeDate",
            "isNewPriceReductionLessThan30PercentOfOriginalPrice"};

    public PromotionEvaluator(Item item) {
        this.item = item;
    }

    public void reducethePrice(Double priceToReduce) {


        for (int i = 0; i < businessRuleMethods.length; i++) {
            try {
                Method method = Item.class.getMethod(businessRuleMethods[i]);
                method.invoke(item);
            } catch (NoSuchMethodException e) {

            } catch (IllegalAccessException ia){

            } catch (InvocationTargetException ite) {

            }
            
        }
    }


    public boolean isPriceToReduceGreaterThanCurrentPrice(Double priceToReduce) {
        return (priceToReduce > item.price);
    }

    public boolean isPriceReductionPercentageWithinRange(Double priceToReduce) {
        return (priceToReduce >= (item.price * 0.05) && priceToReduce <= item.price * 0.3);
    }

    public boolean doesPreviousPromotion30DaysNotIntersectWithLastPriceChangeDate() {
        if (item.promotion != null && item.lastPriceChangeDate != null) {
            if (!priceIsChangedLessThan30DaysAgo()) {
                item.promotion = new Promotion();
                item.originalPrice = item.price;
            } else {
                //no new promotion
            }
            return (ChronoUnit.DAYS.between((item.promotion.getPromotionBeginDate()), item.lastPriceChangeDate) > 30);
        } else {
            return true;
        }
    }

    public boolean isNewPriceReductionLessThan30PercentOfOriginalPrice(Double priceToReduce) {
        if (item.originalPrice != null) {
            if (priceToReduce < item.originalPrice * 0.3) {
                return true;
            } else {
                if (item.promotion != null) {
                    item.promotion.expirePromotion();
                }
                item.originalPrice = null;
                return false;
            }

        } else {
            return true;
        }
    }

    public boolean priceIsChangedLessThan30DaysAgo() {
        if (item.lastPriceChangeDate != null) {
            LocalDate now = LocalDate.now();
            if (ChronoUnit.DAYS.between(item.lastPriceChangeDate, now) < 30) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }




}
