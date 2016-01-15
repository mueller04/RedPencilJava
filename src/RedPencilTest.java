import org.junit.*;

import java.time.LocalDate;


public class RedPencilTest {


    @Test
    public void whenanItemisMarkedAsAPromotionItReturnsPromotionInTheString(){
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.beginPromotion();

        //Assert
        Assert.assertEquals("Coat (promotion)", item.toString());
        Assert.assertEquals(true, item.getPromotion());
    }

    @Test
    public void whenThePriceIsReducedBy4Point99PercentThePromotionDoesNotBegin(){
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.reducePrice(0.24);

        //Assert
        Assert.assertEquals("Coat", item.toString());
    }

    @Test
    public void whenThePriceIsReducedBy5PercentThePromotionBegins()
    {
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.reducePrice(0.25);

        //Assert
        Assert.assertEquals("Coat (promotion)", item.toString());
        Assert.assertEquals(true, item.getPromotion());
    }

    @Test
    public void whenThePriceIsReducedByLargerPriceThanCurrentPricePromotionDoesNotBegin()
    {
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.reducePrice(5.01);

        //Assert
        Assert.assertEquals("Coat", item.toString());
        Assert.assertEquals(false, item.getPromotion());
        Assert.assertEquals(5.00, item.getPrice(), 0);
    }

    @Test
    public void whenThePriceIsReducedByEqualPriceToCurrentPricePromotionDoesNotBegin()
    {
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.reducePrice(5.00);

        //Assert
        Assert.assertEquals("Coat", item.toString());
        Assert.assertEquals(false, item.getPromotion());
        Assert.assertEquals(5.00, item.getPrice(), 0);
    }

    @Test
    public void whenPriceIsReducedBy5Point2PercentThePromotionBegins(){
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.reducePrice(0.26);

        //Assert
        Assert.assertEquals("Coat (promotion)", item.toString());
        Assert.assertEquals(true, item.getPromotion());
    }

    @Test
    public void whenPriceIsReducedThePriceChanges(){
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.reducePrice(0.26);

        //Assert
        Assert.assertEquals(4.74, item.getPrice(), 0);
    }

    @Test
    public void whenPriceIsReducedBy30PercentThePromotionBegins(){
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.reducePrice(1.50);

        //Assert
        Assert.assertEquals("Coat (promotion)", item.toString());
        Assert.assertEquals(true, item.getPromotion());
    }

    @Test
    public void whenPriceIsReducedBy30Point1PercentThePromotionDoesNotBegin(){
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.reducePrice(1.55);

        //Assert
        Assert.assertEquals("Coat", item.toString());
        Assert.assertEquals(false, item.getPromotion());
    }

    @Test
    public void whenAPromotionBeginsTheDateIsRecorded(){
        //Arrange
        Item item = new Item("Coat", 5.00);
        LocalDate expectedDate = LocalDate.now();

        //Act
        item.reducePrice(1);

        //Assert
        Assert.assertEquals(expectedDate, item.getPromotionBeginDate());
    }

    @Test
    public void whenNoPromotionTheDateIsNotRecorded(){
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.reducePrice(1.55);

        //Assert
        Assert.assertEquals(null, item.getPromotionBeginDate());
    }

    @Test
    public void whenPromotionDateIs31DaysOldNoPromotionText() {
        //Arrange
        Item item = new Item("Coat", 5.00);
        LocalDate now = LocalDate.now();
        LocalDate beginPromoDate = now.minusDays(31);

        //Act
        item.reducePrice(1.55);
        item.setBeginDateForTest(beginPromoDate);

        //Assert
        Assert.assertEquals("Coat", item.toString());
        Assert.assertEquals(false, item.getPromotion());
    }

    @Test
    public void whenPromotionDateIs30DaysOldPromotionTextDisplays() {
        //Arrange
        Item item = new Item("Coat", 5.00);
        LocalDate now = LocalDate.now();
        LocalDate beginPromoDate = now.minusDays(30);

        //Act
        item.reducePrice(1.20);
        item.setBeginDateForTest(beginPromoDate);

        //Assert
        Assert.assertEquals("Coat (promotion)", item.toString());
        Assert.assertEquals(true, item.getPromotion());
    }

