package bravest.ptt.efastquery.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import bravest.ptt.efastquery.R;
import bravest.ptt.efastquery.data.Result;
import bravest.ptt.efastquery.data.wordbook.W;
import bravest.ptt.efastquery.utils.PLog;

/**
 * Created by root on 3/1/17.
 */

public class FDPagerAdapter extends PagerAdapter {

    private ArrayList<W> mData;
    private Context mContext;
    private LayoutInflater mInflater;

    public FDPagerAdapter(Context mContext, ArrayList<W> mData) {
        this.mData = mData;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return Integer.valueOf(((View)object).getTag().toString());
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(mContext);
        }

        View view = mInflater.inflate(R.layout.item, container, false);
        view.setTag(position);
        container.addView(view);
        getView(position, view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        try{
            if (container.indexOfChild((View) object) < 3) {
                container.removeView((View) object);
            }
        }catch (Exception e) {
            PLog.log(e);
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    private void getView(int position, View view) {
        ViewHolder holder;
        holder = new ViewHolder();
        holder.wordText = (TextView) view.findViewById(R.id.fd_word);
        holder.ukPhoneticView = view.findViewById(R.id.fd_uk_phonetic);
        holder.usPhoneticView = view.findViewById(R.id.fd_us_phonetic);
        holder.ukPhoneticText = (TextView) view.findViewById(R.id.fd_uk_phonetic_text);
        holder.usPhoneticText = (TextView) view.findViewById(R.id.fd_us_phonetic_text);
        holder.explains = (TextView) view.findViewById(R.id.fd_explains);

        Result result = (Result) mData.get(position);
        holder.wordText.setText(result.query);
        PhoneticClickListener listener = new PhoneticClickListener(result.query);
        holder.ukPhoneticView.setOnClickListener(listener);
        holder.usPhoneticView.setOnClickListener(listener);

        holder.ukPhoneticText.setText(result.uk_phonetic);
        holder.usPhoneticText.setText(result.us_phonetic);
        holder.explains.setText(result.getExplainsString());
    }


    private class PhoneticClickListener implements View.OnClickListener {
        String word;

        PhoneticClickListener(String word) {
            this.word = word;
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.fd_uk_phonetic:
                    //ask uk voice
                    break;
                case R.id.fd_us_phonetic:
                    //ask us voice
                    break;
            }
        }
    }

    private class ViewHolder {
        TextView wordText;
        View usPhoneticView;
        View ukPhoneticView;
        TextView ukPhoneticText;
        TextView usPhoneticText;
        TextView explains;
    }
}