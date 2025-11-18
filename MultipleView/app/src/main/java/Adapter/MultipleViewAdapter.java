package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multipleview.R;

import java.util.List;

import Model.UserModel;

public class MultipleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    private final List<Object> items;

    private final int TYPE_TEXT = 0;
    private final int TYPE_IMAGE = 1;
    private final int TYPE_USER = 2;

    public MultipleViewAdapter(Context context, List<Object> items) {
        this.context = context;
        this.items = items;
    }

    public class TextHolder extends RecyclerView.ViewHolder {
        TextView tvText;
        public TextHolder(@NonNull View itemView) {
            super(itemView);
            tvText = itemView.findViewById(R.id.tv_text);
        }
        void bind(String text) {
            tvText.setText(text);
        }
    }

    public class ImageHolder extends RecyclerView.ViewHolder {
        ImageView imvImage;
        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            imvImage = itemView.findViewById(R.id.imv_image);
        }
        void bind(int drawableRes) {
            imvImage.setImageResource(drawableRes);
        }
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAddress = itemView.findViewById(R.id.tv_address);
        }
        void bind(UserModel user) {
            tvName.setText(user.getName());
            tvAddress.setText(user.getAddress());
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object item = items.get(position);
        if (item instanceof String) {
            return TYPE_TEXT;
        } else if (item instanceof Integer) {
            return TYPE_IMAGE;
        } else if (item instanceof UserModel) {
            return TYPE_USER;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        switch (viewType) {
            case TYPE_TEXT:
                view = inflater.inflate(R.layout.row_text, parent, false);
                return new TextHolder(view);
            case TYPE_IMAGE:
                view = inflater.inflate(R.layout.row_image, parent, false);
                return new ImageHolder(view);
            case TYPE_USER:
                view = inflater.inflate(R.layout.row_user, parent, false);
                return new UserHolder(view);
        }
        throw new IllegalArgumentException("Invalid view type");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = items.get(position);
        switch (holder.getItemViewType()) {
            case TYPE_TEXT:
                ((TextHolder) holder).bind((String) item);
                break;
            case TYPE_IMAGE:
                ((ImageHolder) holder).bind((Integer) item);
                break;
            case TYPE_USER:
                ((UserHolder) holder).bind((UserModel) item);
                break;
        }

        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, "Click at item " + position, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}