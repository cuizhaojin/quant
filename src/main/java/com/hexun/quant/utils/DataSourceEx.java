package com.hexun.quant.utils;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLFeatureNotSupportedException;

/**
 * Created by hexun on 2017/1/25.
 */
public class DataSourceEx extends BasicDataSource{
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(DataSourceEx.class);
    @Override
    public synchronized void close(){
        try {
            logger.info("datasource注销mysql 驱动,driverurl={}",getUrl());
            DriverManager.deregisterDriver(DriverManager.getDriver(getUrl()));
            super.close();
        }catch(java.sql.SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}