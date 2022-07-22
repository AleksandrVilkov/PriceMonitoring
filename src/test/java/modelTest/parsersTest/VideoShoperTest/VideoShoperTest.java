package modelTest.parsersTest.VideoShoperTest;

import com.vilkov.PriceMonitoring.model.entity.Product;
import com.vilkov.PriceMonitoring.model.parsers.VideoShoper.VideoShoperParser;
import org.junit.Test;

public class VideoShoperTest {
    @Test
    public void getProductTest() {
        VideoShoperParser videoShoperParser = new VideoShoperParser();
        Product product = videoShoperParser.getProduct("https://video-shoper.ru/shipment/robot-pylesos-roborock-q7-max-plus-global-black.html");
        System.out.println(product);
    }
}
