package cn.lichengwu.app.batch.core;

/**
 * interface to declare whether an value match this class instance
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2013-01-03 5:38 PM
 */
public interface Matchable {

    boolean match(Object value);
}
