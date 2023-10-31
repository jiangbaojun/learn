package com.mrk.translation.test;

import com.deepl.api.TextResult;
import com.deepl.api.Translator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DeeplTest {

    private static final String authKey = "329f9fd8-b275-984e-7097-34431ee7fb87:fx";
    private String text = "This failure was the making of him."+System.lineSeparator()+"hello\\r\nwhat";
    private String text2 = "If you recently upgraded from Free to Pro, you could try regenerating your API key and trying with the new key.I figured out the original issue creator had a DeepL Pro account, rather than a DeepL API account.On March 13, we will officially begin rolling out our initiative to require all developers who contribute code on GitHub.com to enable one or more forms of two-factor authentication (2FA) by the end of 2023. Read on to learn about what the process entails and how you can help secure the software supply chain with 2FA.";
    @Test
    public void t1() throws Exception {
        Translator translator = new Translator(authKey);
        TextResult result =translator.translateText(text, null, "zh");
        System.out.println(result.getText());
    }

    public static void main(String[] args) throws Exception {
        String a = "hello\r\nwhat";
        int i = a.indexOf(System.lineSeparator());
        System.out.println(a);
    }

    @Test
    public void t2() throws Exception {
        for (LANGEnum value : LANGEnum.values()) {
            Translator translator = new Translator(authKey);
            TextResult result =translator.translateText(text, null, value.getKey().split("_")[0]);
            System.out.println(result.getText());
            System.out.println("------------------");
        }
    }

    public List<String> translateToList(String text, String sourceLang, String targetLang) throws Exception {
        Translator translator = new Translator(authKey);
        TextResult result =translator.translateText(text, sourceLang, targetLang);
        String resultText = result.getText();
        String[] split = resultText.split(System.lineSeparator());
        return Stream.of(split).collect(Collectors.toList());
    }

    public String translateToText(String text, String sourceLang, String targetLang) throws Exception {
        Translator translator = new Translator(authKey);
        TextResult result =translator.translateText(text, sourceLang, targetLang);
        return result.getText();
    }

}