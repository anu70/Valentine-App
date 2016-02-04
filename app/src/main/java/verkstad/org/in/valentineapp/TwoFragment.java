package verkstad.org.in.valentineapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by coder on 1/15/2016.
 */
public class TwoFragment extends android.support.v4.app.Fragment{
    Functions functions;
    RecyclerView recyclerViewlb;
    ArrayList<Integer> ranks_leader_board;
    ArrayList<String> name_leader_board,red_leader_board,yellow_leader_board;
    TextView only_from_gender;
    CheckBox filter;
    boolean is_checkbox_checked;
    SearchView search;
    SearchView.OnQueryTextListener listener;
    ProgressBar loading;
    TableLayout users_tablelayout,loaders_tablelayout;
    ImageView lbboy,lbgirl;
    public TwoFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_two, container, false);
        functions=new Functions();
        recyclerViewlb= (RecyclerView) view.findViewById(R.id.recycler_view_leader_board);
        ranks_leader_board=new ArrayList<Integer>();
        name_leader_board=new ArrayList<String>();
        red_leader_board=new ArrayList<String>();
        yellow_leader_board = new ArrayList<String>();
        only_from_gender= (TextView) view.findViewById(R.id.only_from_gender);
        filter= (CheckBox) view.findViewById(R.id.filter);
        search= (SearchView) view.findViewById(R.id.search);
        users_tablelayout= (TableLayout) view.findViewById(R.id.users_tablelayout);
        loaders_tablelayout= (TableLayout) view.findViewById(R.id.loader_tablelayout);
        loading= (ProgressBar) view.findViewById(R.id.lb_users_progressbar);
        loading.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.DST_IN);
        lbboy= (ImageView) view.findViewById(R.id.lbboy);
        lbgirl= (ImageView) view.findViewById(R.id.lbgirl);

        listener = new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();

                return false;
            }
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        };


        functions.get_current_user(TwoFragment.this.getActivity());

        functions.get_users(TwoFragment.this.getActivity(), new Functions.VolleyCallback() {
            @Override
            public void onSuccess(final int size) {
                // Toast.makeText(TwoFragment.this.getActivity(),"ok1",Toast.LENGTH_SHORT).show();

                lbgirl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        filter.setChecked(false);
                        only_from_gender.setText("Roses only from boys:");
                            int rank = 1;
                            ranks_leader_board.clear();
                            name_leader_board.clear();
                            red_leader_board.clear();
                            yellow_leader_board.clear();
                            for (int i = 0; i < size; i++) {
                                if (functions.gender[i].equals("female")) {
                                    ranks_leader_board.add(rank);
                                    rank++;
                                    name_leader_board.add(functions.users[i]);
                                    red_leader_board.add(functions.total_red_roses[i]);
                                    yellow_leader_board.add(functions.total_yellow_roses[i]);
                                    functions.display_in_recyclerview(TwoFragment.this.getActivity(), recyclerViewlb, search, ranks_leader_board, name_leader_board, red_leader_board, yellow_leader_board);

                                }
                            }



                    }
                });

                lbboy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        filter.setChecked(false);
                        only_from_gender.setText("Roses only from girls:");
                        int rank = 1;
                        ranks_leader_board.clear();
                        name_leader_board.clear();
                        red_leader_board.clear();
                        yellow_leader_board.clear();
                        for (int i = 0; i < size; i++) {
                            if (functions.gender[i].equals("male")) {
                                ranks_leader_board.add(rank);
                                rank++;
                                name_leader_board.add(functions.users[i]);
                                red_leader_board.add(functions.total_red_roses[i]);
                                yellow_leader_board.add(functions.total_yellow_roses[i]);
                                functions.display_in_recyclerview(TwoFragment.this.getActivity(), recyclerViewlb, search, ranks_leader_board, name_leader_board, red_leader_board, yellow_leader_board);

                            }
                        }

                    }
                });

                filter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        is_checkbox_checked = ((CheckBox) v).isChecked();
                        if(is_checkbox_checked){
                            if(only_from_gender.getText().equals("Roses only from boys:")){
                                functions.sorting(TwoFragment.this.getActivity(),"female", new Functions.VolleyCallback() {

                                    @Override
                                    public void onSuccess(int size_sorted) {
                                        ranks_leader_board.clear();name_leader_board.clear();red_leader_board.clear();yellow_leader_board.clear();
                                        int rank =1;
                                        for (int i=0;i<size_sorted;i++){
                                            if(functions.gender_sorted[i].equals("female")){
                                                ranks_leader_board.add(rank);
                                                rank++;
                                                name_leader_board.add(functions.users_sorted[i]);
                                                red_leader_board.add(functions.red_roses_from_boys_sorted[i]);
                                                yellow_leader_board.add(functions.yellow_roses_from_boys_sorted[i]);
                                            }

                                        }
                                        functions.display_in_recyclerview(TwoFragment.this.getActivity(),recyclerViewlb,search,ranks_leader_board,name_leader_board,red_leader_board,yellow_leader_board);

                                    }
                                });

                            }
                            else{
                                functions.sorting(TwoFragment.this.getActivity(),"male", new Functions.VolleyCallback() {

                                    @Override
                                    public void onSuccess(int size_sorted) {
                                        ranks_leader_board.clear();name_leader_board.clear();red_leader_board.clear();yellow_leader_board.clear();
                                        int rank =1;
                                        for (int i=0;i<size_sorted;i++){
                                            if(functions.gender_sorted[i].equals("male")){
                                                ranks_leader_board.add(rank);
                                                rank++;
                                                name_leader_board.add(functions.users_sorted[i]);
                                                red_leader_board.add(functions.red_roses_from_girls_sorted[i]);
                                                yellow_leader_board.add(functions.yellow_roses_from_girls_sorted[i]);
                                            }

                                        }
                                        functions.display_in_recyclerview(TwoFragment.this.getActivity(),recyclerViewlb,search,ranks_leader_board,name_leader_board,red_leader_board,yellow_leader_board);

                                    }
                                });
                            }
                        }
                        else {
                            if(only_from_gender.getText().equals("Roses only from boys:")){
                                lbgirl.performClick();
                            }
                            else {
                                lbboy.performClick();
                            }
                        }

                    }
                });
                if (functions.current_user_gender.equals("female")) {
                    lbgirl.performClick();
                } else {
                    lbboy.performClick();
                }

                loaders_tablelayout.setVisibility(View.GONE);
                users_tablelayout.setVisibility(View.VISIBLE);
            }
        });

      /**  filter.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        is_checkbox_checked = ((CheckBox) v).isChecked();

        if (is_checkbox_checked) {
        if(only_from_gender.getText().equals("Roses only from boys:")){
            functions.sorting(TwoFragment.this.getActivity(),"female", new Functions.VolleyCallback() {

        @Override
        public void onSuccess(int size_sorted) {
        ranks_leader_board.clear();name_leader_board.clear();red_leader_board.clear();yellow_leader_board.clear();
        int rank =1;
        for (int i=0;i<size_sorted;i++){
            if(functions.gender_sorted[i].equals("female")){
                ranks_leader_board.add(rank);
                rank++;
                name_leader_board.add(functions.users_sorted[i]);
                red_leader_board.add(functions.red_roses_from_boys_sorted[i]);
                yellow_leader_board.add(functions.yellow_roses_from_boys_sorted[i]);
            }

        }
        functions.display_in_recyclerview(TwoFragment.this.getActivity(),recyclerViewlb,search,ranks_leader_board,name_leader_board,red_leader_board,yellow_leader_board);

        }
        });
        }
        else{
            functions.sorting(TwoFragment.this.getActivity(),"male", new Functions.VolleyCallback() {

                @Override
                public void onSuccess(int size_sorted) {
                    ranks_leader_board.clear();name_leader_board.clear();red_leader_board.clear();yellow_leader_board.clear();
                    int rank =1;
                    for (int i=0;i<size_sorted;i++){
                        if(functions.gender_sorted[i].equals("male")){
                            ranks_leader_board.add(rank);
                            rank++;
                            name_leader_board.add(functions.users_sorted[i]);
                            red_leader_board.add(functions.red_roses_from_girls_sorted[i]);
                            yellow_leader_board.add(functions.yellow_roses_from_girls_sorted[i]);
                        }

                    }
                    functions.display_in_recyclerview(TwoFragment.this.getActivity(),recyclerViewlb,search,ranks_leader_board,name_leader_board,red_leader_board,yellow_leader_board);

                }
            });

        }

        }
        else {
            lbboy.performClick();
      //  functions.display_in_recyclerview(TwoFragment.this.getActivity(),recyclerViewlb,search,ranks_leader_board,functions.users,functions.total_red_roses,functions.total_yellow_roses);
        }

        }
        });**/

        //search.OnQueryTextListener
        return view;
    }

    /**
     * Created by coder on 1/15/2016.
     */
    public static class ThreeFragment extends android.support.v4.app.Fragment  {
        //String login_url="http://192.168.16.1/Valentine/index.php";
        String[] name={"anu","abhi","chetan"};
        Context context;
        private ShoutListview adapter;
       private Long lastMsgTime;
        private static Handler handler;
        private boolean isRunning;
        private List<Shout> Shoutitems;

        public ThreeFragment() {
            // Required empty public constructor
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Shoutitems = new ArrayList<Shout>();


        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            final View view=inflater.inflate(R.layout.fragment_three, container, false);
            Button shout=(Button)view.findViewById(R.id.shout);
            //ListView listView=(ListView)view.findViewById(R.id.list);
           ListView listView= (ListView)view. findViewById(R.id.list);

           /** ArrayAdapter adapter=new ArrayAdapter(ThreeFragment.this.getActivity(), android.support.design.R.layout.support_simple_spinner_dropdown_item,name);
            listView.setAdapter(adapter); **/
           /** for(int i=0;i<3;i++) {
                Shout shouts = new Shout();
                shouts.setId("123456789");
                shouts.setMessage("Hi There");
                shouts.setName("Abhi");
                shouts.setTime((long) 1453139839);
                Shoutitems.add(shouts);
            } **/


            //ShoutList();
            adapter=new ShoutListview(ThreeFragment.this.getActivity(),Shoutitems);
            listView.setAdapter(adapter);
            listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
           // listView.setStackFromBottom(false);
            handler=new Handler();
            shout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Profile profile = Profile.getCurrentProfile();
                    final String name = profile.getName();
                    final String id=profile.getId();
                    final EditText editText = (EditText) view.findViewById(R.id.EditText);
                    final String message = editText.getText().toString();
                    if(editText.length()==0)
                        return;
                    Shout shouts = new Shout();
                    final Date obj=new Date();
                    shouts.setId(id);
                    shouts.setMessage(message);
                    shouts.setName(name);
                    // shouts.setTime(jsonObject.getLong("time"));
                    shouts.setTime(obj.getTime());
                    final Long time=shouts.getTime();

                    Shoutitems.add(0,shouts);
                    adapter.notifyDataSetChanged();
                    editText.setText(null);
                    RequestQueue request = Volley.newRequestQueue(ThreeFragment.this.getActivity());


                    StringRequest stringrequest = new StringRequest(Request.Method.POST,AppConfig.Request_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast toast= Toast.makeText(ThreeFragment.this.getActivity(), "Check Internet Connection ", Toast.LENGTH_LONG);
                            toast.getView().setBackgroundColor(Color.RED);
                            toast.show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("name", name);
                            params.put("message", message);
                            params.put("id",id);
                            params.put("time",time.toString());
                            return params;
                        }
                    };
                    request.add(stringrequest);
                }
            });
            return view;
        }


        @Override
        public void onResume() {
            super.onResume();
            isRunning = true;
            ShoutList();
        }

        @Override
        public void onPause() {
            super.onPause();
            isRunning = false;

        }

        public void ShoutList(){

            RequestQueue request=Volley.newRequestQueue(ThreeFragment.this.getActivity());
            StringRequest stringRequest=new StringRequest(Request.Method.POST, AppConfig.Request_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    try {

                        JSONArray jsonArray=new JSONArray(s);
                        if(jsonArray!=null && jsonArray.length()>0) {
                            for (int i =0; i<jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                Shout shouts = new Shout();
                                shouts.setId(jsonObject.getString("id"));
                                shouts.setMessage(jsonObject.getString("message"));
                                shouts.setName(jsonObject.getString("name"));
                                shouts.setTime(jsonObject.getLong("time"));
                                Shoutitems.add(0,shouts);

                                if (lastMsgTime == null
                                        || lastMsgTime.compareTo(shouts.getTime())<0)
                                    lastMsgTime = shouts.getTime();
                                adapter.notifyDataSetChanged();

                            }
                        }
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (isRunning)
                                ShoutList();
                            }
                        },1000);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast toast= Toast.makeText(ThreeFragment.this.getActivity(), "Check Internet Connection ", Toast.LENGTH_LONG);
                    toast.getView().setBackgroundColor(Color.RED);
                    toast.show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                   if (Shoutitems.size()==0)
                    params.put("TAG","ShoutList");
                    else{
                        params.put("TAG","ShoutListSorted");
                        params.put("lastMsgTime",lastMsgTime.toString());
                       params.put("id",Profile.getCurrentProfile().getId());
                       //params.put("lastMsgTime","1450289156000");
                    }
                    return params;
                }
            };
            request.add(stringRequest);
        }
    }
}
/**  filter.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
is_checkbox_checked = ((CheckBox) v).isChecked();

if (is_checkbox_checked) {
if(only_from_gender.getText().equals("Roses only from boys:")){

functions.sorting(TwoFragment.this.getActivity(),"female", new Functions.VolleyCallback() {
@Override
public void onSuccess(int size_sorted) {
ranks_leader_board.clear();name_leader_board.clear();red_leader_board.clear();yellow_leader_board.clear();
int rank =1;
for (int i=0;i<size_sorted;i++){
ranks_leader_board.add(rank);
rank++;
name_leader_board.add(functions.users_sorted[i]);


}
functions.display_in_recyclerview(TwoFragment.this.getActivity(),recyclerViewlb,search,ranks_leader_board,functions.users_sorted,functions.red_roses_from_boys_sorted,functions.yellow_roses_from_boys_sorted);

}
});
}
else{
functions.sorting(TwoFragment.this.getActivity(), "male", new Functions.VolleyCallback() {
@Override
public void onSuccess(int size) {

functions.display_in_recyclerview(TwoFragment.this.getActivity(),recyclerViewlb,search,ranks_leader_board,functions.users_sorted,functions.red_roses_from_girls_sorted,functions.yellow_roses_from_girls_sorted);

}
});

}

} else {
functions.display_in_recyclerview(TwoFragment.this.getActivity(),recyclerViewlb,search,ranks_leader_board,functions.users,functions.total_red_roses,functions.total_yellow_roses);
}

}
});**/
/** if (functions.users[i].equals(functions.current_user_name) && functions.gender[i].equals("male")) {
 only_from_gender.setText("Roses only from girls:");
 Toast.makeText(TwoFragment.this.getActivity(), "ok", Toast.LENGTH_SHORT).show();
 } else if (functions.users[i].equals(functions.current_user_name) && functions.gender[i].equals("female")) {
 only_from_gender.setText("Roses only from boys:");
 }**/
