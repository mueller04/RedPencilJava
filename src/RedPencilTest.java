import org.junit.*;

import java.time.LocalDate;


public class RedPencilTest {


    @Test
    public void whenanItemisMarkedAsAPromotionItReturnsPromotionInTheString(){
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.attemptPriceReduction(1.20);

        //Assert
        Assert.assertEquals("Coat (promotion)", item.toString());
        Assert.assertEquals(true, item.promotion.getPromotion());
    }

    @Test
    public void whenThePriceIsReducedBy4Point99PercentThePromotionDoesNotBegin(){
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.attemptPriceReduction(0.24);

        //Assert
        Assert.assertEquals("Coat", item.toString());
    }

    @Test
    public void whenThePriceIsReducedBy5PercentThePromotionBegins()
    {
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.attemptPriceReduction(0.25);

        //Assert
        Assert.assertEquals("Coat (promotion)", item.toString());
        Assert.assertEquals(true, item.promotion.getPromotion());
    }

    @Test
    public void whenThePriceIsReducedByLargerPriceThanCurrentPricePromotionDoesNotBegin()
    {
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.attemptPriceReduction(5.01);

        //Assert
        Assert.assertEquals("Coat", item.toString());
        Assert.assertEquals(null, item.promotion);
        Assert.assertEquals(5.00, item.getPrice(), 0);
    }

    @Test
    public void whenThePriceIsReducedByEqualPriceToCurrentPricePromotionDoesNotBegin()
    {
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.attemptPriceReduction(5.00);

        //Assert
        Assert.assertEquals("Coat", item.toString());
        Assert.assertEquals(null, item.promotion);
        Assert.assertEquals(5.00, item.getPrice(), 0);
    }

    @Test
    public void whenPriceIsReducedBy5Point2PercentThePromotionBegins(){
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.attemptPriceReduction(0.26);

        //Assert
        Assert.assertEquals("Coat (promotion)", item.toString());
        Assert.assertEquals(true, item.promotion.getPromotion());
    }

    @Test
    public void whenPriceIsReducedThePriceChanges(){
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.attemptPriceReduction(0.26);

        //Assert
        Assert.assertEquals(4.74, item.getPrice(), 0);
    }

    @Test
    public void whenPriceIsReducedBy30PercentThePromotionBegins(){
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.attemptPriceReduction(1.50);

        //Assert
        Assert.assertEquals("Coat (promotion)", item.toString());
        Assert.assertEquals(true, item.promotion.getPromotion());
    }

    @Test
    public void whenPriceIsReducedBy30Point1PercentThePromotionDoesNotBegin(){
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.attemptPriceReduction(1.55);

        //Assert
        Assert.assertEquals("Coat", item.toString());
        Assert.assertEquals(null, item.promotion);
    }

    @Test
    public void whenAPromotionBeginsTheDateIsRecorded(){
        //Arrange
        Item item = new Item("Coat", 5.00);
        LocalDate expectedDate = LocalDate.now();

        //Act
        item.attemptPriceReduction(1);

        //Assert
        Assert.assertEquals(expectedDate, item.promotion.getPromotionBeginDate());
    }

    /* Now that I changed it so that no promotion object is created when a promotion doesn't happen, should this test
    change to testing that no promotion object is created rather than specifically testing for the date and needing
    to create a promotion object?  before I was testing that the object is null, I was using getpromoDate on the promo object. */
    @Test
    public void whenNoPromotionTheDateIsNotRecorded(){
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.attemptPriceReduction(1.55);

        //Assert
        Assert.assertEquals(null, item.promotion);
    }

    //In the below test, calling item.toString during the first assert sets the promotion boolean flag correctly for the second assert
    @Test
    public void whenPromotionDateIs31DaysOldNoPromotionText() {
        //Arrange
        Item item = new Item("Coat", 5.00);
        LocalDate now = LocalDate.now();
        LocalDate beginPromoDate = now.minusDays(31);

        //Act
        item.attemptPriceReduction(1.20);
        item.promotion.setBeginDateForTest(beginPromoDate);

        //Assert
        Assert.assertEquals("Coat", item.toString());
        Assert.assertEquals(false, item.promotion.getPromotion());
    }

