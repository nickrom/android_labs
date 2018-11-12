package android.nickrom.lab3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

class ListAdapter extends RecyclerView.Adapter<ListAdapter.ElemViewHolder> {

    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<String> mNews = new ArrayList<>();
    private Context mContext = null;
    private int calls;

    ListAdapter(ArrayList<String> titles, ArrayList<String> news) {
        mTitles = titles;
        mNews = news;
        calls = 0;
    }

    @Override
    public ElemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("Create ViewHolder", Integer.toString(calls++));
        Context context = parent.getContext();
        mContext = context;
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        final ElemViewHolder viewHolder = new ListAdapter.ElemViewHolder(view);

        viewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, viewHolder.title.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ElemViewHolder holder, final int position) {
        Log.i("Holder set data", mTitles.get(position));
        holder.setData(mTitles.get(position), mNews.get(position));
    }

    @Override
    public int getItemCount()
    {
        Log.i("Count Items", Integer.toString(mTitles.size()));
        return mTitles.size();
    }

    static class ElemViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;

        static int index=0;

        ElemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleView);
            content = itemView.findViewById(R.id.contentView);
            index++;

        }

        void setData(String header, String data) {
            title.setText(header);
            content.setText(data);
        }
    }
}
