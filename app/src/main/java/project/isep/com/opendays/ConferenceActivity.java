package project.isep.com.opendays;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import project.isep.com.opendays.data.ConfItem;
import project.isep.com.opendays.data.OpenContract;

/**
 * Created by Tharanipathy PC on 11-11-2015.
 */
public class ConferenceActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private static final int CONFERENCE_LOADER = 1;

    private ArrayList<ConfItem> items = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView textView;
    private MyAdapter adapter;

    private LoaderCallbacks<Cursor> mCallback;

    private static final String[] PROJECTION = {
            OpenContract.ConferenceEntry.TABLE_NAME + "." + OpenContract.ConferenceEntry._ID,
            OpenContract.ConferenceEntry.TABLE_NAME + "." + OpenContract.ConferenceEntry.COLUMN_CONF_TITLE
    };

    public static final int COL_ID = 0;
    public static final int COL_TITLE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference);
        textView = (TextView)findViewById(R.id.empty_view);
        recyclerView = (RecyclerView)findViewById(R.id.conf_list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MyAdapter(this, items);
        recyclerView.setAdapter(adapter);

        mCallback = this;

        getSupportLoaderManager().initLoader(CONFERENCE_LOADER, null, mCallback);

    }

    private void setVisible(){
        if (items.size() == 0){
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = OpenContract.ConferenceEntry.CONTENT_URI;
        return new CursorLoader(this, uri, PROJECTION, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        while(data.moveToNext()){
            String title = data.getString(COL_TITLE);
            ConfItem item = new ConfItem(title);
            items.add(item);
        }
        setVisible();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
