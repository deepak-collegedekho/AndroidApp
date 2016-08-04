package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Suresh Kumar on 10/2/16.
 */
public class VideoStats implements Parcelable {
    private int viewCount;
    private int likeCount;
    private int dislikeCount;
    private int favoriteCount;
    private int commentCount;

    protected VideoStats(Parcel in) {
        viewCount = in.readInt();
        likeCount = in.readInt();
        dislikeCount = in.readInt();
        favoriteCount = in.readInt();
        commentCount = in.readInt();
    }

    public VideoStats() {

    }

    public static final Creator<VideoStats> CREATOR = new Creator<VideoStats>() {
        @Override
        public VideoStats createFromParcel(Parcel in) {
            return new VideoStats(in);
        }

        @Override
        public VideoStats[] newArray(int size) {
            return new VideoStats[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(viewCount);
        dest.writeInt(likeCount);
        dest.writeInt(dislikeCount);
        dest.writeInt(favoriteCount);
        dest.writeInt(commentCount);
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
