package com.anshishagua.constants;

/**
 * User: lixiao
 * Date: 2018/4/25
 * Time: 下午11:05
 */

public enum ContentType {
    HTML("text/html", new String[] {".html", ".htm"}),
    TEXT("text/plain", new String [] {".txt"}),
    JPG("image/jpeg", new String [] {".jpg", ".jpeg"}),
    PNG("image/png", new String[] {".png"}),
    JSON("application/json", new String[] {".json"}),
    CSS("text/css", new String[] {".css"});

    private String mimeType;
    private String [] fileExtensions;

    ContentType(String mimeType, String [] fileExtensions) {
        this.mimeType = mimeType;
        this.fileExtensions = fileExtensions;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String[] getFileExtensions() {
        return fileExtensions;
    }

    public static ContentType parseByFileExtension(String fileExtension) {
        for (ContentType contentType : values()) {
            for (String string : contentType.fileExtensions) {
                if (string.equalsIgnoreCase(fileExtension)) {
                    return contentType;
                }
            }
        }

        return null;
    }
}
