package com.kinisoftware.givemenews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.kinisoftware.givemenews.model.SearchFilters;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterActivity extends AppCompatActivity {

    public static final String DUE_DATE_FORMAT = "dd/MM/yy";

    private SearchFilters searchFilters;

    @BindView(R.id.etBeginDate)
    EditText etBeginDate;
    @BindView(R.id.spSortOrder)
    Spinner spSortOrder;
    @BindView(R.id.cbArts)
    CheckBox cbArts;
    @BindView(R.id.cbSports)
    CheckBox cbSports;
    @BindView(R.id.cbStyleFashion)
    CheckBox cbStyleFashion;

    @OnClick(R.id.btSave)
    void saveFilters() {
        String beginDate = etBeginDate.getText().toString();
        try {
            if (!beginDate.isEmpty()) {
                searchFilters.setBeginDate(getDateFormatForDueDate().parse(beginDate));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String sortOrder = (String) spSortOrder.getSelectedItem();
        searchFilters.setSortOrder(sortOrder);

        if (cbArts.isChecked()) {
            searchFilters.addDeskValues(cbArts.getText().toString());
        }

        if (cbSports.isChecked()) {
            searchFilters.addDeskValues(cbSports.getText().toString());
        }

        if (cbStyleFashion.isChecked()) {
            searchFilters.addDeskValues(cbStyleFashion.getText().toString());
        }

        Intent intent = new Intent();
        intent.putExtra("filters", searchFilters);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);

        searchFilters = new SearchFilters();
    }

    private SimpleDateFormat getDateFormatForDueDate() {
        return new SimpleDateFormat(DUE_DATE_FORMAT);
    }
}
