/**
 * Author		:	VenomVendor
 * Dated		:	6 Dec, 2013 1:19:40 AM, IST.
 * Project		:	SimpleListView-CheckBox
 * Contact		:	info@VenomVendor.com
 * URL			:	https://www.google.co.in/search?q=VenomVendor
 * Copyright(c)	:	WTF.!
 **/

package vee.simplelistview.checkbox;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private ListView mListView;
    private static int count = 0;
    private static boolean isNotAdded = true;
    private CheckBox checkBox_header;
    final CustomAdapter adapter = new CustomAdapter(this);
    final static String[] textviewContent = {
            "Content1", "Content2", "Content3", "Content4", "Content5", "Content6",
            "Content7", "Content8", "Content9", "Content10", "Content11",
            "Content12", "Content13", "Content14", "Content15", "Content16",
    };

    /**
     * To save checked items, and <b>re-add</b> while scrolling.
     */
    SparseBooleanArray mChecked = new SparseBooleanArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.list_view);

        /*
         * To avoid adding multiple times
         */
        if (isNotAdded) {

            /*
             * mListView (ListView) //DO NOT ADD `NULL` here.
             */
            final View headerView = getLayoutInflater().inflate(R.layout.custom_list_view_header,
                    mListView, false);

            checkBox_header = (CheckBox) headerView.findViewById(
                    R.id.checkBox_header);

            /*
             * Select All / None DO NOT USE "setOnCheckedChangeListener" here.
             */
            checkBox_header.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    /*
                     * Set all the checkbox to True/False
                     */
                    for (int i = 0; i < count; i++) {
                        mChecked.put(i, checkBox_header.isChecked());
                    }

                    /*
                     * Update View
                     */
                    adapter.notifyDataSetChanged();

                }
            });

            /*
             * Add Header to ListView
             */
            mListView.addHeaderView(headerView);

            isNotAdded = false;
        }

        /*
         * Set Adapter After Adding Header
         */
        mListView.setAdapter(adapter);

        /*
         * Set "OnItemClickListener" after adding Adapter
         */
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /*
                 * Use "if else" only if header is added
                 */
                if (position == 0) {
                    Toast.makeText(getApplicationContext(),
                            checkBox_header.getId() + "\n" + checkBox_header.isChecked(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    position = position - 1; // "-1" If Header is Added
                    Toast.makeText(getApplicationContext(),
                            textviewContent[position] + "\n" + mChecked.get(position),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    /*
     * CustomAdapter
     */
    public class CustomAdapter extends BaseAdapter {

        Activity sActivity;

        public CustomAdapter(final Activity mActivity) {
            this.sActivity = mActivity;
        }

        @Override
        public int getCount() {

            /*
             * Length of our listView
             */
            count = MainActivity.textviewContent.length;
            return count;
        }

        @Override
        public Object getItem(int position) {

            /*
             * Current Item
             */
            return position;
        }

        @Override
        public long getItemId(int position) {

            /*
             * Current Item's ID
             */
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View mView = convertView;

            if (mView == null) {

                /*
                 * LayoutInflater
                 */
                final LayoutInflater sInflater = (LayoutInflater) sActivity.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);

                /*
                 * Inflate Custom List View
                 */
                mView = sInflater.inflate(R.layout.custom_list_view, null, false);

            }

            /* **************CUSTOM LISTVIEW OBJECTS**************** */

            /*
             * DO NOT MISS TO ADD "mView"
             */
            final TextView sTV1 = (TextView) mView.findViewById(R.id.textView);
            final ImageView sIMG = (ImageView) mView.findViewById(R.id.imageView);
            final CheckBox mCheckBox = (CheckBox) mView.findViewById(
                    R.id.checkBox);

            /* **************CUSTOM LISTVIEW OBJECTS**************** */

            /* **************ADDING CONTENTS**************** */
            sTV1.setText(MainActivity.textviewContent[position]);
            sIMG.setImageResource(R.drawable.logo);

            mCheckBox.setOnCheckedChangeListener(
                    new OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked) {

                                /*
                                 * Saving Checked Position
                                 */
                                mChecked.put(position, isChecked);

                                /*
                                 * Find if all the check boxes are true
                                 */
                                if (isAllValuesChecked()) {

                                    /*
                                     * set HeaderCheck box to true
                                     */
                                    checkBox_header.setChecked(isChecked);
                                }

                            } else {

                                /*
                                 * Removed UnChecked Position
                                 */
                                mChecked.delete(position);

                                /*
                                 * Remove Checked in Header
                                 */
                                checkBox_header.setChecked(isChecked);

                            }

                        }
                    });

            /*
             * Set CheckBox "TRUE" or "FALSE" if mChecked == true
             */
            mCheckBox.setChecked((mChecked.get(position) == true ? true : false));

            /* **************ADDING CONTENTS**************** */

            /*
             * Return View here
             */
            return mView;
        }

        /*
         * Find if all values are checked.
         */
        protected boolean isAllValuesChecked() {

            for (int i = 0; i < count; i++) {
                if (!mChecked.get(i)) {
                    return false;
                }
            }

            return true;
        }

    }
}
