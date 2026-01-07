package com.example.androiduidesignlab;

import android.content.Context;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class ApiService {
    private static ApiService instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private static final String BASE_URL = "http://10.240.72.69/comp2000/coursework/";
    private static final String STUDENT_ID = "10778555";

    public interface ApiResponseListener {
        void onSuccess(JSONObject response);
        void onError(String message);
    }

    public interface ApiStringListener {
        void onSuccess(String response);
        void onError(String message);
    }

    private ApiService(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized ApiService getInstance(Context context) {
        if (instance == null) {
            instance = new ApiService(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void createStudentDatabase(ApiStringListener listener) {
        String url = BASE_URL + "create_student/" + STUDENT_ID;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, 
                listener::onSuccess, 
                error -> listener.onError(getErrorMessage(error)));
        addToRequestQueue(stringRequest);
    }

    public void login(String username, String password, ApiResponseListener listener) {
        String url = BASE_URL + "read_user/" + STUDENT_ID + "/" + username;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject user = response.getJSONObject("user");
                        String correctPassword = user.getString("password");
                        if (password.equals(correctPassword)) {
                            listener.onSuccess(user);
                        } else {
                            listener.onError("Incorrect password");
                        }
                    } catch (JSONException e) {
                        listener.onError("Error parsing response: " + e.getMessage());
                    }
                },
                error -> listener.onError(getErrorMessage(error)));

        addToRequestQueue(jsonObjectRequest);
    }

    public void createUser(String username, String password, String firstname, String lastname, String email, String contact, String usertype, ApiResponseListener listener) {
        String url = BASE_URL + "create_user/" + STUDENT_ID;
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("username", username);
            requestBody.put("password", password);
            requestBody.put("firstname", firstname);
            requestBody.put("lastname", lastname);
            requestBody.put("email", email);
            requestBody.put("contact", contact);
            requestBody.put("usertype", usertype);
        } catch (JSONException e) {
            listener.onError("Error creating request body: " + e.getMessage());
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                listener::onSuccess,
                error -> listener.onError(getErrorMessage(error))
        );
        addToRequestQueue(jsonObjectRequest);
    }

    private String getErrorMessage(Exception error) {
        NetworkResponse networkResponse = error instanceof com.android.volley.VolleyError ? ((com.android.volley.VolleyError) error).networkResponse : null;
        String errorMessage = "Request failed";
        if (networkResponse != null) {
            errorMessage += " (Status code: " + networkResponse.statusCode + ")";
            try {
                String responseBody = new String(networkResponse.data, "utf-8");
                errorMessage += ": " + responseBody;
            } catch (UnsupportedEncodingException e) {
                // ignore
            }
        } else if (error.getMessage() != null) {
            errorMessage += ": " + error.getMessage();
        }
        return errorMessage;
    }
}
