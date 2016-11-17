package by.training.common;

public enum WriterType {

    JSON("json"), XML("xml");

    private String writerType;

    private WriterType(String writerType) {
        this.writerType = writerType;
    }

    @Override
    public String toString() {
        return writerType;
    }

}
