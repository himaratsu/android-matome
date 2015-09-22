package in.mashroom.matome;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by himara2 on 15/09/22.
 */
public class BaseEntryFlagment extends Fragment {

    ListView lv;
    View view;
    ParseQuery<ParseObject> query;

    public ParseQuery<ParseObject> constructQuery() {
        query = ParseQuery.getQuery("Entry");
        return query;
    }

    public void fetchParse() {
        query = constructQuery();
        FindCallback<ParseObject> callback = new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> results, com.parse.ParseException e) {

                if(isDetached() || getActivity() == null || view == null) {
                    return;
                }

                if (results == null) {
                    Log.d("entry", "The getFirst request failed.");
                } else {
                    Log.d("entry", "Retrieved the object." + results.get(0).getString("title"));

                    CustomListItemAdapter adapter = new CustomListItemAdapter(getActivity(), results);
                    lv = (ListView)view.findViewById(R.id.listView1);
                    lv.setAdapter(adapter);
                }
            }
        };
        query.findInBackground(callback);
    }

    @Override
    public void onDetach() {
        query.cancel();
        super.onDetach();
        Toast.makeText(getActivity(), "onDetach", Toast.LENGTH_SHORT).show();
    }

}
