package org.itri.bioreactor2.data.parser;

/**
 * Created by norman on 4/25/14.
 */
public interface DataParser {
    public void addListener(DataParserListener listener);
    public void parseData(byte[] data, int len);
    public void disconnect();
}
