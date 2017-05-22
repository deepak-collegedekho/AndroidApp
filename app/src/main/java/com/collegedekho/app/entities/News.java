package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author Mayank Gautam
 *         Created: 03/07/15
 */
public class News implements Parcelable {
    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel source) {
            return new News(source);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };
    public String title;
    public String content;
    public String image;
    public String web_resource_uri;
    public long template_type;
    public String published_on;
    public String similar_news;
    public int id;
    public String stream;
    public String slug;
    public String uri_id;
    private int is_study_abroad = 0;

    public int getNews_source() {
        return news_source;
    }

    public void setNews_source(int news_source) {
        this.news_source = news_source;
    }

    public int news_source;
    private ArrayList<String> similarNewsIds ;

    public News() {
    }

    public News(Parcel source) {
        title = source.readString();
        content = source.readString();
        image = source.readString();
        web_resource_uri = source.readString();
        template_type = source.readLong();
        published_on = source.readString();
        id  =   source.readInt();
        similar_news = source.readString();
        stream = source.readString();
        slug = source.readString();
        uri_id = source.readString();
        news_source=source.readInt();
        is_study_abroad = source.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(image);
        dest.writeString(web_resource_uri);
        dest.writeLong(template_type);
        dest.writeString(published_on);
        dest.writeString(similar_news);
        dest.writeInt(id);
        dest.writeString(stream);
        dest.writeString(slug);
        dest.writeString(uri_id);
        dest.writeInt(news_source);
        dest.writeInt(is_study_abroad);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWeb_resource_uri() {
        return web_resource_uri;
    }

    public void setWeb_resource_uri(String web_resource_uri) {
        this.web_resource_uri = web_resource_uri;
    }

    public void setTemplate_type(long template_type) {
        this.template_type = template_type;
    }

    public void setPublished_on(String published_on) {
        this.published_on = published_on;
    }

    public int getId() {  return id;  }

    public void setId(int id) { this.id = id;  }

    public String getSimilar_news() { return similar_news;  }

    public void setSimilar_news(String similar_news) { this.similar_news = similar_news;   }

    public ArrayList<String> getSimilarNewsIds() {  return similarNewsIds;  }

    public void setSimilarNewsIds(ArrayList<String> similarNewsIds) { this.similarNewsIds = similarNewsIds;   }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getUri_id() {
        return uri_id;
    }

    public void setUri_id(String uri_id) {
        this.uri_id = uri_id;
    }

    public int getIs_study_abroad() {
        return is_study_abroad;
    }

    public void setIs_study_abroad(int is_study_abroad) {
        this.is_study_abroad = is_study_abroad;
    }
}
