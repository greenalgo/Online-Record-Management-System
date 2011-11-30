package com.green.base.entity.generic.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao {

	public void save(Object object) throws Exception;

	public void save(List<Object> objectList) throws Exception;

	public <T> T find(Class<T> clazz, Serializable id) throws Exception;

	public void delete(Object object) throws Exception;

	public <T> List<T> findAll(Class<T> clazz) throws Exception;

	public <T> T getReference(Class<T> clazz, Object id) throws Exception;

	public <T> T merge(T object) throws Exception;

	public <T> List<T> merge(List<T> objectList) throws Exception;

	public <T> T mergeNT(T object) throws Exception;

	public <T> List<T> mergeNT(List<T> objectList) throws Exception;

	public <T> List<T> findListWithEagerOnClassWithInnerJoinOnCondition(
			Class<T> clazz, String conditionColumnName,
			Serializable conditionColumnValue, String joinRelationName)
			throws Exception;

	public <T> T findFirstWithEagerOnClassWithInnerJoinOnCondition(
			Class<T> clazz, String conditionColumnName,
			Serializable conditionColumnValue, String joinRelationName)
			throws Exception;

	public <T> T findFirstWhereCondition(Class<T> clazz,
			String conditionColumnName, Serializable conditionColumnValue)
			throws Exception;

	public <T> List<T> findAllWhereCondition(Class<T> clazz,
			String conditionColumnName, Serializable conditionColumnValue)
			throws Exception;

	public <T> List<T> findListWhereInCondition(Class<T> clazz,
			String conditionColumnName, Serializable... conditionColumnValue)
			throws Exception;

	public <T> List<T> multiSelectAll(Class<T> clazz, String columnNamesCsv);

	public <X, Y> Long countDistinctOnRootWhereAllMatch(Class<X> queryClass,
			Class<Y> rootClass, String conditionColumnNamesCsv,
			Serializable... conditionColumnValues);

	public <X, Y> Long countDistinctOnRootWhereAllMatchAndLike(
			Class<X> queryClass, Class<Y> rootClass, String likeColumnName,
			String likeColumnValue, String conditionColumnNamesCsv,
			Serializable... conditionColumnValues);

	public <X, Y> Long countDistinctOnRootWhereAllMatchAndMultiLike(
			Class<X> queryClass, Class<Y> rootClass, String likeColumnNameCsv,
			String[] likeColumnValues, String conditionColumnNamesCsv,
			Serializable... conditionColumnValues);

	public <X, Y> List<X> multiSelectWhereAllMatch(Class<X> queryClass,
			Class<Y> rootClass, String columnNamesCsv,
			String conditionColumnNamesCsv,
			Serializable... conditionColumnValues);

	public <X, Y> List<X> multiSelectWithLimitWhereAllMatch(
			Class<X> queryClass, Class<Y> rootClass, Integer startLimit,
			Integer maxSize, String columnNamesCsv,
			String conditionColumnNamesCsv,
			Serializable... conditionColumnValues);

	public <X, Y> List<X> multiSelectWithLimitWhereAllMatchAndMultiLike(
			Class<X> queryClass, Class<Y> rootClass, Integer startLimit,
			Integer maxSize, String columnNamesCsv, String likeColumnNameCsv,
			String[] likeColumnValues, String conditionColumnNamesCsv,
			Serializable... conditionColumnValues);

	public <X, Y> List<X> multiSelectWithLimitWhereAllMatchAndLike(
			Class<X> queryClass, Class<Y> rootClass, Integer startLimit,
			Integer maxSize, String columnNamesCsv, String likeColumnName,
			String likeColumnValue, String conditionColumnNamesCsv,
			Serializable... conditionColumnValues);

	public List<Object> executeNamedQuery(String namedQuery, String[] params,
			Object[] values);

}
