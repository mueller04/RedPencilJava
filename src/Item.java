public class Item {

    public String displayText;

    public Item(String displayText){
        this.displayText = displayText;
    }

    public void beginPromotion(){
        this.displayText = displayText + " " + "(promotion)";
    }

    @Override
    public String toString(){
        return displayText;
    }
}
