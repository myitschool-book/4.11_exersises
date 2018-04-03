package ru.samsung.itschool.book.mediastoreprovidersample;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    ListView listView;

    private SimpleCursorAdapter adapter;

    final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.imageList);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            showThumbnails();
        }
    }

    void showThumbnails() {
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showThumbnails();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the thumbnails", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
