package com.kinisoftware.givemenews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kinisoftware.givemenews.model.Article;

import java.util.List;

public class ArticlesAdapter extends ArrayAdapter<Article> {

    public ArticlesAdapter(@NonNull Context context, @NonNull List<Article> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Article article = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_article, parent, false);
        }

        TextView tvHeadline = (TextView) convertView.findViewById(R.id.tvHeadline);
        tvHeadline.setText(article.getHeadline().getMain());

        return convertView;
    }
}
