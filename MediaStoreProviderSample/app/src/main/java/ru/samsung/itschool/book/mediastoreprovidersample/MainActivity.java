package ru.samsung.itschool.book.mediastoreprovidersample;

import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.imageList);

        adapter = new SimpleCursorAdapter(this, R.layout.my_item, null,
                new String[] {
                        MediaStore.Images.Thumbnails.DATA,
                        MediaStore.Images.Thumbnails.DATA
                },
                new int[] {
                        R.id.imageView,
                        R.id.textView
                }, 0);
        listView.setAdapter(adapter);

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
                new String[] {
                        MediaStore.Images.Thumbnails._ID,
                        MediaStore.Images.Thumbnails.DATA
                }, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
