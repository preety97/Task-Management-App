package kitkatstudio.com.taskmanagement.response;

import org.json.JSONException;

public interface Response {
    public void processFinish(String result) throws JSONException;
}
