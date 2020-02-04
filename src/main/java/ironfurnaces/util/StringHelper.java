package ironfurnaces.util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class StringHelper {


    public static List<String> displayEnergy(int energy, int capacity) {
        List<String> text = new ArrayList<String>();
        NumberFormat format = NumberFormat.getInstance();
        String i = format.format(energy);
        String j = format.format(capacity);
        i = i.replaceAll("\u00A0", " ");
        j = j.replaceAll("\u00A0", " ");
        text.add(i + " / " + j + " RF");
        return text;
    }

}
