// Generated code from Butter Knife. Do not modify!
package com.twiscode.kubisadmin;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class ProfileUserActivity_ViewBinding<T extends ProfileUserActivity> implements Unbinder {
  protected T target;

  public ProfileUserActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.textDescription = finder.findRequiredViewAsType(source, R.id.iniDeskripsi, "field 'textDescription'", TextView.class);
    target.tabLayout = finder.findRequiredViewAsType(source, R.id.tab_layout, "field 'tabLayout'", TabLayout.class);
    target.viewPager = finder.findRequiredViewAsType(source, R.id.tab_viewpager, "field 'viewPager'", ViewPager.class);
    target.textNama = finder.findRequiredViewAsType(source, R.id.namaJudul, "field 'textNama'", TextView.class);
    target.imageProfile = finder.findRequiredViewAsType(source, R.id.civProfilePicture, "field 'imageProfile'", ImageView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.textDescription = null;
    target.tabLayout = null;
    target.viewPager = null;
    target.textNama = null;
    target.imageProfile = null;

    this.target = null;
  }
}
