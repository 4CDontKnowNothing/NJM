package org.ht.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.ht.pojo.Borrowmoney;

public interface BaseDao<ID extends Object, Domain extends Object> {

	/**
	 * query by id
	 *
	 * @param id
	 * @param vp
	 * @return
	 */
	Domain get(@Param("id") ID id);

	/**
	 * delete
	 *
	 * @param id
	 * @param vp
	 */
	int delete(@Param("id") ID id);

	/**
	 * Paging query
	 *
	 * @param pagination
	 * @param params
	 * @param <V>
	 * @return
	 */
	public List<Borrowmoney> findListByPage(Map<String, Object> map);

	<V> List<Domain> findList(@Param("params") Map<String, V> params);

	/**
	 * save
	 *
	 * @param domain
	 */
	int create(Domain domain);

	/**
	 * update
	 *
	 * @param domain
	 */
	int update(Domain domain);

}
