package com.collegedekho.app.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author Suresh Kumar
 *         Created: 03/07/15
 */
public class Articles implements Parcelable{


    public static final Creator<Articles> CREATOR = new Creator<Articles>() {
        @Override
        public Articles createFromParcel(Parcel source) {  return new Articles(source);   }

        @Override
        public Articles[] newArray(int size) {  return new Articles[size];   }
    };

    public String title;
    public String content;
    public String image;
    public long template_type;
    public String published_on;
    public String similar_articles;
    public int id;
    public String stream;
    private ArrayList<String> similarArticlesIds ;

     public  Articles() {
     }

    protected Articles(Parcel source) {
        title = source.readString();
        content = source.readString();
        image = source.readString();
        template_type = source.readLong();
        published_on = source.readString();
        similar_articles = source.readString();
        stream = source.readString();
        id  =   source.readInt();
    }
    @Override
    public int describeContents() {   return 0;  }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(image);
        dest.writeLong(template_type);
        dest.writeString(published_on);
        dest.writeString(similar_articles);
        dest.writeString(stream);
        dest.writeInt(id);
    }


    public void setTitle(String title) {  this.title = title;   }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTemplate_type(long template_type) {
        this.template_type = template_type;
    }

    public void setPublished_on(String published_on) {
        this.published_on = published_on;
    }

    public int getId() {  return id;  }

    public void setId(int id) { this.id = id;  }

    public String getSimilar_articles() {  return similar_articles;   }

    public void setSimilar_articles(String similar_articles) { this.similar_articles = similar_articles;   }

    public ArrayList<String> getSimilarArticlesIds() {      return similarArticlesIds;   }

    public void setSimilarArticlesIds(ArrayList<String> similarArticlesIds) {  this.similarArticlesIds = similarArticlesIds;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }
}
