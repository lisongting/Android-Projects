package cn.ssdut.lst.supportdesignwidget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    NavigationView navigationView;
    FloatingActionButton fab;
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = (NavigationView) findViewById(R.id.navi_view);
        fab = (FloatingActionButton) findViewById(R.id.id_fab);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navi_gallery) {
                    Toast.makeText(MainActivity.this, "selected gallery item", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.navi_contact) {
                    Toast.makeText(MainActivity.this, "selected contact item", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.navi_slideshow) {
                    Toast.makeText(MainActivity.this, "selected slideshow item", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.navi_share) {
                    Toast.makeText(MainActivity.this, "selected share item", Toast.LENGTH_SHORT).show();
                }
                DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Clicked FloatingActionButton", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void createSnackbar(View v) {
        //snackbar是一个类似Toast一样可以短暂停留的提示组件。可以向右滑动让其消失（前提是要在CoordinatorLayout中）
        //make的第一个参数是一个父view，snackbar尝试依附在一个父view上
        Snackbar.make(fab, "这是一个 SnackBar.", Snackbar.LENGTH_LONG)
                .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "点击了确定按钮", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }


    public void showCoordinatorActivity(View view) {
        startActivity(new Intent(this, CoordinatorActivity.class));

    }
}
