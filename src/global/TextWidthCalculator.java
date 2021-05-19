package global;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class TextWidthCalculator {
    static final Text helper;
    static final double DEFAULT_WRAPPING_WIDTH;
    static final double DEFAULT_LINE_SPACING;
    static final String DEFAULT_TEXT;
    static final TextBoundsType DEFAULT_BOUNDS_TYPE;

    static {
        helper = new Text();
        DEFAULT_WRAPPING_WIDTH = helper.getWrappingWidth();
        DEFAULT_LINE_SPACING = helper.getLineSpacing();
        DEFAULT_TEXT = helper.getText();
        DEFAULT_BOUNDS_TYPE = helper.getBoundsType();
    }

    public static TextWidthCalculator getInstance(){
        if(instance == null) instance = new TextWidthCalculator();
        return instance;
    }
    private TextWidthCalculator(){};

    static private TextWidthCalculator instance;
    private Font name;
    private Font other;
    private double offset;

    public void setName(Font name) {
        this.name = name;
    }

    public void setOther(Font other) {
        this.other = other;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public double computeTextWidthName(String text){
        return this.computeTextWidth(name,text,offset);
    }
    public double computeTextWidthOther(String text){
        return this.computeTextWidth(other,text,offset);
    }

    private double computeTextWidth(Font font,String text,double offset) {

        if(font!=null) System.out.println(font.getFamily()+" " + text);

        helper.setText(text);
        helper.setFont(font);

        helper.setWrappingWidth(0.0D);
        helper.setLineSpacing(0.0D);
        double d = helper.prefWidth(-1.0D);
        helper.setWrappingWidth((int) Math.ceil(d));
        d = Math.ceil(helper.getLayoutBounds().getWidth());

        helper.setWrappingWidth(DEFAULT_WRAPPING_WIDTH);
        helper.setLineSpacing(DEFAULT_LINE_SPACING);
        helper.setText(DEFAULT_TEXT);
        return d+offset;
    }
}



