package com.Model.ConfigModel;

import lombok.Data;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.CloseableHttpClient;

@Data
public class Http {
    public static CookieStore cookieStore;
    public static CloseableHttpClient httpClient;
}
