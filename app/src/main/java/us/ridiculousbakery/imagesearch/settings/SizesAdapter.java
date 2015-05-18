package us.ridiculousbakery.imagesearch.settings;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by bkuo on 5/18/15.
 */
public class SizesAdapter extends ArrayAdapter<String>  {
    public SizesAdapter(Context context, List<String> sizes) {
        super(context, android.R.layout.simple_dropdown_item_1line, sizes);
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder viewholder;
//        ImageResult imageResult=getItem(position);
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
//            viewholder = new ViewHolder();
//            viewholder.tvResult = (TextView) convertView.findViewById(R.id.tvResult);
//            viewholder.ivResult = (ImageView) convertView.findViewById(R.id.ivResult);
//            convertView.setTag(viewholder);
//        } else {
//            viewholder = (ViewHolder) convertView.getTag();
//        }
//        viewholder.ivResult.setImageResource(0);
//        Picasso.with(getContext()).load(imageResult.fullUrl).fit().centerCrop().into(viewholder.ivResult);
//        viewholder.tvResult.setText(Html.fromHtml(imageResult.title));
//        return convertView;
//    }
//
//    private class ViewHolder {
//        public TextView tvResult;
//        public ImageView ivResult;
//
//    }
}
