package eli.example.com.tracktv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import eli.example.com.tracktv.R;
import eli.example.com.tracktv.entities.Season;

/**
 * Created by Administrador on 11/2/15.
 */
public class AdapterGrid extends BaseAdapter {
    private final List<Season> mDataset;

    private Context context;
    private LayoutInflater inflater;

    public AdapterGrid(final Context context,List<Season> mDataset) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mDataset = mDataset;
    }

    @Override
    public int getCount() {
        return mDataset.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataset.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_gridview, parent, false);
            holder = new ViewHolder();
            assert view != null;

            holder.imageView = (ImageView) view.findViewById(R.id.image);
            holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Glide.with(context)
                .load(mDataset.get(position).getImgThumb())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).error(R.mipmap.serie_thumbnail_placeholder)
                .into(holder.imageView);


        return view;
    }

    static class ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
    }
}
