package com.sainnt.blogrestapi.exception;

public class BlogApiException extends RuntimeException{
    private int httpStatusCode;

    public BlogApiException(String message) {
        super(message);
    }

    public BlogApiException(String message, int httpStatusCode) {
        super( formatMessage( message, httpStatusCode));
        this.httpStatusCode = httpStatusCode;

    }

    public BlogApiException(String message, Throwable cause, int httpStatusCode) {
        super(formatMessage( message, httpStatusCode));
        this.httpStatusCode = httpStatusCode;
    }

    private static String formatMessage(String message,int httpStatusCode){
        return String.format("status code: %d,\n%s",httpStatusCode,message);
    }
}
