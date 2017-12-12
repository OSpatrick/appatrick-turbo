package com.example.patrick.setremindme;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;

/**
 * Created by Patrick on 12/4/2017.
 */

public class ExpandableListAdapters extends BaseExpandableListAdapter {

        private int i = 0;
        private Context _context;
        private List<String> _listDataHeader = new ArrayList<>(); // header titles
        // child data in format of header title, child title
        private HashMap<String, List<String>> _listDataChild;

        public ExpandableListAdapters(Context context, List<String> listDataHeader,
                                     HashMap<String, List<String>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }

        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            if(!(_listDataChild.get(_listDataHeader.get(groupPosition)) == null)) {
                return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                        .get(childPosititon);
            }
            else
                return null;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            String childText = "hello";
            childText = (String) getChild(groupPosition, childPosition);

            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.child_layout, null);
            }

            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.textViewChild);

            txtListChild.setText(childText);
            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            if(!(_listDataChild.get(_listDataHeader.get(groupPosition)) == null)) {
                return i = _listDataChild.get(_listDataHeader.get(groupPosition)).size();
            }
            else {
                return i;
            }
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        /*
        @param int groupPosition integer that state where group position is
        @param boolean isExpanded gets the state of the group, if it is expanded or not
        @param View convertView will convert the view of the view of the group
         */
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.parent_layout, null);
            }

            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.textViewParent);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);

            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }