package us.ridiculousbakery.imagesearch.search;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import us.ridiculousbakery.imagesearch.ImageResult;
import us.ridiculousbakery.imagesearch.R;

/**
 * Created by bkuo on 5/12/15.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult> {


    public ImageResultsAdapter(Context context, List<ImageResult> images) {
        super(context, R.layout.item_image_result, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder;
        ImageResult imageResult=getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
            viewholder = new ViewHolder();
            viewholder.tvResult = (TextView) convertView.findViewById(R.id.tvResult);
            viewholder.ivResult = (ImageView) convertView.findViewById(R.id.ivResult);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        viewholder.ivResult.setImageResource(0);
        Picasso.with(getContext()).load(imageResult.fullUrl).fit().centerCrop().into(viewholder.ivResult);
        viewholder.tvResult.setText(Html.fromHtml(imageResult.title));
        return convertView;
    }

    private class ViewHolder {
        public TextView tvResult;
        public ImageView ivResult;

    }
}
