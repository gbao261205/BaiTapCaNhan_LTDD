package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.animation.R;

import java.util.List;


public class CustomAnimationAdapter extends RecyclerView.Adapter<CustomAnimationAdapter.AnimationViewHolder> {

    private final Context mContext;
    private final List<String> mDatas;

    public CustomAnimationAdapter(Context mContext, List<String> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public AnimationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_animation, parent, false);
        return new AnimationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimationViewHolder holder, int position) {
        String item = mDatas.get(position);
        holder.tvItem.setText(item);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class AnimationViewHolder extends RecyclerView.ViewHolder {
        TextView tvItem;

        public AnimationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.tv_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String newItem = mDatas.get(position) + " (Đã cập nhật)";
                        replaceItem(position, newItem);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        removeItem(position);
                    }
                    return true;
                }
            });
        }
    }

    public void addItem(String item) {
        mDatas.add(item);
        notifyItemInserted(mDatas.size() - 1);
    }

    public void replaceItem(int position, String item) {
        mDatas.remove(position);
        mDatas.add(position, item);
        notifyItemChanged(position);
    }

    public void removeItem(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }
}