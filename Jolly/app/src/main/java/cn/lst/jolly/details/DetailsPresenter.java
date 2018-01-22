package cn.lst.jolly.details;

import android.support.annotation.NonNull;

import cn.lst.jolly.R;
import cn.lst.jolly.data.ContentType;
import cn.lst.jolly.data.DoubanMomentContent;
import cn.lst.jolly.data.DoubanMomentNewsPosts;
import cn.lst.jolly.data.GuokrHandpickContentResult;
import cn.lst.jolly.data.ZhihuDailyContent;
import cn.lst.jolly.data.source.datasource.DoubanMomentContentDataSource;
import cn.lst.jolly.data.source.datasource.DoubanMomentNewsDataSource;
import cn.lst.jolly.data.source.datasource.GuokrHandpickContentDataSource;
import cn.lst.jolly.data.source.datasource.ZhihuDailyContentDataSource;
import cn.lst.jolly.data.source.repository.DoubanMomentContentRepository;
import cn.lst.jolly.data.source.repository.DoubanMomentNewsRepository;
import cn.lst.jolly.data.source.repository.GuokrHandpickContentRepository;
import cn.lst.jolly.data.source.repository.GuokrHandpickNewsRepository;
import cn.lst.jolly.data.source.repository.ZhihuDailyContentRepository;
import cn.lst.jolly.data.source.repository.ZhihuDailyNewsRepository;

/**
 * Created by lisongting on 2018/1/16.
 */

public class DetailsPresenter implements DetailsContract.Presenter{
    private final DetailsContract.View mView;
    private DoubanMomentNewsRepository mDoubanNewsRepository;
    private DoubanMomentContentRepository mDoubanContentRepository;
    private ZhihuDailyNewsRepository mZhihuNewsRepository;
    private ZhihuDailyContentRepository mZhihuContentRepository;
    private GuokrHandpickNewsRepository mGuokrNewsRepository;
    private GuokrHandpickContentRepository mGuokrContentRepository;

    public DetailsPresenter(DetailsContract.View view,
                            DoubanMomentNewsRepository doubanMomentNewsRepository,
                            DoubanMomentContentRepository doubanMomentContentRepository) {
        this.mView = view;
        this.mDoubanNewsRepository = doubanMomentNewsRepository;
        this.mDoubanContentRepository = doubanMomentContentRepository;
        mView.setPresenter(this);
    }
    public DetailsPresenter(@NonNull DetailsContract.View view,
                            @NonNull ZhihuDailyNewsRepository zhihuNewsRepository,
                            @NonNull ZhihuDailyContentRepository zhihuContentRepository) {
        this.mView = view;
        mView.setPresenter(this);
        mZhihuNewsRepository = zhihuNewsRepository;
        mZhihuContentRepository = zhihuContentRepository;
    }

    public DetailsPresenter(@NonNull DetailsContract.View view,
                            @NonNull GuokrHandpickNewsRepository guokrNewsRepository,
                            @NonNull GuokrHandpickContentRepository guokrContentRepository) {
        this.mView = view;
        this.mView.setPresenter(this);
        mGuokrNewsRepository = guokrNewsRepository;
        mGuokrContentRepository = guokrContentRepository;
    }

    @Override
    public void start() {

    }

    @Override
    public void favorite(ContentType type, int id, boolean favorite) {
        if (type == ContentType.TYPE_ZHIHU_DAILY) {
            mZhihuNewsRepository.favoriteItem(id, favorite);
        } else if (type == ContentType.TYPE_DOUBAN_MOMENT) {
            mDoubanNewsRepository.favoriteItem(id, favorite);
        } else {
            mGuokrNewsRepository.favoriteItem(id, favorite);
        }
    }

    @Override
    public void loadDoubanContent(final int  id) {
        mDoubanContentRepository.getDoubanMomentContent(id, new DoubanMomentContentDataSource.LoadDoubanMomentContentCallback() {
            @Override
            public void onContentLoaded(@NonNull final DoubanMomentContent content) {
                mDoubanNewsRepository.getItem(id, new DoubanMomentNewsDataSource.GetNewsItemCallback() {
                    @Override
                    public void onItemLoaded(@NonNull DoubanMomentNewsPosts item) {
                        if (mView.isActive()) {
                            mView.showDoubanMomentContent(content, item.getThumbs());
                        }
                    }

                    @Override
                    public void onDataNotAvailable() {
                        if (mView.isActive()) {
                            mView.showMessage(R.string.something_wrong);
                        }

                    }
                });

            }

            @Override
            public void onDataNotAvailable() {
                if (mView.isActive()) {
                    mView.showMessage(R.string.something_wrong);
                }
            }
        });
    }

