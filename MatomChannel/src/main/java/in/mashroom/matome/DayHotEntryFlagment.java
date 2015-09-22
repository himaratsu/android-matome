package in.mashroom.matome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by himara2 on 15/09/21.
 */
public class DayHotEntryFlagment extends Fragment {

    ListView lv;
    MainActivity mActivity;

    private View view;

    public DayHotEntryFlagment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(view==null) {
            view=inflater.inflate(R.layout.activity_latest_entry, container,false);
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }

        lv = (ListView)view.findViewById(R.id.listView1);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParseObject parseObject = (ParseObject) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(), parseObject.getString("link"), Toast.LENGTH_SHORT).show();

                Uri uri = Uri.parse(parseObject.getString("link"));
                Intent i = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(i);
            }
        });

        fetchParse();

        return view;
    }

    public void fetchParse() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Entry");
        query.orderByDescending("hatebu");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = calendar.getTime();
        query.whereGreaterThan("posttime", yesterday);
        FindCallback<ParseObject> callback = new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> results, com.parse.ParseException e) {
                if (results == null) {
                    Log.d("entry", "The getFirst request failed.");
                } else {
                    Log.d("entry", "Retrieved the object." + results.get(0).getString("title"));

                    if (results.size() > 0) {
                        CustomListItemAdapter adapter = new CustomListItemAdapter(getActivity(), results);
                        lv = (ListView) view.findViewById(R.id.listView1);
                        lv.setAdapter(adapter);
                    }
                }
            }
        };
        query.findInBackground(callback);
    }

}
