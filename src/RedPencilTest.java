import org.junit.Test.*;
import org.junit.Assert.*;
import org.junit.*;


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
    public void whenPriceIsReducedBy5Point4PercentThePromotionBegins(){
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

}