    @Test
    public void whenPriceChangedWithin15DaysAndNoCurrentPromotionThenCannotBeginPromotion() {
        //Arrange
        Item item = new Item("Coat", 5.00);
        LocalDate now = LocalDate.now();
        LocalDate lastPriceChangeDate = now.minusDays(15);

        item.setLastPriceChangeDate(lastPriceChangeDate);

        //Act
        item.reducePrice(1.20);

        //Assert
        Assert.assertEquals(3.8, item.getPrice(), 0);
        Assert.assertEquals("Coat", item.toString());
        Assert.assertEquals(false, item.getPromotion());
    }

    @Test
    public void whenPriceChangedWithin15DaysCannotUpdatePromoDate() {
        //Arrange
        Item item = new Item("Coat", 5.00);
        LocalDate now = LocalDate.now();
        LocalDate beginPromoDate = now.minusDays(30);
        LocalDate lastPriceChangeDate = now.minusDays(15);

        item.setBeginDateForTest(beginPromoDate);
        item.setLastPriceChangeDate(lastPriceChangeDate);

        //Act
        item.reducePrice(1.20);

        //Assert
        Assert.assertEquals(beginPromoDate, item.getPromotionBeginDate());
        Assert.assertEquals(3.8, item.getPrice(), 0);
    }

    @Test
    public void when31DaysSinceLastPriceChangeCanCreateNewPromotion() {
        //Arrange
        Item item = new Item("Coat", 5.00);
        LocalDate now = LocalDate.now();
        LocalDate lastPriceChangeDate = now.minusDays(31);
        item.setLastPriceChangeDate(lastPriceChangeDate);

        //Act
        item.reducePrice(1.20);

        //Assert
        Assert.assertEquals(true, item.getPromotion());
        Assert.assertEquals(3.80, item.getPrice(), 0);
        Assert.assertEquals(now, item.getPromotionBeginDate());
    }

    @Test
    public void when30DaysSinceLastPriceChangeCanCreateNewPromotion() {
        //Arrange
        Item item = new Item("Coat", 5.00);
        LocalDate now = LocalDate.now();
        LocalDate lastPriceChangeDate = now.minusDays(30);
        item.setLastPriceChangeDate(lastPriceChangeDate);

        //Act
        item.reducePrice(1.20);

        //Assert
        Assert.assertEquals(true, item.getPromotion());
        Assert.assertEquals(3.80, item.getPrice(), 0);
        Assert.assertEquals(now, item.getPromotionBeginDate());
    }

   @Test
   public void whenPriceIsReducedDuringPromotionThePromotionIsNotProlonged() {
       //Arrange
       Item item = new Item("Coat", 5.00);
       Double priceToReduceIsValidToBeginPromotion = 1.20;

       item.reducePrice(priceToReduceIsValidToBeginPromotion);

       LocalDate now = LocalDate.now();
       LocalDate beginPromoDate = now.minusDays(15);;
       item.setBeginDateForTest(beginPromoDate);
       item.setLastPriceChangeDate(beginPromoDate);

       //Act
       priceToReduceIsValidToBeginPromotion = .25;
       item.reducePrice(priceToReduceIsValidToBeginPromotion);

       //Assert
       Assert.assertEquals(true, item.getPromotion());
       Assert.assertEquals(3.55, item.getPrice(), 0);
       Assert.assertEquals(beginPromoDate, item.getPromotionBeginDate());
   }

    @Test
    public void whenPriceIsIncreasedCurrentPromotionEnds() {
        //Arrange
        Item item = new Item("Coat", 5.00);
        Double priceToReduceIsValidToBeginPromotion = 1.20;
        item.reducePrice(priceToReduceIsValidToBeginPromotion);

        //Act
        item.increasePrice(.02);

        //Assert
        Assert.assertEquals(false, item.getPromotion());
        Assert.assertEquals("Coat", item.toString());
        Assert.assertEquals(3.82, item.getPrice(), 0);
    }

}
