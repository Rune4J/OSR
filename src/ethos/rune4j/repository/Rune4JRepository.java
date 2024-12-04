package ethos.rune4j.repository;

import ethos.runehub.RunehubConstants;

public interface Rune4JRepository<T> {

    String URL = "jdbc:sqlite:" + RunehubConstants.RUNE4J;

    T save(T t);
    T update(T t);

}
