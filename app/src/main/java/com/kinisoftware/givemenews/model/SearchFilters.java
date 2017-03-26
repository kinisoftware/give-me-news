package com.kinisoftware.givemenews.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class SearchFilters implements Serializable {

    private static String BEGIN_DATE_FORMAT = "yyyyMMdd";

    private Date beginDate;
    private String sortOrder;
    private List<String> deskValues;

    public SearchFilters() {
        beginDate = null;
        sortOrder = "";
        deskValues = new ArrayList<>();
    }

    public boolean shouldFilter() {
        if (beginDate != null) {
            Logger.getAnonymousLogger().info("Filter by begin date");
            return true;
        } else if (sortOrder != null && !sortOrder.isEmpty()) {
            Logger.getAnonymousLogger().info("Filter by order");
            return true;
        } else if (!deskValues.isEmpty()) {
            Logger.getAnonymousLogger().info("Filter by desk values");
            return true;
        }
        return false;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void addDeskValues(String deskValue) {
        deskValues.add(deskValue);
    }

    public String getBeginDate() {
        if (beginDate != null) {
            return new SimpleDateFormat(BEGIN_DATE_FORMAT).format(beginDate);
        }
        return null;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public String getDeskValues() {
        if (deskValues.isEmpty()) {
            return null;
        }

        StringBuilder newsDesk = new StringBuilder();
        newsDesk.append("news_desk:(");
        for (String deskValue : deskValues) {
            newsDesk.append(String.format("\"%s\"", deskValue));
        }
        newsDesk.append(")");
        return newsDesk.toString();
    }
}