    @Test
    public void whenPromotionDateIs30DaysOldPromotionTextDisplays() {
        //Arrange
        Item item = new Item("Coat", 5.00);
        LocalDate now = LocalDate.now();
        LocalDate beginPromoDate = now.minusDays(30);

        //Act
        item.attemptPriceReduction(1.20);
        item.promotion.setBeginDateForTest(beginPromoDate);

        //Assert
        Assert.assertEquals("Coat (promotion)", item.toString());
        Assert.assertEquals(true, item.promotion.getPromotion());
    }

    @Test
    public void whenPriceChangedWithin15DaysAndNoCurrentPromotionThenCannotBeginPromotion() {
        //Arrange
        Item item = new Item("Coat", 5.00);
        LocalDate now = LocalDate.now();
        LocalDate lastPriceChangeDate = now.minusDays(15);

        item.setLastPriceChangeDate(lastPriceChangeDate);

        //Act
        item.attemptPriceReduction(1.20);

        //Assert
        Assert.assertEquals(3.8, item.getPrice(), 0);
        Assert.assertEquals("Coat", item.toString());
        Assert.assertEquals(null, item.promotion);
    }

    @Test
    public void when31DaysSinceLastPriceChangeCanCreateNewPromotion() {
        //Arrange
        Item item = new Item("Coat", 5.00);
        LocalDate now = LocalDate.now();
        LocalDate lastPriceChangeDate = now.minusDays(31);
        item.setLastPriceChangeDate(lastPriceChangeDate);

        //Act
        item.attemptPriceReduction(1.20);

        //Assert
        Assert.assertEquals(true, item.promotion.getPromotion());
        Assert.assertEquals(3.80, item.getPrice(), 0);
        Assert.assertEquals(now, item.promotion.getPromotionBeginDate());
    }

    @Test
    public void when30DaysSinceLastPriceChangeCanCreateNewPromotion() {
        //Arrange
        Item item = new Item("Coat", 5.00);
        LocalDate now = LocalDate.now();
        LocalDate lastPriceChangeDate = now.minusDays(30);
        item.setLastPriceChangeDate(lastPriceChangeDate);

        //Act
        item.attemptPriceReduction(1.20);

        //Assert
        Assert.assertEquals(true, item.promotion.getPromotion());
        Assert.assertEquals(3.80, item.getPrice(), 0);
        Assert.assertEquals(now, item.promotion.getPromotionBeginDate());
    }

   @Test
   public void whenPriceIsReducedDuringPromotionThePromotionIsNotProlonged() {
       //Arrange
       Item item = new Item("Coat", 5.00);
       Double priceToReduceIsValidToBeginPromotion = 1.20;

       item.attemptPriceReduction(priceToReduceIsValidToBeginPromotion);

       LocalDate now = LocalDate.now();
       LocalDate beginPromoDate = now.minusDays(15);
       item.promotion.setBeginDateForTest(beginPromoDate);
       item.setLastPriceChangeDate(beginPromoDate);

       //Act
       priceToReduceIsValidToBeginPromotion = .25;
       item.attemptPriceReduction(priceToReduceIsValidToBeginPromotion);

       //Assert
       Assert.assertEquals(true, item.promotion.getPromotion());
       Assert.assertEquals(3.55, item.getPrice(), 0);
       Assert.assertEquals(beginPromoDate, item.promotion.getPromotionBeginDate());
   }

    @Test
    public void whenPriceIsIncreasedCurrentPromotionEnds() {
        //Arrange
        Item item = new Item("Coat", 5.00);
        Double priceToReduceIsValidToBeginPromotion = 1.20;
        item.attemptPriceReduction(priceToReduceIsValidToBeginPromotion);

        //Act
        item.increasePrice(.02);

        //Assert
        Assert.assertEquals(false, item.promotion.getPromotion());
        Assert.assertEquals("Coat", item.toString());
        Assert.assertEquals(3.82, item.getPrice(), 0);
    }

