package com.example.tvlaunchertoukir.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.example.tvlaunchertoukir.R;

import java.util.ArrayList;
import java.util.List;

public class FunctionModel {

    private String title;
    private Drawable icon;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public ArrayList<FunctionModel> getFunctionsList(Context context){

        FunctionModel functionModel = new FunctionModel();
        functionModel.setTitle("Application Uninstall");
        functionModel.setIcon(context.getResources().getDrawable(R.drawable.uninstall));

        ArrayList<FunctionModel> list = new ArrayList<>();
        list.add(functionModel);

        return list;
    }
}
