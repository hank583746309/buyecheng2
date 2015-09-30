package com.hangzhou.tonight.module.message.fragment;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hangzhou.tonight.R;
import com.hangzhou.tonight.module.base.constant.SysModuleConstant;
import com.hangzhou.tonight.module.base.fragment.BEmptyListviewFragment;
import com.hangzhou.tonight.module.base.util.AsyncTaskUtil;
import com.hangzhou.tonight.module.base.util.inter.Callback;
/**
 * 个人被评论列表
 * @author hank
 *
 */
public class CommentFragment extends BEmptyListviewFragment {

	List<DataModel> listData = null;
	BaseAdapter adapter;

	@Override protected void doListeners() {
		
	}

	@Override protected void doHandler() {
		listData = new ArrayList<DataModel>();
		adapter = new EntityAdapter();
		mListView.setAdapter(adapter);
	}
	
	@Override protected void doPostData() {
		loadData();
	}
	
	int  page = 0;
	long time = 0;
	private void loadData(){
		page++;
		JSONObject params = new JSONObject();
		params.put("page", page);
		AsyncTaskUtil.postData(getActivity(), "getSelfReply", params, new Callback() {
			@Override public void onSuccess(JSONObject result) {
				time = result.getLongValue("time");
				listData.addAll(JSONArray.parseArray(result.getString("replys"), DataModel.class));
			}
			
			@Override public void onFail(String msg) {
				if(SysModuleConstant.VALUE_DEV_MODEL){
					String[] strs = {"凌晨风暴","Moomo"};
					String[] content = {"中国光棍危机2020年或全面爆发：光棍男性上千万","印度拟在中印边境增设新司令部及超过40所哨站"};
					for(int i=0,len = strs.length;i<len;i++){
						DataModel m = new DataModel();
						m.nick = strs[i];
						m.content = content[i];
						listData.add(m);
					}
					adapter.notifyDataSetChanged();
				}
			}
		});
		
	}

	class EntityAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return listData.size();
		}

		@Override
		public Object getItem(int position) {
			return listData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			DataModel model = listData.get(position);
			ViewHolder hodler = null;
			if(convertView == null){
				convertView = View.inflate(getActivity(), R.layout.item_message_fragment_comment, null);
				hodler 		= new ViewHolder(convertView);
			}
			hodler = (ViewHolder) convertView.getTag();
			hodler.tv_username.setText(model.nick);
			hodler.tv_content .setText(model.content);
			return convertView;
		}
		
		class ViewHolder{
			TextView   tv_username,tv_content;
			public ViewHolder(View view){
				this.tv_username= (TextView) view.findViewById(R.id.message_comment_username);
				this.tv_content = (TextView) view.findViewById(R.id.message_comment_content);
				view.setTag(this);
			}
		}
	}
	
	class DataModel{
		String rid,uid,nick,content,type,url;

		public String getRid() { return rid; } 		public String getUid() { return uid; } 		public String getNick() { return nick; } 		public String getContent() { return content; } 		public String getType() { return type; } 		public String getUrl() { return url; } 		public void setRid(String rid) { this.rid = rid; } 		public void setUid(String uid) { this.uid = uid; } 		public void setNick(String nick) { this.nick = nick; } 		public void setContent(String content) { this.content = content; } 		public void setType(String type) { this.type = type; } 		public void setUrl(String url) { this.url = url; }

	}

}
