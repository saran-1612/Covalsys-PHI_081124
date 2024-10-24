package com.covalsys.phi_scanner.ui.screen_shot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covalsys.phi_scanner.R;
import com.covalsys.phi_scanner.data.database.entities.ImageLine;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class ListImageAdapter extends RecyclerView.Adapter<ListImageAdapter.ViewHolder> {

    private Context mContext;
    Callback mCallback;
    List<ImageLine> list;

    public ListImageAdapter(Context context, List<ImageLine> lineList){

        this.list = lineList;
        this.mContext = context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_image_adapter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageLine line = list.get(position);
      //  Glide.with(mContext).load(line.getImagePath()).into(holder.view);
        /*try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            line.getImagePath().compress(Bitmap.CompressFormat.PNG, 100, stream);
            Glide.with(mContext)
                    .load(stream.toByteArray())
                    .into(holder.view);
        } catch (Exception e) {
            Log.e("Tag",e.getMessage());
            throw new RuntimeException(e);
        }*/
        holder.view.setImageBitmap(line.getImagePath());

        /*holder.view.setImageBitmap(line.getImagePath());*/
        holder.enter_Remarks.setText(line.getRemark());
        holder.delete_btn.setOnClickListener(v -> mCallback.onClickDelete(position,line));
    }

    public interface Callback{

        void onClickDelete(int position, ImageLine line);

    }

    public void setOnClick(Callback callback){

        this.mCallback = callback;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<ImageLine> lines){
        this.list = lines;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextInputEditText enter_Remarks;
        ImageView view,delete_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView.getRootView());
            view = itemView.findViewById(R.id.image1);
            enter_Remarks = itemView.findViewById(R.id.remarks_);
            delete_btn = itemView.findViewById(R.id.delete_icon);
        }

    }
}
