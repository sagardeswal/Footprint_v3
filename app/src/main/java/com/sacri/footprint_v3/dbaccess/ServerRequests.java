package com.sacri.footprint_v3.dbaccess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.sacri.footprint_v3.R;
import com.sacri.footprint_v3.activities.FeedActivity;
import com.sacri.footprint_v3.callback.AddEventCallback;
import com.sacri.footprint_v3.callback.AddPlaceCallback;
import com.sacri.footprint_v3.callback.AddStoryCallback;
import com.sacri.footprint_v3.callback.GetEventCallback;
import com.sacri.footprint_v3.callback.GetPlaceCallback;
import com.sacri.footprint_v3.callback.GetStoriesForEventCallback;
import com.sacri.footprint_v3.callback.GetStoryCallback;
import com.sacri.footprint_v3.callback.LoginUserCallback;
import com.sacri.footprint_v3.callback.RegisterUserCallback;
import com.sacri.footprint_v3.entity.EventDetails;
import com.sacri.footprint_v3.entity.PlaceDetails;
import com.sacri.footprint_v3.entity.Story;
import com.sacri.footprint_v3.entity.UserDetails;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Sagar Deswal on 13/09/15.
 */
public class ServerRequests {

    private static final String FOOTPRINT_LOGGER = "com.sacri.footprint_v3";
    private ProgressDialog progressDialog;
//    private Activity callingActivity;
    private static int RESULT_LOAD_IMG = 1;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://www.footprint.comuv.com/";
    public static final String LOGIN_URL = SERVER_ADDRESS + "/login.php";
    public static final String REGISTER_URL = SERVER_ADDRESS + "/register.php";
    public static final String ADD_PLACE_URL = SERVER_ADDRESS + "/place.php";
    public static final String ADD_EVENT_URL = SERVER_ADDRESS + "/event.php";
    public static final String ADD_STORY_URL = SERVER_ADDRESS + "/story.php";
    public static final String FETCH_PLACE_URL = SERVER_ADDRESS + "/fetch_places.php";
    public static final String FETCH_EVENT_URL = SERVER_ADDRESS + "/fetch_events.php";
    public static final String FETCH_STORY_URL = SERVER_ADDRESS + "/fetch_stories.php";
    public static final String FETCH_STORY_FOR_EVENT_URL = SERVER_ADDRESS + "/fetch_stories_for_event.php";

    private static final String HTTP_ERROR_MSG ="HTTP ERROR WHILE LOGGING IN";

    public ServerRequests(Context context){
//        callingActivity = (Activity) context;
        progressDialog = new ProgressDialog(context,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Processing...");
    }


    public String sendGetRequest(String uri) {
        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String result;

            StringBuilder sb = new StringBuilder();

            while((result = bufferedReader.readLine())!=null){
                sb.append(result);
            }

            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }


    public String sendPostRequest(String requestURL,
                                  HashMap<String, String> postDataParams) {

        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(CONNECTION_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));

                response = br.readLine();
            }
            else {
                response= HTTP_ERROR_MSG;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        Log.i(FOOTPRINT_LOGGER, "Post params : " + result.toString());
        return result.toString();
    }


    ////////////////////////////////LOGIN USER STARTS///////////////////////////////////////
    public void loginUserInBackground(UserDetails userDetails, LoginUserCallback loginUserCallback){
        new LoginUserAsyncTask(userDetails,loginUserCallback).execute();
    }

    public class LoginUserAsyncTask extends AsyncTask<Void,Void,UserDetails>{

        UserDetails userDetails;
        LoginUserCallback loginUserCallback;

