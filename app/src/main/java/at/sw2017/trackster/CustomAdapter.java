package at.sw2017.trackster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import at.sw2017.trackster.models.Student;

/**
 * Created by MichaelH on 31.05.2017.
 */


public class CustomAdapter extends ArrayAdapter<Student> implements View.OnClickListener {

    private List<Student> dataSet;
    private List<Student> dataSetOriginal;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtVorname;
        TextView txtNachname;
        TextView txtid;
    }

    public CustomAdapter(List<Student> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.dataSetOriginal = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Student student=(Student)object;

        switch (v.getId())
        {

        }
    }

    public List<Student> getDataSet() {
        return dataSet;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        //Student dataModel = dataSet.getItem(position);
        /*if(position>dataSet.size())
            return null;*/

        Student dataModel = new Student();

        if(position < dataSet.size())
            dataModel = dataSet.get(position);

        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtVorname = (TextView) convertView.findViewById(R.id.firstname);
            viewHolder.txtNachname = (TextView) convertView.findViewById(R.id.surname);
            viewHolder.txtid = (TextView) convertView.findViewById(R.id.id_number);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

       /* Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;*/

        if(position < dataSet.size()) {
            viewHolder.txtVorname.setText(dataModel.getVorname());
            viewHolder.txtNachname.setText(dataModel.getNachname());
        }
        else{
            viewHolder.txtVorname.setText("");
            viewHolder.txtNachname.setText("");
        }
       // viewHolder.txtid.setText(dataModel.getId());

        // Return the completed view to render on screen
        return convertView;

    }

    public Filter getFilter() {
        return new Filter() {
            ArrayList<Student> results;
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults oReturn = new FilterResults();
                results = new ArrayList<Student>();
                results.clear();

                if(constraint == null)
                    dataSet = dataSetOriginal;

                if (constraint != null) {
                    if (dataSetOriginal != null && dataSetOriginal.size() > 0) {
                        for (final Student g : dataSetOriginal) {
                            String s = g.getVorname();
                            if (g.getVorname().toLowerCase()
                                    .contains(constraint.toString().toLowerCase()) || g.getNachname().toLowerCase().contains(constraint.toString().toLowerCase()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                    oReturn.count = results.size();
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                dataSet = (ArrayList<Student>) results.values;
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }

}
