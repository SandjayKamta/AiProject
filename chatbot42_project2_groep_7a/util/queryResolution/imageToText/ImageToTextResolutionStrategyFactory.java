package com.example.chatbot42_project2_groep_7a.util.queryResolution.imageToText;

import com.example.chatbot42_project2_groep_7a.util.queryResolution.QueryResolutionStrategy;
import com.example.chatbot42_project2_groep_7a.util.queryResolution.QueryResolutionStrategyFactory;

public class ImageToTextResolutionStrategyFactory implements QueryResolutionStrategyFactory {
    @Override
    public QueryResolutionStrategy createQueryResolutionStrategy() {
        return new ImageToTextResolutionStrategy();
    }
}
