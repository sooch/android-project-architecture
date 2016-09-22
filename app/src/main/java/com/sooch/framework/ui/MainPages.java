package com.sooch.framework.ui;

import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.sooch.framework.R;
import com.sooch.framework.ui.fragment.FragmentCamera;
import com.sooch.framework.ui.fragment.FragmentGallery;

/**
 * Created by Takashi Sou on 2016/06/20.
 */
public enum MainPages {

    CAMERA(R.id.nav_camera, R.string.title_camera, FragmentCamera.class.getSimpleName()) {
        @Override
        public Fragment createFragment() {
            return FragmentCamera.newInstance();
        }
    },
    GALLERY(R.id.nav_gallery, R.string.title_gallery, FragmentGallery.class.getSimpleName()) {
        @Override
        public Fragment createFragment() {
            return FragmentGallery.newInstance();
        }
    }
    ;

    private final int menuId;
    private final int titleResId;
    private final String pageName;

    MainPages(int menuId, int titleResId, String pageName) {
        this.menuId = menuId;
        this.titleResId = titleResId;
        this.pageName = pageName;
    }

    /**
     * {@link #forMenuId(MenuItem)}
     * @param item
     * @return
     */
    public static MainPages forMenuId(MenuItem item) {
        return forMenuId(item.getItemId());
    }

    /**
     * メニューIDから逆引きする
     * @param id
     * @return
     */
    public static MainPages forMenuId(int id) {
        for (MainPages page : values()) {
            if (page.menuId == id) {
                return page;
            }
        }
        throw new AssertionError("no menu enum found for the id. you forgot to implement?");
    }

    /**
     * {@link #forName(String)}
     * @param fragment
     * @return
     */
    public static MainPages forName(Fragment fragment) {
        return forName(fragment.getClass().getSimpleName());
    }

    /**
     * Fragment名から逆引きする
     * @param name
     * @return
     */
    public static MainPages forName(String name) {
        for (MainPages page : values()) {
            if (page.pageName.equals(name)) {
                return page;
            }
        }
        throw new AssertionError("no menu enum found for the id. you forgot to implement?");
    }

    /**
     * メニューIDを取得する
     * @return
     */
    public int getMenuId() {
        return menuId;
    }

    /**
     * タイトルリソースを取得する
     * @return
     */
    public int getTitleResId() {
        return titleResId;
    }

    /**
     * ページ名を取得する
     * @return
     */
    public String getPageName() {
        return pageName;
    }

    /**
     * Fragmentの生成
     * @return
     */
    public abstract Fragment createFragment();
}
