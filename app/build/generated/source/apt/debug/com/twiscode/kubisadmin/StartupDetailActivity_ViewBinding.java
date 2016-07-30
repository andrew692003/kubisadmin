// Generated code from Butter Knife. Do not modify!
package com.twiscode.kubisadmin;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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

    this.target = null;
  }
}