        public LoginUserAsyncTask(UserDetails userDetails, LoginUserCallback loginUserCallback) {

            this.userDetails = userDetails;
            this.loginUserCallback = loginUserCallback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(UserDetails returnedUserDetails) {
            progressDialog.dismiss();
            loginUserCallback.done(returnedUserDetails);
            super.onPostExecute(returnedUserDetails);
        }

        @Override
        protected UserDetails doInBackground(Void... params) {
            UserDetails returnedUserDetails = null;
            HashMap<String,String> data = new HashMap<>();
            Log.i(FOOTPRINT_LOGGER, "email : " + userDetails.getEmail());
            Log.i(FOOTPRINT_LOGGER, "password : " + userDetails.getPaswordhashcode());
            data.put("email",userDetails.getEmail());
            data.put("password",userDetails.getPaswordhashcode());
            try {
                String result = sendPostRequest(LOGIN_URL, data);
                Log.i(FOOTPRINT_LOGGER, "Result : " + result);
                if (result.length() == 0) {
                    returnedUserDetails = null;
                    Log.i(FOOTPRINT_LOGGER, "JSON Object is Null : ");
                } else {
                    JSONObject jsonObject = new JSONObject(result);
                    Integer userID = jsonObject.getInt("usr_ID");
                    String fullname = jsonObject.getString("usr_fullname");
                    String mobile = jsonObject.getString("usr_mobile");
                    String email = jsonObject.getString("usr_email");
                    returnedUserDetails = new UserDetails(userID,fullname, mobile, email);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            if(returnedUserDetails==null){
                Log.i(FOOTPRINT_LOGGER, "returnedUserDetails is Null : ");
            }
            return returnedUserDetails;
        }
    }
    ////////////////////////////////LOGIN USER ENDS///////////////////////////////////////

    ////////////////////////////////REGISTER USER STARTS///////////////////////////////////////

    public void registerUserInBackground(UserDetails userDetails, RegisterUserCallback registerUserCallback){
        progressDialog.show();
        new RegisterUserAsyncTask(userDetails,registerUserCallback).execute();
    }

    public class RegisterUserAsyncTask extends AsyncTask<Void, Void, String> {

        UserDetails userDetails;
        RegisterUserCallback registerUserCallback;


        RegisterUserAsyncTask(UserDetails userDetails, RegisterUserCallback registerUserCallback) {
            this.userDetails = userDetails;
            this.registerUserCallback = registerUserCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            Log.i(FOOTPRINT_LOGGER, "RegisterUserAsyncTask doInBackground begins");
            String response = null;
            HashMap<String,String> data = new HashMap<>();
            data.put("usr_fullname",userDetails.getFullname());
            data.put("usr_email",userDetails.getEmail());
            data.put("usr_mobile",userDetails.getMobile());
            data.put("usr_passwordhashcode",userDetails.getPaswordhashcode());
            Log.i(FOOTPRINT_LOGGER, "userDetails : " + userDetails.toString());
            try {
                String result = sendPostRequest(REGISTER_URL, data);
                Log.i(FOOTPRINT_LOGGER, "Result : " + result);
                response = result;
            }catch(Exception e){
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            progressDialog.dismiss();
            registerUserCallback.done(response);
            super.onPostExecute(response);

        }
    }



    ////////////////////////////////REGISTER USER ENDS///////////////////////////////////////

    ////////////////////////////////ADD PLACE STARTS///////////////////////////////////////
    public void addPlaceDataInBackground(PlaceDetails placeDetails, AddPlaceCallback addPlaceCallback){
        progressDialog.show();
        new AddPlaceDataAsyncTask(placeDetails,addPlaceCallback).execute();
    }

    public class AddPlaceDataAsyncTask extends AsyncTask<Void, Void, String> {

        PlaceDetails placeDetails;
        AddPlaceCallback addPlaceCallback;

        AddPlaceDataAsyncTask(PlaceDetails placeDetails, AddPlaceCallback addPlaceCallback) {
            this.placeDetails = placeDetails;
            this.addPlaceCallback = addPlaceCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            Log.i(FOOTPRINT_LOGGER, "AddPlaceDataAsyncTask doInBackground begins");
            String response = null;
            HashMap<String,String> data = new HashMap<>();
            data.put("pl_title",placeDetails.getTitle());
            data.put("pl_description", placeDetails.getDescription());
            data.put("pl_category",placeDetails.getCategory());
            data.put("pl_longitude",placeDetails.getLongitude().toString());
            data.put("pl_latitude",placeDetails.getLatitude().toString());
//            String filePath = placeDetails.getPhotoFile().getPath();
//            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//            String imageString = getStringImage(bitmap);
//            data.put("pl_image",imageString);
            Log.i(FOOTPRINT_LOGGER, "placeDetails : " + placeDetails.toString());
            try {
                String result = sendPostRequest(ADD_PLACE_URL, data);
                Log.i(FOOTPRINT_LOGGER, "Result : " + result);
                response = result;
            }catch(Exception e){
                e.printStackTrace();
            }

            return response;

        }

        @Override
        protected void onPostExecute(String response) {
            progressDialog.dismiss();
            addPlaceCallback.done(response);
            super.onPostExecute(response);
        }
    }

    ////////////////////////////////ADD PLACE ENDS///////////////////////////////////////

    ////////////////////////////////FETCH PLACES STARTS///////////////////////////////////////

    public void fetchPlaceDataInBackground(String location, GetPlaceCallback getPlaceCallback){
        progressDialog.show();
        new FetchPlaceDataAsyncTask(location,getPlaceCallback).execute();
    }

    public class FetchPlaceDataAsyncTask extends AsyncTask<Void, Void, ArrayList<PlaceDetails>> {

        String location;
        GetPlaceCallback getPlaceCallback;

        FetchPlaceDataAsyncTask(String location, GetPlaceCallback getPlaceCallback) {
            this.location = location;
            this.getPlaceCallback = getPlaceCallback;
        }

        @Override
        protected ArrayList<PlaceDetails> doInBackground(Void... params) {
            Log.i(FOOTPRINT_LOGGER, "doInBackground begins");
            HashMap<String,String> data = new HashMap<>();
            data.put("location", location);
            Log.i(FOOTPRINT_LOGGER, "location=" + location);
            ArrayList<PlaceDetails> placeDetailsArrayList = new ArrayList<>();
            try {
                String result = sendPostRequest(FETCH_PLACE_URL, data);
                Log.i(FOOTPRINT_LOGGER, "Result : " + result);
                if(result==null){
                    placeDetailsArrayList = null;
                    Log.i(FOOTPRINT_LOGGER, "JSON Object is Null : ");
                }else{
                    JSONArray jsonArray = new JSONArray(result);
                    for(int i=0; i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Integer placeID = jsonObject.getInt("pl_ID");
                        String title = jsonObject.getString("pl_title");
                        String description = jsonObject.getString("pl_description");
                        int locID = jsonObject.getInt("pl_loc_id");
                        String category = jsonObject.getString("pl_category");
                        String longitude = jsonObject.getString("pl_loc_long");
                        String latitude = jsonObject.getString("pl_loc_lat");
                        PlaceDetails placeDetails = new PlaceDetails(placeID,
                                title,
                                description,
                                locID,
                                category,
                                Double.parseDouble(longitude),
                                Double.parseDouble(latitude));
                        placeDetailsArrayList.add(placeDetails);
                        Log.i(FOOTPRINT_LOGGER, "placeDetails : " + placeDetails.toString());
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            if(placeDetailsArrayList==null){
                Log.i(FOOTPRINT_LOGGER, "placeDetailsArrayList is Null : ");
            }
            return placeDetailsArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<PlaceDetails> placeDetailsArrayList) {
            progressDialog.dismiss();
            getPlaceCallback.done(placeDetailsArrayList);
            super.onPostExecute(placeDetailsArrayList);
//            ((FeedActivity)callingActivity).getmFeedPagerAdaptor().notifyDataSetChanged();
        }
    }

    ////////////////////////////////FETCH PLACES ENDS///////////////////////////////////////


    ////////////////////////////////ADD EVENT STARTS///////////////////////////////////////
    public void addEventDataInBackground(EventDetails eventDetails, AddEventCallback addEventCallback){
        progressDialog.show();
        new AddEventDataAsyncTask(eventDetails,addEventCallback).execute();
    }

    public class AddEventDataAsyncTask extends AsyncTask<Void, Void, String> {

        EventDetails eventDetails;
        AddEventCallback addEventCallback;

        AddEventDataAsyncTask(EventDetails placeDetails, AddEventCallback addEventCallback) {
            this.eventDetails = placeDetails;
            this.addEventCallback = addEventCallback;
        }

        @Override
        protected String doInBackground(Void... params) {
            Log.i(FOOTPRINT_LOGGER, "AddEventDataAsyncTask doInBackground begins");
            String response = null;
            HashMap<String,String> data = new HashMap<>();
            data.put("ev_title",eventDetails.getEventTitle());
            data.put("ev_description", eventDetails.getEventDescription());
            data.put("ev_repeat_weekly",eventDetails.getRepeatedWeekly().toString());
            DateFormat startDateFormat = new SimpleDateFormat("dd MM yyyy");
            String startDate = startDateFormat.format(eventDetails.getStartDate());
            data.put("ev_start_date",startDate);
            DateFormat endDateFormat = new SimpleDateFormat("dd MM yyyy");
            String endDate = endDateFormat.format(eventDetails.getEndDate());
            data.put("ev_end_date",endDate);
            data.put("ev_start_time_hour", eventDetails.getStartTimeHour().toString());
            data.put("ev_start_time_minute", eventDetails.getStartTimeMinutes().toString());
            data.put("ev_end_time_hour", eventDetails.getEndTimeHour().toString());
            data.put("ev_end_time_minute", eventDetails.getEndTimeMinutes().toString());
            data.put("ev_longitude", eventDetails.getLongitude().toString());
            data.put("ev_latitude", eventDetails.getLatitude().toString());
            data.put("ev_address", eventDetails.getAddress());

            Log.i(FOOTPRINT_LOGGER, "eventDetails : " + eventDetails.toString());
            try {
                String result = sendPostRequest(ADD_EVENT_URL, data);
                Log.i(FOOTPRINT_LOGGER, "Result : " + result);
                response = result;
            }catch(Exception e){
                e.printStackTrace();
            }

            return response;

        }

        @Override
        protected void onPostExecute(String response) {
            progressDialog.dismiss();
            addEventCallback.done(response);
            super.onPostExecute(response);
        }
    }

    ////////////////////////////////ADD EVENT ENDS///////////////////////////////////////

    ///////////////////////////////FETCH EVENT STARTS///////////////////////////////////////

    public void fetchEventDataInBackground(String eventID, GetEventCallback getEventCallback){
        progressDialog.show();
        new FetchEventDataAsyncTask(eventID, getEventCallback).execute();
    }

    public class FetchEventDataAsyncTask extends AsyncTask<Void, Void, ArrayList<EventDetails>> {

        GetEventCallback getEventCallback;
        String eventID;


        FetchEventDataAsyncTask(String eventID, GetEventCallback getEventCallback) {
            this.eventID = eventID;
            this.getEventCallback = getEventCallback;
        }

        @Override
        protected ArrayList<EventDetails> doInBackground(Void... params) {
            Log.i(FOOTPRINT_LOGGER, "doInBackground begins");
            HashMap<String,String> data = new HashMap<>();
            data.put("ev_id", eventID);
            Log.i(FOOTPRINT_LOGGER, "eventID=" + eventID);
            ArrayList<EventDetails> eventDetailsArrayList = new ArrayList<>();
            try {
                String result = sendPostRequest(FETCH_EVENT_URL, data);
                Log.i(FOOTPRINT_LOGGER, "Result : " + result);
                if(result==null){
                    eventDetailsArrayList = null;
                    Log.i(FOOTPRINT_LOGGER, "JSON Object is Null : ");
                }else{
                    JSONArray jsonArray = new JSONArray(result);
                    for(int i=0; i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Integer ev_id = jsonObject.getInt("ev_id");
                        Integer ev_pl_id = jsonObject.getInt("ev_pl_id");
                        Integer ev_loc_id = jsonObject.getInt("ev_loc_id");
                        String ev_title = jsonObject.getString("ev_title");
                        String ev_description = jsonObject.getString("ev_description");
                        String ev_repeat_weekly = jsonObject.getString("ev_repeat_weekly");
                        String ev_start_date = jsonObject.getString("ev_start_date");
                        DateFormat startDateFormat = new SimpleDateFormat("dd MM yyyy");
                        Date startDate = startDateFormat.parse(ev_start_date);
                        String ev_end_date = jsonObject.getString("ev_end_date");
                        DateFormat endDateFormat = new SimpleDateFormat("dd MM yyyy");
                        Date endDate = endDateFormat.parse(ev_end_date);
                        String ev_start_time_hour = jsonObject.getString("ev_start_time_hour");
                        String ev_start_time_minute = jsonObject.getString("ev_start_time_minute");
                        String ev_end_time_hour = jsonObject.getString("ev_end_time_hour");
                        String ev_end_time_minute = jsonObject.getString("ev_end_time_minute");
                        String loc_longitude = jsonObject.getString("loc_longitude");
                        String loc_latitude = jsonObject.getString("loc_latitude");
                        String ev_address = jsonObject.getString("ev_address");
                        EventDetails eventDetails = new EventDetails( ev_id, ev_pl_id, ev_loc_id,
                                ev_title,
                                ev_description,
                                Boolean.parseBoolean(ev_repeat_weekly),
                                startDate,
                                endDate,
                                Integer.parseInt(ev_start_time_hour),
                                Integer.parseInt(ev_start_time_minute),
                                Integer.parseInt(ev_end_time_hour),
                                Integer.parseInt(ev_end_time_minute),
                                Double.parseDouble(loc_longitude),
                                Double.parseDouble(loc_latitude),
                                ev_address);
                        eventDetailsArrayList.add(eventDetails);
                        Log.i(FOOTPRINT_LOGGER, "eventDetails : " + eventDetails.toString());
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            if(eventDetailsArrayList==null){
                Log.i(FOOTPRINT_LOGGER, "eventDetailsArrayList is Null : ");
            }
            return eventDetailsArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<EventDetails> eventDetailsArrayList) {
            progressDialog.dismiss();
            getEventCallback.done(eventDetailsArrayList);
            super.onPostExecute(eventDetailsArrayList);
//            ((FeedActivity)callingActivity).getmFeedPagerAdaptor().notifyDataSetChanged();
        }
    }

    ///////////////////////////////FETCH EVENT ENDS///////////////////////////////////////


    ////////////////////////////////ADD STORY STARTS///////////////////////////////////////
    public void addStoryDataInBackground(Story newStory, AddStoryCallback addStoryCallback){
        progressDialog.show();
        new AddStoryDataAsyncTask(newStory,addStoryCallback).execute();
    }

    public class AddStoryDataAsyncTask extends AsyncTask<Void, Void, String> {

        Story newStory;
        AddStoryCallback addStoryCallback;

        public AddStoryDataAsyncTask(Story newStory, AddStoryCallback addStoryCallback) {
            this.addStoryCallback = addStoryCallback;
            this.newStory = newStory;
        }

        @Override
        protected String doInBackground(Void... params) {
            Log.i(FOOTPRINT_LOGGER, "AddStoryDataAsyncTask doInBackground begins");
            String response = null;
            HashMap<String,String> data = new HashMap<>();
            data.put("st_usr_id", newStory.getUserID().toString());
            data.put("st_pl_id",newStory.getPlaceID().toString());
            data.put("st_loc_id", newStory.getLocID().toString());
            data.put("st_ev_id", newStory.getEventID().toString());
            data.put("st_text", newStory.getText());
            Log.i(FOOTPRINT_LOGGER, "New story : " + newStory.toString());
            try {
                String result = sendPostRequest(ADD_STORY_URL, data);
                Log.i(FOOTPRINT_LOGGER, "Result : " + result);
                response = result;
            }catch(Exception e){
                e.printStackTrace();
            }

            return response;

        }

        @Override
        protected void onPostExecute(String response) {
            progressDialog.dismiss();
            addStoryCallback.done();
            super.onPostExecute(response);
        }
    }

    ////////////////////////////////ADD STORY ENDS///////////////////////////////////////


    ////////////////////////////////FETCH STORY STARTS///////////////////////////////////////

    public void fetchStoryDataInBackground(String userID, GetStoryCallback getStoryCallback){
        progressDialog.show();
        new FetchStoryDataAsyncTask(userID,getStoryCallback).execute();
    }

    public class FetchStoryDataAsyncTask extends AsyncTask<Void, Void, ArrayList<Story>> {

        String userID;
        GetStoryCallback getStoryCallback;

        FetchStoryDataAsyncTask(String userID, GetStoryCallback getStoryCallback) {
            this.userID = userID;
            this.getStoryCallback = getStoryCallback;
        }

        @Override
        protected ArrayList<Story> doInBackground(Void... params) {
            Log.i(FOOTPRINT_LOGGER, "doInBackground begins");
            HashMap<String,String> data = new HashMap<>();
            data.put("userID", userID);
            Log.i(FOOTPRINT_LOGGER, "userID=" + userID);
            ArrayList<Story> storyArrayList = new ArrayList<>();
            try {
                String result = sendPostRequest(FETCH_STORY_URL, data);
                Log.i(FOOTPRINT_LOGGER, "Result : " + result);
                if(result==null){
                    storyArrayList = null;
                    Log.i(FOOTPRINT_LOGGER, "JSON Object is Null : ");
                }else{
                    JSONArray jsonArray = new JSONArray(result);
                    for(int i=0; i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Integer storyID = jsonObject.getInt("st_id");
                        Integer locID = jsonObject.getInt("st_loc_id");
                        String text = jsonObject.getString("st_text");
                        int placeID = jsonObject.getInt("st_pl_id");
                        Story story = new Story(locID,
                                placeID,
                                storyID,
                                text,
                                Integer.parseInt(userID));
                        storyArrayList.add(story);
                        Log.i(FOOTPRINT_LOGGER, "story : " + story.toString());
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            if(storyArrayList==null){
                Log.i(FOOTPRINT_LOGGER, "storyArrayList is Null : ");
            }
            return storyArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Story> storyArrayList) {
            progressDialog.dismiss();
            getStoryCallback.done(storyArrayList);
            super.onPostExecute(storyArrayList);
        }
    }

    ////////////////////////////////FETCH STORY ENDS///////////////////////////////////////

    ////////////////////////////////FETCH STORIES FOR EVENT STARTS///////////////////////////////////////

    public void fetchStoryForEventDataInBackground(String eventID, GetStoriesForEventCallback getStoriesForEventCallback){
        progressDialog.show();
        new FetchStoryDataForEventAsyncTask(eventID,getStoriesForEventCallback).execute();
    }

    public class FetchStoryDataForEventAsyncTask extends AsyncTask<Void, Void, ArrayList<Story>> {

        String eventID;
        GetStoriesForEventCallback getStoriesForEventCallback;

        FetchStoryDataForEventAsyncTask(String eventID, GetStoriesForEventCallback getStoriesForEventCallback) {
            this.eventID = eventID;
            this.getStoriesForEventCallback = getStoriesForEventCallback;
        }

        @Override
        protected ArrayList<Story> doInBackground(Void... params) {
            Log.i(FOOTPRINT_LOGGER, "doInBackground begins");
            HashMap<String,String> data = new HashMap<>();
            data.put("eventID", eventID);
            Log.i(FOOTPRINT_LOGGER, "eventID=" + eventID);
            ArrayList<Story> storyArrayList = new ArrayList<>();
            try {
                String result = sendPostRequest(FETCH_STORY_FOR_EVENT_URL, data);
                Log.i(FOOTPRINT_LOGGER, "Result : " + result);
                if(result==null){
                    storyArrayList = null;
                    Log.i(FOOTPRINT_LOGGER, "JSON Object is Null : ");
                }else{
                    JSONArray jsonArray = new JSONArray(result);
                    for(int i=0; i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Integer storyID = jsonObject.getInt("st_id");
                        Integer locID = jsonObject.getInt("st_loc_id");
                        String text = jsonObject.getString("st_text");
                        int placeID = jsonObject.getInt("st_pl_id");
                        int eventID = jsonObject.getInt("st_ev_id");
                        Story story = new Story();
                        story.setStoryID(storyID);
                        story.setLocID(locID);
                        story.setText(text);
                        story.setPlaceID(placeID);
                        story.setEventID(eventID);
                        storyArrayList.add(story);
                        Log.i(FOOTPRINT_LOGGER, "story : " + story.toString());
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            if(storyArrayList==null){
                Log.i(FOOTPRINT_LOGGER, "storyArrayList is Null : ");
            }
            return storyArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Story> storyArrayList) {
            progressDialog.dismiss();
            getStoriesForEventCallback.done(storyArrayList);
            super.onPostExecute(storyArrayList);
        }
    }

    ////////////////////////////////FETCH STORIES FOR EVENT ENDS///////////////////////////////////////

//    public String getStringImage(Bitmap bmp){
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes = baos.toByteArray();
//        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//        return encodedImage;
//    }
}
