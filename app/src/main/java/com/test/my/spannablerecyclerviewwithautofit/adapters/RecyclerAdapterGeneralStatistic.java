package com.test.my.spannablerecyclerviewwithautofit.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.my.spannablerecyclerviewwithautofit.R;

import org.lucasr.twowayview.widget.SpannableGridLayoutManager;
import org.lucasr.twowayview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapterGeneralStatistic extends RecyclerView.Adapter<RecyclerAdapterGeneralStatistic.ViewHolder> {
    private List<Statistic> itemList;
    private Context context;

    static public class Statistic {
        public String title;
        public String data;
        public String description;
        public String type;
        public int imageID;

        public Statistic(String title, String data, String type, int resID, String description){
            this.title = title;
            this.data = data;
            this.type = type;
            this.imageID = resID;
            this.description = description;

        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView card;
        private final TextView title, data, description;
        public ViewHolder(ViewGroup parent, int viewType) {
            super(LayoutInflater.from(parent.getContext()).inflate(
                    viewType == 0 ? R.layout.view_statistic_parameter : (viewType == 1) ? R.layout.view_statistic_call : R.layout.view_statictic_duration, parent, false));
            if (viewType == 0) {
                card = (ImageView) itemView.findViewById(R.id.parameter_image);
                title = (TextView) itemView.findViewById(R.id.parameter_title);
                data = (TextView) itemView.findViewById(R.id.parameter_data);
                description = (TextView) itemView.findViewById(R.id.parameter_description);
            } else if(viewType == 1){
                card = (ImageView) itemView.findViewById(R.id.call_image);
                title = (TextView) itemView.findViewById(R.id.call_title);
                data = (TextView) itemView.findViewById(R.id.call_data);
                description = null;
            } else{
                card = (ImageView) itemView.findViewById(R.id.duration_image);
                title = (TextView) itemView.findViewById(R.id.duration_title);
                data = (TextView) itemView.findViewById(R.id.duration_data);
                description = (TextView) itemView.findViewById(R.id.duration_description);
            }

        }

        public void bind(Statistic stat) {
            title.setText(stat.title);
            title.setPaintFlags(title.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            data.setText(stat.data);
            if(stat.imageID > 0)
                card.setImageResource(stat.imageID);
            if(description != null && !stat.description.equals(""))
                description.setText(stat.description);
        }
    }

    private OnItemClickListener<Statistic> onItemClickListener;
    private OnItemLongClickListener<Statistic> onItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener<Statistic> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<Statistic> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setDisplayItems(List<Statistic> listStatistic){
        this.itemList = listStatistic;
    }

    public RecyclerAdapterGeneralStatistic(Context context) {
        this.context = context;
        setHasStableIds(false);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Statistic stat = itemList.get(position);
        int rowSpan = 1, colSpan = 1;
        List<Float> list = new ArrayList<>();
        final SpannableGridLayoutManager.LayoutParams lp =
                (SpannableGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();

        holder.bind(stat);

        if(stat.type.equals("duration")){
            rowSpan = 2;
            colSpan = 2;
            list.add(0.5f);
            list.add(1.0f);
        }else if(stat.type.equals("call")){
            holder.data.setTextSize(TypedValue.COMPLEX_UNIT_SP, 40);
            colSpan = 3;
            list.add(1.5f);
        }else if(stat.type.equals("acd") || stat.type.equals("asr")){
            holder.card.setVisibility(View.GONE);
            holder.description.setVisibility(View.GONE);
            if(stat.type.equals("asr")){
                holder.itemView.setBackgroundColor(Color.parseColor("#FF7E00"));
            }
            else
                list.add(0.5f);
        }else if(stat.type.equals("use")){
            holder.itemView.setBackgroundColor(Color.parseColor("#00C4B0"));
            list.add(1.5f);
        }else if(stat.type.equals("closed")){
            holder.itemView.setBackgroundColor(Color.parseColor("#FF7E00"));
            list.add(1.5f);
        }else if(stat.type.equals("simblock")){
            holder.itemView.setBackgroundColor(Color.parseColor("#2E5894"));
            list.add(1.5f);
        }



        if (lp.rowSpan != rowSpan || lp.colSpan != colSpan || list.size() > 0) {
            lp.rowSpan = rowSpan;
            lp.colSpan = colSpan;
            if(list.size() > 0)
                lp.listRelativeSizes = list;
            holder.itemView.setLayoutParams(lp);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(stat);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return onItemLongClickListener != null && onItemLongClickListener.onLongClick(stat);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (this.itemList != null)? this.itemList.size() : 0;
    }

    public interface OnItemClickListener<T> {
        void onClick(T t);
    }

    public interface OnItemLongClickListener<T> {
        boolean onLongClick(T t);
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        switch(itemList.get(position).type){
            case "duration":
                type = 2;
                break;
            case "call":
            case "asr":
            case "acd":
                type = 0;
                break;
            case "use":
            case "closed":
            case "simblock":
                type = 1;
                break;
            default:
                break;
        }
        return type;
    }
}
