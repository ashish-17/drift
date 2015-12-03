package edu.ru.cs512.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "page_trends")
public class PageTrend {

    @Id
    private String pageTitle;

    @Field("201511")
    private List<PageData> pageData;

    private static class PageData {

        @Field("date")
        private String date;

        @Field("viewCount")
        private String viewCount;

        @Field("trend")
        private String trend;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getViewCount() {
            return viewCount;
        }

        public void setViewCount(String viewCount) {
            this.viewCount = viewCount;
        }

        public String getTrend() {
            return trend;
        }

        public void setTrend(String trend) {
            this.trend = trend;
        }
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public List<PageData> getPageData() {
        return pageData;
    }

    public void setPageData(List<PageData> pageData) {
        this.pageData = pageData;
    }

    public void sort() {
        Collections.sort(this.pageData, new Comparator<PageData>() {
            @Override
            public int compare(PageData pd1, PageData pd2) {
                return pd1.getDate().compareTo(pd2.getDate());
            }
        });
    }
}
