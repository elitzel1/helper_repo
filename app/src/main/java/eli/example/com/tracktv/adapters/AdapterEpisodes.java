package eli.example.com.tracktv.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import eli.example.com.tracktv.R;

/**
 * Created by eli on 02/11/15.
 */
public class AdapterEpisodes extends RecyclerView.Adapter<AdapterEpisodes.ViewHolder>{
    private ArrayList<String> mDataset;

    public AdapterEpisodes(ArrayList<String> mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

            holder.txtTitle.setText(mDataset.get(position));
            holder.txtNumber.setText("E" + (position + 1));

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtTitle;
        public TextView txtNumber;

        public ViewHolder(View v) {
            super(v);
            txtTitle = (TextView) v.findViewById(R.id.txtNameEpisode);
            txtNumber = (TextView) v.findViewById(R.id.txtNumber);
        }
    }
}
