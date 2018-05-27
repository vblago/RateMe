package ltd.vblago.rateme.util;

import ltd.vblago.rateme.model.Opinion;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;


public class Retrofit {

    private static final String ENDPOINT = "https://script.google.com/macros/s/AKfycbyRBrQBxcjaLgzcwtRrK5zCRjfvlWP2eKJaZuzYaoH_CY4gig6Z";
    private static final String TABLE_ID = "1Sf-9DIoTngmlZReicj0P_z2vuO4FaljIiVtQX3w25JQ";
    private static ApiInterface apiInterface;

    static {
        initialize();
    }

    interface ApiInterface {
        @GET("/exec")
        void setOpinion(@Query("id") String id,
                        @Query("date") String date,
                        @Query("time") String time,
                        @Query("point") String point,
                        @Query("q1") String q1,
                        @Query("q2") String q2,
                        @Query("q3") String q3,
                        @Query("q4") String q4,
                        @Query("q5") String q5,
                        Callback<String> callback);

    }

    private static void initialize() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        apiInterface = restAdapter.create(ApiInterface.class);
    }

    public static void setOpinion(Opinion opinion, Callback<String> callback) {
        apiInterface.setOpinion(TABLE_ID, opinion.date, opinion.time, opinion.point,
                opinion.q1, opinion.q2, opinion.q3, opinion.q4, opinion.q5, callback);
    }
}
