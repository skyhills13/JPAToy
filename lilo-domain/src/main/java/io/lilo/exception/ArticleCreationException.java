package io.lilo.exception;

public class ArticleCreationException extends Exception {

    private String errorMessage;

    public ArticleCreationException(String message) {
        this.errorMessage = message;
    }

    @Override
    public String getMessage() {

        if (errorMessage == null) {
            return super.getMessage();
        }

        return errorMessage;
    }
}
