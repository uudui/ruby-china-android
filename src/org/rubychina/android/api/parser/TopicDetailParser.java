/*Copyright (C) 2012 Longerian (http://www.longerian.me)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/
package org.rubychina.android.api.parser;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rubychina.android.api.response.TopicDetailResponse;
import org.rubychina.android.type.Reply;
import org.rubychina.android.util.JsonUtil;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

public class TopicDetailParser extends JSONParseHandler<TopicDetailResponse> {

	private TopicDetailResponse resp = new TopicDetailResponse();
	
	@Override
	public TopicDetailResponse getModel() {
		return resp;
	}
	
	@Override
	public void parse(String source) {
		try {
			JSONArray jsonReplies = new JSONObject(source).getJSONArray("replies");
			List<Reply> replies = new ArrayList<Reply>();
			Type type = new TypeToken<List<Reply>>(){}.getType();
			replies = JsonUtil.fromJsonArray(jsonReplies.toString(), type);
			resp.setReplies(replies);
			resp.setSuccess(true);
		} catch (JsonSyntaxException e) {
			resp.setSuccess(false);
			e.printStackTrace();
		} catch (JsonParseException e) {
			resp.setSuccess(false);
			e.printStackTrace();
		} catch (JSONException e) {
			resp.setSuccess(false);
			e.printStackTrace();
		}
	}

}
