package com.vankien96.mooview.data.datasource;

import com.vankien96.mooview.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 12/26/17.
 */

public final class AdsDataSoure {

    private static String[] mArrayImageAds = {
            "9KqhygG22XaAkDoZavDpc5vNNry.jpg", "mVr0UiqyltcfqxbAUcLl9zWL8ah.jpg",
            "xydu6XhKVFf5FomLi9diWjoWc5d.jpg", "wVTYlkKPKrljJfugXN7UlLNjtuJ.jpg",
            "uExPmkOHJySrbJyJDJylHDqaT58.jpg", "bd1X5nNrrAHVGG0MxsAeCOPPh1w.jpg"
    };

    private AdsDataSoure() {
        //no-op
    }

    public static List<String> getListAds() {
        List<String> listAds = new ArrayList<>();
        for (int i = 0; i < mArrayImageAds.length; i++) {
            listAds.add(StringUtils.convertPosterPathToUrlPoster(mArrayImageAds[i]));
        }
        return listAds;
    }
}
