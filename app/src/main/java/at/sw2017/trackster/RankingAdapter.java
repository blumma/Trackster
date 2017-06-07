package at.sw2017.trackster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import at.sw2017.trackster.models.CalculateScore;
import at.sw2017.trackster.models.Student;

public class RankingAdapter extends BaseAdapter {

    private Student[] ranks;
    private final int size;

    private DecimalFormat df = new DecimalFormat("0.00");

    public class ViewHolder {
        private TextView userName;
        private TextView data;
    }

    Context context;
    LayoutInflater inflater;
    int page_nr;

    public RankingAdapter(Context applicationContext, List<Student> students, int page_nr) {
        this.context = applicationContext;
        this.size = students.size();
        List<Student> myStundents = new ArrayList<>(students);
        this.page_nr = page_nr;
        inflater = (LayoutInflater.from(applicationContext));


        switch (page_nr) {
            case 0:
                Collections.sort(myStundents, new Comparator<Student>() {
                    @Override
                    public int compare(Student s1, Student s2) {
                        return -1 * Double.compare(s1.getOverallScore(), s2.getOverallScore());
                    }
                });
                break;
            case 1:
                Collections.sort(myStundents, new Comparator<Student>() {
                    @Override
                    public int compare(Student s1, Student s2) {
                        return Double.compare(s1.getPerformance60mRun(), s2.getPerformance60mRun());
                    }
                });
                break;
            case 2:
                Collections.sort(myStundents, new Comparator<Student>() {
                    @Override
                    public int compare(Student s1, Student s2) {
                        return Double.compare(s1.getPerformance1000mRun(), s2.getPerformance1000mRun());
                    }
                });
                break;
            case 3:
                Collections.sort(myStundents, new Comparator<Student>() {
                    @Override
                    public int compare(Student s1, Student s2) {
                        return -1 * Double.compare(s1.getPerformanceLongJump(), s2.getPerformanceLongJump());
                    }
                });
                break;
            case 4:
                Collections.sort(myStundents, new Comparator<Student>() {
                    @Override
                    public int compare(Student s1, Student s2) {
                        return -1 * Double.compare(s1.getPerformanceLongThrow(), s2.getPerformanceLongThrow());
                    }
                });
                break;
            default:
                break;
        }

        ranks = myStundents.toArray(new Student[myStundents.size()]);
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder mViewHolder = null;

        if (view == null) {

            mViewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.activity_listview, null);

            mViewHolder.userName = (TextView) view.findViewById(R.id.tv);
            mViewHolder.data = (TextView) view.findViewById(R.id.data);

            view.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) view.getTag();
        }

        switch (page_nr) {
            case 0:
                mViewHolder.data.setText(df.format(ranks[position].getOverallScore()));
                break;
            case 1:
                mViewHolder.data.setText(df.format(ranks[position].getPerformance60mRun()));
                break;
            case 2:
                mViewHolder.data.setText(df.format(ranks[position].getPerformance1000mRun()));
                break;
            case 3:
                mViewHolder.data.setText(df.format(ranks[position].getPerformanceLongJump()));
                break;
            case 4:
                mViewHolder.data.setText(df.format(ranks[position].getPerformanceLongThrow()));
                break;
            default:
                break;
        }
        mViewHolder.userName.setText(ranks[position].getVorname() + " " + ranks[position].getNachname());
        return view;
    }
}


