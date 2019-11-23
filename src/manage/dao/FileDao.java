package manage.dao;
import java.sql.Connection;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import dao.ylx_db;

public class FileDao{
	/*
	 * ���ܣ����ؽ����
	 */
	public JSONObject getRecord(ManageFile query) throws SQLException, IOException, JSONException {
		String tableName="manage_file";
		//��ʼ��ѯ���ݿ�
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		try {
			ylx_db query_db = new ylx_db(query.getDbName());
			//����sql��䣬���ݴ��ݹ����Ĳ�ѯ��������
			String sql="";
			int count=0;
			query.setTableName(tableName);
			sql=createGetRecordSql(query);
			ResultSet rs = query_db.executeQuery(sql);
			while (rs.next()) {
				List list = new ArrayList();
				list.add(rs.getString("id"));
				list.add(rs.getString("title"));
				list.add(rs.getString("content"));
				list.add(rs.getString("type"));
				list.add(rs.getString("limit_time"));
				list.add(rs.getString("user_id"));
				list.add(rs.getString("creator"));
				list.add(rs.getString("create_time"));
				list.add(rs.getString("breeds"));
				list.add(rs.getString("sex"));
				list.add(rs.getString("others"));
				if(query.getUserId()!=null && query.getUserId().equals(rs.getString("user_id"))){
					list.add("1");
				}else{
					list.add("0");
				}
				list.add(count);
				count=count+1;	//�����¼�¼������
				jsonList.add(list);
			}
			rs.close();
			query_db.close();
		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
			resultCode=10;
			resultMsg="��ѯ���ݿ���ִ���"+sqlexception.getMessage();
		}
		//////////���ݿ��ѯ��ϣ��õ���json����jsonList//////////
		//jsonList.clear();
		//���濪ʼ�������ص�json
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("aaData",jsonList);
		jsonObj.put("result_msg",resultMsg);//���������������ó�"error"��
		jsonObj.put("result_code",resultCode);//����0��ʾ������������0�ͱ�ʾ�д���������������
		return jsonObj;
	}
	public JSONObject modifyRecord(ManageFile file) throws JSONException{
		//String action,String dbName,String id,String title,String content,String creator,String createTime
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		try {
			ylx_db query_db = new ylx_db(file.getDbName());
			//����sql��䣬���ݴ��ݹ����Ĳ�ѯ��������
			String tableName="manage_file";
			String sql="update "+tableName+" set title='"+file.getTitle()+"',content='"+file.getContent()+"',limit_time='"+file.getLimitTime()+"',breeds='"+file.getBreeds()+"',sex='"+file.getSex()+"',others='"+file.getOthers()+"' where id="+file.getId();
			query_db.executeUpdate(sql);
			sql="select * from "+tableName+" order by create_time desc";
			ResultSet rs = query_db.executeQuery(sql);
			while (rs.next()) {
				List list = new ArrayList();
				list.add(rs.getString("id"));
				list.add(rs.getString("content"));
				jsonList.add(list);
			}
			rs.close();
			query_db.close();
		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
			resultCode=10;
			resultMsg="��ѯ���ݿ���ִ���"+sqlexception.getMessage();
		}
		//���濪ʼ�������ص�json
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("aaData",jsonList);
		jsonObj.put("action",file.getAction());
		jsonObj.put("result_msg",resultMsg);//���������������ó�"error"��
		jsonObj.put("result_code",resultCode);//����0��ʾ������������0�ͱ�ʾ�д���������������
		return jsonObj;
	}
	public JSONObject getRecordById(String action,String dbName,String id) throws JSONException{
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		try {
			ylx_db query_db = new ylx_db(dbName);
			//����sql��䣬���ݴ��ݹ����Ĳ�ѯ��������
			String tableName="manage_file";
			String sql="select * from "+tableName+" where id="+id+" order by create_time desc";
			ResultSet rs = query_db.executeQuery(sql);
			while (rs.next()) {
				List list = new ArrayList();
				list.add(rs.getString("id"));
				list.add(rs.getString("content"));
				jsonList.add(list);
			}
			rs.close();
			query_db.close();
		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
			resultCode=10;
			resultMsg="��ѯ���ݿ���ִ���"+sqlexception.getMessage();
		}
		//���濪ʼ�������ص�json
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("aaData",jsonList);
		jsonObj.put("action",action);
		jsonObj.put("result_msg",resultMsg);//���������������ó�"error"��
		jsonObj.put("result_code",resultCode);//����0��ʾ������������0�ͱ�ʾ�д���������������
		return jsonObj;
	}
	public JSONObject addRecord(ManageFile file) throws JSONException, SQLException{
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		ylx_db query_db = new ylx_db(file.getDbName());
		//����sql��䣬���ݴ��ݹ����Ĳ�ѯ��������
		String tableName="manage_file";
		String sql="insert into "+tableName+"(parent_id,title,content,limit_time,breeds,sex,others,user_id,creator,create_time) values('"+file.getParentId()+"','"+file.getTitle()+"','"+file.getContent()+
			"','"+file.getLimitTime()+"','"+file.getBreeds()+"','"+file.getSex()+"','"+file.getOthers()+"','"+file.getUserId()+"','"+file.getCreator()+"','"+file.getCreateTime()+"')";
		query_db.executeUpdate(sql);
		query_db.close();
		//���濪ʼ�������ص�json
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("aaData",jsonList);
		jsonObj.put("action",file.getAction());
		jsonObj.put("result_msg",resultMsg);//���������������ó�"error"��
		jsonObj.put("result_code",resultCode);//����0��ʾ������������0�ͱ�ʾ�д���������������
		return jsonObj;
	}
	public JSONObject deleteRecord(String action,String dbName,String[] ids,String creator,String createTime) throws JSONException, SQLException{
		String tableName="manage_file";
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		ylx_db query_db = new ylx_db(dbName);
		//����sql��䣬���ݴ��ݹ����Ĳ�ѯ��������
		for(int i=0;i<ids.length;i++){
			String sql="delete from "+tableName+" where id="+ids[i];
			query_db.executeUpdate(sql);
		}
		query_db.close();
		//���濪ʼ�������ص�json
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("aaData",jsonList);
		jsonObj.put("action",action);
		jsonObj.put("result_msg",resultMsg);//���������������ó�"error"��
		jsonObj.put("result_code",resultCode);//����0��ʾ������������0�ͱ�ʾ�д���������������
		return jsonObj;
	}
	public JSONObject setRecordTop(String action,String dbName,String type,String userId,String id) throws JSONException, SQLException{
		String tableName="manage_file";
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		ylx_db query_db = new ylx_db(dbName);
		ylx_db update_db = new ylx_db(dbName);
		//����sql��䣬���ݴ��ݹ����Ĳ�ѯ��������
		String sql="select max(priority) as priority from manage_file where user_id='"+userId+"'";
		int priority=0;
		ResultSet rs=query_db.executeQuery(sql);
		if(rs.next()){
			priority=rs.getInt("priority");
		}
		query_db.close();
		update_db.executeUpdate("update manage_file set priority="+(priority+1)+" where id="+id);
		update_db.close();
		//���濪ʼ�������ص�json
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("aaData",jsonList);
		jsonObj.put("action",action);
		jsonObj.put("result_msg",resultMsg);//���������������ó�"error"��
		jsonObj.put("result_code",resultCode);//����0��ʾ������������0�ͱ�ʾ�д���������������
		return jsonObj;
	}
	/*
	 * ���ܣ�������ʷ��¼��ѯ��sql���,type=all��ѯ���У�type=id��ѯĳ����¼�����°����������ò�ѯ
	 */
	private String createGetRecordSql(ManageFile query){
		String sql="";
		String where="";
		if(query.getId()!=null && !query.getId().equals("null")){
			where="where id="+query.getId();
		}
		if(query.getTitle()!=null && !query.getTitle().equals("null") && !query.getTitle().isEmpty()){
			if(!where.isEmpty()){
				where=where+" and title like '%"+query.getTitle()+"%'";
			}else{
				where="where title like '%"+query.getTitle()+"%'";
			}
		}
		if(query.getTimeFrom()!=null && query.getTimeTo()!=null && !query.getTimeFrom().isEmpty()){
			if(!where.isEmpty()){
				where=where+" and create_time between '"+query.getTimeFrom()+"' and '"+query.getTimeTo()+"'";
			}else{
				where="where create_time between '"+query.getTimeFrom()+"' and '"+query.getTimeTo()+"'";
			}
		}
		if(query.getType()!=null && query.getType().equals("all") && query.getUserRole().equals("manager")){
			sql="select * from "+query.getTableName()+" order by create_time desc";
		}else{
			if(query.getId()!=null && !query.getId().equals("null")){
				sql="select * from "+query.getTableName()+" where id="+query.getId();
			}else{
				if(where.isEmpty()){
					sql="select * from "+query.getTableName()+" where user_id='"+query.getUserId()+"' order by create_time desc";
				}else{
					sql="select * from "+query.getTableName()+" "+where+" and user_id='"+query.getUserId()+"' order by create_time desc";
				}
			}
		}
		return sql;
	}
}
