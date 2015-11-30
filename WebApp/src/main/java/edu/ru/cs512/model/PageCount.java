package edu.ru.cs512.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "page_counts")
public class PageCount {

    @Id
    private String pageTitle;
    
    @Field("201511")
    private List<PageData> pageData;
    
    private static class PageData {
    	
    	@Field("date")
    	private String date;
    	
    	@Field("viewCount")
    	private String viewCount;
		/**
		 * @return the date
		 */
		public String getDate() {
			return date;
		}
		/**
		 * @param date the date to set
		 */
		public void setDate(String date) {
			this.date = date;
		}
		/**
		 * @return the viewCount
		 */
		public String getViewCount() {
			return viewCount;
		}
		/**
		 * @param viewCount the viewCount to set
		 */
		public void setViewCount(String viewCount) {
			this.viewCount = viewCount;
		}
    }

	/**
	 * @return the pageTitle
	 */
	public String getPageTitle() {
		return pageTitle;
	}

	/**
	 * @param pageTitle the pageTitle to set
	 */
	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	/**
	 * @return the pageData
	 */
	public List<PageData> getPageData() {
		return pageData;
	}

	/**
	 * @param pageData the pageData to set
	 */
	public void setPageData(List<PageData> pageData) {
		this.pageData = pageData;
	}
}
