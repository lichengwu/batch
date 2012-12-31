package oliver.app.batch.persistence;

import java.util.List;

import oliver.app.batch.domain.Proxy;

/**
 * {@link Proxy} Mapper
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2012-12-31 11:56 AM
 */
public interface ProxyMapper {

    public Proxy getById(Integer id);

    /**
     * get top n proxy by speed
     * 
     * @param topN
     * @return
     */
    public List<Proxy> getTopProxy(int topN);

    /**
     * persistence proxy
     * 
     * @param proxy
     */
    public void insert(Proxy proxy);

}
