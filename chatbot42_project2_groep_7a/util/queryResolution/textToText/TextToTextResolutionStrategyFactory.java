package com.example.chatbot42_project2_groep_7a.util.queryResolution.textToText;

import com.example.chatbot42_project2_groep_7a.util.queryResolution.QueryResolutionStrategy;
import com.example.chatbot42_project2_groep_7a.util.queryResolution.QueryResolutionStrategyFactory;

public class TextToTextResolutionStrategyFactory implements QueryResolutionStrategyFactory {
    @Override
    public QueryResolutionStrategy createQueryResolutionStrategy() {
        return new TextToTextResolutionStrategy();
    }
}
