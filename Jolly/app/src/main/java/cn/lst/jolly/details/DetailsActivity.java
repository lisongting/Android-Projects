package cn.lst.jolly.details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.lst.jolly.R;
import cn.lst.jolly.data.ContentType;
import cn.lst.jolly.data.source.local.DoubanMomentContentLocalDataSource;
import cn.lst.jolly.data.source.local.DoubanMomentNewsLocalDataSource;
import cn.lst.jolly.data.source.local.GuokrHandpickContentLocalDataSource;
import cn.lst.jolly.data.source.local.GuokrHandpickNewsLocalDataSource;
import cn.lst.jolly.data.source.local.ZhihuDailyContentLocalDataSource;
import cn.lst.jolly.data.source.local.ZhihuDailyNewsLocalDataSource;
import cn.lst.jolly.data.source.remote.DoubanMomentContentRemoteDataSource;
import cn.lst.jolly.data.source.remote.DoubanMomentNewsRemoteDataSource;
import cn.lst.jolly.data.source.remote.GuokrHandpickContentRemoteDataSource;
import cn.lst.jolly.data.source.remote.GuokrHandpickNewsRemoteDataSource;
import cn.lst.jolly.data.source.remote.ZhihuDailyContentRemoteDataSource;
import cn.lst.jolly.data.source.remote.ZhihuDailyNewsRemoteDataSource;
import cn.lst.jolly.data.source.repository.DoubanMomentContentRepository;
import cn.lst.jolly.data.source.repository.DoubanMomentNewsRepository;
import cn.lst.jolly.data.source.repository.GuokrHandpickContentRepository;
import cn.lst.jolly.data.source.repository.GuokrHandpickNewsRepository;
import cn.lst.jolly.data.source.repository.ZhihuDailyContentRepository;
import cn.lst.jolly.data.source.repository.ZhihuDailyNewsRepository;

/**
 * Created by lisongting on 2018/1/2.
 */

public class DetailsActivity extends AppCompatActivity {
    public static final String KEY_ARTICLE_TYPE = "KEY_ARTICLE_TYPE";
    public static final String KEY_ARTICLE_ID = "KEY_ARTICLE_ID";
    public static final String KEY_ARTICLE_TITLE = "KEY_ARTICLE_TITLE";
    public static final String KEY_ARTICLE_IS_FAVORITE = "KEY_ARTICLE_IS_FAVORITE";

    private DetailsFragment mDetailsFragment;
    private ContentType mType;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);
        if (savedInstanceState != null) {
            mDetailsFragment = (DetailsFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, DetailsFragment.class.getSimpleName());
        } else {
            mDetailsFragment = DetailsFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, mDetailsFragment, DetailsFragment.class.getSimpleName())
                    .commit();
        }
        mType = (ContentType) getIntent().getSerializableExtra(KEY_ARTICLE_TYPE);
        if (mType == ContentType.TYPE_ZHIHU_DAILY) {
            new DetailsPresenter(mDetailsFragment,
                    ZhihuDailyNewsRepository.getInstance(ZhihuDailyNewsRemoteDataSource.getInstance(),
                            ZhihuDailyNewsLocalDataSource.getInstance(DetailsActivity.this)),
                    ZhihuDailyContentRepository.getInstance(ZhihuDailyContentRemoteDataSource.getInstance(),
                            ZhihuDailyContentLocalDataSource.getInstance(DetailsActivity.this)));
        }  else if (mType == ContentType.TYPE_DOUBAN_MOMENT) {
            new DetailsPresenter(mDetailsFragment,
                    DoubanMomentNewsRepository.getInstance(DoubanMomentNewsRemoteDataSource.getInstance(),
                            DoubanMomentNewsLocalDataSource.getInstance(DetailsActivity.this)),
                    DoubanMomentContentRepository.getInstance(DoubanMomentContentRemoteDataSource.getInstance(),
                            DoubanMomentContentLocalDataSource.getInstance(DetailsActivity.this)));
        } else if (mType == ContentType.TYPE_GUOKR_HANDPICK) {
            new DetailsPresenter(mDetailsFragment,
                    GuokrHandpickNewsRepository.getInstance(GuokrHandpickNewsRemoteDataSource.getInstance(),
                            GuokrHandpickNewsLocalDataSource.getInstance(DetailsActivity.this)),
                    GuokrHandpickContentRepository.getInstance(GuokrHandpickContentRemoteDataSource.getInstance(), GuokrHandpickContentLocalDataSource.getInstance(DetailsActivity.this)));
        }
    }

    public void onDestroy(){
        super.onDestroy();
        ZhihuDailyContentRepository.destroyInstance();
        DoubanMomentContentRepository.destroyInstance();
        GuokrHandpickContentRepository.destroyInstance();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mDetailsFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, DetailsFragment.class.getSimpleName(), mDetailsFragment);
        }
    }
}
