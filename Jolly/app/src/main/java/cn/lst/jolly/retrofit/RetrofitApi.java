package cn.lst.jolly.retrofit;

import cn.lst.jolly.data.DoubanMomentContent;
import cn.lst.jolly.data.DoubanMomentNews;
import cn.lst.jolly.data.GuokrHandpickContent;
import cn.lst.jolly.data.GuokrHandpickNews;
import cn.lst.jolly.data.ZhihuDailyContent;
import cn.lst.jolly.data.ZhihuDailyNews;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by lisongting on 2017/12/18.
 */

public interface RetrofitApi {

    String ZHIHU_DAILY_BASE = "https://news-at.zhihu.com/api/4/news";

    String DOUBAN_MOMENT_BASE = "https://moment.douban.com/api/";

    String GUOKR_HANDPICK_BASE = "http://apis.guokr.com/minisite/";

    interface ZhihuDailyService{
        @GET("before/{data}")
        Call<ZhihuDailyNews> getZhihuList(@Path("date") String date);

        @GET("{id}")
        Call<ZhihuDailyContent> getZhihuContent(@Path("id") int id);
    }

    interface DoubanMomentService{
        @GET("stream/date/{date}")
        Call<DoubanMomentNews> getDoubanList(@Path("date") String date);

        @GET("post/{id}")
        Call<DoubanMomentContent> getDoubanMoment(@Path("id") int id);
    }

    interface GuokrHandpickService{
        @GET("article.json?retrieve_type=by_minisite")
        Call<GuokrHandpickNews> getGuokrHandpick(@Query("offset") int offset, @Query("limit") int limit);

        @GET("article/{id}.json")
        Call<GuokrHandpickContent> getGuokrContent(@Path("id") int id);
    }
}
