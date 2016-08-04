// Generated code from Butter Knife. Do not modify!
package com.twiscode.kubisadmin;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class StartupMediaFragment_ViewBinding<T extends StartupMediaFragment> implements Unbinder {
  protected T target;

  public StartupMediaFragment_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.rvMedia = finder.findRequiredViewAsType(source, R.id.rvMedia, "field 'rvMedia'", RecyclerView.class);
    target.tvNoData = finder.findRequiredViewAsType(source, R.id.tvNoData, "field 'tvNoData'", TextView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.rvMedia = null;
    target.tvNoData = null;

    this.target = null;
  }
}
