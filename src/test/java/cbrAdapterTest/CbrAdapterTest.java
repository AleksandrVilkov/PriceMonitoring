package cbrAdapterTest;

import com.vilkov.PriceMonitoring.cbrAdapter.CbrAdapter;
import com.vilkov.PriceMonitoring.model.entity.Valute;
import org.junit.Test;

import java.util.List;

public class CbrAdapterTest {
    @Test
    public void getRateTest() {
        CbrAdapter cbrAdapter = new CbrAdapter();
      List<Valute> valutes =  cbrAdapter.getValutes();
    }
}
