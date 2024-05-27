package com.example.chatbot42_project2_groep_7a.util.queryResolution;

public interface QueryResolutionStrategy {
    <T, R> QueryResolutionResult<R> resolve(QueryResolutionForm<T> queryForm);
}
