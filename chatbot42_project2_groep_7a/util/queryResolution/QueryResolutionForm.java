package com.example.chatbot42_project2_groep_7a.util.queryResolution;

public class QueryResolutionForm<T> {
    private T queryData;

    public QueryResolutionForm(T queryData) {
        this.queryData = queryData;
    }

    public T getQueryData() {
        return queryData;
    }
}