package com.qmul.Social.Network.utils;

public class HelperUtil {

    public static String getImageUrl(long filesize, Long imageId, String imageUrl)
    {
        String baseUrl = AuthUtil.getBaseUrl();
        try
        {
            long size = filesize;
            if(size > 0)
            {
                String url = baseUrl+imageUrl;
                url = url.replace("{id}", imageId.toString());
                return url;
            }
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
        return null;
    }

}
