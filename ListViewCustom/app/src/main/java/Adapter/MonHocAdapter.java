package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.listviewcustom.R;

import java.util.List;

import Module.MonHoc;

public class MonHocAdapter extends BaseAdapter{
    private Context context;
    private int layout;
    private List<MonHoc> monHocList;

    private static class ViewHolder{
        TextView textName,textDesc;
        ImageView imagePic;
    }

    public MonHocAdapter(Context context, int layout, List<MonHoc> monHocList) {
        this.context = context;
        this.layout = layout;
        this.monHocList = monHocList;
    }

    public Context getContext() {
        return context;
    }

    public int getLayout() {
        return layout;
    }

    public List<MonHoc> getMonHocList() {
        return monHocList;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setLayout(int layout) {
        this.layout = layout;
    }

    public void setMonHocList(List<MonHoc> monHocList) {
        this.monHocList = monHocList;
    }

    @Override
    public int getCount(){
        return monHocList.size();
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //khởi tạo viewholder
        ViewHolder viewHolder;
        //lấy context
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //gọi view chứa layout
            view = inflater.inflate(layout, null);
            //ánh xạ view
            viewHolder = new ViewHolder();
            viewHolder.textName = (TextView) view.findViewById(R.id.textViewName);
            viewHolder.textDesc = (TextView) view.findViewById(R.id.textViewDescription);
            viewHolder.imagePic = (ImageView) view.findViewById(R.id.ImgView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        //gán giá trị
        MonHoc monHoc = monHocList.get(i);
        viewHolder.textName.setText(monHoc.getName());
        viewHolder.textDesc.setText(monHoc.getDesc());
        viewHolder.imagePic.setImageResource(monHoc.getPic());
        //trả về view
        return view;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

}



