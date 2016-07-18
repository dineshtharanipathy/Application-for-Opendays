package project.isep.com.opendays;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Tharanipathy PC on 11-11-2015.
 */
public class StartingActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 4;

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        TextView conference = (TextView)findViewById(R.id.conference);
        conference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartingActivity.this, ConferenceActivity.class));
            }
        });

        TextView about = (TextView)findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartingActivity.this, AboutActvity.class));
            }
        });

        TextView web = (TextView)findViewById(R.id.web);
        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        TextView news = (TextView)findViewById(R.id.news);
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogNews();
                //startActivity(new Intent(StartingActivity.this, NewsFeedActivity.class));
            }
        });

        /*TextView tweets = (TextView)findViewById(R.id.tweets);
        tweets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //Log.d(TAG, "item position: " + position);
            return ScreenSlidePageFragment.create(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    private void showDialog(){
        String[] items = {"ISEP", "STUDENTS", "MAPS"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Website");
        builder.setCancelable(true);
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent intent = new Intent(StartingActivity.this, WebsiteActivity.class);
                        intent.putExtra("website", Config.ISEP_WEBSITE);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(StartingActivity.this, WebsiteActivity.class);
                        intent1.putExtra("website", Config.STUDENTS_WEBSITE);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(StartingActivity.this, WebsiteActivity.class);
                        intent2.putExtra("website", Config.MAPS_WEBSITE);
                        startActivity(intent2);
                        break;
                }
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    private void showDialogNews(){
        String[] items = {"Tweets", "General News"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose News");
        builder.setCancelable(true);
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent intent = new Intent(StartingActivity.this, WebsiteActivity.class);
                        intent.putExtra("website", Config.TWITTER_WEBSITE);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(StartingActivity.this, WebsiteActivity.class);
                        intent1.putExtra("website", Config.NEWS_WEBSITE);
                        startActivity(intent1);
                        break;
                }
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }
}
