package com.chris.tatusafety.services;

/**
 * Created by Josephine Menge on 06/07/2017.
 */

import com.chris.tatusafety.Constants;
import com.chris.tatusafety.Modules.Tweet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

/**
 * Created by Josephine Menge on 02/07/2017.
 */

public class TwitterService {
    public static void findTweets(String topic, Callback callback) {
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(Constants.CONSUMER_KEY,Constants.CONSUMER_SECRET);
        consumer.setTokenWithSecret(Constants.TOKEN,Constants.TOKEN_SECRET);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new SigningInterceptor(consumer)).build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.QUERY_PARAMETERS,topic);
        urlBuilder.addQueryParameter(Constants.RESULT_PARAMETERS,"recent");
        urlBuilder.addQueryParameter(Constants.COUNT,"100");


        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call1 = client.newCall(request);
        call1.enqueue(callback);
    }
    public ArrayList<Tweet> processResults(Response response) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject twitterJSON = new JSONObject(jsonData);
                JSONArray statussesJSON = twitterJSON.getJSONArray("statuses");
                for (int i = 0; i < statussesJSON.length(); i++) {
                    JSONObject tweetObject = statussesJSON.getJSONObject(i);
                    String tweetText = tweetObject.getString("text");
                    ArrayList<String> hashTags = new ArrayList<>();
                    JSONArray hashTagsArray = tweetObject.getJSONObject("entities").optJSONArray("hashTags");
                    if (hashTagsArray != null && hashTagsArray.length() > 0) {
                        for (int j = 0; j < hashTagsArray.length(); j++) {
                            hashTags.add(hashTagsArray.getJSONObject(j).getString("text"));
                        }
                    } else {
                        hashTags.add("No HashTags!");
                    }

                    String externalLink;

                    if (tweetObject.getJSONObject("entities").getJSONArray("urls").length() > 0) {
                        externalLink = tweetObject.getJSONObject("entities").getJSONArray("urls").getJSONObject(0).getString("url");
                    } else {
                        externalLink = "No external links";
                    }
                    String user = tweetObject.getJSONObject("user").getString("name");
                    String created = tweetObject.getString("created_at");
                    Tweet tweet = new Tweet(tweetText,hashTags,externalLink,user,created);
                    tweets.add(tweet);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tweets;
    }

}

