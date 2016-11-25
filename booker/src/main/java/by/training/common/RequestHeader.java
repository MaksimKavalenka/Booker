package by.training.common;

public enum RequestHeader {

    SELECT("/select?"), SUGGEST("/suggest?"), UPDATE("/update?");

    private String requestHeader;

    private RequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    @Override
    public String toString() {
        return requestHeader;
    }

}
