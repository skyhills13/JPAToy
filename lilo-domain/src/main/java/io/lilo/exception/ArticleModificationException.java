package io.lilo.exception;

public class ArticleModificationException extends Exception {
    private String errorMessage;

    public ArticleModificationException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String getMessage() {
        if (errorMessage == null)
            return super.getMessage();

        return errorMessage;
    }
}
