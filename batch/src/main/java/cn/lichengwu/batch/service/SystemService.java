package cn.lichengwu.batch.service;

import java.util.List;

import javax.annotation.Resource;

import cn.lichengwu.batch.domain.Config;
import cn.lichengwu.batch.persistence.ConfigMapper;

import org.springframework.stereotype.Service;

/**
 * SystemService
 * 
 * @author lichengwu
 * @version 1.0
 * @created 2013-01-03 2:13 PM
 */
@Service
public class SystemService {

    @Resource
    ConfigMapper configMapper;

    public List<Config> getAllConig() {
        return configMapper.findAll();
    }
}
