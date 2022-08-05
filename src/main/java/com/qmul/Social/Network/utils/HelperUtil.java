package com.qmul.Social.Network.utils;

import com.qmul.Social.Network.model.persistence.PostImage;

public class HelperUtil {

    public static final String imageUrl = "/api/post/{id}/image";

    public static String getImageUrl(PostImage image, Long postId)
    {
        String baseUrl = AuthUtil.getBaseUrl();
        try
        {
            long size = image.getFileSize();
            if(size > 0)
            {
                String url = baseUrl+imageUrl;
                url = url.replace("{id}", postId.toString());
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
