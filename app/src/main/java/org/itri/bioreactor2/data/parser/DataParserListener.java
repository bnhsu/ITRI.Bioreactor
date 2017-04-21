package org.itri.bioreactor2.data.parser;

import java.util.Map;

/**
 * Created by norman on 4/25/14.
 */
public interface DataParserListener {
    public void dataParsed(Map<String, String> data);
}
