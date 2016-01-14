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
    }

    @Test
    public void whenPriceIsReducedBy5Point2PercentThePromotionBegins(){
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.reducePrice(0.26);

        //Assert
        Assert.assertEquals("Coat (promotion)", item.toString());
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
    }

    @Test
    public void whenPriceIsReducedBy30Point1PercentThePromotionDoesNotBegin(){
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.reducePrice(1.55);

        //Assert
        Assert.assertEquals("Coat", item.toString());
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

        //Act
        item.reducePrice(1.55);
        LocalDate testBeginDate = LocalDate.of(2015, 11, 30);
        LocalDate testCurrentDate = LocalDate.of(2015, 12, 31);
        item.setupTestDates(testBeginDate, testCurrentDate);


        //Assert
        Assert.assertEquals("Coat", item.toString());
    }

    @Test
    public void whenPromotionDateIs30DaysOldPromotionTextDisplays() {
        //Arrange

        Item item = new Item("Coat", 5.00);

        //Act
        item.reducePrice(1.20);
        LocalDate testBeginDate = LocalDate.of(2015, 11, 30);
        LocalDate testCurrentDate = LocalDate.of(2015, 12, 30);
        item.setupTestDates(testBeginDate, testCurrentDate);


        //Assert
        Assert.assertEquals("Coat (promotion)", item.toString());
    }

}
