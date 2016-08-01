// Generated code from Butter Knife. Do not modify!
package com.twiscode.kubisadmin;

import android.widget.ListView;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class HomeFragment_ViewBinding<T extends HomeFragment> implements Unbinder {
  protected T target;

  public HomeFragment_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.submissionView = finder.findRequiredViewAsType(source, R.id.submissionListView, "field 'submissionView'", ListView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.submissionView = null;

    this.target = null;
  }
}
