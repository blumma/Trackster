package at.sw2017.trackster;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.common.collect.Iterables;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.TreeMultimap;

import java.util.List;

import at.sw2017.trackster.models.Student;

public class RankingAdapter extends BaseAdapter {


    public class ViewHolder
    {
        private TextView userName;
        private TextView data;
    }

    Context context;
    List<Student> students;
    LayoutInflater inflater;
    int page_nr;

    public RankingAdapter(Context applicationContext, List<Student> students, int page_nr) {
        this.context = applicationContext;
        this.students = students;
        this.page_nr = page_nr;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return students.size();
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

    }
    else
    {
        mViewHolder = (ViewHolder)view.getTag();
    }

        SortedSetMultimap<Double,String> myTreeMultimap = TreeMultimap.create();

        switch (page_nr)
        {
            case 0:
                for (Student s : students) {
                    myTreeMultimap.put(s.getPerformance60mRun(), s.getVorname() + " " + s.getNachname());
                }
                System.out.println(myTreeMultimap);
                break;

            case 1:
                for (Student s : students) {
                    myTreeMultimap.put(s.getPerformance1000mRun(), s.getVorname() + " " + s.getNachname());
                }

                break;
            case 2:
                for (Student s : students) {
                    myTreeMultimap.put(s.getPerformanceLongJump(), s.getVorname() + " " + s.getNachname());
                }

                break;

            default:
                break;
        }

        mViewHolder.data.setText(Iterables.get(myTreeMultimap.keys(), position).toString());
        mViewHolder.userName.setText(Iterables.get(myTreeMultimap.values(), position));

        return view;

    }
}


