package cn.lichengwu.app.batch.core;

import org.jsoup.nodes.Document;

/**
 * parsing {@link Document} to domain which you want
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2013-01-13 PM2:07
 */
public interface DomainParser<T> {

    T parse(Document document);

}
