package com.green.base.entity.generic.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.criteria.CriteriaBuilder.In;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.green.base.entity.generic.dao.GenericDao;

@Repository
public class GenericDaoImpl implements GenericDao, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 466149412327762202L;

	@PersistenceContext
	private EntityManager entityManager;

	private static transient Logger LOGGER = Logger
			.getLogger(GenericDaoImpl.class);

	public void setEntityManager(EntityManager entityManager) throws Exception {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() throws Exception {
		return entityManager;
	}

	@Override
	@Transactional
	public void save(Object object) throws Exception {
		this.entityManager.persist(object);
	}

	@Override
	@Transactional
	public void save(List<Object> objectList) throws Exception {
		for (Object object : objectList) {
			this.entityManager.persist(object);
		}

	}

	@Override
	public <T> T find(Class<T> clazz, Serializable id) throws Exception {
		return this.entityManager.find(clazz, id);
	}

	@Override
	@Transactional
	public void delete(Object object) throws Exception {
		this.entityManager.remove(this.entityManager.merge(object));

	}

	@Override
	public <T> List<T> findAll(Class<T> clazz) throws Exception {
		return this.entityManager.createQuery("from " + clazz.getSimpleName(),
				clazz).getResultList();
	}

	@Override
	public <T> T getReference(Class<T> clazz, Object id) throws Exception {
		return this.entityManager.getReference(clazz, id);
	}

	@Override
	@Transactional
	public <T> T merge(T object) throws Exception {
		return this.entityManager.merge(object);
	}

	@Override
	@Transactional
	public <T> List<T> merge(List<T> objectList) throws Exception {
		List<T> list = new ArrayList<T>();
		T t;
		for (int i = 0; i < objectList.size(); ++i) {
			LOGGER.info("Merging instance at index" + i);
			t = this.entityManager.merge(objectList.get(i));
			LOGGER.info("Merged successfuly entry at " + i);
			list.add(t);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> executeNamedQuery(String namedQuery, String[] params,
			Object[] values) {
		Query query = this.entityManager.createNamedQuery(namedQuery);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(params[i], values[i]);
		}
		return query.getResultList();
	}

	@Override
	public <T> List<T> findListWithEagerOnClassWithInnerJoinOnCondition(
			Class<T> clazz, String conditionColumnName,
			Serializable conditionColumnValue, String joinRelationName)
			throws Exception {
		QueryBuilder<T> queryBuilder = new QueryBuilder<T>(clazz);
		eagerFetchOnJoinHelper(queryBuilder, conditionColumnName,
				conditionColumnValue, joinRelationName, JoinType.INNER);
		queryBuilder.select();
		return queryBuilder.getResultList();

	}

	@Override
	public <T> T findFirstWithEagerOnClassWithInnerJoinOnCondition(
			Class<T> clazz, String conditionColumnName,
			Serializable conditionColumnValue, String joinRelationName)
			throws Exception {
		QueryBuilder<T> queryBuilder = new QueryBuilder<T>(clazz);
		eagerFetchOnJoinHelper(queryBuilder, conditionColumnName,
				conditionColumnValue, joinRelationName, JoinType.INNER);
		queryBuilder.select();
		return queryBuilder.getResult();

	}

	@Override
	public <T> T findFirstWhereCondition(Class<T> clazz,
			String conditionColumnName, Serializable conditionColumnValue) {
		QueryBuilder<T> queryBuilder = new QueryBuilder<T>(clazz);
		addWhereClause(queryBuilder, conditionColumnName, conditionColumnValue);
		queryBuilder.select();
		return queryBuilder.getResult();

	}

	@Override
	public <T> List<T> findAllWhereCondition(Class<T> clazz,
			String conditionColumnName, Serializable conditionColumnValue) {
		QueryBuilder<T> queryBuilder = new QueryBuilder<T>(clazz);
		addWhereClause(queryBuilder, conditionColumnName, conditionColumnValue);
		queryBuilder.select();
		return queryBuilder.getResultList();

	}

	@Override
	public <T> List<T> findListWhereInCondition(Class<T> clazz,
			String conditionColumnName, Serializable... conditionColumnValues) {
		QueryBuilder<T> queryBuilder = new QueryBuilder<T>(clazz);
		addWhereInClause(queryBuilder, conditionColumnName,
				conditionColumnValues);
		queryBuilder.select();
		return queryBuilder.getResultList();

	}

	@Override
	public <T> List<T> multiSelectAll(Class<T> clazz, String columnNamesCsv) {
		QueryBuilder<T> queryBuilder = new QueryBuilder<T>(clazz);
		queryBuilder.multiSelection(getSelectionList(queryBuilder.root,
				columnNamesCsv));
		return queryBuilder.getResultList();

	}

	@Override
	public <X, Y> List<X> multiSelectWhereAllMatch(Class<X> queryClass,
			Class<Y> rootClass, String columnNamesCsv,
			String conditionColumnNamesCsv,
			Serializable... conditionColumnValues) {
		CountQueryBuilder<X, Y> countQueryBuilder = new CountQueryBuilder<X, Y>(
				queryClass, rootClass);
		countQueryBuilder.multiSelection(getSelectionList(
				countQueryBuilder.fromRoot, columnNamesCsv));
		addWhereClause(countQueryBuilder, conditionColumnNamesCsv,
				conditionColumnValues);
		return countQueryBuilder.getResultList();

	}

	@Override
	public <X, Y> List<X> multiSelectWithLimitWhereAllMatch(
			Class<X> queryClass, Class<Y> rootClass, Integer startLimit,
			Integer maxSize, String columnNamesCsv,
			String conditionColumnNamesCsv,
			Serializable... conditionColumnValues) {

		CountQueryBuilder<X, Y> countQueryBuilder = new CountQueryBuilder<X, Y>(
				queryClass, rootClass);
		countQueryBuilder.multiSelection(getSelectionList(
				countQueryBuilder.fromRoot, columnNamesCsv));
		addWhereClause(countQueryBuilder, conditionColumnNamesCsv,
				conditionColumnValues);
		return countQueryBuilder.getResultList(startLimit, maxSize);

	}

	@Override
	public <X, Y> List<X> multiSelectWithLimitWhereAllMatchAndLike(
			Class<X> queryClass, Class<Y> rootClass, Integer startLimit,
			Integer maxSize, String columnNamesCsv, String likeColumnName,
			String likeColumnValue, String conditionColumnNamesCsv,
			Serializable... conditionColumnValues) {

		CountQueryBuilder<X, Y> countQueryBuilder = new CountQueryBuilder<X, Y>(
				queryClass, rootClass);
		countQueryBuilder.multiSelection(getSelectionList(
				countQueryBuilder.fromRoot, columnNamesCsv));
		// addlikeClause(countQueryBuilder, likeColumnName, likeColumnValue);
		addWhereClauseWithLikeClause(countQueryBuilder, likeColumnName,
				likeColumnValue, conditionColumnNamesCsv, conditionColumnValues);
		return countQueryBuilder.getResultList(startLimit, maxSize);

	}

	@Override
	public <X, Y> List<X> multiSelectWithLimitWhereAllMatchAndMultiLike(
			Class<X> queryClass, Class<Y> rootClass, Integer startLimit,
			Integer maxSize, String columnNamesCsv, String likeColumnNameCsv,
			String[] likeColumnValues, String conditionColumnNamesCsv,
			Serializable... conditionColumnValues) {

		CountQueryBuilder<X, Y> countQueryBuilder = new CountQueryBuilder<X, Y>(
				queryClass, rootClass);
		countQueryBuilder.multiSelection(getSelectionList(
				countQueryBuilder.fromRoot, columnNamesCsv));
		// addlikeClause(countQueryBuilder, likeColumnName, likeColumnValue);
		addWhereClauseWithMultiLikeClause(countQueryBuilder, likeColumnNameCsv,
				likeColumnValues, conditionColumnNamesCsv,
				conditionColumnValues);
		return countQueryBuilder.getResultList(startLimit, maxSize);

	}

	@Override
	public <X, Y> Long countDistinctOnRootWhereAllMatch(Class<X> queryClass,
			Class<Y> rootClass, String conditionColumnNamesCsv,
			Serializable... conditionColumnValues) {

		CountQueryBuilder<X, Y> countQueryBuilder = new CountQueryBuilder<X, Y>(
				queryClass, rootClass);

		addWhereClause(countQueryBuilder, conditionColumnNamesCsv,
				conditionColumnValues);
		return countQueryBuilder.getCount();

	}

	@Override
	public <X, Y> Long countDistinctOnRootWhereAllMatchAndLike(
			Class<X> queryClass, Class<Y> rootClass, String likeColumnName,
			String likeColumnValue, String conditionColumnNamesCsv,
			Serializable... conditionColumnValues) {

		CountQueryBuilder<X, Y> countQueryBuilder = new CountQueryBuilder<X, Y>(
				queryClass, rootClass);

		addWhereClauseWithLikeClause(countQueryBuilder, likeColumnName,
				likeColumnValue, conditionColumnNamesCsv, conditionColumnValues);
		return countQueryBuilder.getCount();

	}

	@Override
	public <X, Y> Long countDistinctOnRootWhereAllMatchAndMultiLike(
			Class<X> queryClass, Class<Y> rootClass, String likeColumnNameCsv,
			String[] likeColumnValues, String conditionColumnNamesCsv,
			Serializable... conditionColumnValues) {

		CountQueryBuilder<X, Y> countQueryBuilder = new CountQueryBuilder<X, Y>(
				queryClass, rootClass);

		addWhereClauseWithMultiLikeClause(countQueryBuilder, likeColumnNameCsv,
				likeColumnValues, conditionColumnNamesCsv,
				conditionColumnValues);
		return countQueryBuilder.getCount();

	}

	// ================================= HELPERS
	// ==========================================

	private <X, Y> void addWhereClause(
			CountQueryBuilder<X, Y> countQueryBuilder,
			String conditionColumnNamesCsv,
			Serializable... conditionColumnValues) {
		if ("".endsWith(conditionColumnNamesCsv)) {
			return;
		}
		String[] conditionColumnNames = conditionColumnNamesCsv.split(",");
		List<Predicate> list = new ArrayList<Predicate>();
		for (int i = 0; i < conditionColumnNames.length; ++i) {
			list.add(countQueryBuilder.criteriaBuilder
					.and(countQueryBuilder.criteriaBuilder.equal(
							evaluatePathFor(conditionColumnNames[i],
									countQueryBuilder.fromRoot, Object.class),
							// countQueryBuilder.fromRoot
							// .get(conditionColumnNames[i]),
							conditionColumnValues[i])));
		}
		countQueryBuilder.criteriaQuery.where(list.toArray(new Predicate[0]));
	}

	@SuppressWarnings("unused")
	private <T> void addWhereClause(QueryBuilder<T> queryBuilder,
			String conditionColumnNamesCsv,
			Serializable... conditionColumnValues) {
		if ("".endsWith(conditionColumnNamesCsv)) {
			return;
		}
		String[] conditionColumnNames = conditionColumnNamesCsv.split(",");
		List<Predicate> list = new ArrayList<Predicate>();
		for (int i = 0; i < conditionColumnNames.length; ++i) {
			list.add(queryBuilder.criteriaBuilder
					.and(queryBuilder.criteriaBuilder.equal(queryBuilder.root
							.get(conditionColumnNames[i]),
							conditionColumnValues[i])));
		}
		queryBuilder.criteriaQuery.where(list.toArray(new Predicate[0]));
	}

	private <X, Y> void addWhereClauseWithLikeClause(
			CountQueryBuilder<X, Y> countQueryBuilder, String likeColumnName,
			String likeColumnValue, String conditionColumnNamesCsv,
			Serializable... conditionColumnValues) {

		List<Predicate> list = new ArrayList<Predicate>();

		if (!"".equals(conditionColumnNamesCsv)) {
			String[] conditionColumnNames = conditionColumnNamesCsv.split(",");
			for (int i = 0; i < conditionColumnNames.length; ++i) {
				list.add(countQueryBuilder.criteriaBuilder
						.and(countQueryBuilder.criteriaBuilder.equal(
								countQueryBuilder.fromRoot
										.get(conditionColumnNames[i]),
								conditionColumnValues[i])));
			}
		}
		// list.add(countQueryBuilder.criteriaBuilder.like(
		// countQueryBuilder.fromRoot.<String> get(likeColumnName),
		// likeColumnValue));
		list.add(countQueryBuilder.criteriaBuilder.like(evaluatePathFor(
				likeColumnName, countQueryBuilder.fromRoot, String.class),
		// countQueryBuilder.fromRoot.<String> get(likeColumnName),
				likeColumnValue));
		countQueryBuilder.criteriaQuery.where(list.toArray(new Predicate[0]));

	}

	private <X, Y> void addWhereClauseWithMultiLikeClause(
			CountQueryBuilder<X, Y> countQueryBuilder,
			String likeColumnNameCsv, String[] likeColumnValues,
			String conditionColumnNamesCsv,
			Serializable... conditionColumnValues) {

		List<Predicate> list = new ArrayList<Predicate>();

		if (!"".equals(conditionColumnNamesCsv)) {
			String[] conditionColumnNames = conditionColumnNamesCsv.split(",");
			for (int i = 0; i < conditionColumnNames.length; ++i) {
				list.add(countQueryBuilder.criteriaBuilder
						.and(countQueryBuilder.criteriaBuilder.equal(
								countQueryBuilder.fromRoot
										.get(conditionColumnNames[i]),
								conditionColumnValues[i])));
			}
		}
		// list.add(countQueryBuilder.criteriaBuilder.like(
		// countQueryBuilder.fromRoot.<String> get(likeColumnName),
		// likeColumnValue));
		if (!"".equals(likeColumnNameCsv)) {
			String[] likeColumnNames = likeColumnNameCsv.split(",");
			for (int i = 0; i < likeColumnNames.length; ++i) {
				list.add(countQueryBuilder.criteriaBuilder.like(
						evaluatePathFor(likeColumnNames[i],
								countQueryBuilder.fromRoot, String.class),
						// countQueryBuilder.fromRoot.<String>
						// get(likeColumnName),
						likeColumnValues[i]));
			}

		}

		countQueryBuilder.criteriaQuery.where(list.toArray(new Predicate[0]));
	}

	private <T> void eagerFetchOnJoinHelper(QueryBuilder<T> queryBuilder,
			String conditionColumnName, Serializable conditionColumnValue,
			String joinRelationName, JoinType joinType) {
		addWhereClause(queryBuilder, conditionColumnName, conditionColumnValue);
		queryBuilder.root.fetch(joinRelationName, joinType);

	}

	private <T> void addWhereClause(QueryBuilder<T> queryBuilder,
			String conditionColumnName, Serializable conditionColumnValue) {
		if ("".endsWith(conditionColumnName)) {
			return;
		}
		queryBuilder.criteriaQuery.where(queryBuilder.criteriaBuilder.equal(
				queryBuilder.root.get(conditionColumnName),
				conditionColumnValue));
	}

	private <T> void addWhereInClause(QueryBuilder<T> queryBuilder,
			String conditionColumnName, Serializable... conditionColumnValues) {

		Path<Object> path = queryBuilder.root.get(conditionColumnName);
		In<Object> in = queryBuilder.criteriaBuilder.in(path);
		for (Serializable conditionColumnValue : conditionColumnValues) {
			in.value(conditionColumnValue);
		}
		queryBuilder.criteriaQuery.where(in);

	}

	private <T> List<Selection<?>> getSelectionList(Root<T> root,
			String columnNamesCsv) {
		String[] columnNames = columnNamesCsv.split(",");
		List<Selection<?>> selections = new ArrayList<Selection<?>>();
		for (String column : columnNames) {
			if (!column.contains("."))
				selections.add(root.get(column));
			else
				selections.add(evaluatePath(column, root));
		}
		return selections;
	}

	private <T, X> Path<X> evaluatePathFor(String column, Root<T> root,
			Class<X> clazz) {
		String[] splitColumn = column.split("\\.");
		Path<X> path = root.get(splitColumn[0]);
		for (int i = 1; i < splitColumn.length; i++) {
			path = path.get(splitColumn[i]);
		}

		return path;
	}

	private <T> Path<Object> evaluatePath(String column, Root<T> root) {
		return evaluatePathFor(column, root, Object.class);
	}

	class QueryBuilder<T> {

		private CriteriaQuery<T> criteriaQuery;
		private Root<T> root;
		private CriteriaBuilder criteriaBuilder;

		public QueryBuilder(Class<T> clazz) {
			criteriaBuilder = entityManager.getCriteriaBuilder();
			this.criteriaQuery = criteriaBuilder.createQuery(clazz);
			this.root = this.criteriaQuery.from(clazz);
		}

		public void select() {
			criteriaQuery.select(root);
		}

		public void multiSelection(List<Selection<?>> selections) {
			criteriaQuery.multiselect(selections);
		}

		public List<T> getResultList() {
			return query().getResultList();
		}

		public T getResult() {
			return query().getSingleResult();
		}

		private TypedQuery<T> query() {
			return entityManager.createQuery(criteriaQuery);
		}

	}

	class CountQueryBuilder<X, Y> {
		private CriteriaQuery<X> criteriaQuery;
		private Root<Y> fromRoot;
		private CriteriaBuilder criteriaBuilder;

		public CountQueryBuilder(Class<X> queryClass, Class<Y> rootClass) {
			criteriaBuilder = entityManager.getCriteriaBuilder();
			this.criteriaQuery = criteriaBuilder.createQuery(queryClass);
			this.fromRoot = criteriaQuery.from(rootClass);
		}

		public void multiSelection(List<Selection<?>> selections) {
			criteriaQuery.multiselect(selections);

		}

		public List<X> getResultList() {
			return query().getResultList();
		}

		public List<X> getResultList(Integer startLimit, Integer maxSize) {
			return query().setFirstResult(startLimit).setMaxResults(maxSize)
					.getResultList();
		}

		private TypedQuery<X> query() {
			return entityManager.createQuery(criteriaQuery);
		}

		@SuppressWarnings("unchecked")
		public Long getCount() {
			criteriaQuery.select((Selection<? extends X>) criteriaBuilder
					.count(fromRoot));
			return (Long) query().getResultList().get(0);

		}
	}

	@Override
	public <T> T mergeNT(T object) throws Exception {
		return this.entityManager.merge(object);
	}

	@Override
	public <T> List<T> mergeNT(List<T> objectList) throws Exception {
		List<T> list = new ArrayList<T>();
		T t;
		for (int i = 0; i < objectList.size(); ++i) {
			LOGGER.info("Merging instance at index" + i);
			t = this.entityManager.merge(objectList.get(i));
			LOGGER.info("Merged successfuly entry at " + i);
			list.add(t);
		}
		return list;
	}

}
