package com.kaplandroid.githubrepolistclient.restapi.model.response;

import android.content.Context;

import com.kaplandroid.githubrepolistclient.util.DateUtil;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by KAPLANDROID on 2.09.2016.
 */

public class RepoInfo implements Serializable {
    private String name;
    private String description;
    private Date updated_at;
    private String language;
    private String homepage;

    //There are more values that included to this class but only needed values parsed

    public String getShortInfo(Context ctx) {
        return "Name: " + getName() + "\n\n" +
                "Language: " + getLanguage() + "\n\n" +
                "HomePage: " + getHomepage() + "\n\n" +
                "Description: " + getDescription() + "\n\n" +
                "Last Update: " + DateUtil.getUpdateTimeText(ctx, getUpdated_at(), Calendar.getInstance().getTime());
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return "RepoInfo{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", updated_at=" + updated_at +
                ", language='" + language + '\'' +
                ", homepage='" + homepage + '\'' +
                '}';
    }
}