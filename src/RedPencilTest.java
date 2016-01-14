import org.junit.Test.*;
import org.junit.Assert.*;
import org.junit.*;


public class RedPencilTest {

    @Test
    public void whenanItemisMarkedAsAPromotionItReturnsPromotionInTheString(){
        //Arrange
        Item item = new Item("Coat");

        //Act
        item.beginPromotion();

        //Assert
        Assert.assertEquals("Coat (promotion)", item.toString());


    }

}
