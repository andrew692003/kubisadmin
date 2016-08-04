// Generated code from Butter Knife. Do not modify!
package com.twiscode.kubisadmin;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class SuggestionDetailActivity_ViewBinding<T extends SuggestionDetailActivity> implements Unbinder {
  protected T target;

  public SuggestionDetailActivity_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.btnAcc = finder.findRequiredViewAsType(source, R.id.accept, "field 'btnAcc'", FloatingActionButton.class);
    target.btnDec = finder.findRequiredViewAsType(source, R.id.decline, "field 'btnDec'", FloatingActionButton.class);
    target.viewPager = finder.findRequiredViewAsType(source, R.id.tab_viewpager, "field 'viewPager'", ViewPager.class);
    target.nameSuggest = finder.findRequiredViewAsType(source, R.id.nameSuggest, "field 'nameSuggest'", TextView.class);
    target.imageSuggest = finder.findRequiredViewAsType(source, R.id.imageSuggest, "field 'imageSuggest'", ImageView.class);
    target.gotoprof = finder.findRequiredViewAsType(source, R.id.gotoProf, "field 'gotoprof'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.btnAcc = null;
    target.btnDec = null;
    target.viewPager = null;
    target.nameSuggest = null;
    target.imageSuggest = null;
    target.gotoprof = null;

    this.target = null;
  }
}
