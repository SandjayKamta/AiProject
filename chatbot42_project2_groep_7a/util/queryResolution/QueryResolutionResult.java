package com.example.chatbot42_project2_groep_7a.util.queryResolution;

public record QueryResolutionResult<R>(R resultData) {

    @Override
    public R resultData() {
        Class<?> dataType = resultData.getClass();
        System.out.println("Data Type: " + dataType.getName());
        return resultData;
    }
}
