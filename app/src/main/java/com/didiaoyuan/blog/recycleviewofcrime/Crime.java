package com.didiaoyuan.blog.recycleviewofcrime;

import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Mr.Qu on 2017/9/10.
 */

public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private String mPeopleName;
    public Crime() {
//        mId = UUID.randomUUID();
//        mDate = new Date();
        this(UUID.randomUUID());
    }

    public Crime(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public String getPeopleName() {
        return mPeopleName;
    }

    public void setPeopleName(String peopleName) {
        mPeopleName = peopleName;
    }
/*获取照片名称*/
    public String getPhotoFilename(){
        return "IMG_"+getId().toString()+".jpg";
    }
}
