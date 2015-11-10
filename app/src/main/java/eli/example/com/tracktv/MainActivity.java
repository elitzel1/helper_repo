package eli.example.com.tracktv;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import eli.example.com.tracktv.entities.Season;
import eli.example.com.tracktv.utils.Utility;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener {

    /**To change the name of the serie, go to the class Constants**/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onFragmentInteraction(Season season) {
        Intent i = new Intent(this,SeasonDetailActivity.class);
        i.putExtra(Utility.TAG_EXTRAS,season);
        startActivity(i);
    }
}
