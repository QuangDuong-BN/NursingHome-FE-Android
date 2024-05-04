package com.example.nursinghome_android.usersubactivities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Guideline;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.nursinghome_android.R;

public class ChooseHealtyOrMealPlan extends AppCompatActivity {

    Button button1,button2 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_healty_or_meal_plan);

        Toolbar toolbar = findViewById(R.id.toolbarHealtyOrMealPlan);
        setSupportActionBar(toolbar);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back);
            actionBar.setHomeAsUpIndicator(upArrow);

        }

        button1 = findViewById(R.id.btnViewDetail1);
        button2 = findViewById(R.id.btnViewDetail2);

        button1.setOnClickListener(v -> {
            Intent intent = new Intent(ChooseHealtyOrMealPlan.this, Healthactivity.class);
            startActivity(intent);
        });

        button2.setOnClickListener(v -> {
            Intent intent = new Intent(ChooseHealtyOrMealPlan.this, MealPlanActivity.class);
            startActivity(intent);
        });

        ImageView imageView1 = findViewById(R.id.imageHealtyRecord);
        Glide.with(this)
                .load("https://res.cloudinary.com/djq4zsauv/image/upload/v1714812599/tvszykb1oylsys6h0cdo.png")
                .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(12)))
                .into(imageView1);

        ImageView imageView2 = findViewById(R.id.imageMealPlan);
        Glide.with(this)
                .load("https://res.cloudinary.com/djq4zsauv/image/upload/v1714812381/pfjukajvnfz9levufnl6.png")
                .apply(new RequestOptions().transform(new CenterCrop(), new RoundedCorners(12)))
                .into(imageView2);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
