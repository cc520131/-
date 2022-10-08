package com.mr.clock.dao;
public class DAOFactory {//DAO工厂类
    public static DAO getDAO() {//获取DAO对象
        return new DAOMysqlImpl();// 返回基于MySQL的实现类对象
    }
}