    //This still does not appear to be implemented correctly, I created an originalPrice field but I don't appear to actually be using that field yet
    //in the loop.  Weirdly, this test always passes, I cannot get it to fail in the first place.
    @Test
    public void ifPriceIsReducedDuringPromotionByMoreThan30PercentOfOriginalPricePromotionIsEnded() {
        //Arrange
        Item item = new Item("Coat", 5.00);
        Double priceToReduceIsValidToBeginPromotion = .25;
        item.attemptPriceReduction(priceToReduceIsValidToBeginPromotion);

        Assert.assertEquals(true, item.promotion.getPromotion());

        //Act
        Double priceToReduceIsGreaterThan30PercentOfOriginalPrice = 1.51;
        item.attemptPriceReduction(priceToReduceIsGreaterThan30PercentOfOriginalPrice);

        //Assert
        Assert.assertEquals(3.24, item.getPrice(), 0);
        Assert.assertEquals(false, item.promotion.getPromotion());
        Assert.assertEquals("Coat", item.toString());
    }

    @Test
    public void ifPriceisReduced30OrMoreDaysAfterLastPriceChangeAndLastPriceChangeBeginDateIntersectsWithLastPromotionEndDateNoNewPromotionBegins() {
        //Arrange
        Item item = new Item("Coat", 5.00);
        Double priceToReduceIsValidToBeginPromotion = 1.20;
        item.attemptPriceReduction(priceToReduceIsValidToBeginPromotion);
        Assert.assertEquals(true, item.promotion.getPromotion());

        LocalDate now = LocalDate.now();
        LocalDate beginPromoDate = now.minusDays(45);
        LocalDate testPriceChangeDate = now.minusDays(30);
        item.promotion.setBeginDateForTest(beginPromoDate);
        item.setLastPriceChangeDate(testPriceChangeDate);

        priceToReduceIsValidToBeginPromotion = .25;

        //Act
        item.attemptPriceReduction(priceToReduceIsValidToBeginPromotion);
        item.toString();

        //Assert
        //Assert.assertEquals(3.55, item.getPrice(), 0);
        Assert.assertEquals(false, item.promotion.getPromotion());
        Assert.assertEquals("Coat", item.toString());
    }

    /*general questions - without a data store I feel this application is a weird state machine.  I can't think of how to "expire"
    a promotion after 30 days other than to do so when a method is called actively so it checks the current date, in my case the toString method.

    This issue really shows in my last test above where I have to call toString in my Act in order to update the state for my assert
    against the promotion boolean.  I technically can have logic tied to commonly used methods like "reducePrice" so that always updates the state
    but that also seems wonky.
    */

    @Test
    public void ifPriceisReduced30OrMoreDaysAfterLastPriceChangeAndLastPriceChangeBeginDateDoesNotIntersectsWithLastPromotionEndDateANewPromotionBegins() {
        //Arrange
        Item item = new Item("Coat", 5.00);
        Double priceToReduceIsValidToBeginPromotion = 1.20;
        item.attemptPriceReduction(priceToReduceIsValidToBeginPromotion);
        Assert.assertEquals(true, item.promotion.getPromotion());

        LocalDate now = LocalDate.now();
        LocalDate beginPromoDate = now.minusDays(80);
        LocalDate testPriceChangeDate = now.minusDays(30);
        item.promotion.setBeginDateForTest(beginPromoDate);
        item.setLastPriceChangeDate(testPriceChangeDate);

        priceToReduceIsValidToBeginPromotion = .25;

        //Act
        item.attemptPriceReduction(priceToReduceIsValidToBeginPromotion);
        item.toString();

        //Assert
        Assert.assertEquals(3.55, item.getPrice(), 0);
        Assert.assertEquals(true, item.promotion.getPromotion());
        Assert.assertEquals("Coat (promotion)", item.toString());
    }

}

//for the reducePrice method, i refactored for "clarity".  One of the nested if's is confusing, I added an "else" that does nothing
//except provide a comment for clarity.  I've seen this done in code before.  Is this a good practice?