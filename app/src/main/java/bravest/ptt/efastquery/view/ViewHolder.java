package bravest.ptt.efastquery.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import bravest.ptt.efastquery.R;
/**
 * Created by pengtian on 2017/1/19.
 */

public class ViewHolder extends RecyclerView.ViewHolder{
    TextView tv_content;
    ImageView iv_voice;
    ImageView iv_favourite;
    ImageView iv_delete;

    public ViewHolder(View itemView) {
        super(itemView);
        tv_content = (TextView) itemView.findViewById(R.id.item_content);
        iv_voice = (ImageView) itemView.findViewById(R.id.item_voice);
        iv_favourite = (ImageView) itemView.findViewById(R.id.item_favourite);
        iv_delete = (ImageView) itemView.findViewById(R.id.item_delete);
    }
}