    @Override
    public void loadZhihuDailyContent(int id) {
        mZhihuContentRepository.getZhihuDailyContent(id, new ZhihuDailyContentDataSource.LoadZhihuDailyContentCallback() {
            @Override
            public void onContentLoaded(@NonNull ZhihuDailyContent content) {
                if (mView.isActive()) {
                    mView.showZhihuDailyContent(content);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (mView.isActive()) {
                    mView.showMessage(R.string.something_wrong);
                }
            }
        });
    }


    @Override
    public void loadGuokrHandpickContent(int id) {
        mGuokrContentRepository.getGuokrHandpickContent(id, new GuokrHandpickContentDataSource.LoadGuokrHandpickContentCallback() {
            @Override
            public void onContentLoaded(@NonNull GuokrHandpickContentResult content) {
                if (mView.isActive()) {
                    mView.showGuokrHandpickContent(content);
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (mView.isActive()) {
                    mView.showMessage(R.string.something_wrong);
                }
            }
        });
    }

    @Override
    public void getLink(ContentType type, final int requestCode, int id) {
        switch (type) {
            case TYPE_ZHIHU_DAILY:
                mZhihuContentRepository.getZhihuDailyContent(id, new ZhihuDailyContentDataSource.LoadZhihuDailyContentCallback() {
                    @Override
                    public void onContentLoaded(@NonNull ZhihuDailyContent content) {
                        if (mView.isActive()) {
                            String url = content.getShareUrl();
                            if (requestCode == DetailsFragment.REQUEST_SHARE) {
                                mView.share(url);
                            } else if (requestCode == DetailsFragment.REQUEST_COPY_LINK) {
                                mView.copyLink(url);
                            } else if(requestCode == DetailsFragment.REQUEST_OPEN_WITH_BROWSER){
                                mView.openWithBrowser(url);
                            }
                        }
                    }

                    @Override
                    public void onDataNotAvailable() {
                        if (mView.isActive()) {
                            mView.showMessage(R.string.share_error);
                        }

                    }
                });
                break;
            case TYPE_DOUBAN_MOMENT:
                mDoubanContentRepository.getDoubanMomentContent(id, new DoubanMomentContentDataSource.LoadDoubanMomentContentCallback() {
                    @Override
                    public void onContentLoaded(@NonNull DoubanMomentContent content) {
                        if (mView.isActive()) {
                            String url = content.getUrl();
                            if (requestCode == DetailsFragment.REQUEST_SHARE) {
                                mView.share(url);
                            } else if (requestCode == DetailsFragment.REQUEST_COPY_LINK) {
                                mView.copyLink(url);
                            } else if (requestCode == DetailsFragment.REQUEST_OPEN_WITH_BROWSER) {
                                mView.openWithBrowser(url);
                            }
                        }
                    }

                    @Override
                    public void onDataNotAvailable() {
                        if (mView.isActive()) {
                            mView.showMessage(R.string.share_error);
                        }
                    }
                });
                break;
            case TYPE_GUOKR_HANDPICK:
                mGuokrContentRepository.getGuokrHandpickContent(id, new GuokrHandpickContentDataSource.LoadGuokrHandpickContentCallback() {
                    @Override
                    public void onContentLoaded(@NonNull GuokrHandpickContentResult content) {
                        if (mView.isActive()) {
                            String url = content.getUrl();
                            if (requestCode == DetailsFragment.REQUEST_SHARE) {
                                mView.share(url);
                            } else if (requestCode == DetailsFragment.REQUEST_COPY_LINK) {
                                mView.copyLink(url);
                            } else if (requestCode == DetailsFragment.REQUEST_OPEN_WITH_BROWSER) {
                                mView.openWithBrowser(url);
                            }
                        }
                    }

                    @Override
                    public void onDataNotAvailable() {
                        if (mView.isActive()) {
                            mView.showMessage(R.string.share_error);
                        }
                    }
                });
                break;
        }
    }
}
