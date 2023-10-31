package com.mrk.translation.test;

/**
 * 语言常量
 * @author jiangbaojun
 * @date 2023/4/14 13:46
 */
public enum LANGEnum {
//    PRC("zh_CN", "Chinese", "简体中文", 1);

    PRC("zh_CN", "Chinese", "简体中文", 1),
    SPANISH("es_ES", "Spanish", "Español", 3),
    FRENCH("fr_FR", "French", "Français", 4),
    GERMAN("de_DE", "German", "Deutsch", 5),
    ITALIAN("it_IT", "Italian", "Italiano", 6),
    KOREAN("ko_KR", "Korean", "한국어", 7),
    FLEMISH("nl_NL", "Flemish", "Vlaams", 8),
    JAPANESE("ja_JP", "Japanese", "日本語", 9);

    private String key;
    private String langName;
    private String displayName;
    private Integer index;

    LANGEnum(String key, String langName, String displayName, Integer index) {
        this.displayName = displayName;
        this.langName = langName;
        this.key = key;
        this.index = index;
    }

    public String getKey() {
        return key;
    }

    public String getLangName() {
        return langName;
    }

    public Integer getIndex() {
        return index;
    }

    public String getDisplayName() {
        return displayName;
    }
}
