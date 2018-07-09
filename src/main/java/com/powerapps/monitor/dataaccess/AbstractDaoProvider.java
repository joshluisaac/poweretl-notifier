package com.powerapps.monitor.dataaccess;

import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;


public abstract class AbstractDaoProvider implements IDaoProvider {
  
private IAbstractSqlSessionProvider sqlSessionProvider;

private

public AbstractDaoProvider(IAbstractSqlSessionProvider sqlSessionProvider) {
  this.sqlSessionProvider = sqlSessionProvider;
}

/* (non-Javadoc)
 * @see com.kollect.etl.dataaccess.IDaoProvider#getBatchSqlSession()
 */
@Override
public SqlSession getBatchSqlSession() {
  return sqlSessionProvider.getBatchExecutionSqlSession();
}

/* (non-Javadoc)
 * @see com.kollect.etl.dataaccess.IDaoProvider#insertQuery(java.lang.String, java.lang.Object)
 */
@Override
public int insertQuery (final String queryName, Object object) throws PersistenceException {
  return sqlSessionProvider.insert(queryName, object);
}

/* (non-Javadoc)
 * @see com.kollect.etl.dataaccess.IDaoProvider#updateQuery(java.lang.String, java.lang.Object)
 */
@Override
public int updateQuery (final String queryName, Object object) throws PersistenceException {
  return sqlSessionProvider.update(queryName, object);
}

/* (non-Javadoc)
 * @see com.kollect.etl.dataaccess.IDaoProvider#executeQuery(java.lang.String, java.lang.Object)
 */
@Override
public <T> List<T> executeQuery(final String queryName, Object args)  throws PersistenceException {
  return sqlSessionProvider.queryObject(queryName, args);
}

/* (non-Javadoc)
 * @see com.kollect.etl.dataaccess.IDaoProvider#executeQueryItr(java.lang.String, java.lang.Object)
 */
@Override
public <T> Iterator<T> executeQueryItr(final String queryName, Object args) throws PersistenceException {
  return sqlSessionProvider.query(queryName, args);
}

}

