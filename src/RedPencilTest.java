import org.junit.*;
import sun.util.resources.en.CalendarData_en;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;


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
        Assert.assertEquals(expectedDate, item.getDate());
    }

    @Test
    public void whenNoPromotionTheDateIsNotRecorded(){
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.reducePrice(1.55);

        //Assert
        Assert.assertEquals(null, item.getDate());
    }

    @Test
    public void whenPromotionDateIs31DaysOldNoPromotionText() {
        //Arrange
        Item item = new Item("Coat", 5.00);
        LocalDate now = LocalDate.now();
        LocalDate beginPromoDate = now.minusDays(31);

        //Act
        item.reducePrice(1.55);
        item.setupTestDates(beginPromoDate);

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
        item.setupTestDates(beginPromoDate);

        //Assert
        Assert.assertEquals("Coat (promotion)", item.toString());
        Assert.assertEquals(true, item.getPromotion());
    }

}