/**  int r=0;
 int y=0;**/
/**  int count_red_from_girls_lb=0;
 int count_red_from_boys_lb=0;
 int count_yellow_from_girls_lb=0;
 int count_yellow_from_boys_lb=0;**/
/**functions.count_roses(TwoFragment.this.getActivity(), new Functions.VolleyCallback() {
 String current_name;

 @Override
 public void onSuccess(int size_json) {
 for (int z = 0; z < size; z++) {
 current_name = names_leader_board.get(z);
 // Toast.makeText(TwoFragment.this.getActivity(),current_name,Toast.LENGTH_SHORT).show();
 for (int i = 0; i < size_json; i++) {
 if (functions.receiver_json[i].equals(current_name)) {
 if (functions.red_rose_json[i].equals("1")) {
 for (int j = 0; j < size; j++) {
 if (functions.sender_json[i].equals(functions.users[j]) && functions.gender[j].equals("female")) {
 count_red_from_girls_lb++;
 } else if (functions.sender_json[i].equals(functions.users[j]) && functions.gender[j].equals("male")) {
 count_red_from_boys_lb++;
 }
 }
 }

 if (functions.yellow_rose_json[i].equals("1")) {
 for (int j = 0; j < size; j++) {
 if (functions.sender_json[i].equals(functions.users[j]) && functions.gender[j].equals("female")) {
 count_yellow_from_girls_lb++;
 } else if (functions.sender_json[i].equals(functions.users[j]) && functions.gender[j].equals("male")) {
 count_yellow_from_boys_lb++;
 }
 }
 }

 }

 }

 if(is_checkbox_checked){
 r = 0;
 y = 0;
 }
 else {
 r=count_red_from_boys_lb+count_red_from_girls_lb;
 y=count_yellow_from_boys_lb+count_yellow_from_girls_lb;


 }

 counts_red_leader_board.add(r);
 counts_yellow_leader_board.add(y);
 //Collections.sort(counts_red_leader_board);
 count_yellow_from_girls_lb = 0;
 count_yellow_from_boys_lb = 0;
 count_red_from_girls_lb = 0;
 count_red_from_boys_lb = 0;

 }
 //  Toast.makeText(TwoFragment.this.getActivity(), Arrays.toString(ranks_leader_board.toArray()), Toast.LENGTH_SHORT).show();
 // Toast.makeText(TwoFragment.this.getActivity(), Arrays.toString(counts_red_leader_board.toArray()), Toast.LENGTH_SHORT).show();


 recyclerViewlb.setLayoutManager(new LinearLayoutManager(TwoFragment.this.getActivity()));
 RecyclerViewAdapterlb adapterlb = new RecyclerViewAdapterlb(TwoFragment.this.getActivity(), ranks_leader_board, names_leader_board, counts_red_leader_board, counts_yellow_leader_board);
 // RecyclerViewAdapterlb adapterlb=new RecyclerViewAdapterlb(TwoFragment.this.getActivity(),a2,a1,a3,a4);
 recyclerViewlb.setAdapter(adapterlb);


 }
 });**/
/**     ArrayList<String> a1 = new ArrayList<String>();
 ArrayList<Integer> a2  =new ArrayList<Integer>();
 ArrayList<Integer> a3 = new ArrayList<Integer>();
 ArrayList<Integer> a4 = new ArrayList<Integer>();
 a1.add("anu");a1.add("abhi");a2.add(1);a2.add(2);a3.add(1);a3.add(2);a4.add(2);a4.add(1);**/