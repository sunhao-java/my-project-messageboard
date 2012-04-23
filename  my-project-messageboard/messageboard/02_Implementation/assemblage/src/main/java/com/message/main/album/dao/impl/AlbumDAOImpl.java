package com.message.main.album.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.message.base.cache.utils.ObjectCache;
import com.message.base.hibernate.impl.GenericHibernateDAOImpl;
import com.message.base.jdbc.GenericJdbcDAO;
import com.message.base.pagination.PaginationSupport;
import com.message.main.album.dao.AlbumDAO;
import com.message.main.album.pojo.Album;
import com.message.main.album.pojo.Photo;
import com.message.resource.ResourceType;

/**
 * 相册DAO实现.
 * 
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0
 * @createTime 2012-4-21 下午05:53:00
 */
public class AlbumDAOImpl extends GenericHibernateDAOImpl implements AlbumDAO {
	private static final Logger logger = LoggerFactory.getLogger(AlbumDAOImpl.class);
	
	/**
	 * 缓存
	 */
	private ObjectCache cache;
	/**
	 * JDBC
	 */
	private GenericJdbcDAO genericJdbcDAO;

	public void setCache(ObjectCache cache) {
		this.cache = cache;
	}

	public void setGenericJdbcDAO(GenericJdbcDAO genericJdbcDAO) {
		this.genericJdbcDAO = genericJdbcDAO;
	}

	public PaginationSupport getAlbumList(Long userId, int start, int num)
			throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
 		StringBuffer sql = new StringBuffer();
		sql.append("select a.pk_id as pk_id from t_message_album a ");
		sql.append(" where a.delete_flag = :deleteFlag and a.ower_id = :owerId order by a.pk_id desc ");
		params.put("owerId", userId);
		params.put("deleteFlag", ResourceType.DELETE_NO);
		
		return this.genericJdbcDAO.getBeanPaginationSupport(sql.toString(), null, start, num, params, Album.class);
	}

	public Album loadAlbum(Long pkId) throws Exception {
		Album album = (Album) this.cache.get(Album.class, pkId);
		if(album == null){
			album = (Album) this.loadObject(Album.class, pkId);
			this.cache.put(Album.class, pkId);
		}
		
		return album;
	}

	public <T> T saveEntity(T entity) throws Exception {
		this.saveObject(entity);
		if(entity instanceof Album){
			this.cache.put(entity, ((Album) entity).getPkId());
		} else if(entity instanceof Photo){
			this.cache.put(entity, ((Photo) entity).getPkId());
		}
		
		return entity;
	}

	public void updateEntity(Object entity) throws Exception {
		this.updateObject(entity);
		if(entity instanceof Album){
			this.cache.remove(Album.class, ((Album) entity).getPkId());
			this.cache.put(entity, ((Album) entity).getPkId());
		} else if(entity instanceof Photo){
			this.cache.remove(Album.class, ((Photo) entity).getPkId());
			this.cache.put(entity, ((Photo) entity).getPkId());
		}
	}

	public int updateBySQL(String table, String column, Object value, Object pkValue) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("update ").append(table).append(" t set t.").append(column).append(" = ");
		
		if(value instanceof String){
			sql.append(" '").append(value.toString()).append("'");
		} else {
			sql.append(value);
		}
		
		sql.append(" where t.pk_id = ").append(pkValue);
		if(logger.isDebugEnabled())
			logger.debug("the update sql is '{}'", sql.toString());
		
		int result = this.genericJdbcDAO.update(sql.toString());
		if(table.indexOf("photo") != -1) {
			Photo p = (Photo) this.loadObject(Photo.class, (Long) pkValue);
			this.cache.put(p, (Long) pkValue);
		} else if(table.indexOf("album") != -1) {
			Album a = (Album) this.loadObject(Album.class, (Long) pkValue);
			this.cache.put(a, (Long) pkValue);
		}
		return result;
	}

	public int getPhotoCount(Long albumId) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "select count(*) from t_message_album_photo p where p.album_id = :albumId and p.delete_flag = :deleteFlag";
		params.put("albumId", albumId);
		params.put("deleteFlag", ResourceType.DELETE_NO);
		
		return this.genericJdbcDAO.queryForInt(sql, params);
	}

	public PaginationSupport getPhotosByAlbum(Long albumId, int start, int num) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.pk_id from t_message_photo p where p.pk_id in ");
		sql.append("(select ap.photo_id from t_message_album_photo ap where ap.album_id = :albumId)");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("albumId", albumId);
		
		return this.genericJdbcDAO.getBeanPaginationSupport(sql.toString(), null, start, num, params, Photo.class);
	}

	public Photo loadPhoto(Long pkId) throws Exception {
		Photo p = (Photo) this.cache.get(Photo.class, pkId);
		if(p == null){
			p = (Photo) this.loadObject(Photo.class, pkId);
			this.cache.put(p, pkId);
		}
		
		return p;
	}

}
