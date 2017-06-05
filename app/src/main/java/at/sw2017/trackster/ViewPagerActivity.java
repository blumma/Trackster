package at.sw2017.trackster;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Vector;

import at.sw2017.trackster.api.ApiClient;
import at.sw2017.trackster.api.ApiInterface;
import at.sw2017.trackster.models.Student;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Patrick on 26.05.2017.
 */

public class ViewPagerActivity extends Activity {

    private Context mContext;
    private ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_ranking);
        ListView listview1 = new ListView(mContext);
        ListView listview2 = new ListView(mContext);
        ListView listview3 = new ListView(mContext);
        ListView listview4 = new ListView(mContext);
        ListView listview5 = new ListView(mContext);

        Vector<View> pages = new Vector<View>();

        pages.add(listview1);
        pages.add(listview2);
        pages.add(listview3);
        pages.add(listview4);
        pages.add(listview5);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        CustomPagerAdapter adapter = new CustomPagerAdapter(mContext, pages);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(2);

        getStudentList(listview1, listview2, listview3, listview4, listview5);
    }

    private void getStudentList(final ListView listview1, final ListView listview2, final ListView listview3,
                                final ListView listview4, final ListView listview5) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<List<Student>> call = apiService.getStudents();
        call.enqueue(new Callback<List<Student>>() {


            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful()) {

                    List<Student> students = response.body();
                    populateRanking(students, listview1, listview2, listview3, listview4, listview5);

                } else {
                    switch (response.code()) {
                        case 401:
                            Toast.makeText(getApplication(), R.string.not_logged_in, Toast.LENGTH_SHORT).show();
                            Intent k = new Intent(ViewPagerActivity.this, LoginActivity.class);
                            startActivity(k);
                            break;
                        case 500:
                        default:
                            Toast.makeText(getApplication(), R.string.error_student_list, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
            }
        });

    }

    public void populateRanking(final List<Student> students, final ListView listview1, final ListView listview2,
                                final ListView listview3, final ListView listview4, final ListView listview5) {
        View header = (View) getLayoutInflater().inflate(R.layout.list_view_header, null);
        listview1.addHeaderView(header);

        listview2.addHeaderView(header);
        listview3.addHeaderView(header);
        listview4.addHeaderView(header);
        listview5.addHeaderView(header);

        ListAdapter cust_adapter = new RankingAdapter(mContext, students, 0);
        listview1.setAdapter(cust_adapter);
        cust_adapter = new RankingAdapter(mContext, students, 1);
        listview2.setAdapter(cust_adapter);
        cust_adapter = new RankingAdapter(mContext, students, 2);
        listview3.setAdapter(cust_adapter);
        cust_adapter = new RankingAdapter(mContext, students, 3);
        listview4.setAdapter(cust_adapter);
        cust_adapter = new RankingAdapter(mContext, students, 4);
        listview5.setAdapter(cust_adapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                ListAdapter cust_adapter;
                TextView textView = (TextView) findViewById(R.id.txtHeader);
                switch (position)
                {
                    case 0:

                        textView.setText(R.string.ranking_title);
                        break;

                    case 1:

                        textView.setText("60 Meter");
                        break;

                    case 2:
                        textView.setText("1000 Meter");
                        break;

                    case 3:
                        textView.setText("Weitsprung");
                        break;
                    case 4:
                        textView.setText("Schlagball");
                        break;

                    default:
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}



