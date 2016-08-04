// Generated code from Butter Knife. Do not modify!
package com.twiscode.kubisadmin;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class StartupDetailActivity_ViewBinding<T extends StartupDetailActivity> implements Unbinder {
  protected T target;

  public StartupDetailActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.textDescription = finder.findRequiredViewAsType(source, R.id.text_description, "field 'textDescription'", TextView.class);
    target.tabLayout = finder.findRequiredViewAsType(source, R.id.tab_layout, "field 'tabLayout'", TabLayout.class);
    target.viewPager = finder.findRequiredViewAsType(source, R.id.tab_viewpager, "field 'viewPager'", ViewPager.class);
    target.btnAcc = finder.findRequiredViewAsType(source, R.id.accept, "field 'btnAcc'", FloatingActionButton.class);
    target.btnDec = finder.findRequiredViewAsType(source, R.id.decline, "field 'btnDec'", FloatingActionButton.class);
    target.btnUpvote = finder.findRequiredViewAsType(source, R.id.btnUpvote, "field 'btnUpvote'", Button.class);
    target.btnGet = finder.findRequiredViewAsType(source, R.id.btnGet, "field 'btnGet'", Button.class);
    target.btnDiscuss = finder.findRequiredViewAsType(source, R.id.btnDiscuss, "field 'btnDiscuss'", Button.class);
    target.mainImage = finder.findRequiredViewAsType(source, R.id.main_image, "field 'mainImage'", ImageView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.textDescription = null;
    target.tabLayout = null;
    target.viewPager = null;
    target.btnAcc = null;
    target.btnDec = null;
    target.btnUpvote = null;
    target.btnGet = null;
    target.btnDiscuss = null;
    target.mainImage = null;

    this.target = null;
  }
}
