package com.vilkov.PriceMonitoring.model.parsers;

import com.vilkov.PriceMonitoring.model.entity.Product;
import com.vilkov.PriceMonitoring.model.parsers.dnsParser.CitilinkParser;
import com.vilkov.PriceMonitoring.model.parsers.globusParser.GlobusParser;
import com.vilkov.PriceMonitoring.model.parsers.perekrestokParser.PerekrestokParser;

public class ParserHelper {
    public static final String GLOBUS = "www.globus.ru";
    public static final String PEREKRESTOK = "www.perekrestok.ru";
    public static final String CITILINK = "/www.citilink.ru";
    public static Product getProduct(String url) {

        if (url.contains(GLOBUS))
            return GlobusParser.getProduct(url);

        if (url.contains(PEREKRESTOK))
            return PerekrestokParser.getProduct(url);

        if (url.contains(CITILINK))
            return CitilinkParser.getProduct(url);

        return null;
    }
}
