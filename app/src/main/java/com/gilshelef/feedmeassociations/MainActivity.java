package com.gilshelef.feedmeassociations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

// this screen comes after registration - assuming associationId provided

public class MainActivity extends AppCompatActivity implements DonationsListFragment.OnItemClicked, DonationsListFragment.OnItemLongClicked {

    private ImageView heartImageView;
    private View circleBackground;
    private TextView guideTextView;

    private static final DecelerateInterpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set heart animation
        heartImageView = (ImageView) findViewById(R.id.heart);
        circleBackground = findViewById(R.id.circleBg);
        guideTextView = (TextView) findViewById(R.id.guideView);

        // set toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getTitle(0));
        setSupportActionBar(toolbar);

        // set tabs
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.list_icon));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.map_icon));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.heart_icon));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // set viewPager and pageAdapter
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.lightPrimaryColor);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                toolbar.setTitle(getTitle(tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(getApplicationContext(), R.color.iconsUnselected);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    private int getTitle(int position) {
        switch (position){
            case 0:
                return R.string.donations_tab;
            case 1:
                return R.string.map_tab;
            case 2:
                return R.string.shopping_cart_tab;
            default:
                return R.string.app_name;

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.association_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                Toast.makeText(getApplicationContext(),"Filter Selected",Toast.LENGTH_LONG).show();
                return true;
            case R.id.item:
                Toast.makeText(getApplicationContext(),"Item Selected",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClicked(Donation selected) {
        Intent detailIntent = new Intent(getApplicationContext(), DonationDetailActivity.class);
        detailIntent.putExtra(DonationDetailActivity.type, selected.getType());
        detailIntent.putExtra(DonationDetailActivity.description, selected.getDescription());
        detailIntent.putExtra(DonationDetailActivity.imageUrl, selected.getImageUrl());
        detailIntent.putExtra(DonationDetailActivity.phone, selected.getPhone());
        detailIntent.putExtra(DonationDetailActivity.firstName, selected.getFirstName());
        detailIntent.putExtra(DonationDetailActivity.lastName, selected.getLastName());
        detailIntent.putExtra(DonationDetailActivity.defaultImage, selected.getDefaultImage());
        detailIntent.putExtra(DonationDetailActivity.date, selected.getDate());
        detailIntent.putExtra(DonationDetailActivity.isAvailable, selected.isAvailable());
        startActivity(detailIntent);
    }

    @Override
    public void onItemLongClicked(Donation clicked) {
        // TODO take donation
        heart();
    }
    private void heart() {
        guideTextView.setVisibility(View.INVISIBLE);
        circleBackground.setVisibility(View.VISIBLE);
        heartImageView.setVisibility(View.VISIBLE);

        circleBackground.setScaleY(0.1f);
        circleBackground.setScaleX(0.1f);
        circleBackground.setAlpha(1f);
        heartImageView.setScaleY(0.1f);
        heartImageView.setScaleX(0.1f);

        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator bgScaleYAnim = ObjectAnimator.ofFloat(circleBackground, "scaleY", 0.1f, 1f);
        bgScaleYAnim.setDuration(200);
        bgScaleYAnim.setInterpolator(DECELERATE_INTERPOLATOR);
        ObjectAnimator bgScaleXAnim = ObjectAnimator.ofFloat(circleBackground, "scaleX", 0.1f, 1f);
        bgScaleXAnim.setDuration(200);
        bgScaleXAnim.setInterpolator(DECELERATE_INTERPOLATOR);
        ObjectAnimator bgAlphaAnim = ObjectAnimator.ofFloat(circleBackground, "alpha", 1f, 0f);
        bgAlphaAnim.setDuration(200);
        bgAlphaAnim.setStartDelay(150);
        bgAlphaAnim.setInterpolator(DECELERATE_INTERPOLATOR);

        ObjectAnimator imgScaleUpYAnim = ObjectAnimator.ofFloat(heartImageView, "scaleY", 0.1f, 1f);
        imgScaleUpYAnim.setDuration(300);
        imgScaleUpYAnim.setInterpolator(DECELERATE_INTERPOLATOR);
        ObjectAnimator imgScaleUpXAnim = ObjectAnimator.ofFloat(heartImageView, "scaleX", 0.1f, 1f);
        imgScaleUpXAnim.setDuration(300);
        imgScaleUpXAnim.setInterpolator(DECELERATE_INTERPOLATOR);

        ObjectAnimator imgScaleDownYAnim = ObjectAnimator.ofFloat(heartImageView, "scaleY", 1f, 0f);
        imgScaleDownYAnim.setDuration(300);
        imgScaleDownYAnim.setInterpolator(ACCELERATE_INTERPOLATOR);
        ObjectAnimator imgScaleDownXAnim = ObjectAnimator.ofFloat(heartImageView, "scaleX", 1f, 0f);
        imgScaleDownXAnim.setDuration(300);
        imgScaleDownXAnim.setInterpolator(ACCELERATE_INTERPOLATOR);

        animatorSet.playTogether(bgScaleYAnim, bgScaleXAnim, bgAlphaAnim, imgScaleUpYAnim, imgScaleUpXAnim);
        animatorSet.play(imgScaleDownYAnim).with(imgScaleDownXAnim).after(imgScaleUpYAnim);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                reset();
            }
        });
        animatorSet.start();
    }

    public void reset() {
        circleBackground.setVisibility(View.GONE);
        heartImageView.setVisibility(View.GONE);
        guideTextView.setVisibility(View.VISIBLE);
    }


}
