// Generated code from Butter Knife. Do not modify!
package com.twiscode.kubisadmin;

import butterknife.Unbinder;
import butterknife.internal.Finder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class StartupMediaFragment_ViewBinding<T extends StartupMediaFragment> implements Unbinder {
  protected T target;

  public StartupMediaFragment_ViewBinding(T target, Finder finder, Object source) {
    this.target = target;

    target.gridFounder = finder.findRequiredViewAsType(source, R.id.gridFounder, "field 'gridFounder'", ExpandableHeightGridView.class);
    target.gridUpvotes = finder.findRequiredViewAsType(source, R.id.gridUpvotes, "field 'gridUpvotes'", ExpandableHeightGridView.class);
  }

  @Override
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.gridFounder = null;
    target.gridUpvotes = null;

    this.target = null;
  }
}
