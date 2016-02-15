import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Promotion {

    //put into item object
    public boolean isPromotion = false;
    public String promotionText = "";
    public LocalDate promotionBeginDate;

    public Promotion() {
        isPromotion = true;
        promotionText = " (promotion)";
        promotionBeginDate = LocalDate.now();
    }

    //put into PromotionManager  delete this class
    public LocalDate getPromotionBeginDate() {return promotionBeginDate;}

    public boolean getPromotion() {return isPromotion;}

    public void expirePromotionIfAged() {
        if (promotionBeginDate != null) {
            LocalDate now = LocalDate.now();
            if (ChronoUnit.DAYS.between(promotionBeginDate, now) > 30) {
                expirePromotion();
            }
        }
    }

    public void expirePromotion() {
        isPromotion = false;
        promotionText = "";
        promotionBeginDate = null;
    }

    //Test Methods
    public void setBeginDateForTest(LocalDate testBeginDate) {
        this.promotionBeginDate = testBeginDate;
    }
}
