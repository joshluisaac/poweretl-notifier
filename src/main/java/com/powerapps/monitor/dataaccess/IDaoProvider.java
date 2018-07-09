package com.powerapps.monitor.dataaccess;


import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

public interface IDaoProvider {

  SqlSession getBatchSqlSession();

  int insertQuery(String queryName, Object object) throws PersistenceException;

  int updateQuery(String queryName, Object object) throws PersistenceException;

  <T> List<T> executeQuery(String queryName, Object args) throws PersistenceException;

  <T> Iterator<T> executeQueryItr(String queryName, Object args) throws PersistenceException;

}
