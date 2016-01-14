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

    @Test public void whenThePriceIsReducedBy4Point99PercentThePromotionDoesNotBegin(){
        //Arrange
        Item item = new Item("Coat", 5.00);

        //Act
        item.reducePrice(0.24);

        //Assert
        Assert.assertEquals("Coat", item.toString());
    }

    

}
