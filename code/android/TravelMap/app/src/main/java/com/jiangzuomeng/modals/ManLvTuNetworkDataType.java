package com.jiangzuomeng.modals;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wilbert on 2015/12/13.
 */
public abstract class ManLvTuNetworkDataType {
    public URL getUrl(String key) throws MalformedURLException {
        URL url = null;
        switch (key) {
            case StaticStrings.ADD:
                url = getAddUrl();
                break;
            case StaticStrings.UPDATE:
                url =  getUpdateUrl();
                break;
            case StaticStrings.REMOVE:
                url = getRemoveUrl();
                break;
            case StaticStrings.QUERY:
                url = getQueryUrl();
                break;
        }
        return url;
    }

    public abstract URL getAddUrl() throws MalformedURLException;
    public abstract URL getQueryUrl();
    public abstract URL getUpdateUrl() throws MalformedURLException;
    public abstract URL getRemoveUrl() throws MalformedURLException;
}
