package in.mashroom.matome;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by himara2 on 15/09/21.
 */
public class CustomListItemAdapter extends ArrayAdapter<ParseObject> {

    private LayoutInflater mLayoutInflater;

    public CustomListItemAdapter(Context context, List<ParseObject> objects) {
        // 第2引数はtextViewResourceIdとされていますが、カスタムリストアイテムを使用する場合は特に意識する必要のない引数です
        super(context, 0, objects);
        // レイアウト生成に使用するインフレーター
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        // ListViewに表示する分のレイアウトが生成されていない場合レイアウトを作成する
        if (convertView == null) {
            // レイアウトファイルからViewを生成する
            view = mLayoutInflater.inflate(R.layout.list_item, parent, false);
        } else {
            // レイアウトが存在する場合は再利用する
            view = convertView;
        }

        // リストアイテムに対応するデータを取得する
        ParseObject entry = getItem(position);

        // 各Viewに表示する情報を設定
        TextView text1 = (TextView) view.findViewById(R.id.TitleText);
        text1.setText(entry.getString("title"));
        TextView text2 = (TextView) view.findViewById(R.id.SubTitleText);
        text2.setText(entry.getString("blogTitle"));
        TextView hatebuText = (TextView) view.findViewById(R.id.HatebuText);
        hatebuText.setText(entry.getNumber("hatebu").toString() + " users");
        TextView dateText = (TextView) view.findViewById(R.id.DateText);
        dateText.setText(entry.getCreatedAt().toString());

        return view;
    }
}
