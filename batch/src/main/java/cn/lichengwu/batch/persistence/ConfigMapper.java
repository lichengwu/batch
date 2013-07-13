package cn.lichengwu.batch.persistence;

import java.util.List;

import cn.lichengwu.batch.domain.Config;

/**
 * {@link Config} Mapper
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2013-01-03 2:23 PM
 */
public interface ConfigMapper {

    /**
     * find all config
     * 
     * @return
     */
    public List<Config> findAll();

}
