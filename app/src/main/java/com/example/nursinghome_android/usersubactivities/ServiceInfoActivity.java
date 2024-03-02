package com.example.nursinghome_android.usersubactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nursinghome_android.ListViewSetUp.DropdownAdapter;
import com.example.nursinghome_android.R;
import com.example.nursinghome_android.mainactivity.UserActivity;

import java.util.Arrays;
import java.util.List;

public class ServiceInfoActivity extends AppCompatActivity implements DropdownAdapter.OnClickListener {
    ImageButton buttonBack;
    private ListView mDropdownListView;
    private DropdownAdapter mAdapter;
    private List<String> mDropdownItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_info);

        buttonBack = findViewById(R.id.imageViewBack);
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(ServiceInfoActivity.this, UserActivity.class);
            startActivity(intent);
            finish();
        });

        mDropdownItems = Arrays.asList("Item 1", "Item 2", "Item 3", "Item 4");

        mDropdownListView = findViewById(R.id.listViewDropdown);
        mAdapter = new DropdownAdapter(this, mDropdownItems, this);
        mDropdownListView.setAdapter(mAdapter);

    }

    @Override
    public void onItemClick(String item) {
        // Xử lý sự kiện khi một mục được nhấn
//        Toast.makeText(this, "Clicked on item: " + item, Toast.LENGTH_SHORT).show();
    }
}