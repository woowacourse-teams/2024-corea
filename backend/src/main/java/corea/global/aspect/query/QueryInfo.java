package corea.global.aspect.query;

public interface QueryInfo {

    void increaseQueryCount(String query);

    String toFormatString();

    boolean isExceedQuery();

    int getCount();

    void clear();
}
