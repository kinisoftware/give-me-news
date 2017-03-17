package com.kinisoftware.givemenews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kinisoftware.givemenews.model.Article;
import com.kinisoftware.givemenews.model.Multimedia;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvHeadline;
        ImageView ivThumbnail;

        ViewHolder(View itemView) {
            super(itemView);

            tvHeadline = (TextView) itemView.findViewById(R.id.tvHeadline);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.ivThumbnail);
        }
    }

    private List<Article> articles;
    private Context context;

    public ArticlesAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item_article, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = articles.get(position);

        TextView tvHeadline = holder.tvHeadline;
        tvHeadline.setText(article.getHeadline().getMain());

        Multimedia thumbnail = article.getThumbnail();
        if (thumbnail != null) {
            Picasso.with(holder.itemView.getContext())
                    .load(thumbnail.getUrl())
                    .into(holder.ivThumbnail);
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
