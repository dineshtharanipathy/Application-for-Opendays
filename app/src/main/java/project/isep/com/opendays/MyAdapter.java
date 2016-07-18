package project.isep.com.opendays;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import project.isep.com.opendays.data.ConfItem;

/**
 * Created by Tharanipathy PC on 13-01-2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.DataHolder> {

    private List<ConfItem> items;
    private Context context;

    public MyAdapter(Context context, List<ConfItem> items){
        this.context = context;
        this.items = items;
    }


    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view, parent, false);

        return new DataHolder(view);
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        ConfItem item = items.get(position);
        holder.title.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class DataHolder extends RecyclerView.ViewHolder {

        protected TextView title;

        public DataHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.conf_title);
        }
    }
}
