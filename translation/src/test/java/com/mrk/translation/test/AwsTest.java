package com.mrk.translation.test;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClient;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;

public class AwsTest {
    private static final String REGION = "us-east-1";

    public static void main( String[] args ) {

        // Create credentials using a provider chain. For more information, see
        // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html

        AWSStaticCredentialsProvider awsStaticCredentialsProvider = new AWSStaticCredentialsProvider(new BasicAWSCredentials("AKIAS6Y6UNUHFU5OQX6G", "gs+brS/VMHoHIxcPKQSBclBkpaPVaYbpaHhJJD1h"));
        AmazonTranslate translate = AmazonTranslateClient.builder()
                .withCredentials(awsStaticCredentialsProvider)
                .withRegion(REGION)
                .build();

        for (LANGEnum value : LANGEnum.values()) {
            TranslateTextRequest request = new TranslateTextRequest()
                    .withText("This failure was the making of him."+System.lineSeparator()+"hello\r\nwhat")
                    .withSourceLanguageCode("en")
                    .withTargetLanguageCode(value.getKey().split("_")[0]);
            TranslateTextResult result  = translate.translateText(request);
            System.out.println(result.getTranslatedText());
        }
    }
}