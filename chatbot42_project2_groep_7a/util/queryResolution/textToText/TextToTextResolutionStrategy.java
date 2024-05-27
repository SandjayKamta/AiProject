package com.example.chatbot42_project2_groep_7a.util.queryResolution.textToText;

import com.example.chatbot42_project2_groep_7a.util.queryResolution.QueryResolutionForm;
import com.example.chatbot42_project2_groep_7a.util.queryResolution.QueryResolutionResult;
import com.example.chatbot42_project2_groep_7a.util.queryResolution.QueryResolutionStrategy;

public class TextToTextResolutionStrategy implements QueryResolutionStrategy {
    @Override
    public QueryResolutionResult resolve(QueryResolutionForm queryForm) {
        Object queryData = queryForm.getQueryData();
        String formattedPrompt = queryData.toString().toLowerCase();

        if (formattedPrompt.contains("hallo")) {
            return new QueryResolutionResult("Hallo, hoe kan ik je helpen?");
        } else if (formattedPrompt.contains("naam")) {
            return new QueryResolutionResult("Mijn naam is Chatbot 42");
        } else if (formattedPrompt.contains("kleur")) {
            return new QueryResolutionResult("Mijn favoriete kleur is blauw");
        } else if (formattedPrompt.contains("eten")) {
            return new QueryResolutionResult("Mijn favoriete eten is pizza");
        } else {
            return new QueryResolutionResult("Ik begrijp je niet, probeer het opnieuw");
        }
    }
}
